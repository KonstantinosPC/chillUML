package chill.guys.chillUML.repositories;

import chill.guys.chillUML.domain.CRCCollaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CRCCollaboratorRepository extends JpaRepository<CRCCollaborator, Integer> {
}
