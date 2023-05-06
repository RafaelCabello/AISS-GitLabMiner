package aiss.gitlabminer.service;

import aiss.gitlabminer.model.ProjectSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProjectServiceTest {

    @Autowired
    ProjectService service;
    @Test
    @DisplayName("Get all projects")
    void findAllProjects() {
        List<ProjectSearch> projects = service.findAllProjects();
        assertTrue(!projects.isEmpty(), "No projects");
        projects.forEach(p -> {
            System.out.println(p);
            System.out.println("************************************************************************************");
        });
    }
}