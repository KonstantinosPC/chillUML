package chill.guys.chillUML.DatagramGenerators;

import chill.guys.chillUML.domain.UseCase;

import java.util.List;

public class PlantUMLUseCaseDiagramGenerator implements UseCaseDiagramGenerator {
    @Override
    public String generateDiagram(List<UseCase> usecases) {
        String returnString = "";
        List<String> actors;
        returnString +="@startuml\n";
        returnString += "left to right direction\n";
        for(UseCase usecase: usecases){
            actors = usecase.getActors();
            for(String actor:actors){
                returnString += ":"+actor+": --> ("+ usecase.getUseCaseName()+") \n";
            }
        }
        returnString+="@enduml\n";
        return returnString;
    }
}
