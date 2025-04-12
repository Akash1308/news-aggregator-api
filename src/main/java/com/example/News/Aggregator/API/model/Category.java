package com.example.News.Aggregator.API.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(unique = true, nullable = false)
    private int id;

    @Column(unique = true, nullable = false)
    private String categoryName;
}
