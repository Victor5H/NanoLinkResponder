package com.harshit.NanoLinkResponder.controller;

import com.harshit.NanoLinkResponder.service.UrlMappingService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class UrlController {
    Logger logger = LoggerFactory.getLogger(UrlController.class);
    @Autowired
    UrlMappingService urlMappingService;
    @GetMapping("/**")
    public ResponseEntity<?> get(HttpServletRequest httpServletRequest) throws ExecutionException, InterruptedException {
        String shortUrl = httpServletRequest.getRequestURI().substring(1);
        logger.info(shortUrl);
        System.out.println(shortUrl);
        if(shortUrl.isBlank()){
            logger.info(" no url ");
            return ResponseEntity.badRequest().body("no url");
        }
        CompletableFuture<String> future = urlMappingService.getUrl(shortUrl);
        logger.info("async called");
        while (true){
            if(future.isDone()){
                logger.info("async done");
                break;
            }
        }
        String longUrl = future.get();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(longUrl));
        return new ResponseEntity<Void>(httpHeaders,HttpStatus.PERMANENT_REDIRECT);
    }
}
