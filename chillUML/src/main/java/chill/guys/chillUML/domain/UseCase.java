package chill.guys.chillUML.domain;

import jakarta.persistence.*;
import java.util.List;

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
    private Project project;

    @Lob
    @Column(name = "mainflow")
    private String mainFlow;

    @ElementCollection
    @CollectionTable(name = "usecase_actors", joinColumns = @JoinColumn(name = "usecase_id"))
    @Column(name = "Actors")
    private List<String> actors;


    @ElementCollection
    @CollectionTable(name = "usecase_preconditions", joinColumns = @JoinColumn(name = "usecase_id"))
    @Column(name = "precondition")
    private List<String> precond;

    @ElementCollection
    @CollectionTable(name = "usecase_alt_flows", joinColumns = @JoinColumn(name = "usecase_id"))
    @Column(name = "flow")
    private List<String> altFlow;

    @Lob
    @Column(name = "postcond")
    private String postcond;



    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public List<String> getPrecond() {
        return precond;
    }

    public void setPrecond(List<String> precond) {
        this.precond = precond;
    }

    public List<String> getAltFlow() {
        return altFlow;
    }

    public void setAltFlow(List<String> altFlow) {
        this.altFlow = altFlow;
    }

    public String getPostflow() {
        return postcond;
    }

    public void setPostflow(String postflow) {
        this.postcond = postflow;
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