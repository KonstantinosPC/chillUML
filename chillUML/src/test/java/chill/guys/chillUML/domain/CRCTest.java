package chill.guys.chillUML.domain;

import chill.guys.chillUML.repositories.CrcRepository;
import chill.guys.chillUML.repositories.ProjectRepository;
import chill.guys.chillUML.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CRCTest {

    @Autowired
    private CrcRepository crcRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCrcCreationAndRelationship() {

        User owner = new User();
        owner.setUsername("domainTester");
        userRepository.save(owner);

        Project project = new Project();
        project.setProjectName("UML Project");
        project.setOwner(owner);
        projectRepository.save(project);


        CRC crc = new CRC();
        crc.setCrcName("AuthService");
        crc.setProjectId(project);

        CRC savedCrc = crcRepository.save(crc);

        assertTrue(savedCrc.getCrcId()>0);
        assertEquals("AuthService", savedCrc.getCrcName());
        assertEquals("UML Project", savedCrc.getProjectId().getProjectName());
    }
}