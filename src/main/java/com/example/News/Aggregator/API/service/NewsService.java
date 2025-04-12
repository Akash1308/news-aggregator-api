package com.example.News.Aggregator.API.service;

import com.example.News.Aggregator.API.dto.NewsResponse;
import com.example.News.Aggregator.API.model.Category;
import com.example.News.Aggregator.API.model.User;
import com.example.News.Aggregator.API.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NewsService {

    @Autowired
    private UserRepo repo;

//    private static final String apiKey = "c0c419372ece74dadde0a9356e60ef70";

    private static final String API = "https://gnews.io/api/v4/top-headlines?category=CATEGORY&apikey=c0c419372ece74dadde0a9356e60ef70&lang=en";
    String finalApi;

    @Autowired
    RestTemplate restTemplate;

    /*
        public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        }
    */

    ResponseEntity<NewsResponse> response;

    @Autowired
    private RedisService redisService;


    public NewsResponse getNewsByPreferences(){

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = repo.findByEmail(email);

        Set<Category> preferences = user.getPreferences();
        Set<NewsResponse.Article> allArticles = new HashSet<>();

        for(Category category : preferences) {

            String redisKey = "news:" + category.getCategoryName().toLowerCase();
            NewsResponse rediResponse = redisService.get(redisKey, NewsResponse.class);

            if (rediResponse != null) {
                allArticles.addAll(rediResponse.getArticles());
            } else {
                finalApi = API.replace("CATEGORY", category.getCategoryName());

                try {
                    response = restTemplate.exchange(finalApi, HttpMethod.GET, null, NewsResponse.class);
                    if (response.getBody() != null && response.getBody().getArticles() != null) {
                        allArticles.addAll(response.getBody().getArticles());

                        // 3. Save category response to Redis (30 min TTL)
                        redisService.set(redisKey,new NewsResponse(response.getBody().getArticles().size(), response.getBody().getArticles()),300L);
                    }
                } catch (Exception e) {
                    System.out.println("Failed to fetch for category: " + category);
                }
            }
        }
        List<NewsResponse.Article> sortedArticles = allArticles.stream()
                .sorted(Comparator.comparing(NewsResponse.Article::getPublishedAt).reversed())
                .toList();

        // the process of converting json response to corresponding java object is called Deserialization, and vice versa is Serialization.
        return new NewsResponse(sortedArticles.size(), new ArrayList<>(sortedArticles));
    }
}
