package com.example.News.Aggregator.API.service;

import com.example.News.Aggregator.API.dto.RegisterUserRequest;
import com.example.News.Aggregator.API.model.Category;
import com.example.News.Aggregator.API.repo.CategoryRepo;
import com.example.News.Aggregator.API.repo.UserRepo;
import com.example.News.Aggregator.API.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private CategoryRepo categoryRepo;


    public ResponseEntity<User> saveUser(RegisterUserRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Category> preferences = new HashSet<>(categoryRepo.findAllById(request.getCategoryIds()));
        user.setPreferences(preferences);
        user.setRole("USER");
        repo.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
