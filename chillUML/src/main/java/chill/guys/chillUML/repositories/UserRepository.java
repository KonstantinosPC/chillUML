package chill.guys.chillUML.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chill.guys.chillUML.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    void deleteById(int id);
    Optional<User> findByEmail(String email);
}