package chill.guys.chillUML.DatagramGenerators;

import chill.guys.chillUML.domain.CRC;

import java.util.List;

public interface ClassDiagramGenerator {
    public String generateClassDiagram(List<CRC> crcs);
}
