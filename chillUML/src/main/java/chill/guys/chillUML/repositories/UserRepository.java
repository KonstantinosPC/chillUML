package chill.guys.chillUML.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chill.guys.chillUML.domain.User;

import javax.swing.text.html.Option;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    Optional<User> deleteById(int id);
    Optional<User> findByEmail(String email);
}