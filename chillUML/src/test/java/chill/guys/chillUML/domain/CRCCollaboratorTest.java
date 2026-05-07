package chill.guys.chillUML.domain;

import chill.guys.chillUML.repositories.CrcRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CRCCollaboratorTest {
    @Autowired
    private CrcRepository crcRepository;

    @Test
    public void testCRCCollaboratorMethods() {
        CRC gui = new CRC();
        gui.setCrcName("GraphicUserInterface");

        CRC api = new CRC();
        api.setCrcName("BackendAPI");

        crcRepository.save(gui);
        crcRepository.save(api);

        CRCCollaborator link = new CRCCollaborator();
        link.setCrc(gui);
        link.setCrc_collaborator(api);

        assertEquals("GraphicUserInterface", link.getCrc().getCrcName());
        assertEquals("BackendAPI", link.getCrc_collaborator().getCrcName());
    }
}