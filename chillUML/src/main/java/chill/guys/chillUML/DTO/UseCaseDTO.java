package chill.guys.chillUML.DTO;

import chill.guys.chillUML.domain.Project;

import java.util.List;

public class UseCaseDTO {
    private String useCaseName;
    private String mainFlow;
    private List<String> actors;
    private Project project;
    private String stringPreCond;
    private String stringAltFlow;
    private List<String> preCond;
    private List<String> altFlow;
    private String postflow;

    public void setStringPreCond(String preCond){
        this.stringPreCond = preCond;
    }

    public String getStringPreCond(){
        return this.stringPreCond;
    }

    public void setStringAltFlow(String altFlow){
        this.stringAltFlow = altFlow;
    }

    public String getStringAltFlow(){
        return stringAltFlow;
    }

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

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<String> getPreCond() {
        return preCond;
    }

    public void setPreCond(List<String> preCond) {
        this.preCond = preCond;
    }

    public List<String> getAltFlow() {
        return altFlow;
    }

    public void setAltFlow(List<String> altFlow) {
        this.altFlow = altFlow;
    }

    public String getPostflow() {
        return postflow;
    }

    public void setPostflow(String postflow) {
        this.postflow = postflow;
    }
}
