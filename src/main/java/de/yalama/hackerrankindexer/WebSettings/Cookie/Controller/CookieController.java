package de.yalama.hackerrankindexer.WebSettings.Cookie.Controller;

import de.yalama.hackerrankindexer.WebSettings.Cookie.Service.CookieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This class serves for testing purposes to learn how to handle cookies
 */

@Slf4j
@RestController
@RequestMapping("/cookie")
public class CookieController {

    private CookieService cookieService;

    public CookieController(CookieService cookieService) {
        this.cookieService = cookieService;
    }

    @GetMapping
    public String getCookie(HttpServletRequest httpServletRequest, HttpServletResponse response) {
        String id = this.cookieService.getCookieValueString(httpServletRequest, response);
        log.info(id);

        return id;
    }

    @GetMapping("/string")
    public String getCookieString(HttpServletResponse response, HttpServletRequest httpServletRequest) {
        return this.cookieService.getCookieValueString(httpServletRequest, response);
    }
}
