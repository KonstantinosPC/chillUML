package chill.guys.chillUML.factories;

import chill.guys.chillUML.DatagramGenerators.NomnomlUseCaseDiagramGenerator;
import chill.guys.chillUML.DatagramGenerators.PlantUMLUseCaseDiagramGenerator;
import chill.guys.chillUML.DatagramGenerators.UseCaseDiagramGenerator;

public class UseCaseDiagramGeneratorFactory {
    public UseCaseDiagramGenerator createUseCaseDiagramGenerator(String type){
        UseCaseDiagramGenerator generator;

        if(type.equals("plantUml")){
            generator = new PlantUMLUseCaseDiagramGenerator();
        } else if (type.equals("nomnoml")) {
            generator = new NomnomlUseCaseDiagramGenerator();
        }else{
            return null;
        }
        return generator;
    }
}
