package de.yalama.hackerrankindexer.WebSettings.Cookie.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CookieServiceImpl extends CookieService{
    @Override
    public Cookie createCookie(String sessionId) {
        return null;
    }

    @Override
    public Optional<String> readServletCookie(HttpServletRequest request) {
        return Optional.empty();
    }

    @Override
    public Cookie createDeletionCookie(HttpServletRequest request) {
        return null;
    }
}
