package aiss.gitlabminer.service;

import aiss.gitlabminer.model.CommitSearch;
import aiss.gitlabminer.model.ProjectSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class CommitService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ProjectService service;

    public List<CommitSearch> findAllCommits() {
        List<CommitSearch> commits = new ArrayList<>();
        List<ProjectSearch> projects = service.findAllProjects();
        for(ProjectSearch project : projects){
            String uri = "https://gitlab.com/api/v4/projects/" + project.getId() + "/repository/commits";
            CommitSearch[] commitsSearch = restTemplate.getForObject(uri, CommitSearch[].class);
            commits.addAll(Arrays.stream(commitsSearch).toList());
        }
        return commits;
    }
}
