package aiss.gitlabminer.service;

import aiss.gitlabminer.model.CommitSearch;
import aiss.gitlabminer.model.IssueSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class IssueServiceTest {

    @Autowired
    IssueService service;
    @Test
    @DisplayName("Get all issues")
    void findAllIssues() {
        List<IssueSearch> issues = service.findAllIssues();
        assertTrue(!issues.isEmpty(), "No issues");
        issues.forEach(i-> {
            System.out.println(i);
            System.out.println("*************************************************************************************");
        });
    }

}
