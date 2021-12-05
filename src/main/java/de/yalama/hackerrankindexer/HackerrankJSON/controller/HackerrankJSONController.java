package de.yalama.hackerrankindexer.HackerrankJSON.controller;

import de.yalama.hackerrankindexer.HackerrankJSON.model.HackerrankJSON;
import de.yalama.hackerrankindexer.HackerrankJSON.service.HackerrankJSONService;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.Security.service.JwtService;
import de.yalama.hackerrankindexer.User.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/json")
@RequiredArgsConstructor
public class HackerrankJSONController {

    @Autowired
    private HackerrankJSONService hackerrankJSONService;

    @Autowired
    private HeaderService headerService;

    @PostMapping
    public Integer persistData(@RequestBody HackerrankJSON hackerrankJSON, HttpServletRequest request) {
        User user = this.headerService.getUserFromHeader(request);
        return this.hackerrankJSONService.parse(hackerrankJSON, user);
    }
}
