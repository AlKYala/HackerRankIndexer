package de.yalama.hackerrankindexer.Challenge.Repository;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
}
