package chill.guys.chillUML.domain;


import jakarta.persistence.*;

@Entity
@Table(name = "usecases")
public class UseCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usecase_id")
    private int useCaseId;

    @Column(name = "usecase_name")
    private String useCaseName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project projectID;

    @Lob
    @Column(name = "mainflow")
    private String mainFlow;

    @Column(name = "Actors")
    private String actors;

    @Lob
    @Column(name = "precond")
    private String precond;

    @Lob
    @Column(name = "alt_flow")
    private String altFlow;

    @Lob
    @Column(name = "postflow")
    private String postflow;

    public Project getProjectID() {
        return projectID;
    }

    public void setProjectID(Project projectID) {
        this.projectID = projectID;
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

    public String getPrecond() {
        return precond;
    }

    public void setPrecond(String precond) {
        this.precond = precond;
    }

    public String getAltFlow() {
        return altFlow;
    }

    public void setAltFlow(String altFlow) {
        this.altFlow = altFlow;
    }

    public String getPostflow() {
        return postflow;
    }

    public void setPostflow(String postflow) {
        this.postflow = postflow;
    }

    public int getUseCaseId() {
        return useCaseId;
    }

    public String getUseCaseName() {
        return useCaseName;
    }

    public void setUseCaseName(String useCaseName) {
        this.useCaseName = useCaseName;
    }

}
