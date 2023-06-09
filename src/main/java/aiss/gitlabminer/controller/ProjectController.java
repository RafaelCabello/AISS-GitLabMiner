package aiss.gitlabminer.controller;

import aiss.gitlabminer.model.Commit;
import aiss.gitlabminer.model.Issue;
import aiss.gitlabminer.model.Project;
import aiss.gitlabminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/gitlabminer")
public class ProjectController {
    @Autowired
    RestTemplate template;
    @Autowired
    ProjectService projectService;

    //POST http://localhost:8081/gitlabminer/{id}[?sinceCommits=5&sinceIssues=30&maxPages=2]
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public Project create(@PathVariable Integer id, @RequestParam(defaultValue = "2") Long sinceCommits
            , @RequestParam(defaultValue = "20") Long sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages) {
        Project _project = projectService.getProject(id, maxPages);
        List<Commit> commits = _project.getCommits().stream()
                .filter(c -> LocalDateTime.parse(c.getCommitted_date().substring(0,23)).isAfter(LocalDateTime.now().minusDays(sinceCommits))).toList();
        List<Issue> issues = _project.getIssues().stream()
                .filter(i -> LocalDateTime.parse(i.getUpdatedAt().substring(0,23)).isAfter(LocalDateTime.now().minusDays(sinceIssues))).toList();
        Project filtredProject = new Project(_project.getId(), _project.getName(), _project.getWebUrl(), commits, issues);
        String uri = "http://localhost:8080/gitminer/projects";
        Project createdProject = template.postForObject(uri, filtredProject, Project.class);
        return createdProject;
    }

    //GET http://localhost:8081/gitlabminer/{id}
    @GetMapping("/{id}")
    public Project findProject(@PathVariable Integer id, @RequestParam(defaultValue = "2") Long sinceCommits
    , @RequestParam(defaultValue = "20") Long sinceIssues, @RequestParam(defaultValue = "2") Integer maxPages) {
        Project _project = projectService.getProject(id, maxPages);
        List<Commit> commits = _project.getCommits().stream()
               .filter(c -> LocalDateTime.parse(c.getCommitted_date().substring(0,23)).isAfter(LocalDateTime.now().minusDays(sinceCommits))).toList();
        List<Issue> issues = _project.getIssues().stream()
                .filter(i -> LocalDateTime.parse(i.getUpdatedAt().substring(0,23)).isAfter(LocalDateTime.now().minusDays(sinceIssues))).toList();
        Project filtredProject = new Project(_project.getId(), _project.getName(), _project.getWebUrl(), commits, issues);
        return filtredProject;
    }

}
