package de.yalama.hackerrankindexer.SubmissionFlat.Repository;

import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface SubmissionFlatRepository extends JpaRepository<SubmissionFlat, Long> {
    @Query("select s from SubmissionFlat s where s.id in (:submissionIDs)")
    public abstract Collection<SubmissionFlat> getSubmissionsFromIDs(Collection<Long> submissionIDs);

    @Query("select s from SubmissionFlat s where s.userData.id in (:userDataId)")
    public abstract List<SubmissionFlat> findAllByUserDataId(Long userDataId);

    @Query("select s from SubmissionFlat s where s.userData.id in (:userDataId) and s.score = 1")
    public abstract List<SubmissionFlat> getAllPassed(Long userDataId);

    @Query("select s from SubmissionFlat s where s.userData.id in (:userDataId) and s.score = 1")
    public abstract List<SubmissionFlat> getAllFailed(Long userDataId);

    @Query("select s from SubmissionFlat s where s.userData.id in (:userDataId) and s.challenge.id in (:challengeId)")
    public abstract List<SubmissionFlat> getSubmissionsByChallengeIdAndUserDataId(Long challengeId, Long userDataId);

    @Query("select s from SubmissionFlat s where s.userData.id in (:userDataId) and s.language in (:pLanguageId)")
    public abstract List<SubmissionFlat> getSubmissionsByPlanguageIdAndUserDataId(Long pLanguageId, Long userDataId);
}
