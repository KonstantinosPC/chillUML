package chill.guys.chillUML.domain;
import jakarta.persistence.*;

@Entity
@Table(name ="projects")


public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "owner")
    private String owner;

    public int getID(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getOwner(){return owner;}


    public void setName(String newName){
        this.name = newName;
    }
}
