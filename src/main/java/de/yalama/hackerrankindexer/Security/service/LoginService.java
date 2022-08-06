package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.Security.model.AuthenticationRequest;
import de.yalama.hackerrankindexer.Security.model.AuthenticationResponse;
import de.yalama.hackerrankindexer.Security.model.LogInResponse;
import de.yalama.hackerrankindexer.Security.model.LoginValidResponse;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.User.Service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtService jwtTokenUtil;

    private final UserService userService;

    public ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest) {
        this.checkIfPasswordIsCorrect(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        boolean isUserVerified = this.checkIsUserVerified(userDetails);
        return ResponseEntity.ok(new LogInResponse(jwt, isUserVerified)); //you can put any object here, response will be json representation
    }

    public ResponseEntity<?> checkisLoggedIn(HttpServletRequest httpServletRequest) {
        String jwtToken = httpServletRequest.getHeader("Authorization");
        //catch so we dont get errors in console
        boolean isValid = false;
        try {
            isValid = !this.jwtTokenUtil.isTokenExpired(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
        ResponseEntity<?> response = ResponseEntity.ok(new LoginValidResponse(isValid));
        return response;
    }

    private void checkIfPasswordIsCorrect(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            this.authenticationManager.authenticate(token);
        } catch (BadCredentialsException e){
            throw new RuntimeException("Incorrect email or password",e);
        }
    }

    private boolean checkIsUserVerified(UserDetails userDetails) {
        User user = this.userService.findByEmail(userDetails.getUsername());
        return user.isVerified();
    }
}
