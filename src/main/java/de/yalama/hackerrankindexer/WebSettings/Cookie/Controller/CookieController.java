package de.yalama.hackerrankindexer.WebSettings.Cookie.Controller;

import de.yalama.hackerrankindexer.WebSettings.Cookie.Service.CookieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

    /*
    TODO Check:
    0. Wo HttpServletResponse einarbeiten?
    1. is value first set to null?
    2. assign the value
    3. check if you request again if the value is not null anymore
     */
    @GetMapping
    public String getCookie(HttpSession httpSession, HttpServletRequest httpServletRequest) {
        String id = this.cookieService.readServletCookie(httpServletRequest).get().getValue();
        log.info(id);
        if(id == null) {
            Cookie cookie = this.cookieService.createCookie(httpSession.getId());

        }
        return id;
    }

    @GetMapping("/string")
    public String getCookieString(HttpServletRequest httpServletRequest) {
        return this.cookieService.getCookieValueString(httpServletRequest);
    }

}
