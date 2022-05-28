package de.yalama.hackerrankindexer.UserData.Controller;

import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Service.UserDataService;
import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
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
import java.util.List;

@RestController
@RequestMapping("/userdata")
@CrossOrigin(origins = "http://localhost:4200")
public class UserDataController implements BaseController<UserData, Long> {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private HeaderService headerService;

    /**
     * Used to generate a Permalink
     * @param httpServletRequest
     * @return
     */
    @PostMapping("/generate")
    public String getPermalinkForUserData(HttpServletRequest httpServletRequest) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException,
            BadPaddingException, InvalidKeyException, IOException {
        Long userDataId = Long.parseLong(httpServletRequest.getParameter("userDataId"));
        UserData userData = this.userDataService.findById(userDataId);
        String tokenId = httpServletRequest.getSession().getId().replace("-", "");
        userData.setToken(tokenId);
        String domain = "localhost:8080"; //replace with env variable
        return String.format("%s/userdata/%s", domain, tokenId);
    }

    /**
     * Endpoint to access User data - used for permalinks
     * @param token
     * @return
     */
    @GetMapping("/{token}")
    public UserData resolveUserDataFromPermalink(@PathVariable String token) {
        return this.userDataService.findUserDataByToken(token);
    }

    //CLOSED
    @Override
    public List<UserData> findAll() {
        return this.userDataService.findAll();
    }

    //CLOSED
    @Override
    public UserData findById(Long aLong) throws HackerrankIndexerException {
        return this.userDataService.findById(aLong);
    }

    @PostMapping
    @Override
    public UserData create(UserData userData) throws HackerrankIndexerException {
        return this.userDataService.save(userData);
    }

    //CLOSED
    @Override
    public UserData update(Long aLong, UserData userData) throws HackerrankIndexerException {
        return this.userDataService.update(aLong, userData);
    }

    @DeleteMapping("({id}")
    @Override
    public Long delete(@PathVariable Long id) throws HackerrankIndexerException {
        return this.userDataService.deleteById(id);
    }
}
