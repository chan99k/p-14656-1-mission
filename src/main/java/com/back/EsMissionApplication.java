package com.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
@EnableElasticsearchAuditing(dateTimeProviderRef = "dateTimeProvider")
public class EsMissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsMissionApplication.class, args);
	}

}
