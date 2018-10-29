package com.test.springboot;

import com.test.springboot.github.GithubClient;
import com.test.springboot.github.RepositoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class GithubHealthIndicator implements HealthIndicator {

    @Autowired
    private GithubClient githubClient;

    @Override
    public Health health() {
        try {
            ResponseEntity<RepositoryEvent[]> response = githubClient.fetchEvents("spring-projects", "spring-boot");

            if (response.getStatusCode().is2xxSuccessful()) {
                return Health.up().build();
            } else {
                return Health.down().build();
            }
        } catch (Exception e) {
            return Health.down(e).build();
        }
    }
}
