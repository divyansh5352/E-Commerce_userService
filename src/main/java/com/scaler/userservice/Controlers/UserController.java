package com.scaler.userservice.Controlers;

import com.scaler.userservice.DTO.LoginRequestDTO;
import com.scaler.userservice.DTO.LogoutRequestDTO;
import com.scaler.userservice.DTO.SignupRequestDTO;
import com.scaler.userservice.DTO.userDTO;
import com.scaler.userservice.Exceptions.UserNotFoundException;
import com.scaler.userservice.Repositories.TokensRepository;
import com.scaler.userservice.Services.UserService;
import com.scaler.userservice.models.Tokens;
import com.scaler.userservice.models.User;
import lombok.NonNull;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    // in controller the autowire keyword is optional
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/signup")
    public User signUp( @RequestBody SignupRequestDTO request ){
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();

        return userService.signUp(name,email,password);
    }

    @PostMapping("/login")
    public Tokens login(@RequestBody LoginRequestDTO request ) throws UserNotFoundException {

        return userService.login(request.getEmail(), request.getPassword());

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout( @RequestBody LogoutRequestDTO request ){
        userService.logout(request.getTokenvalue());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/validate/{token}")
    public userDTO validateToken(@PathVariable("token") @NonNull String token ){

        return userService.validateToken(token);

    }

}
