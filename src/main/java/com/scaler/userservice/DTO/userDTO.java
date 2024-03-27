package com.scaler.userservice.DTO;

import com.scaler.userservice.models.Role;
import com.scaler.userservice.models.User;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
public class userDTO {
    private String name;
    private String email;
    @ManyToMany
    private List<Role> roles;
    private boolean isEmailVerified;
    public static userDTO from(User user){
        if( user==null){
            return null;
        }
        userDTO userDTO = new userDTO();
        userDTO.email = user.getName();
        userDTO.name = user.getName();
        userDTO.roles = user.getRoles();
        userDTO.isEmailVerified = user.isEmailVerified();

        return userDTO;
    }

}
