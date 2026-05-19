package chill.guys.chillUML.repositories;
import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.UseCase;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

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
public class UseCaseRepositoryTest {
    @Autowired
    private UseCaseRepository useCaseRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUseCaseNameAndProjectID() {

        User christos = userRepository.findByUsername("christos").orElseThrow();


        Project project = projectRepository.findByProjectNameAndOwnerId("Chill UML Editor", christos).orElseThrow();
        List<String> precond = new ArrayList<>();
        List<String> actors = new ArrayList<>();
        List<String> altcond = new ArrayList<>();

        precond.add("Precondition1");
        precond.add("Precondition2");
        precond.add("Precondition3");

        actors.add("Actor1");
        actors.add("Actor2");
        actors.add("Actor3");

        altcond.add("altcond1");
        altcond.add("altcond2");
        altcond.add("altcond3");

        UseCase usecase = new UseCase();

        usecase.setUseCaseName("UC1");
        usecase.setPrecond(precond);
        usecase.setActors(actors);

        usecase.setMainFlow("MainFlow");
        usecase.setPostflow("PostCondition");

        usecase.setAltFlow(altcond);
        usecase.setProjectId(project);


        useCaseRepository.save(usecase);

        Optional <UseCase> found = useCaseRepository.findByUseCaseNameAndProjectID("UC1",project);

        assertTrue(found.isPresent());
        assertEquals("UC1", found.get().getUseCaseName());

        assertEquals("Chill UML Editor", found.get().getProjectId().getProjectName());

        assertEquals("Precondition1", found.get().getPrecond().get(0));
        assertEquals("Precondition2", found.get().getPrecond().get(1));
        assertEquals("Precondition3", found.get().getPrecond().get(2));



    }
}
