package aiss.gitlabminer.service;

import aiss.gitlabminer.model.ProjectSearch;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    public List<ProjectSearch> findAllProjects() {

        /*
        String uri = "https://api.github.com/repos/spring-projects/spring-framework/commits";
        Commit[] commits = template.getForObject(uri, Commit[].class);
        return Arrays.stream(commits).toList();
        */

        String uri = "https://gitlab.com/api/v4/projects";
        ProjectSearch[] projectSearch = restTemplate.getForObject(uri, ProjectSearch[].class);
        return Arrays.stream(projectSearch).toList();
    }
}
