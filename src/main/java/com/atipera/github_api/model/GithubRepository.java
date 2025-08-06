package com.atipera.github_api.model;

public record GithubRepository(String name, boolean fork, GithubOwner owner) {}
