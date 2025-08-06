package com.atipera.github_api;

import com.atipera.github_api.config.GithubApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GithubApiProperties.class)
public class GithubApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubApiApplication.class, args);
	}
}
