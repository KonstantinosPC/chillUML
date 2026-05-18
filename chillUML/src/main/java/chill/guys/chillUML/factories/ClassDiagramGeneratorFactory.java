package chill.guys.chillUML.factories;

import chill.guys.chillUML.domain.ClassDiagramGenerator;
import chill.guys.chillUML.domain.NomnomlClassDiagramGenerator;
import chill.guys.chillUML.domain.PlantUMLClassDiagramGenerator;

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
