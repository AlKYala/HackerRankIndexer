package de.yalama.hackerrankindexer.GeneralPercentage.Repository;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralPercentageRepository extends JpaRepository<GeneralPercentage, Long> {

    @Query("select count(distinct c.id) from UserData ud inner join Submission s on s.userData.id = ud.id inner join Challenge c on c.id = s.challenge.id where ud.id in (:userDataId)" )
    public Long countAllChallengesByUserDataId(Long userDataId);

    @Query("select count(distinct c.id) from UserData ud inner join Submission s on s.userData.id = ud.id inner join Challenge c on c.id = s.challenge.id where s.score = 1 AND ud.id in (:userDataId)" )
    public Long countAllPassedChallengesByUserDataId(Long userDataId);

    @Query("select count(distinct s.id) from UserData ud inner join Submission s on s.userData.id = ud.id where ud.id in (:userDataId)")
    public Long countAllSubmissionsByUserData(Long userDataId);

    @Query("select count(distinct s.id) from UserData ud inner join Submission s on s.userData.id = ud.id where ud.id in (:userDataId) and s.score = 1")
    public Long countPassedSubmissionsByUserData(Long userDataId);
}