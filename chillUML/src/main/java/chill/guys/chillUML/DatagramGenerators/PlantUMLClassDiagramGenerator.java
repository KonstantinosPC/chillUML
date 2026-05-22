package chill.guys.chillUML.DatagramGenerators;

import chill.guys.chillUML.domain.CRC;

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
                returnString += crc.getCrcName() + " -- " + collaborator.getCrcName() + "\n";
                System.out.println(returnString);
            }
        }
        returnString+="@enduml\n";
        return returnString;
    }
}
