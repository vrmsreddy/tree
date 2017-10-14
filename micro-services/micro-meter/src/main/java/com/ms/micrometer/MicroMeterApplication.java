package com.ms.micrometer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ms.micrometer.config.CPULoad;
import com.ms.micrometer.other.ThreadPoolMetrics;

@SpringBootApplication
public class MicroMeterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroMeterApplication.class, args);
	}
	
	@Bean
    public ThreadPoolMetrics threadPoolMetrics() {
        return new ThreadPoolMetrics();
    }
	
	@Bean
    public CPULoad cpuLoad() {
        return new CPULoad();
    }
	
}
