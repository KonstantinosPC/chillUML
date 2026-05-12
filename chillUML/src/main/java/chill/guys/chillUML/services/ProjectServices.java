package chill.guys.chillUML.services;


import chill.guys.chillUML.DTO.*;
import chill.guys.chillUML.domain.*;
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
    public void editPrecondition(int useCaseID, List<String> newPostcond, RedirectAttributes redirectAttributes );
    public void editMainFlow(int useCaseID, String newMainFlow, RedirectAttributes redirectAttributes );
    public void editAltFlows(int useCaseID, List<String> newAltFlow, RedirectAttributes redirectAttributes );
    public void editPostCondition(int useCaseID, String newPostCond, RedirectAttributes redirectAttributes );
    public List<UseCase> viewAllUseCases(Project project);
    public void deleteUseCase(int useCaseID, RedirectAttributes redirectAttributes);
    public void createCRC(CrcDTO crc,RedirectAttributes redirectAttributes);
    public void updateCrcName(String newName,int crcID,RedirectAttributes redirectAttributes);
    public void updateCrcResponsibilities(int crcID, List<String> newResponsibiities,RedirectAttributes redirectAttributes);
    public void updateCrcColaborators(int crcID,List<CRC> newColaborators,RedirectAttributes redirectAttributes);
    public void updateCrcLinkedUseCases(int crcID,List<UseCase> newLinkedUseCases,RedirectAttributes redirectAttributes);
    public void deleteCrc(int crcID,RedirectAttributes redirectAttributes);
    public String generateUsecaseDiagram(String type,Project projectID);
    public String generateClassDiagram(String type,Project projectID);
    public List<CRC> ViewAllCRC(Project project);

}
