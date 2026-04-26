package chill.guys.chillUML.DTO;

import chill.guys.chillUML.domain.Project;

public class CrcDTO {
    private Project projectID;
    private String crcName;

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
