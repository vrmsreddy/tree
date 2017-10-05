package com.ms.spring.cloud.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.ms.spring.cloud.web.filters.pre.SimpleFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
@EnableCircuitBreaker
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
    
    @Bean
    public SimpleFilter simpleFilter() {
      return new SimpleFilter();
    }
    
}
