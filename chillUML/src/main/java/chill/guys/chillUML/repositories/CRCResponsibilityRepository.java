package chill.guys.chillUML.repositories;

import chill.guys.chillUML.domain.CRCResponsibilities;
import chill.guys.chillUML.domain.UseCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CRCResponsibilityRepository extends JpaRepository<CRCResponsibilities, Integer> {
}
