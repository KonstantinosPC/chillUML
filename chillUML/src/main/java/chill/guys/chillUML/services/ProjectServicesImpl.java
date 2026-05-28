package chill.guys.chillUML.services;

import chill.guys.chillUML.DTO.*;
import chill.guys.chillUML.DatagramGenerators.ClassDiagramGenerator;
import chill.guys.chillUML.DatagramGenerators.UseCaseDiagramGenerator;
import chill.guys.chillUML.domain.*;
import chill.guys.chillUML.factories.*;
import chill.guys.chillUML.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Service
public class ProjectServicesImpl implements ProjectServices{
    String specialChars = "+*/%=!<>&|^~(){}[];,.?:@_$";

    @Autowired
    private UserServices userServices;

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
        project.setUseCases(new ArrayList<UseCase>());
        project.setCrcList(new ArrayList<CRC>());
        projectRepository.save(project);
        redirectAttributes.addFlashAttribute("success","The project " + project.getProjectName() + " has been created successfully");
    }

    @Override
    public List<Project> viewAllProjects(User ownerId) {
        return projectRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Project> viewAllSharedProjects(User sharedId){
        return projectRepository.findBySharedUsersContains(sharedId);
    }

    @Override
    public void updateSharedUser(int projectId, List<String> sharedUsersEmails, RedirectAttributes redirectAttributes){
        Project project = projectRepository.findById(projectId).get();
        if (sharedUsersEmails == null) {
            sharedUsersEmails = new ArrayList<>();
        }

        Set<User> incomingUsers = new HashSet<>();
        for (String email : sharedUsersEmails) {
            if (email.trim().isEmpty()) continue;

            Optional<User> userOpt = userServices.findByEmail(email);
            if (userOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "There is no user with this email:<br>" + email);
                return;
            }
            incomingUsers.add(userOpt.get());
        }

        Set<User> currentSharedUsers = new HashSet<>(project.getSharedUsers());

        for (User currentUser : currentSharedUsers) {
            if (!incomingUsers.contains(currentUser)) {
                project.getSharedUsers().remove(currentUser);
            }
        }

        for (User incomingUser : incomingUsers) {
            if (!currentSharedUsers.contains(incomingUser)) {
                project.addSharedUser(incomingUser);
            }
        }
        projectRepository.save(project);
        return;
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
    public boolean createUseCase(UseCaseDTO useCaseDTO, RedirectAttributes redirectAttributes) {
        if(useCaseDTO.getUseCaseName().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The use case name must not be empty.");
            return false;
        }
        if(useCaseDTO.getUseCaseName().length() > 15){
            redirectAttributes.addFlashAttribute("error","This use case name is over 15 characters long");
            return false;
        }
        if(!(useCaseRepository.findByUseCaseNameAndProject(useCaseDTO.getUseCaseName(),useCaseDTO.getProject()).isEmpty())){
            redirectAttributes.addFlashAttribute("error","Same Use Case Name with an existing Use Case");
            return false;
        }

        if(!(useCaseDTO.getUseCaseName().chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The use case name must not contain special characters.");
            return false;
        }

        if(useCaseDTO.getActors().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Actors field must not be empty.");
            return false
                    ;
        }

        if(useCaseDTO.getPreCond().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Pre-condition filed must not be empty.");
            return false;
        }

        if(useCaseDTO.getMainFlow().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Main flow filed must not be empty.");
            return false;
        }

        UseCase usecase = new UseCase();
        usecase.setUseCaseName(useCaseDTO.getUseCaseName());
        usecase.setProject(useCaseDTO.getProject());
        usecase.setMainFlow(useCaseDTO.getMainFlow());
        usecase.setActors(useCaseDTO.getActors());
        usecase.setPrecond(useCaseDTO.getPreCond());
        usecase.setAltFlow(useCaseDTO.getAltFlow());
        usecase.setPostflow(useCaseDTO.getPostflow());
        useCaseRepository.save(usecase);
        useCaseDTO.getProject().addUseCase(usecase);
        projectRepository.save(useCaseDTO.getProject());
        redirectAttributes.addFlashAttribute("success","The Use Case " + usecase.getUseCaseName() + " has been created successfully");
        return true;
    }

    @Override
    public boolean editUseCaseName(int useCaseID, String newName, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        if(newName.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The use case name must not be empty.");
            return false;
        }
        if(newName.length() > 15){
            redirectAttributes.addFlashAttribute("error","This use case name is over 15 characters long");
            return false;
        }
        if(!(useCaseRepository.findByUseCaseNameAndProject(newName,usecase.getProject()).isEmpty())){
      
            redirectAttributes.addFlashAttribute("error","Same Use Case Name with an existing Use Case");
            return false;
        }

        if(!(newName.chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The use case name must not contain special characters.");
            return false;
        }
        usecase.setUseCaseName(newName);
        useCaseRepository.save(usecase);
        return true;
    }

    @Override
    public boolean editActors(int useCaseID, List<String> newActors, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        if(newActors.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Actors field must not be empty.");
            return false;
        }
        usecase.setActors(newActors);
        useCaseRepository.save(usecase);
        return true;
    }

    @Override
    public boolean editPrecondition(int useCaseID, List<String> newPostcond, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        if(newPostcond.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Pre-condition filed must not be empty.");
            return false;
        }

        usecase.setPrecond(newPostcond);
        useCaseRepository.save(usecase);
        return true;
    }

    @Override
    public boolean editMainFlow(int useCaseID, String newMainFlow, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        if(newMainFlow.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Main flow filed must not be empty.");
            return false;
        }
        usecase.setMainFlow(newMainFlow);
        useCaseRepository.save(usecase);
        return true;
    }

    @Override
    public void editAltFlows(int useCaseID, List<String> newAltFlow, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        usecase.setAltFlow(newAltFlow);
        useCaseRepository.save(usecase);
    }

    @Override
    public void editPostCondition(int useCaseID, String newPostCond, RedirectAttributes redirectAttributes) {
        UseCase usecase = useCaseRepository.findById(useCaseID).get();
        usecase.setPostflow(newPostCond);
        useCaseRepository.save(usecase);
    }

    @Override
    public List<UseCase> viewAllUseCases(Project project) {
        return useCaseRepository.findByProject(project);
    }

    @Override
    @Transactional
    public void deleteUseCase(int useCaseID,RedirectAttributes redirectAttributes) {
        UseCase usecaseDeletion = useCaseRepository.findById(useCaseID).get();
        Project project = usecaseDeletion.getProject();

        if(project.getCrcList() != null){
            for(CRC crc: new ArrayList<>(project.getCrcList())){
                for(UseCase usecase: new ArrayList<>(crc.getLinkedUseCases())){
                    if(usecase.equals(usecaseDeletion)){
                        crc.getLinkedUseCases().remove(usecase);
                        crcRepository.save(crc);
                    }
                }
            }
        }

        String name = usecaseDeletion.getUseCaseName();
        useCaseRepository.deleteById(useCaseID);
        redirectAttributes.addFlashAttribute("success","The Use Case " + name + " has been deleted successfully");
    }

    @Override
    public boolean createCRC(CrcDTO crcDTO, RedirectAttributes redirectAttributes) {
        if(crcDTO.getCrcName().isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Crc name can not be empty");
            return false;
        }
        if(crcDTO.getCrcName().length() > 15){
            redirectAttributes.addFlashAttribute("error","This Crc name is over 15 characters long");
            return false;
        }
        if(!(crcDTO.getCrcName().chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The Crc name must not contain special characters.");
            return false;
        }
        if(!crcRepository.findByCrcNameAndProject(crcDTO.getCrcName(), crcDTO.getProjectID()).isEmpty()){
            redirectAttributes.addFlashAttribute("error","This Crc name already exists in this project");
            return false;
        }
        CRC crc = new CRC();
        crc.setCrcName(crcDTO.getCrcName());
        crc.setProject(crcDTO.getProjectID());
        crc.setCollaborators(crcDTO.getLinked_crc());
        crc.setResponsibilities(crcDTO.getResponsibilities());
        crc.setLinkedUseCases(crcDTO.getUsecases());
        crcRepository.save(crc);

        for(CRC otherCRCs: crcDTO.getLinked_crc()){
            otherCRCs.getCollaborators().add(crc);
            crcRepository.save(otherCRCs);
        }

        crcDTO.getProjectID().addCRC(crc);
        projectRepository.save(crc.getProject());
        redirectAttributes.addFlashAttribute("success","The " + crc.getCrcName() + " has been created successfully");
        return true;
    }


    @Override
    public boolean updateCrcName(String newName, int crcID,RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        if(newName.isEmpty()){
            redirectAttributes.addFlashAttribute("error","The Crc name can not be empty");
            return false;
        }
        if(newName.length() > 15){
            redirectAttributes.addFlashAttribute("error","This Crc name is over 15 characters long");
            return false;
        }
        if(!(newName.chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The CRC name must not contain special characters.");
            return false;
        }
        if(!(crcRepository.findByCrcNameAndProject(newName, crc.getProject()).isEmpty())){
            redirectAttributes.addFlashAttribute("error","This Crc name already exists in this project");
            return false;
        }
        crc.setCrcName(newName);
        crcRepository.save(crc);
        return true;
    }


    @Override
    public void updateCrcResponsibilities(int crcID,List<String> newResponsibiities, RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        crc.setResponsibilities(newResponsibiities);
        crcRepository.save(crc);
    }

    @Override
    @Transactional
    public void updateCrcColaborators(int crcID,List<CRC> newColaborators, RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        for(CRC collaborator : newColaborators){
            if(!collaborator.getCollaborators().contains(crc)){
                collaborator.getCollaborators().add(crc);
                crc.getCollaborators().add(collaborator);
                crcRepository.save(collaborator);
            }
        }

        for(CRC collaborator : new ArrayList<>(crc.getCollaborators())){
            if(!newColaborators.contains(collaborator)){
                collaborator.getCollaborators().remove(crc);
                crc.getCollaborators().remove(collaborator);
                crcRepository.save(collaborator);
            }
        }
        crc.setCollaborators(newColaborators);
        crcRepository.save(crc);

    }

    @Override
    @Transactional
    public void updateCrcLinkedUseCases(int crcID,List<UseCase> newLinkedUseCases, RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();

        for(UseCase usecase: newLinkedUseCases){
            if(!crc.getLinkedUseCases().contains(usecase)){
                crc.getLinkedUseCases().add(usecase);
            }
        }

        crc.getLinkedUseCases().removeIf(usecase -> !newLinkedUseCases.contains(usecase));
        crcRepository.save(crc);
    }

    @Override
    @Transactional
    public void deleteCrc(int crcID,RedirectAttributes redirectAttributes) {
        CRC crc = crcRepository.findById(crcID).get();
        String name = crc.getCrcName();

        if(!crc.getCollaborators().isEmpty()){
            for(CRC otherCrc : new ArrayList<>(crc.getCollaborators())){
                otherCrc.getCollaborators().remove(crc);
                crcRepository.save(otherCrc);
            }
        }
        if (crc.getCollaborators() != null) {
            crc.getCollaborators().clear();
        }
        if (crc.getLinkedUseCases() != null) {
            crc.getLinkedUseCases().clear();
        }
        crcRepository.save(crc);
        crcRepository.delete(crc);
        redirectAttributes.addFlashAttribute("success","The CRC " + name + " has been deleted successfully");
    }

    @Override
    @Transactional
    public String generateUsecaseDiagram(String type, Project projectID) {
        UseCaseDiagramGeneratorFactory factory = new UseCaseDiagramGeneratorFactory();
        List<UseCase> usecases = useCaseRepository.findByProject(projectID);
        UseCaseDiagramGenerator generator = factory.createUseCaseDiagramGenerator(type);
        return generator.generateDiagram(usecases);
    }

    @Override
    @Transactional
    public String generateClassDiagram(String type, Project projectID) {
        ClassDiagramGeneratorFactory factory = new ClassDiagramGeneratorFactory();
        List<CRC> crc = crcRepository.findByProject(projectID);
        ClassDiagramGenerator generator = factory.createClassDiagramGenerator(type);
        return generator.generateClassDiagram(crc);
    }

    @Override
    public List<CRC> viewAllCRC(Project project) {
        return crcRepository.findByProject(project);
    }


}
