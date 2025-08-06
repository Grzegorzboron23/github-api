package com.atipera.github_api.service.impl;

import com.atipera.github_api.config.GithubApiProperties;
import com.atipera.github_api.exception.UserNotFoundException;
import com.atipera.github_api.model.Branch;
import com.atipera.github_api.model.GithubBranch;
import com.atipera.github_api.model.GithubRepository;
import com.atipera.github_api.model.RepositoryInfo;
import com.atipera.github_api.service.GithubService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
public class GithubServiceImpl implements GithubService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    private static final String REPOS_PATH = "/users/{username}/repos";
    private static final String BRANCHES_PATH = "/repos/{owner}/{repoName}/branches";

    public GithubServiceImpl(GithubApiProperties properties) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = properties.getBaseUrl();
    }

    @Override
    public List<RepositoryInfo> getNonForkedRepositories(String username) {
        GithubRepository[] allRepos = getAllRepositories(username);
        if (allRepos == null) {
            return Collections.emptyList();
        }

        List<CompletableFuture<RepositoryInfo>> futures = Arrays.stream(allRepos)
                .filter(repo -> !repo.fork())
                .map(repo -> CompletableFuture.supplyAsync(() -> {
                    List<Branch> branches = fetchBranches(repo.owner().login(), repo.name());
                    return new RepositoryInfo(repo.name(), repo.owner().login(), branches);
                })).toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private List<Branch> fetchBranches(String owner, String repoName) {
        try {
            String branchesUrl = baseUrl + BRANCHES_PATH;
            ResponseEntity<GithubBranch[]> response = restTemplate.getForEntity(
                    branchesUrl,
                    GithubBranch[].class,
                    owner, repoName
            );

            GithubBranch[] branches = response.getBody();
            if (branches == null) {
                return Collections.emptyList();
            }

            return Arrays.stream(branches)
                    .map(b -> new Branch(b.name(), b.commit().sha()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // According to the task requirements, full exception handling is intentionally omitted.
            return Collections.emptyList();
        }
    }

    private GithubRepository[] getAllRepositories(String username) {
        try {
            return restTemplate.getForObject(
                    baseUrl + REPOS_PATH,
                    GithubRepository[].class,
                    username
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not found: " + username);
        }
    }
}
