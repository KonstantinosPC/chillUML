package chill.guys.chillUML.domain;

import java.util.ArrayList;
import java.util.List;

public class PlantUMLClassDiagramGenerator implements ClassDiagramGenerator{
    @Override
    public String generateClassDiagram(List<CRC> crcs) {
        String returnString = "";
        List<CRC> collaborators ;
        returnString +="@startuml\n";
        returnString += "left to right direction\n";
        for(CRC crc : crcs){
            collaborators = new ArrayList<>(crc.getCollaborators());
            for (CRC collaborator: collaborators){
                returnString += "class" + crc.getCrcName() + "--" + collaborator.getCrcName();
                System.out.println(returnString);
            }
        }
        return returnString;
    }
}
