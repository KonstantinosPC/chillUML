package chill.guys.chillUML.domain;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "crc")
public class CRC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crc_id")
    private int crcId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "crc_name")
    private String crcName;

    @ElementCollection
    @CollectionTable(name = "crc_responsibilities", joinColumns = @JoinColumn(name = "crc_id"))
    private List<String> responsibilities;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "crc_linked", joinColumns = @JoinColumn(name = "crc_id"), inverseJoinColumns = @JoinColumn(name = "usecase_id"))
    private List<UseCase> linkedUseCases;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "crc_collaborators", joinColumns = @JoinColumn(name = "crc_id"),inverseJoinColumns = @JoinColumn(name = "collaborator_id"))
    private List<CRC> collaborators;

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<String> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public List<UseCase> getLinkedUseCases() {
        return linkedUseCases;
    }

    public void setLinkedUseCases(List<UseCase> linkedUseCases) {
        this.linkedUseCases = linkedUseCases;
    }

    public List<CRC> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<CRC> collaborators) {
        this.collaborators = collaborators;
    }

    public int getCrcId() {
        return crcId;
    }


    public Project getProject() {
        return project;
    }


    public void setProject(Project project) {
        this.project = project;
    }

    public String getCrcName() {
        return crcName;
    }

    public void setCrcName(String crcName) {
        this.crcName = crcName;
    }

}