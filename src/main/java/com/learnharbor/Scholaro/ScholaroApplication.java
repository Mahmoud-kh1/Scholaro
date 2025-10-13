package com.learnharbor.Scholaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.learnharbor.Scholaro.repository")
@EntityScan("com.learnharbor.Scholaro.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class ScholaroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScholaroApplication.class, args);
	}

}
