package de.yalama.hackerrankindexer.Submission.Repository;

import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    @Query("select s from Submission s where s.id in (:submissionIDs)")
    public abstract Collection<Submission> getSubmissionsFromIDs(Collection<Long> submissionIDs);

    @Query("select s from Submission s where s.userData.id in (:userDataId) and s.score = 1")
    public abstract List<Submission> getAllPassed(Long userDataId);

    @Query("select s from Submission s where s.userData.id in (:userDataId) and s.score = 1")
    public abstract List<Submission> getAllFailed(Long userDataId);

    @Query("select s from Submission s where s.userData.id in (:userDataId) and s.challenge.id in (:challengeId)")
    public abstract List<Submission> getSubmissionsByChallengeIdAndUserDataId(Long challengeId, Long userDataId);
}
