package com.atipera.github_api.integration;

import com.atipera.github_api.model.RepositoryInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnNonForkedRepositoriesWithBranchesAndCommits_whenUserExists() {
        // given
        String username = "Grzegorzboron23";

        // when
        ResponseEntity<RepositoryInfo[]> response = restTemplate.getForEntity(
                "/api/github/user/" + username + "/repos",
                RepositoryInfo[].class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        RepositoryInfo[] repos = response.getBody();
        assertThat(repos).isNotNull();
        assertThat(repos).isNotEmpty();

        for (RepositoryInfo repo : repos) {
            assertThat(repo.repositoryName()).isNotBlank();
            assertThat(repo.ownerLogin()).isEqualToIgnoringCase(username);
            assertThat(repo.branches()).isNotEmpty();

            repo.branches().forEach(branch -> {
                assertThat(branch.name()).isNotBlank();
                assertThat(branch.lastCommitSha()).isNotBlank();
            });
        }
    }
}
