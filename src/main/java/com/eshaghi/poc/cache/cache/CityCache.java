package com.eshaghi.poc.cache.cache;

import com.eshaghi.poc.cache.dto.CityDto;

import java.util.Optional;

public interface CityCache {

    String KAFKA_TOPIC = "city";

    Optional<CityDto> findById(Long id);
}
