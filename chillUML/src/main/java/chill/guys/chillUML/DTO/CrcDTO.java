package chill.guys.chillUML.DTO;
import java.util.List;

import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;

public class CrcDTO {
    private Project projectID;
    private String crcName;
    private List<String> responsibilities;
    private List<UseCase> usecases;
    private List<CRC> linked_crc;

    public CrcDTO() {
    }

    public Project getProjectID() {
        return projectID;
    }

    public String getCrcName() {
        return crcName;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public List<UseCase> getUsecases() {
        return usecases;
    }

    public List<CRC> getLinked_crc() {
        return linked_crc;
    }

    public void setCrcName(String crcName) {
        this.crcName = crcName;
    }

    public void setResponsibilities(List<String> responsibilities){
        this.responsibilities = responsibilities;
    }

    public void setProjectID(Project projectID){
        this.projectID = projectID;
    }

    public void setUsecases(List<UseCase> usecases){
        this.usecases = usecases;
    }

    public void setLinked_crc(List<CRC> linked_crc){
        this.linked_crc = linked_crc;
    }
}