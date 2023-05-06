package aiss.gitlabminer.service;

import aiss.gitlabminer.model.IssueSearch;
import aiss.gitlabminer.model.ProjectSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ProjectService service;

    public List<IssueSearch> findAllIssues() {
        List<IssueSearch> issues = new ArrayList<>();
        List<ProjectSearch> projects = service.findAllProjects();
        for(ProjectSearch project : projects){
            String uri = "https://gitlab.com/api/v4/projects/" + project.getId() + "/issues";
            IssueSearch[] issuesSearch = restTemplate.getForObject(uri, IssueSearch[].class);
            issues.addAll(Arrays.stream(issuesSearch).toList());
        }
        return issues;
    }

    public List<IssueSearch> findByProjectId(Integer id){
        String uri = "https://gitlab.com/api/v4/projects/" + id + "/issues";
        IssueSearch[] issuesSearch = restTemplate.getForObject(uri, IssueSearch[].class);
        return Arrays.stream(issuesSearch).toList();
    }

}
