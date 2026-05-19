package chill.guys.chillUML.repositories;
import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.UseCase;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.services.ProjectServices;
import chill.guys.chillUML.services.ProjectServicesImpl;
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
public class UseCaseRepositoryTest {
    @Autowired
    private UseCaseRepository useCaseRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectServices projectServices;

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

        assertEquals("Actor1", found.get().getActors().get(0));
        assertEquals("Actor2", found.get().getActors().get(1));
        assertEquals("Actor3", found.get().getActors().get(2));

        assertEquals("MainFlow", found.get().getMainFlow());

        assertEquals("PostCondition", found.get().getPostflow());

        assertEquals("altcond1", found.get().getAltFlow().get(0));
        assertEquals("altcond2", found.get().getAltFlow().get(1));
        assertEquals("altcond3", found.get().getAltFlow().get(2));

    }

    @Test
    public void testDeleteUseCase() {
        User christos = userRepository.findByUsername("christos").orElseThrow();
        ProjectServices pj = new ProjectServicesImpl();

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
        UseCase uc = found.get();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();;
        projectServices.deleteUseCase(uc.getUseCaseId(), redirectAttributes);
        found = useCaseRepository.findByUseCaseNameAndProjectID("UC1",project);
        assertFalse(found.isPresent());

    }
}