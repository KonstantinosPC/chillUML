package chill.guys.chillUML.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "crc_responsibilities")
public class CRCResponsibilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "res_id")
    private int resId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crc_id")
    private CRC crcID;

    @Lob
    @Column(name = "description")
    private String description;

    public void setCRCID(CRC crcID) {
        this.crcID = crcID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getResId() {
        return resId;
    }

    public CRC getCrcID(){
        return this.crcID;
    }

    public String getDescription() {
        return this.description;
    }
}
