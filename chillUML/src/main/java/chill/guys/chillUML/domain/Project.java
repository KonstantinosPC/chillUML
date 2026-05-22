package chill.guys.chillUML.domain;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String projectName;

    @Column(name = "projectDescription")
    private String projectDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner")
    private User ownerId;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UseCase> useCases;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CRC> crcList;


    public int getId() {
        return id;
    }

    public String getProjectName(){
        return projectName;
    }

    public String getProjectDescription(){
        return projectDescription;
    }

    public User getOwnerId() {
        return ownerId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public void setOwner(User ownerId){
        this.ownerId = ownerId;
    }

    public List<UseCase> getUseCases() {
        return useCases;
    }

    public void setUseCases(List<UseCase> useCases) {
        this.useCases = useCases;
    }

    public List<CRC> getCrcList() {
        return crcList;
    }

    public void setCrcList(List<CRC> crcList) {
        this.crcList = crcList;
    }

    public void addCRC(CRC crc){
        this.crcList.add(crc);
    }

    public void addUseCase(UseCase useCase){
        this.useCases.add(useCase);
    }
}