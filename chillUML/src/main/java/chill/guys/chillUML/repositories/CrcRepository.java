package chill.guys.chillUML.repositories;

import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrcRepository extends JpaRepository<CRC, Integer> {
    Optional<CRC> findByCrcNameAndProjectID(String crcName, Project projectId);
    Optional<CRC> findById(int crcid);
}
