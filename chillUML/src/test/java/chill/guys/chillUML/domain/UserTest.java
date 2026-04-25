package chill.guys.chillUML.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {


        user = new User();
    }

    @Test
    public void testGettersAndSetters() {
        user.setUsername("testUser");
        user.setEmail("test@test.com");
        user.setPassword("mySecretHash123");
        user.setProfilePicture("image1");


        assertEquals("testUser", user.getUsername());
        assertEquals("test@test.com", user.getEmail());

        assertEquals("mySecretHash123", user.getPassword());
        assertEquals("image1", user.getProfilePicture());

    }
}