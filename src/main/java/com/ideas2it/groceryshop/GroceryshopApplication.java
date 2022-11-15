package com.ideas2it.groceryshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GroceryshopApplication {
	public static void main(String[] args) {
		SpringApplication.run(GroceryshopApplication.class, args);
	}

}
