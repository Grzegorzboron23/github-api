# GitHub API Integration

## Description

This project integrates with the public GitHub API and exposes a single endpoint to fetch all **non-fork** repositories of a given GitHub user. For each repository, it includes its branches and the last commit SHA of each branch.

The implementation follows the exact requirements specified in the recruitment task — nothing more, nothing less.

---

## Endpoint

### `GET /api/github/user/{username}/repos`

Returns a list of **non-forked** repositories for the specified user along with:

- `repositoryName`
- `ownerLogin`
- `branches` – each with:
  - `name`
  - `lastCommitSha`

#### Example response:

```json
[
  {
    "repositoryName": "ExampleRepo",
    "ownerLogin": "exampleUser",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc123..."
      }
    ]
  }
]
``` 
#### Error Handling:
```json
[
  {
  "status": 404,
  "message": "User not found: {username}"
  }
]
```
---
### Requirements

- Java 21  
- Spring Boot 3.2+  
- Maven or Gradle

---

### Running the Application

To run the application locally:

```bash
./gradlew bootRun
# or
mvn spring-boot:run


