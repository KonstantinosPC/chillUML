package chill.guys.chillUML.services;

import chill.guys.chillUML.DTO.*;
import chill.guys.chillUML.domain.*;
import chill.guys.chillUML.factories.*;
import chill.guys.chillUML.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServicesImpl implements ProjectServices{
    String specialChars = "+*/%=!<>&|^~(){}[];,.?:@_$";

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UseCaseRepository useCaseRepository;
    @Autowired
    private CrcRepository crcRepository;

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
        projectRepository.save(project);
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
    public void editPrecondition(int useCaseID, List<String> newPostcond, RedirectAttributes redirectAttributes) {
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
    public void editAltFlows(int useCaseID, List<String> newAltFlow, RedirectAttributes redirectAttributes) {
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

    @Override
    public void createCRC(CrcDTO crcDTO, RedirectAttributes redirectAttributes) {
        if(crcDTO.getCrcName().isEmpty()){
            redirectAttributes.addFlashAttribute("error","This Crc name can not be empty");
            return;
        }
        if(crcDTO.getCrcName().length() > 15){
            redirectAttributes.addFlashAttribute("error","This Crc name is over 15 characters long");
            return;
        }
        if(!(crcDTO.getCrcName().chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The use case name must not contain special characters.");
            return;
        }
        if(crcRepository.findByCrcNameAndProjectId(crcDTO.getCrcName(), crcDTO.getProjectID()).isEmpty()){
            redirectAttributes.addFlashAttribute("error","This Crc name already exists in this project");
            return;
        }
        CRC crc = new CRC();
        crc.setCrcName(crcDTO.getCrcName());
        crc.setProjectID(crcDTO.getProjectID());
        crc.setCollaborators(crcDTO.getLinked_crc());
        crc.setResponsibilities(crcDTO.getResponsibilities());
        crc.setLinkedUseCases(crcDTO.getUsecases());
        crcRepository.save(crc);
    }


    @Override
    public void updateCrcName(String newName, int crcID,RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        if(newName.isEmpty()){
            redirectAttributes.addFlashAttribute("error","This Crc name can not be empty");
            return;
        }
        if(newName.length() > 15){
            redirectAttributes.addFlashAttribute("error","This Crc name is over 15 characters long");
            return;
        }
        if(!(newName.chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The use case name must not contain special characters.");
            return;
        }
        if(crcRepository.findByCrcNameAndProjectId(newName, crc.getProjectID()).isEmpty()){
            redirectAttributes.addFlashAttribute("error","This Crc name already exists in this project");
            return;
        }
        crc.setCrcName(newName);
        crcRepository.save(crc);
        redirectAttributes.addFlashAttribute("success","The Crcs " + newName + " name has been updated successfully");

    }

    @Override
    public void updateCrcResponsibilities(int crcID,List<String> newResponsibiities, RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        crc.setResponsibilities(newResponsibiities);
        crcRepository.save(crc);
        redirectAttributes.addFlashAttribute("success","The Responsibilities have been updated successfully");

    }

    @Override
    public void updateCrcColaborators(int crcID,List<CRC> newColaborators, RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        crc.setCollaborators(newColaborators);
        crcRepository.save(crc);
        redirectAttributes.addFlashAttribute("success","The Collaborators have been updated successfully");

    }

    @Override
    public void updateCrcLinkedUseCases(int crcID,List<UseCase> newLinkedUseCases, RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        crc.setLinkedUseCases(newLinkedUseCases);
        crcRepository.save(crc);
        redirectAttributes.addFlashAttribute("success","The Linked UseCases have been updated successfully");
    }

    @Override
    public void deleteCrc(int crcID,RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        String name = crc.getCrcName();
        crcRepository.delete(crc);
        redirectAttributes.addFlashAttribute("success","The Use Case " + name + " has been deleted successfully");
    }
    @Override
    public String generateUsecaseDiagram(String type, Project projectID) {
        UseCaseDiagramGeneratorFactory factory = new UseCaseDiagramGeneratorFactory();
        List<UseCase> usecases = useCaseRepository.findByProjectID(projectID);
        UseCaseDiagramGenerator generator = factory.createUseCaseDiagramGenerator(type);
        return generator.generateDiagram(usecases);
    }

    @Override
    public String generateClassDiagram(String type, Project projectID) {
        ClassDiagramGeneratorFactory factory = new ClassDiagramGeneratorFactory();
        List<CRC> crc = crcRepository.findByProjectId(projectID);
        ClassDiagramGenerator generator = factory.createClassDiagramGenerator(type);
        return generator.generateClassDiagram(crc);
    }


}
