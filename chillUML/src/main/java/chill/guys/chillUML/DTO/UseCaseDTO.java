package chill.guys.chillUML.DTO;

import chill.guys.chillUML.domain.Project;

public class UseCaseDTO {
    private String useCaseName;
    private String mainFlow;
    private String actors;
    private Project project;
    private String preCond;
    private String altflow;
    private String postflow;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getUseCaseName() {
        return useCaseName;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }

    public String getMainFlow() {
        return mainFlow;
    }

    public void setMainFlow(String mainFlow) {
        this.mainFlow = mainFlow;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPreCond() {
        return preCond;
    }

    public void setPreCond(String preCond) {
        this.preCond = preCond;
    }

    public String getAltflow() {
        return altflow;
    }

    public void setAltflow(String altflow) {
        this.altflow = altflow;
    }

    public String getPostflow() {
        return postflow;
    }

    public void setPostflow(String postflow) {
        this.postflow = postflow;
    }
}
