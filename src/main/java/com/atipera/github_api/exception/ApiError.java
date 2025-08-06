package com.atipera.github_api.exception;

public record ApiError(int status, String message) {}
