package chill.guys.chillUML.DatagramGenerator;

import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.CrcRepository;
import chill.guys.chillUML.repositories.ProjectRepository;
import chill.guys.chillUML.repositories.UseCaseRepository;
import chill.guys.chillUML.repositories.UserRepository;
import chill.guys.chillUML.services.ProjectServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProjectServicesImplDiagramTest {

    @Autowired
    private ProjectServices projectServices;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UseCaseRepository useCaseRepository;

    @Autowired
    private CrcRepository crcRepository;

    private Project testProject;

    @BeforeEach
    public void setUp() {

        User testUser = new User();
        testUser.setUsername("diagram_tester");
        testUser.setEmail("tester@example.com");
        testUser.setPassword("password");
        testUser = userRepository.save(testUser);


        testProject = new Project();
        testProject.setProjectName("DiagramProject");
        testProject.setProjectDescription("Testing diagram generation string output");
        testProject.setOwner(testUser);
        testProject.setUseCases(new ArrayList<>());
        testProject.setCrcList(new ArrayList<>());
        testProject = projectRepository.save(testProject);


        UseCase uc1 = new UseCase();
        uc1.setUseCaseName("LoginUseCase");
        uc1.setProject(testProject);
        uc1.setActors(new ArrayList<>(List.of("Admin", "Customer")));
        uc1.setPrecond(new ArrayList<>(List.of("User is on home page")));
        uc1.setMainFlow("1. User enters credentials");
        uc1.setPostflow("User is authenticated");
        useCaseRepository.save(uc1);

        UseCase uc2 = new UseCase();
        uc2.setUseCaseName("CheckoutUseCase");
        uc2.setProject(testProject);
        uc2.setActors(new ArrayList<>(List.of("Customer", "System")));
        uc2.setPrecond(new ArrayList<>(List.of("Cart is not empty")));
        uc2.setMainFlow("1. User hits purchase");
        uc2.setPostflow("Order placed");
        useCaseRepository.save(uc2);


        CRC crc1 = new CRC();
        crc1.setCrcName("UserManager");
        crc1.setProject(testProject);
        crc1.setResponsibilities(new ArrayList<>(List.of("Authenticates user")));
        crc1.setLinkedUseCases(new ArrayList<>(List.of(uc1)));

        CRC crc2 = new CRC();
        crc2.setCrcName("DatabaseBroker");
        crc2.setProject(testProject);
        crc2.setResponsibilities(new ArrayList<>(List.of("Fetches user data")));
        crc2.setLinkedUseCases(new ArrayList<>(List.of(uc1)));


        crc1 = crcRepository.save(crc1);
        crc2 = crcRepository.save(crc2);


        crc1.setCollaborators(new ArrayList<>(List.of(crc2)));
        crc2.setCollaborators(new ArrayList<>(List.of(crc1)));


        crcRepository.save(crc1);
        crcRepository.save(crc2);
    }

    @Test
    public void testGeneratePlantUMLUseCaseDiagram() {

        String result = projectServices.generateUsecaseDiagram("plantUml", testProject);

        assertNotNull(result);


        assertTrue(result.contains("@startuml"));
        assertTrue(result.contains("left to right direction"));
        assertTrue(result.contains("@enduml"));


        assertTrue(result.contains(":Admin: --> (LoginUseCase)"));
        assertTrue(result.contains(":Customer: --> (LoginUseCase)"));
        assertTrue(result.contains(":Customer: --> (CheckoutUseCase)"));
        assertTrue(result.contains(":System: --> (CheckoutUseCase)"));
    }

    @Test
    public void testGenerateNomnomlUseCaseDiagram() {

        String result = projectServices.generateUsecaseDiagram("nomnoml", testProject);

        assertNotNull(result);


        assertTrue(result.contains("#direction: right"));
        assertTrue(result.contains("bendSize: 0.3"));


        assertTrue(result.contains("[<actor>Admin]"));
        assertTrue(result.contains("[Admin] -> [LoginUseCase]"));

        assertTrue(result.contains("[<actor>Customer]"));
        assertTrue(result.contains("[Customer] -> [LoginUseCase]"));
        assertTrue(result.contains("[Customer] -> [CheckoutUseCase]"));

        assertTrue(result.contains("[<actor>System]"));
        assertTrue(result.contains("[System] -> [CheckoutUseCase]"));
    }

    @Test
    public void testGeneratePlantUMLClassDiagram() {

        String result = projectServices.generateClassDiagram("plantUml", testProject);

        assertNotNull(result);

        assertTrue(result.contains("@startuml"));
        assertTrue(result.contains("left to right direction"));


        assertTrue(result.contains("UserManager -- DatabaseBroker"));
        assertTrue(result.contains("DatabaseBroker -- UserManager"));
    }

    @Test
    public void testGenerateNomnomlClassDiagram() {

        String result = projectServices.generateClassDiagram("nomnoml", testProject);

        assertNotNull(result);

        assertTrue(result.contains("#direction: right"));
        assertTrue(result.contains("bendSize: 0.3"));


        assertTrue(result.contains("[UserManager] -> [DatabaseBroker]"));
        assertTrue(result.contains("[DatabaseBroker] -> [UserManager]"));
    }
}