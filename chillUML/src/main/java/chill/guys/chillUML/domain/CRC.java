package chill.guys.chillUML.domain;


import jakarta.persistence.*;

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
