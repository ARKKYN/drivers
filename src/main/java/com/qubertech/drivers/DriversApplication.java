package com.qubertech.drivers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class DriversApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DriversApplication.class, args);
	}
}
