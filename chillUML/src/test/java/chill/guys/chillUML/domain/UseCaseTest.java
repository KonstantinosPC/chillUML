package chill.guys.chillUML.domain;

import chill.guys.chillUML.repositories.CrcRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UseCaseTest {
    @Test
    public void testUseCaseRequirementsModeling() {
        UseCase loginUseCase = new UseCase();
        List<String> actors = new ArrayList<>();
        actors.add("Registered User");
        loginUseCase.setUseCaseName("User Authentication");
        loginUseCase.setActors(actors);
        loginUseCase.setMainFlow("User fill his credentials");
        loginUseCase.setPostflow("System gives access to the user's profile and the user is redirected to the dashboard.");


        List<String> conditions = Arrays.asList(
                "User must have an active account");
        loginUseCase.setPrecond(conditions);


        List<String> altFlows = Arrays.asList(
                "2a. Invalid password: System shows error message.",
                "2b. Account locked: System prompts for reset."
        );
        loginUseCase.setAltFlow(altFlows);


        assertEquals("User Authentication", loginUseCase.getUseCaseName());
        assertEquals("Registered User", loginUseCase.getActors().get(0));


        assertEquals(1, loginUseCase.getPrecond().size());
        String firstAlt = loginUseCase.getAltFlow().get(0);
        assertEquals("2a. Invalid password: System shows error message.", firstAlt);


        assertEquals("System gives access to the user's profile and the user is redirected to the dashboard.", loginUseCase.getPostflow());
    }

    @Test
    public void testUseCaseProjectAssociation() {
        UseCase uc = new UseCase();
        Project umlProject = new Project();
        umlProject.setProjectName("Security Module Design");

        uc.setProject(umlProject);
        assertNotNull(uc.getProject());
        assertEquals("Security Module Design", uc.getProject().getProjectName());
    }

}