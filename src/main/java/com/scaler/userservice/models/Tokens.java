package com.scaler.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Tokens extends BaseModel{
    private  String value;

    @ManyToOne
    private  User user;
    private Date expiryAt;

}
