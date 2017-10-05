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
 * Implementation of the method signatures
 */
package com.ms.jp.audit.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.jp.audit.domain.Person;
import com.ms.jp.audit.dto.PersonDTO;
import com.ms.jp.audit.repository.PersonRepository;
import com.ms.jp.audit.utils.Utilities;

/**
 * @author juanca <juan.calvopina+dev@gmail.com>
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);
    private final PersonRepository personRepository;
    private String response;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findByText(String id, String name, String lastName) {
        Integer personId = Utilities.isInteger(id);
        return personRepository.findByIdOrFirstNameOrLastName(personId, name, lastName);
    }

    @Override
    public Person findById(int id) {
        return personRepository.findOne(id);
    }

    @Override
    public String save(PersonDTO personDTO) {
        response = "Person saved!";
        personRepository.save(new Person(personDTO));
        logger.info(response);
        return response;
    }

    @Override
    public String update(PersonDTO personDTO) {
        response = "Person updated!";
        Person person = personRepository.findOne(personDTO.getId());
        person = this.updatePerson(person, personDTO);
        personRepository.save(person);
        logger.info(response);
        return response;
    }

    @Override
    public String deleteById(int id) {
        response = "Person deleted!";
        personRepository.delete(id);
        logger.info(response);
        return response;
    }

    private Person updatePerson(Person person, PersonDTO personDTO) {
        person.setFirstName(personDTO.getName());
        person.setLastName(personDTO.getLastName());
        return person;
    }

}
