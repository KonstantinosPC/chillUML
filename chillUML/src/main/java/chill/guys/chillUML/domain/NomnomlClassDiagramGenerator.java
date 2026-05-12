package chill.guys.chillUML.domain;

import java.util.List;

public class NomnomlClassDiagramGenerator implements ClassDiagramGenerator{
    @Override
    public String generateClassDiagram(List<CRC> crcs) {
        String returnString = "";
        returnString += "#direction: right\n#padding: 15\n#spacing: 50\nbendSize: 0.3\n";
        List<CRC> collaborators ;
        for(CRC crc : crcs){
            collaborators = crc.getCollaborators();
            for (CRC collaborator: collaborators){
                returnString += "[" + crc.getCrcName() + "] -> [" + collaborator.getCrcName() + "]"    ;
            }
        }
        return returnString;
    }
}