package com.harshit.NanoLinkResponder.service;

import com.harshit.NanoLinkResponder.dto.UrlMappingDto;
import com.harshit.NanoLinkResponder.exception.NoSuchUrlException;
import com.harshit.NanoLinkResponder.mapper.UrlMappingMapper;
import com.harshit.NanoLinkResponder.model.UrlMapping;
import com.harshit.NanoLinkResponder.repo.UrlMappingRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UrlMappingService {
    Logger logger = LoggerFactory.getLogger(UrlMappingService.class);
    @Autowired
    private UrlMappingRepo urlMappingRepo;
    @Autowired
    private UrlMappingMapper urlMappingMapper;

    @Async
    public CompletableFuture<String> getUrl(String shortUrl) throws NoSuchUrlException {
        logger.info("in get url");
        Optional<UrlMapping> optional = urlMappingRepo.findByShortUrl(shortUrl);
        if (optional.isEmpty()) throw new NoSuchUrlException(shortUrl + "not found");
        UrlMappingDto dto = urlMappingMapper.urlToUrlDto(optional.get());
        String longUrl = dto.getLongUrl();
        logger.info("done get url");
        return CompletableFuture.completedFuture(longUrl);
    }
}
