package chill.guys.chillUML.repositories;

import java.util.List;
import java.util.Optional;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
    Optional<Project> findByProjectName(String projectName);
    List <Project> findByOwnerId(User ownerId);
    Optional<Project> findByProjectNameAndOwnerId(String projectName, User ownerId);
}
