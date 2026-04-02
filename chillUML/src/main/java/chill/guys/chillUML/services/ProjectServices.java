package chill.guys.chillUML.services;
import chill.guys.chillUML.domain.Project;
import java.util.Optional;

import java.util.List;

public interface ProjectServices {
    List<Project> viewAllProjects(int userId);
    void createProject(String projectName);
    void editProject(String newProjectName);
    void deleteProject(String projectName);
    Optional <Project> findByName(String projectName);
}
