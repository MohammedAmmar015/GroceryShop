package com.ideas2it.groceryshop;

<<<<<<< Updated upstream
import com.ideas2it.groceryshop.audit.AuditorAwareImpl;
import com.ideas2it.groceryshop.configuration.CustomUserDetails;
import com.ideas2it.groceryshop.filter.CustomJwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
=======
import com.ideas2it.groceryshop.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
>>>>>>> Stashed changes
public class GroceryshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryshopApplication.class, args);
	}

}
