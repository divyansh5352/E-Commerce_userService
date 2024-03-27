package com.scaler.userservice.Security.Services;

import com.scaler.userservice.Repositories.UserRepository;
import com.scaler.userservice.Security.Models.CustomeUserDetails;
import com.scaler.userservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomeUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomeUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);

        if ( userOptional.isEmpty()){
            throw new UsernameNotFoundException("User by username "+username+" not found.");
        }
        CustomeUserDetails userDetails = new CustomeUserDetails(userOptional.get());
        return userDetails;
    }
}
