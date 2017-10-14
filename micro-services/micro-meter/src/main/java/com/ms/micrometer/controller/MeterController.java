/**
 * Copyright 2017 Pivotal Software, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ms.micrometer.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.micrometer.dto.TestDto;

import io.micrometer.core.annotation.Timed;

@RestController
public class MeterController {
	private static Logger LOGGER = LoggerFactory.getLogger(MeterController.class);
    private List<String> people = Arrays.asList("mike", "suzy");

    /**
     * person data and used for testing API performance
     * @return person data list
     */
    @GetMapping("/api/people")
    @Timed(percentiles = true)
    public List<String> allPeople() {
    	try {
			TimeUnit.MILLISECONDS.sleep(200);
			LOGGER.info("Taking Less Time");
		} catch (InterruptedException e) {
			LOGGER.error("Error in Thread" ,e);
		}
        return people;
    }
    
    /**
     * person data and used for testing API performance
     * @return person data list
     */
    @GetMapping("/api/people2")
    @Timed(percentiles = true)
    public List<String> allPeople2() {
    	try {
    		LOGGER.warn("Taking More Time");
			TimeUnit.MILLISECONDS.sleep(2000);
		} catch (InterruptedException e) {
			LOGGER.error("Error in Thread" ,e);
		}
        return people;
    }
    
    /**
     * It will increase the load only for testing cpu load in Dashboard
     * @return test status
     */
    @GetMapping("/api/cpu/load")
    @Timed(percentiles = true)
    public String testCPULoad() {
    	try {
    		Map<TestDto, Integer> testMap = new HashMap<>();
    		for(int i=0;i<30000;i++){
    			TestDto t1 = new TestDto();
    			t1.setId(i);
    			testMap.put(t1, i);
    		}
    		
		} catch (Exception e) {
			LOGGER.error("Failed to test CPU", e);
		}
        return "CPU-tested";
    }
}
