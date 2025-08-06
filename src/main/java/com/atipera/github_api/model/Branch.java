package com.atipera.github_api.model;

public record Branch(
        String name,
        String lastCommitSha
) {}