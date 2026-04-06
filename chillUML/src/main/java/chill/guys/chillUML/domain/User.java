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

    @Column(name = "profilePicture")
    private String profilePicture;

    public int getID(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

<<<<<<< Updated upstream
    public String getProfilePicture() {
        return profilePicture;
=======
    public String getProfilePicture(){
        return profilePicture;
    }

    public String getPassword(){
        return this.password;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    public void  setProfilePicture(String newProfilPicture){
        this.profilePicture = newProfilPicture;
=======
    public void setProfilePicture(String newProfilePicture) {
        this.profilePicture = newProfilePicture;
>>>>>>> Stashed changes
    }
}