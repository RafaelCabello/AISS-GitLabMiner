package aiss.gitlabminer.service;


import aiss.gitlabminer.model.CommentSearch;
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

}
