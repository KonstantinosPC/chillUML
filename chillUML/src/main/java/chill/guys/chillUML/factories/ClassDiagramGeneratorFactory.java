package chill.guys.chillUML.factories;

import chill.guys.chillUML.DatagramGenerators.ClassDiagramGenerator;
import chill.guys.chillUML.DatagramGenerators.NomnomlClassDiagramGenerator;
import chill.guys.chillUML.DatagramGenerators.PlantUMLClassDiagramGenerator;

public class ClassDiagramGeneratorFactory {
    public ClassDiagramGenerator createClassDiagramGenerator(String type){
        ClassDiagramGenerator generator;
        if("plantUml".equals(type)){
            generator = new PlantUMLClassDiagramGenerator();
        }
        else if("nomnoml".equals(type)){
            generator = new NomnomlClassDiagramGenerator();
        }
        else{
            return null;
        }
        return generator;
    }
}
