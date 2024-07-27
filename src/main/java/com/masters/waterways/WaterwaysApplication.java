package com.masters.waterways;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WaterwaysApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterwaysApplication.class, args);
	}

}
