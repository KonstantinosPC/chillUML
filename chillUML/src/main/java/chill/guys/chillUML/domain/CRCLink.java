package chill.guys.chillUML.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "crc_link")
public class CRCLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usecase_id")
    private UseCase useCase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crc_id")
    private CRC crc;

    public int getId() {
        return id;
    }

    public void setUseCase(UseCase useCase) {
        this.useCase = useCase;
    }

    public UseCase getUseCase() {
        return useCase;
    }

    public void setCrc(CRC crc) {
        this.crc = crc;
    }

    public CRC getCrc() {
        return crc;
    }
}
