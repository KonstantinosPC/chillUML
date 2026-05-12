package chill.guys.chillUML.services;

import chill.guys.chillUML.DTO.ProjectDTO;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.ProjectRepository;
import chill.guys.chillUML.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Sql(
        scripts = "file:src/test/resources/import.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@SpringBootTest
@Transactional
public class ProjectServicesImplTest {

    @Autowired
    private ProjectServices projectServices;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = userRepository.findByUsername("christos").orElseThrow();
    }

    @Test
    public void testCreateProject_SuccessAndFailures() {
        RedirectAttributes attributes = new RedirectAttributesModelMap();


        ProjectDTO dto = new ProjectDTO();
        dto.setProjectName("ProjectUM");
        dto.setProjectDescription("A very nice project");
        dto.setOwnerId(testUser);

        projectServices.createProject(dto, attributes);
        assertEquals("The project ProjectUM has been created successfully", attributes.getFlashAttributes().get("success"));


        assertTrue(projectRepository.findByProjectNameAndOwnerId("ProjectUM", testUser).isPresent());


        attributes = new RedirectAttributesModelMap();
        dto.setProjectName("ThisNameIsWayTooLong123");
        projectServices.createProject(dto, attributes);
        assertEquals("This project name is over 15 characters long", attributes.getFlashAttributes().get("error"));

        attributes = new RedirectAttributesModelMap();
        dto.setProjectName("Bad@Name!");
        projectServices.createProject(dto, attributes);
        assertEquals("The project name must not contain special characters.", attributes.getFlashAttributes().get("error"));

        attributes = new RedirectAttributesModelMap();
        dto.setProjectName("ProjectUM");
        projectServices.createProject(dto, attributes);
        assertEquals("There is already a project with this name", attributes.getFlashAttributes().get("error"));
    }

    @Test
    public void testViewAllProjects() {

        Project p1 = new Project();
        p1.setProjectName("Test 1");
        p1.setOwner(testUser);
        projectRepository.save(p1);

        Project p2 = new Project();
        p2.setProjectName("Test 2");
        p2.setOwner(testUser);
        projectRepository.save(p2);


        List<Project> projects = projectServices.viewAllProjects(testUser);
        assertFalse(projects.isEmpty());
    }

    @Test
    public void testDeleteProject() {

        Project p = new Project();
        p.setProjectName("DelProject");
        p.setOwner(testUser);
        p=projectRepository.save(p);


        projectServices.deleteProject(p.getId());

        Optional<Project> deleted = projectRepository.findById(p.getId());
        assertTrue(deleted.isEmpty());
    }

    @Test
    public void testEditProjectName_SuccessAndFailures() {
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        Project p = new Project();
        p.setProjectName("OldName");
        p.setOwner(testUser);
        p = projectRepository.save(p);

        projectServices.editProjectName(p.getId(), "NewValidName", attributes);


        Project updatedProject = projectRepository.findById(p.getId()).orElseThrow();
        assertEquals("NewValidName", updatedProject.getProjectName());


        projectServices.editProjectName(p.getId(), "Projectttttttt12222", attributes);
        assertEquals("This project name is over 15 characters long", attributes.getFlashAttributes().get("error"));


        attributes = new RedirectAttributesModelMap();
        projectServices.editProjectName(p.getId(), "Project!", attributes);
        assertEquals("The project name must not contain special characters.", attributes.getFlashAttributes().get("error"));
    }

    @Test
    public void testEditProjectDescription() {
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        Project p = new Project();
        p.setProjectName("ChillProject");
        p.setProjectDescription("A very detailed UMl class diagram");
        p.setOwner(testUser);
        p = projectRepository.save(p);

        projectServices.editProjectDescription(p.getId(), "Short and chill description", attributes);

        Project updatedProject = projectRepository.findById(p.getId()).orElseThrow();
        assertEquals("Short and chill description", updatedProject.getProjectDescription());
    }
}