package com.projet.ferme;

import com.projet.ferme.repository.UserRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class DigitalFermeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalFermeApplication.class, args);
	}

}
