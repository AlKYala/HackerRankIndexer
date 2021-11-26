package de.yalama.hackerrankindexer.WebSettings.Cookie.Service;

import de.yalama.hackerrankindexer.Session.Service.SessionService;
import de.yalama.hackerrankindexer.WebSettings.Cookie.Settings.CookieConstants;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Service
@NoArgsConstructor
@Slf4j
public class CookieServiceImpl extends CookieService {

    /*
    TODO Cookie vs HttpCookie?
     https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpCookie.html

     probiers erstmal mit cookie

     Dir stehen viele Optionen offen wie du das angehst
     */

    /**
     * sessionId provided by HttpSession
     * @param response the response to add the cookie to
     * @param sessionId provided by httpSession
     */
    public void addCookieToResponse(HttpServletResponse response, String sessionId) {
        response.addCookie(this.createCookie(sessionId));
    }

    @Override
    public Cookie createCookie(String sessionId) {
        Cookie temp = new Cookie("userId", sessionId);
        temp.setHttpOnly(CookieConstants.httpOnly);
        temp.setSecure(CookieConstants.isSecure);
        temp.setMaxAge(CookieConstants.maxAge);
        temp.setValue(sessionId);
        return temp;
    }

    @Override
    public Optional<Cookie> readServletCookie(HttpServletRequest request) {
        log.info("{}", request.getSession().getId());
        log.info("{}", request == null);
        log.info("{}", request.getCookies().length);
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getValue() != null)
                .findAny();
    }

    @Override
    public Cookie createDeletionCookie(HttpServletRequest request) {
        Cookie temp = this.createCookie(request.getSession().getId());
        temp.setMaxAge(0);
        return temp;
    }

    @Override
    public String getCookieValueString(HttpServletRequest request) {
        return this.readServletCookie(request).get().getValue();
    }
}
