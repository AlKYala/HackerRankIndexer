package de.yalama.hackerrankindexer.User.Controller;

import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.shared.controllers.BaseController;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import de.yalama.hackerrankindexer.shared.services.EmailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UserController implements BaseController<User, Long> {

    @Autowired
    private UserService userService;

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
}
