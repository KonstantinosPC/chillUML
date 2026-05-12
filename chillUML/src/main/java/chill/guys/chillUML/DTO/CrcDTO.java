package chill.guys.chillUML.DTO;

import chill.guys.chillUML.domain.Project;

public class CrcDTO {
    private Project projectId;
    private String crcName;

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    public String getCrcName() {
        return crcName;
    }

    public void setCrcName(String crcName) {
        this.crcName = crcName;
    }
}
