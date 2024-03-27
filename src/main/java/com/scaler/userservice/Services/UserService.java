package com.scaler.userservice.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.userservice.DTO.sendEmailEventDTO;
import com.scaler.userservice.DTO.userDTO;
import com.scaler.userservice.Exceptions.UserNotFoundException;
import com.scaler.userservice.Repositories.TokensRepository;
import com.scaler.userservice.Repositories.UserRepository;
import com.scaler.userservice.models.Tokens;
import com.scaler.userservice.models.User;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private TokensRepository tokensRepository;
    private KafkaTemplate<String,String>kafkaTemplate;
    private ObjectMapper objectMapper;

    public UserService(ObjectMapper objectMapper,KafkaTemplate<String,String>kafkaTemplate,UserRepository userRepository, BCryptPasswordEncoder passwordEncoder , TokensRepository tokensRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokensRepository = tokensRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }
    public User signUp(String name ,String email , String password ){

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(passwordEncoder.encode(password));

        User savedUser = userRepository.save(user);
        sendEmailEventDTO eventDTO = new sendEmailEventDTO();
        eventDTO.setTo(email);
        eventDTO.setSubject("Welcome to my project");
        eventDTO.setBody("Hope you are doing well , this is a system generated mail using Kafka.");

        try {
            kafkaTemplate.send(
                    "SendEmail",
                    objectMapper.writeValueAsString(eventDTO)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return savedUser;

    }
    public Tokens login(String email , String password ) throws UserNotFoundException {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if ( optionalUser.isEmpty()){
            throw new UserNotFoundException("Invalid user details");
        }

        User user = optionalUser.get();

        if (passwordEncoder.matches(password, user.getHashedPassword()) == false){
            throw  new UserNotFoundException("Invalid user details");
        }

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);

        // convert LocalDate to Date
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Tokens tokens = new Tokens();
        tokens.setUser(user);
        tokens.setExpiryAt(expiryDate);
        tokens.setValue(RandomStringUtils.randomAlphanumeric(128));

        return tokensRepository.save(tokens);
    }
    public void logout(String token ){
        Optional<Tokens>optionalTokens = tokensRepository.findByValueAndDeleted(token,false);

        if( optionalTokens.isEmpty()){
            //throw token already expired token
        }

        Tokens tokens = optionalTokens.get();
        tokens.setDeleted(true);
        tokensRepository.save(tokens);
        return;

    }

    public userDTO validateToken(String token ){

        Optional<Tokens>optionalTokens =
                tokensRepository.findByValueAndDeletedAndExpiryAtGreaterThan
                        (token,false , new Date());

        if (optionalTokens.isEmpty()){
            return null;
        }

        User user = optionalTokens.get().getUser();
        userDTO userDTO = com.scaler.userservice.DTO.userDTO.from(user);
        return userDTO;
    }
}
