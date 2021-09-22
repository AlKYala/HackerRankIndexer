package de.yalama.hackerrankindexer.Session.Service;

public abstract class SessionService {
    public abstract long getFreeSessionId();

    public abstract void retireId(long id);

    public abstract boolean checkIdExists(long id);
}
