package com.example.News.Aggregator.API.repo;

import com.example.News.Aggregator.API.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
