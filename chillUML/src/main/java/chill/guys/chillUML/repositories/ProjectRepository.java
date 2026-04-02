package chill.guys.chillUML.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chill.guys.chillUML.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional <Project> findById(int id);
    Optional <Project> findByName(String name);
    Optional <Project> deleteById(int id);
}
