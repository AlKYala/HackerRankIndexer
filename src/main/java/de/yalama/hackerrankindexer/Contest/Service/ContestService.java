package de.yalama.hackerrankindexer.Contest.Service;

import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.shared.services.BaseService;

public abstract class ContestService implements BaseService<Contest> {
    public abstract Contest persist(Contest contest);
    public abstract Contest findByName(String contestName);
    public abstract boolean checkExistsByName(String contestName);
}
