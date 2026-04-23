package chill.guys.chillUML.services;


import chill.guys.chillUML.DTO.ProjectDTO;
import chill.guys.chillUML.DTO.UseCaseDTO;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import chill.guys.chillUML.domain.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface ProjectServices {

    public void createProject(ProjectDTO projectDTO, RedirectAttributes redirectAttributes);
    public List<Project> viewAllProjects(User ownerId);
    public void deleteProject(int project_id);
    public void editProjectName(int projectID,String newName, RedirectAttributes redirectAttributes);
    public void editProjectDescription(int projectID,String newDescription,RedirectAttributes redirectAttributes);
    public void createUseCase(UseCaseDTO useCase, RedirectAttributes redirectAttributes );
    public void editUseCaseName(int useCaseID, String newName, RedirectAttributes redirectAttributes );
    public void editActors(int useCaseID, String newActors, RedirectAttributes redirectAttributes );
    public void editPrecondition(int useCaseID, String newPostcond, RedirectAttributes redirectAttributes );
    public void editMainFlow(int useCaseID, String newMainFlow, RedirectAttributes redirectAttributes );
    public void editAltFlows(int useCaseID, String newAltFlow, RedirectAttributes redirectAttributes );
    public void editPostCondition(int useCaseID, String newPostCond, RedirectAttributes redirectAttributes );
    public List<UseCase> viewAllUseCases(Project project);
    public void deleteUseCase(int useCaseID, RedirectAttributes redirectAttributes);
}
