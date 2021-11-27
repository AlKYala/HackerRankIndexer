package de.yalama.hackerrankindexer.WebSettings.Cookie.Service;

import de.yalama.hackerrankindexer.Session.Service.SessionService;
import de.yalama.hackerrankindexer.WebSettings.Cookie.Settings.CookieConstants;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.DynamicType;
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
    public Cookie readServletCookie(HttpServletRequest request, HttpServletResponse response) {
        if(request.getCookies() == null || request.getCookies().length == 0) {
            return this.createCookie(request, response);
        }
        return request.getCookies()[0];
    }

    private Cookie createCookie(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getSession().getId();
        Cookie cookie = this.createCookie(id);
        response.addCookie(cookie);
        return cookie;
    }

    @Override
    public Cookie createDeletionCookie(HttpServletRequest request) {
        Cookie temp = this.createCookie(request.getSession().getId());
        temp.setMaxAge(0);
        return temp;
    }

    /**
     * Takes a request that has a cookie and gives us the value persisted in the cookie
     * @param request The HttpServletRequest
     * @return the Cookie::getValue
     */
    @Override
    public String getCookieValueString(HttpServletRequest request, HttpServletResponse response) {
        return this.readServletCookie(request, response).getValue();
    }
}
