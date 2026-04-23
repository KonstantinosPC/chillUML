package chill.guys.chillUML.services;

import chill.guys.chillUML.DTO.ProjectDTO;
import chill.guys.chillUML.DTO.UseCaseDTO;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.ProjectRepository;
import chill.guys.chillUML.repositories.UseCaseRepository;
import chill.guys.chillUML.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServicesImpl implements ProjectServices{
    String specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";

    @Autowired
    private ProjectRepository projectRepository;
    private UseCaseRepository useCaseRepository;

    @Override
    @Transactional
    public void createProject(ProjectDTO projectDTO, RedirectAttributes redirectAttributes) {
        if(projectDTO.getProjectName().length() > 15){
            redirectAttributes.addFlashAttribute("error","This project name is over 15 characters long");
            return;
        }
        if(!(projectRepository.findByProjectNameAndOwnerId(projectDTO.getProjectName(),projectDTO.getOwnerId()).isEmpty())){
            redirectAttributes.addFlashAttribute("error","There is already a project with this name");
            return;
        }
        if(!(projectDTO.getProjectName().chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The project name must not contain special characters.");
            return;
        }
        Project project = new Project();
        project.setProjectName(projectDTO.getProjectName());
        project.setProjectDescription(projectDTO.getProjectDescription());
        project.setOwner(projectDTO.getOwnerId());
        projectRepository.save(project);
        redirectAttributes.addFlashAttribute("success","The project " + project.getProjectName() + " has been created successfully");
    }

    @Override
    public List<Project> viewAllProjects(User ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    @Override
    public void deleteProject(int projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public void editProjectName(int projectID,String newName,RedirectAttributes redirectAttributes) {
        Project project = projectRepository.findById(projectID).get();
        if(newName.length() > 15){
            redirectAttributes.addFlashAttribute("error","This project name is over 15 characters long");
            return;
        }
        if(!(projectRepository.findByProjectNameAndOwnerId(newName,project.getOwnerId())).isEmpty()){
            redirectAttributes.addFlashAttribute("error","There is already a project with this name");
            return;
        }
        if(!(newName.chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The project name must not contain special characters.");
            return;
        }
        project.setProjectName(newName);
        projectRepository.save(project);
    }

    @Override
    public void editProjectDescription(int projectID,String newDescription, RedirectAttributes redirectAttributes) {
        Project project = projectRepository.findById(projectID).get();
        project.setProjectDescription(newDescription);
    }
    @Transactional
    @Override
    public void createUseCase(UseCaseDTO useCaseDTO, RedirectAttributes redirectAttributes) {
        if(useCaseDTO.getUseCaseName().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The use case name must not be empty.");
            return;
        }
        if(useCaseDTO.getUseCaseName().length() > 15){
            redirectAttributes.addFlashAttribute("error","This use case name is over 15 characters long");
            return;
        }
        if(!(useCaseRepository.findByUseCaseNameAndProjectId(useCaseDTO.getUseCaseName(),useCaseDTO.getProject()).isEmpty())){
            redirectAttributes.addFlashAttribute("error","Same Use Case Name with an existing Use Case");
            return;
        }

        if(!(useCaseDTO.getUseCaseName().chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The use case name must not contain special characters.");
            return;
        }

        if(useCaseDTO.getActors().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Actors field must not be empty.");
            return;
        }

        if(useCaseDTO.getPreCond().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Pre-condition filed must not be empty.");
            return;
        }

        if(useCaseDTO.getMainFlow().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Main flow filed must not be empty.");
            return;
        }

        UseCase usecase = new UseCase();
        usecase.setUseCaseName(useCaseDTO.getUseCaseName());
        usecase.setProjectID(useCaseDTO.getProject());
        usecase.setMainFlow(useCaseDTO.getMainFlow());
        usecase.setActors(useCaseDTO.getActors());
        usecase.setPrecond(useCaseDTO.getPreCond());
        usecase.setAltFlow(useCaseDTO.getAltflow());
        usecase.setPostflow(useCaseDTO.getPostflow());
        useCaseRepository.save(usecase);
        redirectAttributes.addFlashAttribute("success","The Use Case " + usecase.getUseCaseName() + " has been created successfully");

    }

    @Override
    public void editUseCaseName(int useCaseID, String newName, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        if(newName.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The use case name must not be empty.");
            return;
        }
        if(newName.length() > 15){
            redirectAttributes.addFlashAttribute("error","This use case name is over 15 characters long");
            return;
        }
        if(!(useCaseRepository.findByUseCaseNameAndProjectId(newName,usecase.getProjectID()).isEmpty())){
            redirectAttributes.addFlashAttribute("error","Same Use Case Name with an existing Use Case");
            return;
        }

        if(!(newName.chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The use case name must not contain special characters.");
            return;
        }
        usecase.setUseCaseName(newName);
        useCaseRepository.save(usecase);
        redirectAttributes.addFlashAttribute("success","The Use Case " + usecase.getUseCaseName() + " has been updated successfully");

    }

    @Override
    public void editActors(int useCaseID, String newActors, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        if(newActors.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Actors field must not be empty.");
            return;
        }
        usecase.setActors(newActors);
        useCaseRepository.save(usecase);
        redirectAttributes.addFlashAttribute("success","The Use Case " + usecase.getUseCaseName() + " Actors havve been updated successfully");
    }

    @Override
    public void editPrecondition(int useCaseID, String newPostcond, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        if(newPostcond.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Pre-condition filed must not be empty.");
            return;
        }

        usecase.setPrecond(newPostcond);
        redirectAttributes.addFlashAttribute("success","The Use Case " + usecase.getUseCaseName() + " Pre Conditions have been updated successfully");
    }

    @Override
    public void editMainFlow(int useCaseID, String newMainFlow, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        if(newMainFlow.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Main flow filed must not be empty.");
            return;
        }
        usecase.setMainFlow(newMainFlow);
        redirectAttributes.addFlashAttribute("success","The Use Case " + usecase.getUseCaseName() + " Main Flow has been updated successfully");
    }

    @Override
    public void editAltFlows(int useCaseID, String newAltFlow, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        usecase.setAltFlow(newAltFlow);
        redirectAttributes.addFlashAttribute("success","The Use Case " + usecase.getUseCaseName() + " Alt-Flow has been updated successfully");
    }

    @Override
    public void editPostCondition(int useCaseID, String newPostCond, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        usecase.setPostflow(newPostCond);
        redirectAttributes.addFlashAttribute("success","The Use Case " + usecase.getUseCaseName() + " Post Conditions have been updated successfully");
    }

    @Override
    public List<UseCase> viewAllUseCases(Project project) {
        return useCaseRepository.findByProjectID(project);
    }

    @Override
    public void deleteUseCase(int useCaseID,RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        String name = usecase.getUseCaseName();
        useCaseRepository.deleteById(useCaseID);
        redirectAttributes.addFlashAttribute("success","The Use Case " + name + " has been deleted successfully");
    }

    
}
