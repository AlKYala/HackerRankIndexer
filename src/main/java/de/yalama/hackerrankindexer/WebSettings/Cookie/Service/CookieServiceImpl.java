package de.yalama.hackerrankindexer.WebSettings.Cookie.Service;

import de.yalama.hackerrankindexer.Session.Service.SessionService;
import de.yalama.hackerrankindexer.WebSettings.Cookie.Settings.CookieConstants;
import org.hibernate.Session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CookieServiceImpl extends CookieService {

    /*
    TODO Cookie vs HttpCookie?
     https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpCookie.html
     */

    @Override
    public Cookie createCookie(String sessionId) {
        Cookie temp = new Cookie("userId", sessionId);
        temp.setHttpOnly(CookieConstants.httpOnly);
        temp.setSecure(CookieConstants.isSecure);
        temp.setMaxAge(CookieConstants.maxAge);
        return temp;
    }

    @Override
    public Optional<String> readServletCookie(HttpServletRequest request) {
        return Optional.empty();
    }

    @Override
    public Cookie createDeletionCookie(HttpServletRequest request) {
        Cookie temp = this.createCookie(request.getSession().getId());
        temp.setMaxAge(0);
        return temp;
    }
}
