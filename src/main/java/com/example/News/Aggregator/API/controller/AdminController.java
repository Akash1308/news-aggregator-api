package com.example.News.Aggregator.API.controller;


import com.example.News.Aggregator.API.dto.RegisterUserRequest;
import com.example.News.Aggregator.API.model.Category;
import com.example.News.Aggregator.API.service.AdminService;
import com.example.News.Aggregator.API.service.JWTService;
import com.example.News.Aggregator.API.service.UserService;
import com.example.News.Aggregator.API.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Admin APIs", description = "Admin Register,See users,Add see Categories")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AdminService service;


    @PostMapping("/register")
    @Operation(summary = "Register new Admin")
    public ResponseEntity<User> register(@RequestBody RegisterUserRequest request){

        return service.saveUser(request);
    }

    @PostMapping("/categories")
    @Operation(summary = "Add new Category")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        service.addCategory(category);
        return new ResponseEntity<>("Category added",HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    @Operation(summary = "Get all Categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = service.getAllCategories();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

//    @DeleteMapping("/categories/{id}")
//    public ResponseEntity<?> deleteCategory(@PathVariable int id){
//        service.deleteCategory(id);
//        return new ResponseEntity<>("Deleted",HttpStatus.OK);
//    }

    @GetMapping("/allUsers")
    @Operation(summary = "Fetch all users")
    public ResponseEntity<List<User>> showAllUsers(){
        List<User> allUsers = service.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

}
