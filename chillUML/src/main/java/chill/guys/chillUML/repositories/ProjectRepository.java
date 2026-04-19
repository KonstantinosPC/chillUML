package chill.guys.chillUML.repositories;

import java.util.List;
import java.util.Optional;

import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
    Optional<Project> findByProjectName(String projectName);
    List <Project> findByOwnerId(User ownerId);
    @Query("select p from Project p where p.projectName = : projectName and p.ownerId = : owner")
    Optional<Project> findByProjectNameAndOwnerId(@Param("projectName") String projectName, @Param("owner") User owner);
}

