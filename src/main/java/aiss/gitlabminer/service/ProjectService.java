package aiss.gitlabminer.service;

import aiss.gitlabminer.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    CommitService commitService;

    @Autowired
    IssueService issueService;

    @Autowired
    CommentService commentService;

    public List<ProjectSearch> findAllProjects() {

        String uri = "https://gitlab.com/api/v4/projects";
        ProjectSearch[] projectSearch = restTemplate.getForObject(uri, ProjectSearch[].class);
        return Arrays.stream(projectSearch).toList();
    }

    public ProjectSearch findOne(Integer id) {
        String uri = "https://gitlab.com/api/v4/projects/" + id.toString();
        ProjectSearch projectSearch = restTemplate.getForObject(uri, ProjectSearch.class);
        return projectSearch;
    }

    public Project getProject(Integer id, Integer maxPages) {

        String uri = "https://gitlab.com/api/v4/projects/" + id.toString();
        ProjectSearch projectSearch = restTemplate.getForObject(uri, ProjectSearch.class);
        Project project = new Project(projectSearch.getId().toString(), projectSearch.getName(), projectSearch.getWebUrl());

        //Add commits
        List<CommitSearch> commitsSearch = commitService.findProjectCommits(project.getId(), maxPages);
        List<Commit> commits = new ArrayList<>();
        for (CommitSearch commitSearch: commitsSearch) {
            Commit newCommit = new Commit(
                    commitSearch.getId(),
                    commitSearch.getTitle(),
                    commitSearch.getMessage(),
                    commitSearch.getAuthorName(),
                    commitSearch.getAuthorEmail(),
                    commitSearch.getAuthoredDate(),
                    commitSearch.getCommitterName(),
                    commitSearch.getCommitterEmail(),
                    commitSearch.getCommittedDate(),
                    commitSearch.getWebUrl()
            );
            commits.add(newCommit);
        }
        project.setCommits(commits);

        //Add issues

        List<IssueSearch> issuesSearch = issueService.findByProjectId(project.getId(), maxPages);
        List<Issue> issues = new ArrayList<>();
        for (IssueSearch issueSearch: issuesSearch) {
            String closed = null;
            if (issueSearch.getClosedAt() != null){
                closed = issueSearch.getClosedAt().toString();
            }
            Issue newIssue = new Issue(issueSearch.getId().toString(),
                    issueSearch.getIid().toString(),
                    issueSearch.getTitle(),
                    issueSearch.getDescription(),
                    issueSearch.getState(),
                    issueSearch.getCreatedAt(),
                    issueSearch.getUpdatedAt(),
                    closed,
                    issueSearch.getLabels(),
                    issueSearch.getAuthor(),
                    issueSearch.getAssignee(),
                    issueSearch.getUpvotes(),
                    issueSearch.getDownvotes(),
                    issueSearch.getWebUrl());
            //Add comments
            List<CommentSearch> commentsSearch = commentService.findByProjectIssue(project.getId(), newIssue.getRefId().toString(), maxPages);
            List<Comment> comments = new ArrayList<>();
            for (CommentSearch commentSearch: commentsSearch) {
                Comment newComment = new Comment(
                        commentSearch.getId().toString(),
                        commentSearch.getBody(),
                        commentSearch.getCreatedAt(),
                        commentSearch.getUpdatedAt(),
                        commentSearch.getAuthor()
                );
                comments.add(newComment);
            }
            newIssue.setComments(comments);
            issues.add(newIssue);
        }
        project.setIssues(issues);
        return project;
    }

}
