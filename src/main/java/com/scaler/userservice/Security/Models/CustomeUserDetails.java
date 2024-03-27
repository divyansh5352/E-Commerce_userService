package com.scaler.userservice.Security.Models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.userservice.models.Role;
import com.scaler.userservice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@JsonDeserialize
public class CustomeUserDetails implements UserDetails {
    //User user;
    private List<CustomeGrantedAuthority>authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Long userId;

    public CustomeUserDetails(){}

    public CustomeUserDetails(User user) {
        //this.user = user;

        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.enabled = true;
        this.credentialsNonExpired = true;
        this.password = user.getHashedPassword();
        this.username = user.getEmail();
        this.userId = user.getId();

        List<CustomeGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : user.getRoles() ){
            grantedAuthorities.add(new CustomeGrantedAuthority(role));
        }

        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<CustomeGrantedAuthority> grantedAuthorities = new ArrayList<>();
//        for (Role role : user.getRoles() ){
//            grantedAuthorities.add(new CustomeGrantedAuthority(role));
//        }
//        return grantedAuthorities;
        return authorities;
    }

    @Override
    public String getPassword() {
        //return user.getHashedPassword();
        return password;
    }

    @Override
    public String getUsername() {
        //return user.getEmail();
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //return true;
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return true;
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return true;
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        //return true;
        return enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
