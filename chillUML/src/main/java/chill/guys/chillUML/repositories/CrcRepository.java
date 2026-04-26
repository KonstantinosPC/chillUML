package chill.guys.chillUML.repositories;

import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrcRepository extends JpaRepository<CRC, Integer> {
    Optional<CRC> findByCrcNameAndProjectId(String crcName, Project projectId);
    Optional<CRC> findById(int crcid);
}
