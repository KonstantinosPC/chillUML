package chill.guys.chillUML.repositories;

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
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindByProjectName() {

        Optional<Project> result = projectRepository.findByProjectName("Chill UML Editor");

        assertTrue(result.isPresent());
        assertEquals("Chill UML Editor", result.get().getProjectName());
        assertEquals("A web based UML creator", result.get().getProjectDescription());

        assertEquals("christos", result.get().getOwnerId().getUsername());
    }

    @Test
    public void testFindByProjectName_NotFound() {
        Optional<Project> result = projectRepository.findByProjectName("Project1");

        assertFalse(result.isPresent());
    }

    @Test
    public void testFindByOwnerId() {
        Optional<User> owner = userRepository.findByUsername("christos");
        assertTrue(owner.isPresent());

        List<Project> projects = projectRepository.findByOwnerId(owner.get());


        assertFalse(projects.isEmpty());
        assertEquals(2, projects.size());


        assertTrue(projects.stream().anyMatch(p -> p.getProjectName().equals("Database Schema")));
    }

    @Test
    public void testFindByProjectNameAndOwnerId() {

        Optional<User> owner1 = userRepository.findByUsername("christos");
        Optional<User> owner2 = userRepository.findByUsername("kgeorgiou");


        Optional<Project> successResult = projectRepository.findByProjectNameAndOwnerId("Chill UML Editor", owner1.get());
        assertTrue(successResult.isPresent());
        assertEquals("Chill UML Editor", successResult.get().getProjectName());

        Optional<Project> failResult = projectRepository.findByProjectNameAndOwnerId("Chill UML Editor", owner2.get());
        assertFalse(failResult.isPresent());
    }
}