package de.yalama.hackerrankindexer.Session.Controller;

import de.yalama.hackerrankindexer.Session.Service.SessionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/session")
public class SessionController {

    private SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/getId")
    public Long getCurrentSessionId(HttpServletRequest httpServlet) {
        return this.sessionService.getCurrentSessionId(httpServlet);
    }

    @PostMapping("/retireId/{id}")
    public Long retireSessionId(HttpServletRequest httpServlet, @PathVariable Long id) {
        return this.sessionService.retireId(id);
    }
}
