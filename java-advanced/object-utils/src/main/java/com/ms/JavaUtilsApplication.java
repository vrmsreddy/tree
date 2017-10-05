package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ms.entity.EmployeeEntity;

/**
 * @author bootApp
 *
 */
@SpringBootApplication
public class JavaUtilsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaUtilsApplication.class, args);
		EmployeeEntity e = null;
	}
}
