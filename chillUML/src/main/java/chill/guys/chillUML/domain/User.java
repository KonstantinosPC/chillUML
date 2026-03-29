package chill.guys.chillUML.domain;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;


    public int getID(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public boolean verifyPassword(String newPassword){
        return newPassword.equals(this.password);
    }

    public void setEmail(String newEmail){
        this.email = newEmail;
    }

    public void setUsername(String newUsername){
        this.username = newUsername;
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }
}