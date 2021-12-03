package de.yalama.hackerrankindexer.Session.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class SessionService {

    public abstract String getCurrentSessionId(HttpSession httpSession);
}
