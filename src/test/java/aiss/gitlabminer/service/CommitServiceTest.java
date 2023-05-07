package aiss.gitlabminer.service;

import aiss.gitlabminer.model.CommitSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CommitServiceTest {

    @Autowired
    CommitService service;

    /*
    @Test
    @DisplayName("Get all commits")
    void findAllCommits() {
        List<CommitSearch> commits = service.findAllCommits();
        assertTrue(!commits.isEmpty(), "No commits");
        commits.forEach(c-> {
            System.out.println(c);
            System.out.println("***********************************************************************************");
        });
    }

     */

    @Test
    @DisplayName("Get all commits of project 4207231")
    void findProjectCommits() {
        List<CommitSearch> commits = service.findProjectCommits("4207231", 1);
        assertTrue(!commits.isEmpty(), "No commits");
        commits.forEach(c-> {
            System.out.println(c);
            System.out.println("***********************************************************************************");
        });
    }
}
