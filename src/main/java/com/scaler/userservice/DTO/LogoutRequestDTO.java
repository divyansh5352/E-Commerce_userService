package com.scaler.userservice.DTO;

import com.scaler.userservice.models.Tokens;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequestDTO {
    private String tokenvalue;
}
