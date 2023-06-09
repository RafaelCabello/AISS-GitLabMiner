package aiss.gitlabminer.service;

import aiss.gitlabminer.model.CommitSearch;
import aiss.gitlabminer.model.Project;
import aiss.gitlabminer.model.ProjectSearch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Test
    @DisplayName("Get project with id = 45790011")
    void findProject() {
        ProjectSearch project = service.findOne(45790011);
        System.out.println(project);
    }

    @Test
    @DisplayName("Get complete project with id = 4207231")
    void getProjectTest() {
        Project project = service.getProject(4207231, 1);
        System.out.println(project);
    }
}