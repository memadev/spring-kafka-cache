package com.eshaghi.poc.cache.cache.impl;

import com.eshaghi.poc.cache.cache.CityCache;
import com.eshaghi.poc.cache.dto.CityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CityCacheImpl implements CityCache {

    private static final Logger logger = LoggerFactory.getLogger(CityCacheImpl.class);

    private Map<Long, CityDto> cache = new ConcurrentHashMap<>();
    private ObjectMapper objectMapper;

    @Autowired
    public CityCacheImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<CityDto> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    @KafkaListener(topicPartitions = {@TopicPartition(topic = CityCache.KAFKA_TOPIC,
            partitionOffsets = @PartitionOffset(partition = "0", initialOffset = "0"))})
    public void listen(ConsumerRecord<String, String> record) {
        try {
            CityDto city = objectMapper.readValue(record.value(), CityDto.class);
            cache.put(city.getId(), city);
            logger.info("{} put to cache successfully.", city.getName());
        } catch (IOException ioe) {
            logger.warn("Could not deserialize City.", ioe);
        }
    }
}
