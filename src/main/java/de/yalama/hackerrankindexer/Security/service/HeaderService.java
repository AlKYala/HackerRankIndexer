package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import de.yalama.hackerrankindexer.shared.exceptions.UserNotVerifiedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class HeaderService {

    JwtService jwtService;
    UserService userService;

    public HeaderService(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    private String extractJWTTokenFromRequest(HttpServletRequest request) {
        log.info(request.getHeader("Authorization"));
        return request.getHeader("Authorization");
    }

    private long extractIdFromHeader(HttpServletRequest request) {
        String jwtToken = this.extractJWTTokenFromRequest(request);
        String longString = jwtService.extractId(jwtToken);
        log.info("Extracting userID: {}", longString);
        return Long.valueOf(longString);
    }

    public User getUserFromHeader(HttpServletRequest request) {
        Long id = this.extractIdFromHeader(request);
        System.out.printf("Userid: %d\n", id);
        User user = this.userService.findById(id);
        if(user.isVerified() == false) {
            throw new UserNotVerifiedException(String.format("User %s exists but is not verified", user.getEmail()));
        }
        return user;
    }
}
