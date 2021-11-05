package de.yalama.hackerrankindexer.PLanguage.Service;

import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.services.BaseService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class PLanguageService implements BaseService<PLanguage> {
    /**
     * returns all submissions of a language, queried by language id
     * @param id
     */
    public abstract List<Submission> findSubmissionsOfLanguageAndSessionId(Long id, String sessionId);

    /**
     * returns all languages used by a specific sessionId
     * @param sessionId
     */
    public abstract List<PLanguage> findPLanguagesUsedBySessionId(String sessionId);

    /**
     * returns a pLanguage by matchign name - null if not exists
     * @param name
     */
    public abstract PLanguage findByName(String name);

    /**
     * checks if a language by name exists. Does not check by display name
     * @param name
     */
    public abstract boolean checkExistsByName(String name);

    /**
     * Saves only if a challenge by the same name does not exist already
     * if exists, returns saved challenge instead
     */
    public abstract PLanguage persist(PLanguage pLanguage);

    /**
     * returns all passed submissions for a language by SessionId
     * @param languageId the id of the language
     * @param sessionId the sessionId
     * @return A collection for the passed submissions of the language
     */
    public abstract Collection<Submission> getPassedSubmissionsForLanguage(Long languageId, String sessionId);

    /**
     * See PLanguageService::getPassedSubmissionsForLanguage but for several Languages
     * @param languageIds the ids of the languages
     * @param sessionId the sessionId
     * @return A collection for the passed submissions of the language
     */
    public abstract Collection<Submission> getPassedSubmissionsForAllLanguages(Long[] languageIds, String sessionId);

    /**
     * returns all failed submissions for a language by SessionId
     * @param languageId the id of the language
     * @param sessionId the sessionId
     * @return A collection for the failed submissions of the language
     */
    public abstract Collection<Submission> getFailedSubmissionsForLanguage(Long languageId, String sessionId);

    /**
     * See PLanguageService::getFailedSubmissionsForLanguage but for several Languages
     * @param languageIds the ids of the languages
     * @param sessionId the sessionId
     * @return A collection for the failed submissions of the language
     */
    public abstract Collection<Submission> getFailedSubmissionsForAllLanguages(Long[] languageIds, String sessionId);
}
