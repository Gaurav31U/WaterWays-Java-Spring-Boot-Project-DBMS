package com.masters.waterways;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

@SpringBootApplication
public class WaterwaysApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterwaysApplication.class, args);
	}

}
