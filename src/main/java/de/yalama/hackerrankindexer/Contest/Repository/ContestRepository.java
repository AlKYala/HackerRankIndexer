package de.yalama.hackerrankindexer.Contest.Repository;

import de.yalama.hackerrankindexer.Contest.Model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {

    public Contest findContestByName(String name);
}
