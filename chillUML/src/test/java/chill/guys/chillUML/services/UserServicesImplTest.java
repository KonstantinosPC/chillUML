package chill.guys.chillUML.services;

import chill.guys.chillUML.DTO.RegistrationForm;
import chill.guys.chillUML.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Sql(
        scripts = "file:src/test/resources/import.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@SpringBootTest
@Transactional
public class UserServicesImplTest {

    @Autowired
    private UserServices userServices;

    @Test
    public void testFindByEmailFromImportSql() {

        Optional<User> user = userServices.findByEmail("christos@gmail.com");

        assertTrue(user.isPresent());
        assertEquals("christos", user.get().getUsername());
    }

    @Test
    public void testFindById() {
        Optional<User> user = userServices.findById(1);
        assertEquals("kgeorgiou", user.get().getUsername());

    }

    @Test
    public void testIsUserPresent() {
        assertTrue(userServices.isUserPresent("kgeorgiou"));

        assertFalse(userServices.isUserPresent("tranos"));
    }


    @Test
    public void testSaveAndLoginUserInH2() {


        RegistrationForm form = new RegistrationForm();
        form.setUsername("User1");
        form.setEmail("user1@gmail.com");
        form.setPassword("StrongPass123!");
        form.setConfirmPassword("StrongPass123!");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        userServices.saveUser(form, redirectAttributes);


        boolean loginSuccess = userServices.login("User1", "StrongPass123!");
        assertTrue(loginSuccess);


        boolean loginFail = userServices.login("User1", "WrongPass123!");
        assertFalse(loginFail);
    }
    @Test
    public void testSaveUser_ConflictsWithSqlData() {
        RegistrationForm form = new RegistrationForm();
        form.setUsername("willie");
        form.setEmail("newemail@gmail.com");
        form.setPassword("StrongPass123!");
        form.setConfirmPassword("StrongPass123!");

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        userServices.saveUser(form, redirectAttributes);

        assertEquals("This username already exists", redirectAttributes.getFlashAttributes().get("error"));
    }
    @Test
    public void testLoginHashedPassword() {
        boolean result = userServices.login("martin", "StrongPass123!");
        assertTrue(result);

    }
    @Test
    public void testStrongPassword(){

            RegistrationForm form = new RegistrationForm();
            form.setUsername("Bakasetas");
            form.setEmail("bak@gmail.com");
            form.setPassword("bazelos");
            form.setConfirmPassword("bazelos");

            RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
            userServices.saveUser(form, redirectAttributes);

            boolean isSaved = userServices.isUserPresent("Bakasetas");

            assertFalse(isSaved);
        }

}