package de.yalama.hackerrankindexer.HackerrankJSON.controller;

import de.yalama.hackerrankindexer.HackerrankJSON.model.HackerrankJSON;
import de.yalama.hackerrankindexer.HackerrankJSON.service.HackerrankJSONService;
import de.yalama.hackerrankindexer.Session.Service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/json")
@RequiredArgsConstructor
public class HackerrankJSONController {

    @Autowired
    private HackerrankJSONService hackerrankJSONService;

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public Integer persistData(@RequestBody HackerrankJSON hackerrankJSON, HttpSession httpSession) {
        String sessionId = sessionService.getCurrentSessionId(httpSession);
        return this.hackerrankJSONService.parse(hackerrankJSON, sessionId);
    }
}
