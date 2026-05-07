package chill.guys.chillUML.repositories;

import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Sql(
        scripts = "file:src/test/resources/import.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@SpringBootTest
@Transactional
public class CrcRepositoryTest {

    @Autowired
    private CrcRepository crcRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByCrcNameAndProjectID() {

        User christos = userRepository.findByUsername("christos").orElseThrow();


        Project project = projectRepository.findByProjectNameAndOwnerId("Chill UML Editor", christos).orElseThrow();

        CRC crc = new CRC();
        crc.setCrcName("LoginController");
        crc.setProjectID(project);
        crcRepository.save(crc);

        Optional<CRC> found = crcRepository.findByCrcNameAndProjectID("LoginController", project);

        assertTrue(found.isPresent());
        assertEquals("LoginController", found.get().getCrcName());

        assertEquals("Chill UML Editor", found.get().getProjectID().getProjectName());
    }

    @Test
    public void testFindById() {

        User kgeorgiou = userRepository.findByUsername("kgeorgiou").orElseThrow();
        Project BigProject = projectRepository.findByProjectNameAndOwnerId("Big Project", kgeorgiou).orElseThrow();

        CRC crc = new CRC();
        crc.setCrcName("DataEncryptor");
        crc.setProjectID(BigProject);
        crcRepository.save(crc);

        Optional<CRC> result = crcRepository.findById(1);

        assertTrue(result.isPresent());
        assertEquals("DataEncryptor", result.get().getCrcName());
    }
}