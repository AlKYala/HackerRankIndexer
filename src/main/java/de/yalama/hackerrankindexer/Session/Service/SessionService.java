package de.yalama.hackerrankindexer.Session.Service;

import javax.servlet.http.HttpServletRequest;

public abstract class SessionService {
    public abstract long getFreeSessionId(HttpServletRequest httpServlet);

    public abstract Long retireId(long id);

    public abstract boolean checkIdExists(long id);

    public abstract Long getCurrentSessionId(HttpServletRequest httpServletRequest);
}
