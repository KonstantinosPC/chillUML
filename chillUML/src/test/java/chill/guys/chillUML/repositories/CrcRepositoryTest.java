package chill.guys.chillUML.repositories;

import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.services.ProjectServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.ArrayList;
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

    @Autowired
    private UseCaseRepository useCaseRepository;

    @Autowired
    private ProjectServices projectServices;


    @Test
    public void testFindByCrcNameAndProjectID() {

        User christos = userRepository.findByUsername("christos").orElseThrow();


        Project project = projectRepository.findByProjectNameAndOwnerId("Chill UML Editor", christos).orElseThrow();

        CRC crc = new CRC();
        CRC crc1 = new CRC();
        CRC crc2 = new CRC();
        UseCase uc1 = new UseCase();
        UseCase uc2 = new UseCase();

        List<CRC> collab = new ArrayList<>();
        List<String> respons = new ArrayList<>();
        List<UseCase> usecases = new ArrayList<>();

        crc.setCrcName("LoginController");
        crc1.setCrcName("Collab1");
        crc2.setCrcName("Collab2");

        collab.add(crc1);
        collab.add(crc2);

        respons.add("Responsibility1");
        respons.add("Responsibility2");

        uc1.setUseCaseName("UC1");
        uc2.setUseCaseName("UC2");

        crc.setProjectId(project);

        crc.setCollaborators(collab);
        crc.setResponsibilities(respons);

        usecases.add(uc1);
        usecases.add(uc2);

        crc.setLinkedUseCases(usecases);
        crcRepository.save(crc);
        crcRepository.save(crc1);
        crcRepository.save(crc2);
        useCaseRepository.save(uc1);
        useCaseRepository.save(uc2);

        Optional <CRC> found = crcRepository.findByCrcNameAndProjectId("LoginController", project);

        assertTrue(found.isPresent());
        assertEquals("LoginController", found.get().getCrcName());

        assertEquals("Chill UML Editor", found.get().getProjectId().getProjectName());

        assertEquals("Collab1",found.get().getCollaborators().get(0).getCrcName());
        assertEquals("Collab2",found.get().getCollaborators().get(1).getCrcName());

        assertEquals("Responsibility1",found.get().getResponsibilities().get(0));
        assertEquals("Responsibility2",found.get().getResponsibilities().get(1));

        assertEquals("UC1",found.get().getLinkedUseCases().get(0).getUseCaseName());
        assertEquals("UC2",found.get().getLinkedUseCases().get(1).getUseCaseName());

    }

    @Test
    public void testFindById() {

        User test_user = userRepository.findByUsername("kgeorgiou").orElseThrow();
        Project BigProject = projectRepository.findByProjectNameAndOwnerId("Big Project", test_user).orElseThrow();

        CRC crc = new CRC();
        crc.setCrcName("DataEncryptor");
        crc.setProjectId(BigProject);
        crcRepository.save(crc);

        Optional<CRC> result = crcRepository.findById(1);

        assertTrue(result.isPresent());
        assertEquals("DataEncryptor", result.get().getCrcName());
    }

    @Test
    public void testDeleteCRC() {

        User christos = userRepository.findByUsername("christos").orElseThrow();


        Project project = projectRepository.findByProjectNameAndOwnerId("Chill UML Editor", christos).orElseThrow();

        CRC crc = new CRC();
        CRC crc1 = new CRC();
        CRC crc2 = new CRC();
        UseCase uc1 = new UseCase();
        UseCase uc2 = new UseCase();

        List<CRC> collab = new ArrayList<>();
        List<String> respons = new ArrayList<>();
        List<UseCase> usecases = new ArrayList<>();

        crc.setCrcName("LoginController");
        crc1.setCrcName("Collab1");
        crc2.setCrcName("Collab2");

        collab.add(crc1);
        collab.add(crc2);

        respons.add("Responsibility1");
        respons.add("Responsibility2");

        uc1.setUseCaseName("UC1");
        uc2.setUseCaseName("UC2");

        crc.setProjectId(project);

        crc.setCollaborators(collab);
        crc.setResponsibilities(respons);

        usecases.add(uc1);
        usecases.add(uc2);

        crc.setLinkedUseCases(usecases);
        crcRepository.save(crc);
        crcRepository.save(crc1);
        crcRepository.save(crc2);
        useCaseRepository.save(uc1);
        useCaseRepository.save(uc2);

        Optional <CRC> found = crcRepository.findByCrcNameAndProjectId("LoginController", project);

        assertTrue(found.isPresent());

        CRC testcrc = found.get();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        projectServices.deleteCrc(testcrc.getCrcId(),redirectAttributes);

        found = crcRepository.findByCrcNameAndProjectId("LoginController", project);
        assertFalse(found.isPresent());
    }
}