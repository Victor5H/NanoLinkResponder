package com.harshit.NanoLinkResponder.mapper;

import com.harshit.NanoLinkResponder.dto.UrlMappingDto;
import com.harshit.NanoLinkResponder.model.UrlMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UrlMappingMapper {
    UrlMapping urlDtoToUrl(UrlMappingDto dto);
    UrlMappingDto urlToUrlDto(UrlMapping urlMapping);
}
