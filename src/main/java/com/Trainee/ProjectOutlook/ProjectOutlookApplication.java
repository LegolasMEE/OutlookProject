package com.Trainee.ProjectOutlook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.Trainee.ProjectOutlook.repository")
@EntityScan("com.Trainee.ProjectOutlook.entity")
public class ProjectOutlookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectOutlookApplication.class, args);
	}



}
