package de.yalama.hackerrankindexer.HackerrankJSON.controller;

import de.yalama.hackerrankindexer.HackerrankJSON.model.HackerrankJSON;
import de.yalama.hackerrankindexer.HackerrankJSON.service.HackerrankJSONService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/json")
@RequiredArgsConstructor
public class HackerrankJSONController {


    @Autowired
    private HackerrankJSONService hackerrankJSONService;

    @PostMapping
    public String persistData(@RequestBody HackerrankJSON hackerrankJSON) {
        return this.hackerrankJSONService.parse(hackerrankJSON);
    }
}
