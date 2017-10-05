package com.ms.spring.cloud.ocr.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author vrmsreddy
 *
 */
@SpringBootApplication
@EnableHystrixDashboard
@EnableTurbine
public class HystrixDashboardApplication {

	@SuppressWarnings("javadoc")
	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboardApplication.class, args);
	}
}
