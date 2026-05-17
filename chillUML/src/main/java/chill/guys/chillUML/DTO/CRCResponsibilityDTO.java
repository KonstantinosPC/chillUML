package chill.guys.chillUML.DTO;

import chill.guys.chillUML.domain.CRC;

public class CRCResponsibilityDTO {
    private CRC crcId;
    private String description;

    public CRC getCrcId() {
        return crcId;
    }

    public void setCrcId(CRC crcId) {
        this.crcId = crcId;
    }

    public String getDescriptiom() {
        return description;
    }

    public void setDescriptiom(String descriptiom) {
        this.description = descriptiom;
    }
}
