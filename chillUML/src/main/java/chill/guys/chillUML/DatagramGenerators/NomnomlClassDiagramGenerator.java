package chill.guys.chillUML.DatagramGenerators;

import chill.guys.chillUML.domain.CRC;

import java.util.List;

public class NomnomlClassDiagramGenerator implements ClassDiagramGenerator{
    @Override
    public String generateClassDiagram(List<CRC> crcs) {
        String returnString = "";
        returnString += "#direction: right\n#padding: 15\n#spacing: 50\n#bendSize: 0.3\n";
        List<CRC> collaborators ;
        for(CRC crc : crcs){
            collaborators = crc.getCollaborators();
            for (CRC collaborator: collaborators){
                returnString += "[" + crc.getCrcName() + "] -> [" + collaborator.getCrcName() + "]\n"    ;
            }
        }
        return returnString;
    }
}
