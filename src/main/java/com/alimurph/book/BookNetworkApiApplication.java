package com.alimurph.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import com.alimurph.book.role.Role;
import com.alimurph.book.role.RoleRepository;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAware")
@EnableAsync
public class BookNetworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookNetworkApiApplication.class, args);
	}

	// temp method to initialize the role table
//	@Bean
//	public CommandLineRunner runner(RoleRepository roleRepository) {
//
//		return (args) -> {
//
//			// insert data for role table
//			if(roleRepository.findByName("USER").isEmpty()) {
//				roleRepository.save(
//						new Role.Builder()
//								.setName("USER")
//								.setCreatedDate(LocalDateTime.now())
//								.setCreatedBy("admin")
//								.createRole()
//				);
//			}
//		};
//	}

}
