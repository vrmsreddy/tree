/*
 * MIT License
 *
 * Copyright (c) 2017 JUAN CALVOPINA M
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

/**
 * Endpoint for access to the services
 */
package com.ms.jp.audit.controller;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms.jp.audit.domain.Person;
import com.ms.jp.audit.dto.PersonDTO;
import com.ms.jp.audit.service.PersonService;

/**
 * @author juanca <juan.calvopina+dev@gmail.com>
 */
@RestController
@RequestMapping(value = "/person")
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private static final String WELCOME_PERSON_ENTITY = "Welcome to Envers example: Person Entity";

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/find-all-persons", method = RequestMethod.GET)
    public List<Person> findAllPersons() {
        logger.info("Find all persons");
        return personService.findAll();
    }

    @RequestMapping(value = "/find-by-person", method = RequestMethod.GET)
    public Person findByText(@RequestParam(value = "text") String text) {
        logger.info(String.format("Finding by: %s", text));
        return personService.findByText(text, text, text);
    }

    @RequestMapping(value = "/save-person", method = RequestMethod.POST)
    public String savePerson(@RequestBody PersonDTO personDTO) {
        Validate.notNull(personDTO, "The person cannot be null");
        logger.info(String.format("Saving person: %s", personDTO.toString()));
        return personService.save(personDTO);
    }

    @RequestMapping(value = "/update-person", method = RequestMethod.POST)
    public String updatePerson(@RequestBody PersonDTO personDTO) {
        Validate.notNull(personDTO, "The person cannot be null");
        logger.info(String.format("Updating person: %s", personDTO.toString()));
        return personService.update(personDTO);
    }

    @RequestMapping(value = "/delete-person", method = RequestMethod.DELETE)
    public String deletePerson(@RequestParam(value = "id", required = true) int id) {
        logger.info(String.format("Deleting person: %s", id));
        return personService.deleteById(id);
    }

}
