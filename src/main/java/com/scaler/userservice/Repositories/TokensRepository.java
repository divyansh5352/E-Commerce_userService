package com.scaler.userservice.Repositories;

import com.scaler.userservice.models.Tokens;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokensRepository extends JpaRepository<Tokens, Long> {
    Tokens save( Tokens token);

    Optional<Tokens>findByValueAndDeleted(String token , boolean isDeleted );

    Optional<Tokens>findByValueAndDeletedAndExpiryAtGreaterThan(String Value , boolean Deleted , Date expiryGreaterThan );

}
