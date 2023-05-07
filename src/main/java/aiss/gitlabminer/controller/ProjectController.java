package aiss.gitlabminer.controller;

import aiss.gitlabminer.model.Project;
import aiss.gitlabminer.repository.ProjectRepository;
import aiss.gitlabminer.service.CommitService;
import aiss.gitlabminer.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/gitlabminer")
public class ProjectController {

    private final ProjectRepository repository;
    @Autowired
    RestTemplate template;
    @Autowired
    ProjectService projectService;

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    //POST http://localhost:8081/gitlabminer/{id}[?sinceCommits=5&sinceIssues=30&maxPages=2]
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public Project create(@PathVariable Integer id) {
        Project _project = projectService.getProject(id);

        String uri = "http://localhost:8080/gitminer";
        Project createdProject = template.postForObject(uri, _project, Project.class);
        return createdProject;

        /*
        Project _project = projectRepository.save(new Project(project.getId(), project.getName(), project.getWebUrl(),
                project.getCommits(), project.getIssues()));

         */
    }

    //GET http://localhost:8081/gitlabminer/{id}
    @GetMapping("/{id}")
    public Project findProject(@PathVariable Integer id) {
        Project _project = projectService.getProject(id);

        //String uri = "http://localhost:8080/gitminer/" + id.toString();
        //Project createdProject = template.postForObject(uri, _project, Project.class);
        return _project;
    }

    //GET http://localhost:8081/gitlabminer/projects
    @GetMapping("/projects")
    public List<Project> findAll() {
        return repository.findAll();
    }

    //GET http://localhost:8081/gitlabminer/projects/{id}
    @GetMapping("/projects/{id}")
    public Project findOne(@PathVariable String id) {
        return repository.findOne(id);
    }

}
