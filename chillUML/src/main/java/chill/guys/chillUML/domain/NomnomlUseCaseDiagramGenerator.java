package chill.guys.chillUML.domain;

import java.util.List;

public class NomnomlUseCaseDiagramGenerator implements UseCaseDiagramGenerator{
    @Override
    public String generateDiagram(List<UseCase> usecases) {
        String returnString = "";
        List<String> actors;
        returnString += "#direction: right\n#padding: 15\n#spacing: 50\nbendSize: 0.3\n";
        for(UseCase usecase: usecases){
            actors = usecase.getActors();
            for(String actor:actors){
                returnString += "[<actor>"+actor+"]\n";
                returnString += "["+actor+"] -> ["+usecase.getUseCaseName()+"]\n";
            }
        }
        return returnString;
    }
}
