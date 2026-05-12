package chill.guys.chillUML.domain;


import jakarta.persistence.*;

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
    private Project projectID;

    @Column(name = "crc_name")
    private String crcName;

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

    @Column(name = "crc_responsibilities")
    private List<String> responsibilities;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "crc_linked")
    private List<UseCase> linkedUseCases;

    @ManyToMany(fetch = FetchType.EAGER)
    @Column(name = "crc_collaborators")
    private List<CRC> collaborators;

    public int getCrcId() {
        return crcId;
    }

    public Project getProjectID() {
        return projectID;
    }

    public void setProjectID(Project projectID) {
        this.projectID = projectID;
    }

    public String getCrcName() {
        return crcName;
    }

    public void setCrcName(String crcName) {
        this.crcName = crcName;
    }


}