package com.harshit.NanoLinkResponder.service;

import com.harshit.NanoLinkResponder.dto.UrlMappingDto;
import com.harshit.NanoLinkResponder.exception.NoSuchUrlException;
import com.harshit.NanoLinkResponder.mapper.UrlMappingMapper;
import com.harshit.NanoLinkResponder.model.UrlMapping;
import com.harshit.NanoLinkResponder.repo.UrlMappingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlMappingService {
    @Autowired
    private UrlMappingRepo urlMappingRepo;
    @Autowired
    private UrlMappingMapper urlMappingMapper;

    public String getUrl(String shortUrl) throws NoSuchUrlException {
        Optional<UrlMapping> optional = urlMappingRepo.findByShortUrl(shortUrl);
        if(optional.isEmpty()) throw new NoSuchUrlException(shortUrl+ "not found");
        UrlMappingDto dto = urlMappingMapper.urlToUrlDto(optional.get());
        return dto.getLongUrl();
    }
}
