package com.scaler.userservice.Security.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;



@JsonDeserialize
public class CustomeGrantedAuthority implements GrantedAuthority {
    //private Role role;
    private String authority;
    public CustomeGrantedAuthority(){}

    public CustomeGrantedAuthority(Role role) {
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
