package br.com.m4rc310.budget;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import br.com.m4rc310.budget.data.dto.AuthUser;
import br.com.m4rc310.budget.repositories.AuthUserRepository;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
@PropertySource(ignoreResourceNotFound = true, value = "classpath:/security.properties")
public class BudgetApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(BudgetApplication.class, args);
	}

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
	}
	
	
	
}
