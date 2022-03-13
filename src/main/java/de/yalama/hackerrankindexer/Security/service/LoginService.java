package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.Security.model.AuthenticationRequest;
import de.yalama.hackerrankindexer.Security.model.AuthenticationResponse;
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

    public ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest) {
        this.checkIfPasswordIsCorrect(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    public ResponseEntity<?> checkisLoggedIn(HttpServletRequest httpServletRequest) {
        String jwtToken = httpServletRequest.getHeader("Authorization");
        //catch so we dont get errors in console
        try {
            this.jwtTokenUtil.isTokenExpired(jwtToken);
        } catch (ExpiredJwtException expiredJwtException) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().build();
    }

    private void checkIfPasswordIsCorrect(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            this.authenticationManager.authenticate(token);
        } catch (BadCredentialsException e){
            throw new RuntimeException("Incorrect email or password",e);
        }
    }
}
