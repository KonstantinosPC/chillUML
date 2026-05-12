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
<<<<<<< Updated upstream
    List<UseCase> findByProjectID(Project project);
=======
    List<UseCase> findByProjectId(Project project);
>>>>>>> Stashed changes
    Optional<UseCase> findByUseCaseNameAndProjectId(String useCaseName, Project projectId);

}
