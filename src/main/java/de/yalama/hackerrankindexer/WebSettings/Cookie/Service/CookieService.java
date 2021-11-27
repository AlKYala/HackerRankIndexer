package de.yalama.hackerrankindexer.WebSettings.Cookie.Service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * A servlet to handle all cookie operations
 */
public abstract class CookieService {

    public abstract Cookie createCookie(String sessionId);

    public abstract Cookie readServletCookie(HttpServletRequest request, HttpServletResponse response);

    public abstract Cookie createDeletionCookie(HttpServletRequest request);

    public abstract String getCookieValueString(HttpServletRequest request, HttpServletResponse response);
}
