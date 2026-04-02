package chill.guys.chillUML.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chill.guys.chillUML.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findById(int id);
    Project findByName(String name);
    Project deleteById(int id);
}
