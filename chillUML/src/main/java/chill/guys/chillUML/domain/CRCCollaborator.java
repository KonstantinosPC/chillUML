package chill.guys.chillUML.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "crc_collaborators")
public class CRCCollaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collaborators_id")
    private int collaboratorsID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crc_id")
    private CRC crc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "collab_id")
    private CRC crc_collaborator;

    public int getCollaboratorsID() {
        return collaboratorsID;
    }

    public void setCrc(CRC crc) {
        this.crc = crc;
    }

    public CRC getCrc() {
        return crc;
    }

    public void setCrc_collaborator(CRC crc_collaborator) {
        this.crc_collaborator = crc_collaborator;
    }

    public CRC getCrc_collaborator() {
        return crc_collaborator;
    }
}
