package com.example.News.Aggregator.API.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponse{
    public int totalArticles;
    public ArrayList<Article> articles;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Article{
        public String title;
        public String description;
        public String content;
        public String url;
        public String image;
        public Date publishedAt;
        public Source source;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Source{
        public String name;
        public String url;
    }
}




