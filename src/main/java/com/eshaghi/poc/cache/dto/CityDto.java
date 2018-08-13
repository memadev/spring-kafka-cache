package com.eshaghi.poc.cache.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CityDto {

    private long id;
    private String name;

    @JsonCreator
    public CityDto(@JsonProperty("id") long id,
                   @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityDto cityDto = (CityDto) o;
        return id == cityDto.id && Objects.equals(name, cityDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
