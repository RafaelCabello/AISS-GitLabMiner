package aiss.gitlabminer.service;


import aiss.gitlabminer.model.CommentSearch;
import aiss.gitlabminer.model.IssueSearch;
import aiss.gitlabminer.model.ProjectSearch;
import aiss.gitlabminer.model.UserSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    RestTemplate restTemplate;

    /*
    @Autowired
    ProjectService projectService;
    @Autowired
    IssueService issueService;

    public List<CommentSearch> findAllComments() {

        List<CommentSearch> comments = new ArrayList<>();
        List<ProjectSearch> projects = projectService.findAllProjects();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+"glpat-NTBWo4LMEze4yssZBuzH");
        HttpEntity<CommentSearch[]> request = new HttpEntity<CommentSearch[]>(headers);

        for(ProjectSearch project: projects){
            List<IssueSearch> issues = issueService.findByProjectId(project.getId().toString());
            for(IssueSearch issue: issues){
                String uri = "https://gitlab.com/api/v4/projects/" + project.getId() + "/issues/" + issue.getIid() + "/notes";
                ResponseEntity<CommentSearch[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, CommentSearch[].class);
                CommentSearch[] commentsSearch = response.getBody();
                comments.addAll(Arrays.stream(commentsSearch).toList());
            }
        }
        return comments;
    }
     */

    public List<CommentSearch> findByProjectIssue(String projectId, String issueIid){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+"glpat-NTBWo4LMEze4yssZBuzH");
        HttpEntity<CommentSearch[]> request = new HttpEntity<CommentSearch[]>(headers);

        String uri = "https://gitlab.com/api/v4/projects/" + projectId + "/issues/" + issueIid + "/notes";
        ResponseEntity<CommentSearch[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, CommentSearch[].class);

        CommentSearch[] commentsSearch = response.getBody();
        return Arrays.stream(commentsSearch).toList();
    }

}
