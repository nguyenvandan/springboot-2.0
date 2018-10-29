package com.test.springboot.events;

import com.test.springboot.github.RepositoryEvent;

import java.util.List;

public class DashboardEntry {

    private final GithubProject project;

    private final List<RepositoryEvent> events;

    //private String title;

    public DashboardEntry(GithubProject project, List<RepositoryEvent> events) {
        this.project = project;
        this.events = events;
    }

    public GithubProject getProject() {
        return project;
    }

    public List<RepositoryEvent> getEvents() {
        return events;
    }
}
