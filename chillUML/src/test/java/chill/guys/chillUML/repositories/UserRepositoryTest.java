package chill.guys.chillUML.repositories;
import org.springframework.transaction.annotation.Transactional;
import chill.guys.chillUML.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Sql(
        scripts = "file:src/test/resources/import.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testFindExistingUser(){

        Optional<User> result = userRepository.findByUsername("christos");

        assertTrue(result.isPresent());
        assertEquals("christos", result.get().getUsername());
        assertEquals("christos@gmail.com", result.get().getEmail());
    }

    @Test
    public void testFindNonExistingUser(){

        Optional<User> result = userRepository.findByUsername("random");

        assertFalse(result.isPresent());
    }
    @Test

    public void testDeleteUser() {

        int userId = 1;

        userRepository.deleteById(userId);
        Optional<User> deletedUser = userRepository.findById(userId);

        assertFalse(deletedUser.isPresent());
    }
}