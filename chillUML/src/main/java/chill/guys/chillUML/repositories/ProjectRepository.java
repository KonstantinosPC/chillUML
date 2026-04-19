package chill.guys.chillUML.repositories;

import java.util.List;
import java.util.Optional;

import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{
    Optional<Project> findById(int id);
    List <Optional<Project>> findByOwner(int owner_id);
    Optional<Project> findByName(String name);
    @Query("select p from Project p where p.projectName = : projectName and p.ownerId = : owner_id")
    Optional<Project> findByIdWithProject(String projectName, User owner_id);
}
