package com.test.springboot.events;

import com.test.springboot.github.GithubClient;
import com.test.springboot.github.RepositoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class EventsController {

    @Autowired
    private GithubProjectRepository repository;

    @Autowired
    private GithubClient client;

    @GetMapping("/events/{repoName}")
    @ResponseBody
    public RepositoryEvent[] fetchEvents(@PathVariable String repoName) {
        GithubProject project = repository.findByRepoName(repoName);

        return client.fetchEvents(project.getOrgName(), project.getRepoName()).getBody() ;
    }

    @GetMapping("/")
    public String dashboard(Model model) {

        Iterable<GithubProject> projects = repository.findAll();

        List<DashboardEntry> entries = StreamSupport.stream(projects.spliterator(), true)
                .map(p -> new DashboardEntry(p, client.fetchEventsList(p.getOrgName(), p.getRepoName())))
                .collect(Collectors.toList());

        model.addAttribute("entries", entries);

        return "dashboard";
    }
}
