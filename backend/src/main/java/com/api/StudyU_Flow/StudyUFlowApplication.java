package com.api.StudyU_Flow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class StudyUFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyUFlowApplication.class, args);
	}

}
