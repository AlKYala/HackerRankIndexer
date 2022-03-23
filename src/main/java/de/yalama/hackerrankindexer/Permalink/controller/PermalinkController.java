package de.yalama.hackerrankindexer.Permalink.controller;

import de.yalama.hackerrankindexer.Permalink.Model.PermalinkInformation;
import de.yalama.hackerrankindexer.Permalink.service.PermalinkService;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.User.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/permalink")
@CrossOrigin(origins = "http://localhost:4200")
public class PermalinkController {

    @Autowired
    private PermalinkService permalinkService;

    @Autowired
    private HeaderService headerService;

    /**
     * Used to generate a Permalink
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/generate")
    public String getPermalinkForUser(HttpServletRequest httpServletRequest) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException,
            BadPaddingException, InvalidKeyException, IOException {
        User user = this.headerService.getUserFromHeader(httpServletRequest);
        return this.permalinkService.getPermalinkForUser(user);
    }

    /**
     * Endpoint to access User data - used for permalinks
     * @param token
     * @return
     */
    @GetMapping("/{token}")
    public PermalinkInformation resolveUserFromPermalink(@PathVariable String token) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException,
            BadPaddingException, InvalidKeyException {
        System.out.println("firing");
        System.out.println(token);
        PermalinkInformation permalinkInformation = this.permalinkService.resolveUserFromLink(token);
        return permalinkInformation;
    }

}
