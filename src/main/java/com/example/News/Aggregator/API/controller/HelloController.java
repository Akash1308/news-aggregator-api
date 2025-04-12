package com.example.News.Aggregator.API.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "API Health Check", description = "Check if API is working")
public class HelloController {
    @GetMapping("/hello")
    public String greet(HttpServletRequest request){
        return "Hello World";
    }
}
