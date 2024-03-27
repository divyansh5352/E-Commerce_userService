package com.scaler.userservice.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDTO {

    private String email;
    private String password;
    private String name;
}
