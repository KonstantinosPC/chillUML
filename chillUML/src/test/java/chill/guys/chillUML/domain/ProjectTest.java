package chill.guys.chillUML.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    @Test
    public void testProjectGettersAndSetters() {

        User mockOwner = new User();
        mockOwner.setUsername("chillGuy");

        Project project = new Project();
        project.setProjectName("UML Masterpiece");
        project.setProjectDescription("A very chill UML diagram");
        project.setOwner(mockOwner);

        assertEquals("UML Masterpiece", project.getProjectName());
        assertEquals("A very chill UML diagram", project.getProjectDescription());


        assertNotNull(project.getOwnerId());
        assertEquals("chillGuy", project.getOwnerId().getUsername());
    }
}