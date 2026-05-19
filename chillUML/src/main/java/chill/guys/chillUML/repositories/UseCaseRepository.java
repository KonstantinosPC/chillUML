package chill.guys.chillUML.repositories;


import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import chill.guys.chillUML.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UseCaseRepository extends JpaRepository<UseCase, Integer> {
    Optional<UseCase> findById(int id);
    Optional<UseCase> findByUseCaseName(String useCaseName);
    List<UseCase> findByProject(Project project);
    Optional<UseCase> findByUseCaseNameAndProject(String useCaseName, Project project);


}
