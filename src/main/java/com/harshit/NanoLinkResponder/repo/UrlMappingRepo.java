package com.harshit.NanoLinkResponder.repo;

import com.harshit.NanoLinkResponder.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlMappingRepo extends JpaRepository<UrlMapping,Long> {

    Optional<UrlMapping> findByLongUrl(String longUrl);
    Optional<UrlMapping> findByShortUrl(String shortUrl);
}
