package com.ideas2it.groceryshop;

import com.ideas2it.groceryshop.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class GroceryshopApplication {
	public static void main(String[] args) {
		SpringApplication.run(GroceryshopApplication.class, args);
	}

}
