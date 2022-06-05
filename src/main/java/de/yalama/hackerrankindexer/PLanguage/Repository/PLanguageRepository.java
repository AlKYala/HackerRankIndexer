package de.yalama.hackerrankindexer.PLanguage.Repository;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PLanguageRepository extends JpaRepository<PLanguage, Long> {

    @Query("Select p from PLanguage p inner join SubmissionFlat s on s.language.id = p.id inner join UserData ud on ud.id = s.userData.id where ud.id in (:userDataId)")
    public List<PLanguage> getLanguageByUserDataId(Long userDataId);

    @Query("SELECT p from PLanguage p where p.language in (:pLanguageName)")
    public PLanguage findByName(String pLanguageName);
}
