package com.harshit.NanoLinkResponder.service;

import com.harshit.NanoLinkResponder.dto.UrlMappingDto;
import com.harshit.NanoLinkResponder.exception.NoSuchUrlException;
import com.harshit.NanoLinkResponder.mapper.UrlMappingMapper;
import com.harshit.NanoLinkResponder.model.UrlMapping;
import com.harshit.NanoLinkResponder.repo.UrlMappingRepo;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class UrlMappingService {
    private static final Logger logger = LoggerFactory.getLogger(UrlMappingService.class);

    private UrlMappingRepo urlMappingRepo;
    private UrlMappingMapper urlMappingMapper;
    RedisCacheService redisCacheService;

    private Optional<String> getRedisResult(CompletableFuture<String> future){
        while (true){
            if(future.isDone()){
                String s = future.getNow(null);
                if(s ==null) return Optional.empty();
                return Optional.of(s);
            }
        }
    }

    @Async
    public CompletableFuture<String> getUrl(String shortUrl) throws NoSuchUrlException {
        CompletableFuture<String> redisFuture = redisCacheService.checkForShortUrl(shortUrl);
        logger.info("in get url");
        Optional<UrlMapping> optionalDB = urlMappingRepo.findByShortUrl(shortUrl);
        Optional<String> optionalCache = getRedisResult(redisFuture);
        if(optionalCache.isPresent()){
            logger.info("cache hit");
            return CompletableFuture.completedFuture(optionalCache.get());
        } else if (optionalDB.isPresent()) {
            UrlMappingDto dto = urlMappingMapper.urlToUrlDto(optionalDB.get());
            redisCacheService.insertIntoCache(dto);
            return CompletableFuture.completedFuture(dto.getLongUrl());
        }
        throw new NoSuchUrlException(shortUrl + "not found");
    }
}
