package de.yalama.hackerrankindexer.User.Controller;

import de.yalama.hackerrankindexer.Security.service.HeaderService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController implements BaseController<User, Long> {

    @Autowired
    private UserService userService;

    @Autowired
    private HeaderService headerService; //use here to prevent cyclic dependency

    @Override
    @GetMapping
    public List<User> findAll() {
        return this.userService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) throws HackerrankIndexerException {
        return this.userService.findById(id);
    }

    //BLOCKED ENDPOINT
    @Override
    //@PostMapping("/register")
    public User create(@RequestBody User user) throws HackerrankIndexerException {
        return this.userService.save(user);
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) throws HackerrankIndexerException, NoSuchAlgorithmException {
        return this.userService.register(user);
    }

    @Override
    public User update(@PathVariable Long id, @RequestBody User user) throws HackerrankIndexerException {
        return this.userService.update(id, user);
    }

    @Override
    public Long delete(@PathVariable Long id) throws HackerrankIndexerException {
        return this.userService.deleteById(id);
    }

    @GetMapping("/resetPassword/{id}")
    public String triggerPasswordReset(@PathVariable Long id) {
        return this.userService.triggerPasswordReset(id);
    }


    @GetMapping("/updatePassword")
    public User updatePassword(HttpServletRequest request) throws ValidationException {
        String token = request.getParameter("token");
        return this.userService.setNewPassword(token);
    }
}
