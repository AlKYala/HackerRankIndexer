package de.yalama.hackerrankindexer.UsagePercentage.Repository;

import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsagePercentageRepository extends JpaRepository<UsagePercentage, Long> {

    @Query("select count(s.id) from SubmissionFlat s where s.userData.id in (:userDataId) and s.language.id in (:pLanguageId)")
    public long findNumberSubmissionsInLanguageOfUserData(long userDataId, long pLanguageId);

    @Query("select count(s.id) from SubmissionFlat s where s.userData.id in (:userDataId)")
    public long findNumberSubmissionsOfUserData(long userDataId);
}
