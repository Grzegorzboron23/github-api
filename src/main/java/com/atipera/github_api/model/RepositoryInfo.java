package com.atipera.github_api.model;

import java.util.List;

public record RepositoryInfo(
        String repositoryName,
        String ownerLogin,
        List<Branch> branches
) {}
