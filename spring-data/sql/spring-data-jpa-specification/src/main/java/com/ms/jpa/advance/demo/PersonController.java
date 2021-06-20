package com.ms.jpa.advance.demo;

import com.ms.jpa.advance.demo.db.Person;
import com.ms.jpa.advance.demo.db.PersonRepository;
import com.ms.jpa.advance.specification.Filter;
import com.ms.jpa.advance.specification.SpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class PersonController {
    @Autowired
    PersonRepository personRepository;

    @PostMapping("/persons")
    public List<Person> filter(@RequestBody Filter filter){
        return SpecificationBuilder.selectFrom(personRepository).leftJoin("address").where(filter).findAll();
    }

    @GetMapping("/persons")
    public List<Person> join(@RequestParam(value = "filter",required = false) String queryString){
        List<Person> address = SpecificationBuilder.selectFrom(personRepository).leftJoin("address").where(queryString).findAll();
        return address;
    }


}
