package chill.guys.chillUML.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chill.guys.chillUML.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findById(int id);
    User findByUsername(String username);
    User deleteById(int id);
    User findByEmail(String email);
}