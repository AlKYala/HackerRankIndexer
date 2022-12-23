package de.yalama.hackerrankindexer.SubmissionFlat.Repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
public class SubmissionFlatRepositoryTest {

    @Autowired
    private SubmissionFlatRepository submissionFlatRepository;

    @BeforeAll
    static void ball() {
        //System.out.println("ball");
    }

    @AfterAll
    static void aall() {
        //System.out.println("aall");
    }

    //vor jedem Test ausgefuehrt
    @BeforeEach
    void setUp() {
        System.out.println("Number Repo: " + submissionFlatRepository.findAll().size());
    }

    //nach jedem Test ausgefuehrt
    @AfterEach
    void tearDown() {

    }

    @Test
    void getSubmissionsFromIDs() {
    }

    @Test
    void findAllByUserDataId() {
    }

    @Test
    void getAllPassed() {
    }

    @Test
    void getAllFailed() {
    }

    @Test
    void getSubmissionsByChallengeIdAndUserDataId() {
    }

    @Test
    void getSubmissionsByPlanguageIdAndUserDataId() {
    }
}