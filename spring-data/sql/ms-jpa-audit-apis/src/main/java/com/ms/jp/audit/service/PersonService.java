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
 * List of methods signature
 */
package com.ms.jp.audit.service;

import java.util.List;

import com.ms.jp.audit.domain.Person;
import com.ms.jp.audit.dto.PersonDTO;

/**
 * @author juanca <juan.calvopina+dev@gmail.com>
 */
public interface PersonService {

    /**
     * Retrieves all persons from database
     *
     * @return
     */
    List<Person> findAll();

    /**
     * Finds the person by name or last name
     *
     * @param id
     * @param name
     * @param lastName
     * @return
     */
    Person findByText(String id, String name, String lastName);

    /**
     * Finds the person by int
     *
     * @param id
     * @return
     */
    Person findById(int id);

    /**
     * Adds a new person to the database
     *
     * @param personDTO
     * @return
     */
    String save(PersonDTO personDTO);

    /**
     * Updates a person to the database
     *
     * @param personDTO
     * @return
     */
    String update(PersonDTO personDTO);

    /**
     * Deletes a person by Id from database
     *
     * @param id
     * @return
     */
    String deleteById(int id);

}
