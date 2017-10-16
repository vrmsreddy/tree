package com.ms.jpa.audit.controller;

import com.ms.jpa.audit.model.City;
import com.ms.jpa.audit.repository.CityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private Logger LOGGER = LoggerFactory.getLogger(City.class);
    
    @Autowired
    private CityRepository cityRepository;

    @RequestMapping
    public Iterable<City> listAll() {
        return cityRepository.findAll();
    }

    @RequestMapping(value = "/{cityCode}", method = RequestMethod.GET)
    public City get(@PathVariable Integer cityCode) {
        return cityRepository.findOne(cityCode);
    }

    @RequestMapping(value = "/{cityCode}", method = RequestMethod.POST)
    public City save(@PathVariable Integer cityCode, @RequestBody City city) {
        return cityRepository.save(city);
    }

    @RequestMapping(value = "/{cityCode}", method = RequestMethod.PUT)
    public City update(@PathVariable Integer cityCode, @RequestBody City city) {
        City cityObject = cityRepository.findOne(cityCode);

        cityObject.setName(city.getName());

        return cityRepository.save(cityObject);
    }

    @RequestMapping(value = "/{cityCode}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer cityCode) {
        cityRepository.delete(cityCode);
        return true;
    }
    
    @RequestMapping(value = "/crud", method = RequestMethod.POST)
    public City crudAllTest(@RequestBody City city) {
    	//create
    	cityRepository.save(city);
    	//retrive
    	City cityNew = cityRepository.findOne(city.getCityCode());
    	LOGGER.info("SAVE&RETRIVE"+cityNew);
    	
    	//update
    	cityNew.setName(cityNew.getName()+"Dummy");
    	cityRepository.save(cityNew);
    	cityNew = cityRepository.findOne(city.getCityCode());
    	LOGGER.info("UPDATED"+cityNew);
    	
    	//delete
        cityRepository.delete(city.getCityCode());
        cityNew = cityRepository.findOne(city.getCityCode());
        LOGGER.info("DELETED"+cityNew);
        
        return cityNew;
    }


}
