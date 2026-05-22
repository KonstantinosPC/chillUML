package chill.guys.chillUML.DatagramGenerators;

import chill.guys.chillUML.domain.UseCase;

import java.util.List;

public interface UseCaseDiagramGenerator {
    public String generateDiagram(List<UseCase> usecases);
}
