package chill.guys.chillUML.domain;

import java.util.List;

public class PlantUMLUseCaseDiagramGenerator implements UseCaseDiagramGenerator {
    @Override
    public String generateDiagram(List<UseCase> usecases) {
        String returnString = "";
        String[] actors;
        returnString +="@startuml\n";
        returnString += "left to right direction\n";
        for(UseCase usecase: usecases){
            actors = usecase.getActors().split(",");
            for(String actor:actors){
                returnString += ":"+actor+": --> ("+ usecase.getUseCaseName()+") \n";
            }
        }
        returnString+="@enduml\n";
        return returnString;
    }
}
