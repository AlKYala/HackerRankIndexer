package de.yalama.hackerrankindexer.WebSettings.Cookie.Service;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * A servlet to handle all cookie operations
 */
@Service
public abstract class CookieService {

    public abstract Cookie createCookie(String sessionId);

    public abstract Optional<String> readServletCookie(HttpServletRequest request);

    public abstract Cookie createDeletionCookie(HttpServletRequest request);
}
