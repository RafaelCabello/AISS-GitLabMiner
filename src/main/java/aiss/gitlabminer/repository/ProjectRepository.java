package aiss.gitlabminer.repository;

import aiss.gitlabminer.model.Project;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ProjectRepository {
    List<Project> projects = new ArrayList<>();

    public ProjectRepository() {
        projects.add(new Project(
                UUID.randomUUID().toString(),
                "Proyecto prueba 1",
                "proyectoprueba1.com",
                new ArrayList<>(),
                new ArrayList<>()
        ));

        projects.add(new Project(
                UUID.randomUUID().toString(),
                "Proyecto prueba 2",
                "proyectoprueba2.com",
                new ArrayList<>(),
                new ArrayList<>()
        ));
    }

    public List<Project> findAll() { return projects; };

    public Project findOne(String id) {
        return projects.stream()
                .filter(project -> project.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
