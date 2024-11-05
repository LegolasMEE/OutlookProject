package com.Trainee.ProjectOutlook;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@SpringBootApplication
@EnableJpaRepositories("com.Trainee.ProjectOutlook.repository")
@EntityScan("com.Trainee.ProjectOutlook.entity")
public class ProjectOutlookApplication {

	public static void main(String[] args) {
		Flyway flyway = Flyway.configure()
				.dataSource("jdbc:postgresql://localhost:5432/migratecheck", "postgres", "password")
				.locations("classpath:db/migration")
				.baselineOnMigrate(true)
				.baselineVersion("0")
				.load();
		flyway.migrate();
		SpringApplication.run(ProjectOutlookApplication.class, args);
	}



}
