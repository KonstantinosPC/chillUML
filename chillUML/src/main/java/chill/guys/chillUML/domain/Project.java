package chill.guys.chillUML.domain;
import jakarta.persistence.*;
<<<<<<< Updated upstream

@Entity
@Table(name ="projects")


=======
@Entity
@Table(name = "projects")
>>>>>>> Stashed changes
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

<<<<<<< Updated upstream
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
=======
    @Column(name = "name")
    private String projectName;

    @Column(name = "projectDescription")
    private String projectDescription;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private int ownerId;

    public int getId() {
        return id;
    }
    public String getProjectName(){
        return projectName;
    }

    public String getProjectDescription(){
        return projectDescription;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public void setOwnerId(int ownerId){
        this.ownerId = ownerId;
>>>>>>> Stashed changes
    }
}
