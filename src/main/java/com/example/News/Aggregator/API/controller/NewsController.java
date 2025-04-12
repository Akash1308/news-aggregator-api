package com.example.News.Aggregator.API.controller;

import com.example.News.Aggregator.API.dto.NewsResponse;
import com.example.News.Aggregator.API.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "News API", description = "Fetches News")
public class NewsController {

    @Autowired
    NewsService newsService;

    //List<String> category = List.of("sports","business");

    @GetMapping("/news")
    @Operation(summary = "Fetch News according to user's saved preferences")
    public ResponseEntity<?> getNews(){

        NewsResponse newsResponse = newsService.getNewsByPreferences();
        if(newsResponse != null){
            return new ResponseEntity<>(newsResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
