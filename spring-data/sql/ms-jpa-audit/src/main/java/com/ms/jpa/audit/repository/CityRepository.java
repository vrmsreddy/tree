package com.ms.jpa.audit.repository;

import org.springframework.data.repository.CrudRepository;

import com.ms.jpa.audit.model.City;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Integer> {

    List<City> findByName(String name);

}
