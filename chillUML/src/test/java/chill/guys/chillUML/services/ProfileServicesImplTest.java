package chill.guys.chillUML.services;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.junit.jupiter.api.Assertions.*;

@Sql(
        scripts = "file:src/test/resources/import.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@SpringBootTest
@Transactional
@Import(ProfileServicesImpl.class)

class ProfileServicesImplTest {

    @Autowired
    private ProfileServices profileServices;

    @Autowired
    @Qualifier("profileServicesImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setUsername("testProfileUser");
        testUser.setEmail("testprofile@gmail.com");
        testUser.setPassword(passwordEncoder.encode("OldPass123!"));
        userRepository.save(testUser);
    }

    @Test
    public void testChangeUsername_SuccessAndFailures() {
        RedirectAttributes attributes = new RedirectAttributesModelMap();


        boolean isSuccess = profileServices.changeUsername(testUser, "newValidName", attributes);
        assertTrue(isSuccess);
        assertEquals("newValidName", testUser.getUsername());
        assertEquals("Your username has changed successfully", attributes.getFlashAttributes().get("success"));


        boolean isTaken = profileServices.changeUsername(testUser, "martin", attributes);
        assertFalse(isTaken, "Η μέθοδος έπρεπε να επιστρέψει false γιατί το όνομα υπάρχει");
        assertEquals("This username already exists", attributes.getFlashAttributes().get("error"));


        boolean isTooLong = profileServices.changeUsername(testUser, "ThisNameIsWayTooLong123", attributes);
        assertFalse(isTooLong, "Η μέθοδος έπρεπε να επιστρέψει false λόγω μεγέθους");
        assertEquals("This username is over 15 characters long", attributes.getFlashAttributes().get("error"));
    }

    @Test
    public void testChangeEmail_SuccessAndFailure() {
        RedirectAttributes attributes = new RedirectAttributesModelMap();


        profileServices.changeEmail(testUser, "brandnew@gmail.com", attributes);
        assertEquals("brandnew@gmail.com", testUser.getEmail());
        assertEquals("Your email has changed successfully", attributes.getFlashAttributes().get("success"));


        profileServices.changeEmail(testUser, "christos@gmail.com", attributes);
        assertEquals("This email is already in use", attributes.getFlashAttributes().get("error"));
    }

    @Test
    public void testChangePassword_Success() {
        RedirectAttributes attributes = new RedirectAttributesModelMap();


        profileServices.changePassword(testUser, "OldPass123!", "NewPass123!", "NewPass123!", attributes);

        assertEquals("Password changed successfully", attributes.getFlashAttributes().get("success"));
        assertTrue(passwordEncoder.matches("NewPass123!", testUser.getPassword()));
    }

    @Test
    public void testChangePassword_CrucialFailures() {
        RedirectAttributes attributes = new RedirectAttributesModelMap();


        profileServices.changePassword(testUser, "WRONG_PASS", "NewPass123!", "NewPass123!", attributes);
        assertEquals("Old Password is Wrong", attributes.getFlashAttributes().get("error"));


        profileServices.changePassword(testUser, "OldPass123!", "NoSpecialChar1", "NoSpecialChar1", attributes);
        assertEquals("Please use at least one special character", attributes.getFlashAttributes().get("error"));


        profileServices.changePassword(testUser, "OldPass123!", "NewPass123!", "Different123!", attributes);
        assertEquals("Confirmation password not the same as the new password", attributes.getFlashAttributes().get("error"));
    }

    @Test
    public void testLoadUserByUsername() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("testProfileUser");

        assertNotNull(userDetails);
        assertEquals("testProfileUser", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("NonExistentUser");
        });
    }
}