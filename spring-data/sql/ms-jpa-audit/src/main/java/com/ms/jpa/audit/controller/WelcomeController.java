package com.ms.jpa.audit.controller;

import com.ms.jpa.audit.model.City;
import com.ms.jpa.audit.repository.CityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Autowired
    CityRepository cityRepository;

    @RequestMapping(value= "/hello",  method = RequestMethod.GET)
    public String hello() {
        return "Hello...";
    }

    @RequestMapping(value="/cities", method = RequestMethod.GET)
    public Iterable<City> getCities() {
        return cityRepository.findAll();
    }

}
