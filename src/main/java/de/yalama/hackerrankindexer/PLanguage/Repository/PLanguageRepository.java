package de.yalama.hackerrankindexer.PLanguage.Repository;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PLanguageRepository extends JpaRepository<PLanguage, Long> {
}
