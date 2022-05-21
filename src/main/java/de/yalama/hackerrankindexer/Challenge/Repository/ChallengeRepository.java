package de.yalama.hackerrankindexer.Challenge.Repository;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query("SELECT c from Challenge c inner join Submission s on s.challenge.id = c.id inner join UserData ud on ud.id = s.id where c.id in (:challengeId) and ud.id in (:userDataId)")
    public Challenge getChallengeByIdAndUserDataId(Long challengeId, Long userDataId);

    @Query("SELECT c from Challenge c inner join Submission s on s.challenge.id = c.id inner join UserData ud on ud.id = s.id where ud.id in (:userDataId)")
    public List<Challenge> getAllChallengesByUserDataId(Long userDataId);

    @Query("SELECT c from Challenge c inner join Submission s on s.challenge.id = c.id inner join UserData ud on ud.id = s.id where ud.id in (:userDataId) and s.score = 1")
    public List<Challenge> getPassedChallengesByUserDataId(Long userDataId);

    @Query("SELECT c from Challenge c inner join Submission s on s.challenge.id = c.id inner join UserData ud on ud.id = s.id where ud.id in (:userDataId) and s.score < 1")
    public List<Challenge> getFailedChallengesByUserDataId(Long userDataId);

    @Query("SELECT c from Challenge c where c.challengeName in (:challengeName)")
    public Challenge findByName(String challengeName);
}
