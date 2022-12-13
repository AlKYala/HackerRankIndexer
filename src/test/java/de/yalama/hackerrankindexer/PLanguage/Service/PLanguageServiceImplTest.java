package de.yalama.hackerrankindexer.PLanguage.Service;

import de.yalama.hackerrankindexer.PLanguage.Repository.PLanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PLanguageServiceImplTest {

    @MockBean
    private PLanguageRepository pLanguageRepository;

    private PLanguageServiceImpl pLanguageService;

    @BeforeEach
    void setUp() {
        this.pLanguageService = new PLanguageServiceImpl(pLanguageRepository, new PLanguageInfoService());
    }

    @Test
    void findById() {
    }

    @Test
    @Disabled
    void findAll() {
    }

    @Test
    void findByName() {
    }
}