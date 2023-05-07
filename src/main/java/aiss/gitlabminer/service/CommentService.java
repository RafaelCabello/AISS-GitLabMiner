package aiss.gitlabminer.service;


import aiss.gitlabminer.model.*;
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

    public List<CommentSearch> findByProjectIssue(String projectId, String issueIid, Integer maxPages){
        /*
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+"glpat-NTBWo4LMEze4yssZBuzH");
        HttpEntity<CommentSearch[]> request = new HttpEntity<CommentSearch[]>(headers);

        String uri = "https://gitlab.com/api/v4/projects/" + projectId + "/issues/" + issueIid + "/notes";
        ResponseEntity<CommentSearch[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, CommentSearch[].class);

        CommentSearch[] commentsSearch = response.getBody();
        return Arrays.stream(commentsSearch).toList();
        */
        String uri = "https://gitlab.com/api/v4/projects/" + projectId + "/issues/" + issueIid + "/notes";

        String token = "glpat-NTBWo4LMEze4yssZBuzH";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<CommentSearch[]> request = new HttpEntity<>(null, headers);

        ResponseEntity<CommentSearch[]> response = restTemplate.exchange(uri, HttpMethod.GET, request, CommentSearch[].class);

        List<CommentSearch> comments = new ArrayList<>(Arrays.asList(response.getBody()));

        String nextUrl = getNextPageUrl(response.getHeaders());
        int i = 1;
        while (nextUrl != null && i < maxPages) {
            response = restTemplate.exchange(nextUrl, HttpMethod.GET, request, CommentSearch[].class);
            comments.addAll(new ArrayList<>(Arrays.asList(response.getBody())));
            nextUrl = getNextPageUrl(response.getHeaders());
            i++;
        }
        return comments;
    }

    private static String getNextPageUrl(HttpHeaders headers) {
        String result = null;

        // If there is no link header, return null
        List<String> linkHeader = headers.get("Link");
        if (linkHeader == null)
            return null;

        // If the header contains no links, return null
        String links = linkHeader.get(0);
        if (links == null || links.isEmpty())
            return null;

        // Return the next page URL or null if none.
        for (String token : links.split(", ")) {
            if (token.endsWith("rel=\"next\"")) {
                // Found the next page. This should look something like
                // <https://api.github.com/repos?page=3&per_page=100>; rel="next"
                int idx = token.indexOf('>');
                result = token.substring(1, idx);
                break;
            }
        }

        return result;
    }

}
