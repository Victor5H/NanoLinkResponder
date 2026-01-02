package com.harshit.NanoLinkResponder.controller;

import com.harshit.NanoLinkResponder.service.UrlMappingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class UrlController {
    @Autowired
    UrlMappingService urlMappingService;
    @GetMapping("/**")
    public ResponseEntity<?> get(HttpServletRequest httpServletRequest){
        String shortUrl = httpServletRequest.getRequestURI().substring(1);
        System.out.println(shortUrl);
        if(shortUrl.isBlank())
           return ResponseEntity.badRequest().body("no url");
        String longUrl = urlMappingService.getUrl(shortUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(longUrl));
        return new ResponseEntity<Void>(httpHeaders,HttpStatus.MOVED_PERMANENTLY);
    }
}
