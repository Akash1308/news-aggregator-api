package com.example.News.Aggregator.API.controller;


import com.example.News.Aggregator.API.dto.RegisterUserRequest;
import com.example.News.Aggregator.API.service.JWTService;
import com.example.News.Aggregator.API.service.UserService;
import com.example.News.Aggregator.API.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User APIs", description = "Login and Register User")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService service;


    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest request){

        return service.saveUser(request);
    }

    @PostMapping("login")
    public String login(@RequestBody User user){

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(user.getEmail());
        else
            return "Login Failed";
    }
}
