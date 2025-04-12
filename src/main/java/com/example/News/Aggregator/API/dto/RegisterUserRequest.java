package com.example.News.Aggregator.API.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jdk.jfr.Description;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RegisterUserRequest {

    @NotEmpty
    @Schema(description = "The User's Email")
    private String email;
    @NotEmpty
    private String password;
    private Set<Integer> categoryIds;
}
