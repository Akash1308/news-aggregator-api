package com.example.News.Aggregator.API.service;

import com.example.News.Aggregator.API.dto.RegisterUserRequest;
import com.example.News.Aggregator.API.model.Category;
import com.example.News.Aggregator.API.model.User;
import com.example.News.Aggregator.API.repo.CategoryRepo;
import com.example.News.Aggregator.API.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminService {

    @Autowired
    private UserRepo repo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private CategoryRepo categoryRepo;

    public ResponseEntity<User> saveUser(RegisterUserRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ADMIN");
        Set<Category> preferences = new HashSet<>(categoryRepo.findAllById(request.getCategoryIds()));
        user.setPreferences(preferences);

        repo.save(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public void addCategory(Category category) {

        categoryRepo.save(category);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public List<Category> getAllCategories() {
       return categoryRepo.findAll();
    }

    public void deleteCategory(int id) {
        categoryRepo.deleteById(id);
    }
}
