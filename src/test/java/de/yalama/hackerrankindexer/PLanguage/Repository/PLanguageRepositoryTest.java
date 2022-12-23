package de.yalama.hackerrankindexer.PLanguage.Repository;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PLanguageRepositoryTest {

    @MockBean
    private PLanguageRepository pLanguageRepository;

    @BeforeEach
    void setUp() {
        PLanguage pLanguage = new PLanguage();
        pLanguage.setId(1L);
        pLanguage.setLanguage("Java 19");
        pLanguage.setColor("#000000");
        pLanguage.setDisplayName("Java");
        pLanguage.setFileExtension(".java");
        pLanguage.setSubmissions(new HashSet<SubmissionFlat>());
        pLanguageRepository.save(pLanguage);

        PLanguage p2 = new PLanguage();
        p2.setId(2L);
        p2.setLanguage("The C Language");
        p2.setColor("#FFFFFF");
        p2.setDisplayName("C");
        p2.setFileExtension(".c");
        p2.setSubmissions(new HashSet<SubmissionFlat>());
        pLanguageRepository.saveAll(new ArrayList<>(Arrays.asList(pLanguage, p2)));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByName() {
        PLanguage result = pLanguageRepository.findByName("Java 19");

        assertEquals(result.getLanguage(), "Java 19");
    }
}