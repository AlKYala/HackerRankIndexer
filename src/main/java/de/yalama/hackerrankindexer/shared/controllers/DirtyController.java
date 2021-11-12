package de.yalama.hackerrankindexer.shared.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * This controller is used for testing purposed, especially session Management!
 * This does not go into the project
 *
 * Delete this later or keep for reference purposes
 */
@RestController
@RequestMapping("/dirty")
@Slf4j
public class DirtyController {

    @GetMapping("id")
    public String getSessionId(HttpSession httpSession) {
        return httpSession.getId();
    }
}
