package com.jb.expense_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
@OpenAPIDefinition(
	info = @Info(
		title = "Expense Tracker REST API Documentation",
		description = "Expense Tracker REST API Documentation",
		version = "v1.0",
		contact = @Contact(
			name = "Jayabalajee S K",
			email = "jayabalajeesk04@gmail.com",
			url = "https://www.linkedin.com/in/jayabalajee-s-k"
		),
		license = @License(
			name = "Apache 2.0",
			url = "http://www.apache.org/licenses/LICENSE-2.0"
		)
	),
	externalDocs = @ExternalDocumentation(
		description = "Expense Tracker REST API Documentation for Developers"
	)
)

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

}