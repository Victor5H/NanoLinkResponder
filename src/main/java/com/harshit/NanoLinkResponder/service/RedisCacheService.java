package com.harshit.NanoLinkResponder.service;

import com.harshit.NanoLinkResponder.dto.UrlMappingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {
    @Autowired
    RedisTemplate redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisCacheService.class);

    public void insertIntoCache(UrlMappingDto dto){
        redisTemplate.opsForValue().set(dto.getShortUrl(),dto.getLongUrl(),10, TimeUnit.MINUTES);
    }
    @Async
    public CompletableFuture<String> checkForShortUrl(String shortUrl){
        try{
            Object o =redisTemplate.opsForValue().get(shortUrl);
            if(o==null) return CompletableFuture.completedFuture(null);
            String res = o.toString();
            return CompletableFuture.completedFuture(res);
        }catch (Exception e){
            return CompletableFuture.failedFuture(e);
        }
    }
}
