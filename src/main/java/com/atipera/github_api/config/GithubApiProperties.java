package com.atipera.github_api.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "github.api")
@Getter
@Setter
public class GithubApiProperties {
    private String baseUrl;
}

