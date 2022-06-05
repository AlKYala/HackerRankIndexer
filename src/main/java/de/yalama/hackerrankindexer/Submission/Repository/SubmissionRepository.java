package de.yalama.hackerrankindexer.Submission.Repository;

import de.yalama.hackerrankindexer.Submission.Model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
