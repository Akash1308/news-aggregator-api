package com.example.News.Aggregator.API.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass){

        try {
            Object o = redisTemplate.opsForValue().get(key);

            if(o == null){
                log.warn("Cache miss for key: {}",key);
                return null;
            }
            // Every class has Object as super class so we can convert this object to another class with the help of Object mapper
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(),entityClass);
        }
        catch (Exception e){
            log.error("Exception ", e);
            return null;
        }
    }

    public void set(String key, Object o, Long ttl){
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.SECONDS);
        }
        catch (Exception e){
            log.error("Exception ", e);
        }
    }
}
