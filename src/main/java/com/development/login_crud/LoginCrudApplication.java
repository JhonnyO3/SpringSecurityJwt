package com.development.login_crud;

import com.development.login_crud.dto.RegisterRequest;
import com.development.login_crud.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.development.login_crud.dto.Role.ADMIN;
import static com.development.login_crud.dto.Role.MANAGER;

@SpringBootApplication
public class LoginCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginCrudApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.username("Admin")
					.userEmail("admin@mail.com")
					.userPassword("password")
					.role(ADMIN)
					.build();
			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.username("Admin")
					.userEmail("Admin")
					.userPassword("manager@mail.com")
					.role(MANAGER)
					.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());

		};
	}

}
