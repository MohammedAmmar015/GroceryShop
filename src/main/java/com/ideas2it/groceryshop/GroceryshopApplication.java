package com.ideas2it.groceryshop;

import com.ideas2it.groceryshop.audit.AuditorAwareImpl;
import com.ideas2it.groceryshop.configuration.CustomUserDetails;
import com.ideas2it.groceryshop.filter.CustomJwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ideas2it.groceryshop.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})

public class GroceryshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryshopApplication.class, args);
	}

}
