package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class HeaderService {

    JwtService jwtService;
    UserService userService;

    public HeaderService(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public String extractJWTTokenFromRequest(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public long extractIdFromHeader(HttpServletRequest request) {
        String longString = jwtService.extractId(this.extractJWTTokenFromRequest(request));
        return Long.valueOf(longString);
    }

    public User getUserFromHeader(HttpServletRequest request) {
        return this.userService.findById(this.extractIdFromHeader(request));
    }
}
