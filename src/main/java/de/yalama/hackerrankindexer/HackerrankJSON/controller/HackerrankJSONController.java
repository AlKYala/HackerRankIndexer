package de.yalama.hackerrankindexer.HackerrankJSON.controller;

import de.yalama.hackerrankindexer.HackerrankJSON.model.HackerrankJSON;
import de.yalama.hackerrankindexer.HackerrankJSON.service.HackerrankJSONService;
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

    @PostMapping
    public Integer persistData(@RequestBody HackerrankJSON hackerrankJSON, HttpSession httpSession) {
        return this.hackerrankJSONService.parse(hackerrankJSON);
    }
}
