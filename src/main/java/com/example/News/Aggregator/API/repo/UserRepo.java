package com.example.News.Aggregator.API.repo;

import com.example.News.Aggregator.API.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String username);
}
