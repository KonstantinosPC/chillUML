package chill.guys.chillUML.domain;

import chill.guys.chillUML.repositories.CrcRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CRCResponsibilitiesTest {

    @Autowired
    private CrcRepository crcRepository;

    @Test
    public void testCRCResponsibilitiesMethod() {
        CRC loggerClass = new CRC();
        loggerClass.setCrcName("SystemLogger");

        crcRepository.save(loggerClass);

        CRCResponsibilities resp = new CRCResponsibilities();
        resp.setDescription("Stores any important information about the system");
        resp.setCRCID(loggerClass);

        assertEquals("SystemLogger", resp.getCrcID().getCrcName());
        assertEquals("Stores any important information about the system", resp.getDescription());
    }
}