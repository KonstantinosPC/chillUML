package chill.guys.chillUML.repositories;
<<<<<<< Updated upstream

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chill.guys.chillUML.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional <Project> findById(int id);
    Optional <Project> findByName(String name);
    Optional <Project> deleteById(int id);
=======
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
    @Query("select p from projects p where p.name = : projectName and p.owner_id = : owner_id")
    Optional<Project> findByIdWithProject(String projectName, int owner_id);
>>>>>>> Stashed changes
}
