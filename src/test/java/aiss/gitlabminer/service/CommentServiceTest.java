package aiss.gitlabminer.service;


import aiss.gitlabminer.model.CommentSearch;
import aiss.gitlabminer.model.CommitSearch;
import aiss.gitlabminer.model.ProjectSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    CommentService service;

    /*
    @Test
    @DisplayName("Get all comments")
    void findAllComments() {
        List<CommentSearch> comments = service.findAllComments();
        assertTrue(!comments.isEmpty(), "No comments");
        comments.forEach(c -> {
            System.out.println(c);
            System.out.println("********************************************************************************");
        });
    }
     */

    @Test
    @DisplayName("Get all comments of issue 127556169 (iid = 2395) of project 4207231")
    void findIssueComments() {
        List<CommentSearch> comments = service.findByProjectIssue("4207231", "2395");
        assertTrue(!comments.isEmpty(), "No comments");
        comments.forEach(c-> {
            System.out.println(c);
            System.out.println("***********************************************************************************");
        });
    }
}
