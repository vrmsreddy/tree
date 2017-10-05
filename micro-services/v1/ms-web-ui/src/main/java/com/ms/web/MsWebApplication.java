package com.ms.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan
@Configuration
@EnableEurekaClient
public class MsWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsWebApplication.class, args);
	}
}
