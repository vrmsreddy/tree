package com.ms.jpa.audit.controller;

import com.ms.jpa.audit.dto.EntityWithRevisionDTO;
import com.ms.jpa.audit.model.City;
import com.ms.jpa.audit.repository.CityRevisionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mndeveci on 18.11.2016.
 */

@RestController
@RequestMapping("/api/cities/history")
public class CityRevisionController {

    @Autowired
    private CityRevisionRepository cityRevisionRepository;


    @RequestMapping(value= "/revisions/{cityCode}", method = RequestMethod.GET)
    public List<EntityWithRevisionDTO<City>> getCityRevisions(@PathVariable Integer cityCode) {
        return cityRevisionRepository.listCityRevisions(cityCode);
    }



}
