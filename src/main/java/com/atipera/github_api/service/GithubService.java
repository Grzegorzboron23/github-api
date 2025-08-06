package com.atipera.github_api.service;

import com.atipera.github_api.model.RepositoryInfo;

import java.util.List;

public interface GithubService {
    List<RepositoryInfo> getNonForkedRepositories(String username);
}
