package chill.guys.chillUML.domain;

import chill.guys.chillUML.repositories.CrcRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CRCLinkTest {

    @Autowired
    private CrcRepository crcRepository;

    @Test
    public void testUseCaseLink() {
        CRC auth = new CRC();
        auth.setCrcName("AuthManager");
        crcRepository.save(auth);

        UseCase loginUC = new UseCase();

        CRCLink trace = new CRCLink();
        trace.setCrc(auth);
        trace.setUseCase(loginUC);

        assertNotNull(trace.getUseCase());
        assertEquals("AuthManager", trace.getCrc().getCrcName());
    }

}