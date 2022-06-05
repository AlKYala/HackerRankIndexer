package de.yalama.hackerrankindexer.Contest.Service;

import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.shared.services.BaseService;

public abstract class ContestService implements BaseService<Contest> {

    /**
     * A method to persist a contest only if it cannot be found by name.
     * Saves the contest if not exists, else returns the found contest
     * @param contest The contest to persist
     * @return The persisted contest
     */
    public abstract Contest persist(Contest contest);

    /**
     * Checks if a Contest by given name exists
     * Used for ContestService::persist
     * @param contestName the contest name to check
     * @return The found contest by name (can be null)
     */
    public abstract Contest findByName(String contestName);
}
