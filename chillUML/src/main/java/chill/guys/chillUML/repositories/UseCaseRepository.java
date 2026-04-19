package chill.guys.chillUML.repositories;


import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UseCaseRepository extends JpaRepository<UseCase, Integer> {
    Optional<UseCase> findById(int id);
    List<UseCase> findByProjectID(Project project);
    @Query("SELECT uc FROM UseCase uc WHERE uc.useCaseName = : useCaseName")
    Optional<UseCase> findByUseCaseName(@Param("useCaseName") String useCaseName);
}
