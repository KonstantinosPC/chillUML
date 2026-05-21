package chill.guys.chillUML.controllers;

import chill.guys.chillUML.DTO.CrcDTO;
import chill.guys.chillUML.DTO.UseCaseDTO;
import chill.guys.chillUML.domain.CRC;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.UseCase;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.CrcRepository;
import chill.guys.chillUML.repositories.ProjectRepository;
import chill.guys.chillUML.repositories.UseCaseRepository;
import chill.guys.chillUML.repositories.UserRepository;
import chill.guys.chillUML.services.ProjectServicesImpl;
import chill.guys.chillUML.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ProjectController {

    @Autowired
    UserServicesImpl userServices;

    @Autowired
    ProjectServicesImpl projectServices;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UseCaseRepository useCaseRepository;

    @Autowired
    CrcRepository crcRepository;


    private List<String> stringToActors(String origin){
        return Arrays.asList(origin.split(","));
    }

    private List<String> stringToList(String origin){
        return Arrays.stream(origin.split("\\r?\\n")).map(String::trim).filter(step -> !step.isEmpty()).toList();
    }

    @GetMapping("/project/{owner}/{name}")
    public String toProject(@PathVariable String owner ,@PathVariable String name, @AuthenticationPrincipal UserDetails userDetails, Model model, RedirectAttributes redirectAttributes){
        User currentUser = userServices.findByUsername(userDetails.getUsername()).get();
        Optional<Project> currentProject = projectRepository.findByProjectNameAndOwnerId(name, currentUser);

        if(!currentUser.getUsername().equals(owner)){
            redirectAttributes.addFlashAttribute("critical", "This project isn't yours or the owner hasn't shared it with you");
            return "redirect:/dashboard";
        }

        if(!currentProject.isPresent()){
            redirectAttributes.addFlashAttribute("critical", "This project doesn't exists");
            return "redirect:/dashboard";
        }
        model.addAttribute("user",currentUser);
        model.addAttribute("project",currentProject.get());
        model.addAttribute("crcs",projectServices.viewAllCRC(currentProject.get()));
        model.addAttribute("usecases",projectServices.viewAllUseCases(currentProject.get()));
        model.addAttribute("useCaseDTO", new UseCaseDTO());
        model.addAttribute("code");

        model.addAttribute("crcDTO", new CrcDTO());
        return "project";

    }

    @PostMapping("/project/create-uc/{owner}/{name}")
    public String createUC(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String owner, @PathVariable String name, @ModelAttribute UseCaseDTO useCaseDTO, RedirectAttributes redirectAttributes){
        if(!userDetails.getUsername().equals(owner)){
            redirectAttributes.addFlashAttribute("critical", "This project isn't yours");
            return "redirect:/project/{owner}/{name}";
        }

        useCaseDTO.setProject(projectRepository.findByProjectNameAndOwnerId(name, userServices.findByUsername(userDetails.getUsername()).get()).get());
        if(useCaseDTO.getAltFlow().size() > 0){
            useCaseDTO.setAltFlow(stringToList(useCaseDTO.getAltFlow().get(0)));
        }
        useCaseDTO.setPreCond(stringToList(useCaseDTO.getPreCond().get(0)));
        projectServices.createUseCase(useCaseDTO,redirectAttributes);

        return "redirect:/project/{owner}/{name}";
    }

    @PostMapping("/project/edit-uc/{owner}/{name}")
    public String editUC(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String owner, @RequestParam("edit-uc-id") int useCaseId, @PathVariable String name, @ModelAttribute UseCaseDTO useCaseDTO, RedirectAttributes redirectAttributes){
        if(!userDetails.getUsername().equals(owner)){
            redirectAttributes.addFlashAttribute("critical", "This project isn't yours");
            return "redirect:/project/{owner}/{name}";
        }

        Optional<UseCase> useCaseOpt = useCaseRepository.findById(useCaseId);
        if (useCaseOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "The Use Case you are trying to edit was not found.");
            return "redirect:/project/{owner}/{name}";
        }

        UseCase uneditedUseCase = useCaseOpt.get();


        //UseCase Name
        if(!uneditedUseCase.getUseCaseName().equals(useCaseDTO.getUseCaseName())){
            projectServices.editUseCaseName(uneditedUseCase.getUseCaseId(), useCaseDTO.getUseCaseName(), redirectAttributes);
        }

        //UseCase Actors
        if(!uneditedUseCase.getActors().equals(useCaseDTO.getActors())){
            projectServices.editActors(useCaseId, useCaseDTO.getActors(), redirectAttributes);
        }

        //UseCase PreCondition
        if(!uneditedUseCase.getPrecond().equals(useCaseDTO.getPreCond())){
            projectServices.editPrecondition(useCaseId, useCaseDTO.getPreCond(), redirectAttributes);
        }

        //UseCase MainFlow
        if(!uneditedUseCase.getMainFlow().equals(useCaseDTO.getMainFlow())){
            projectServices.editMainFlow(useCaseId, useCaseDTO.getMainFlow(), redirectAttributes);
        }

        //UseCase AltFlow
        if(!uneditedUseCase.getAltFlow().equals(useCaseDTO.getAltFlow())){
            projectServices.editAltFlows(useCaseId, useCaseDTO.getAltFlow(), redirectAttributes);
        }

        //UseCase PostCondition
        if(!uneditedUseCase.getPostflow().equals(useCaseDTO.getPostflow())){
            projectServices.editPostCondition(useCaseId, useCaseDTO.getPostflow(), redirectAttributes);
        }

        redirectAttributes.addFlashAttribute("success","The Use Case " + uneditedUseCase.getUseCaseName() + " has been updated successfully");
        return "redirect:/project/{owner}/{name}";
    }

    @PostMapping("/project/delete-uc/{owner}/{name}")
    public String deleteUC(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String owner, @RequestParam("usecaseId") String useCaseId, @PathVariable String name, RedirectAttributes redirectAttributes){
        if(!userDetails.getUsername().equals(owner)){
            redirectAttributes.addFlashAttribute("critical", "This project isn't yours");
            return "redirect:/project/{owner}/{name}";
        }

        projectServices.deleteUseCase(Integer.parseInt(useCaseId),redirectAttributes);

        return "redirect:/project/{owner}/{name}";
    }

    @PostMapping("/project/create-crc/{owner}/{name}")
    public String createCRC(@PathVariable String owner, @PathVariable String name, @ModelAttribute CrcDTO crcDTO, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes){
        if(!userDetails.getUsername().equals(owner)){
            redirectAttributes.addFlashAttribute("critical", "This project isn't yours");
            return "redirect:/project/{owner}/{name}";
        }
        if(crcDTO.getLinked_crc() == (null)) crcDTO.setLinked_crc(new ArrayList<>());
        crcDTO.setProjectID(projectRepository.findByProjectNameAndOwnerId(name, userServices.findByUsername(owner).get()).get());
        projectServices.createCRC(crcDTO,redirectAttributes);
        return "redirect:/project/{owner}/{name}";
    }

    @PostMapping("/project/edit-crc/{owner}/{name}")
    public String editCRC(@PathVariable String owner, @PathVariable String name, @ModelAttribute CrcDTO crcDTO, @RequestParam("crcId") String crcId, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes){
        if(!userDetails.getUsername().equals(owner)){
            redirectAttributes.addFlashAttribute("critical", "This project isn't yours");
            return "redirect:/project/{owner}/{name}";
        }

        CRC oldCRC;
        try{
            oldCRC = crcRepository.findById(Integer.parseInt(crcId)).get();
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("critical","Internal Error");
            return "redirect:/project/{owner}/{name}";
        }

        if(!(oldCRC.getCrcName().equals(crcDTO.getCrcName()))){
            projectServices.updateCrcName(crcDTO.getCrcName(), oldCRC.getCrcId(), redirectAttributes);
        }

        if(crcDTO.getLinked_crc() == (null)) crcDTO.setLinked_crc(new ArrayList<>());
        if(!(oldCRC.getLinkedUseCases().equals(crcDTO.getUsecases()))){
            projectServices.updateCrcLinkedUseCases(oldCRC.getCrcId(),crcDTO.getUsecases(),redirectAttributes);
        }

        if(!(oldCRC.getCollaborators().equals(crcDTO.getLinked_crc()))){
            projectServices.updateCrcColaborators(oldCRC.getCrcId(),crcDTO.getLinked_crc(),redirectAttributes);
        }

        if(!(oldCRC.getResponsibilities().equals(crcDTO.getResponsibilities()))){
            projectServices.updateCrcResponsibilities(oldCRC.getCrcId(),crcDTO.getResponsibilities(),redirectAttributes);
        }

        redirectAttributes.addFlashAttribute("success","The CRC " + oldCRC.getCrcName() + " has been updated successfully");
        return "redirect:/project/{owner}/{name}";
    }

    @PostMapping("/project/delete-crc/{owner}/{name}")
    public String deleteCRC(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String owner, @RequestParam("crcId") String crcId, @PathVariable String name, RedirectAttributes redirectAttributes){
        if(!userDetails.getUsername().equals(owner)){
            redirectAttributes.addFlashAttribute("critical", "This project isn't yours");
            return "redirect:/project/{owner}/{name}";
        }
        projectServices.deleteCrc(Integer.parseInt(crcId),redirectAttributes);
        return "redirect:/project/{owner}/{name}";
    }

    @PostMapping("/project/gen-code/{owner}/{name}")
    public String generateCode(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String owner, @PathVariable String name, @RequestParam("website") String website, @RequestParam("umltype") String umltype, Model model, RedirectAttributes redirectAttributes){
        if(!userDetails.getUsername().equals(owner)){
            redirectAttributes.addFlashAttribute("critical", "This project isn't yours");
            return "redirect:/project/{owner}/{name}";
        }

        Project project = projectRepository.findByProjectNameAndOwnerId(name, userServices.findByUsername(owner).get()).get();

        String code = switch (umltype) {
            case "class_diagram" -> projectServices.generateClassDiagram(website, project);
            case "use_case_diagram" -> projectServices.generateUsecaseDiagram(website, project);
            default -> "";
        };

        redirectAttributes.addFlashAttribute("code",code);
        return "redirect:/project/{owner}/{name}";
    }
}
