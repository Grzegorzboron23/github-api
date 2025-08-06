package com.atipera.github_api.controller;

import com.atipera.github_api.model.RepositoryInfo;
import com.atipera.github_api.service.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/user/{username}/repos")
    public List<RepositoryInfo> getRepositories(@PathVariable String username) {
        return githubService.getNonForkedRepositories(username);
    }
}
