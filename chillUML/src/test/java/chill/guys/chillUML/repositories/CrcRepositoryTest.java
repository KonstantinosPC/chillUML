package chill.guys.chillUML.repositories;

import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
<<<<<<< Updated upstream
        crc.setProjectID(project);
=======
        crc.setProjectId(project);
>>>>>>> Stashed changes
        crcRepository.save(crc);

        Optional <CRC> found = crcRepository.findByCrcNameAndProjectId("LoginController", project);

        assertTrue(found.isPresent());
        assertEquals("LoginController", found.get().getCrcName());

<<<<<<< Updated upstream
        assertEquals("Chill UML Editor", found.get().getProjectID().getProjectName());
=======
        assertEquals("Chill UML Editor", found.get().getProjectId().getProjectName());
>>>>>>> Stashed changes
    }

    @Test
    public void testFindById() {

        User test_user = userRepository.findByUsername("kgeorgiou").orElseThrow();
        Project BigProject = projectRepository.findByProjectNameAndOwnerId("Big Project", test_user).orElseThrow();

        CRC crc = new CRC();
        crc.setCrcName("DataEncryptor");
<<<<<<< Updated upstream
        crc.setProjectID(BigProject);
=======
        crc.setProjectId(BigProject);
>>>>>>> Stashed changes
        crcRepository.save(crc);

        Optional<CRC> result = crcRepository.findById(1);

        assertTrue(result.isPresent());
        assertEquals("DataEncryptor", result.get().getCrcName());
    }
}