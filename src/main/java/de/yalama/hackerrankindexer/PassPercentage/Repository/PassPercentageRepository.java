package de.yalama.hackerrankindexer.PassPercentage.Repository;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PassPercentageRepository extends JpaRepository<PassPercentage, Long> {

    @Query("select p from PassPercentage p where p.userData.id in (:userDataId) and p.pLanguage.id in (:pLanguageId)")
    public PassPercentage findByUserAndLanguage(Long userDataId, Long pLanguageId);

    @Query("select count(s.id) from SubmissionFlat s where s.score = 1 and s.userData.id in (:userDataId) and s.language.id in (:pLanguageId)")
    public long findNumberOfPassedSubmissionsOfUserDataAndLanguage(Long userDataId, Long pLanguageId);

    @Query("select count(s.id) from SubmissionFlat s where s.userData.id in (:userDataId) and s.language.id in (:pLanguageId)")
    public long findNumberOfSubmissionsOfUserDataAndLanguage(Long userDataId, Long pLanguageId);
}
