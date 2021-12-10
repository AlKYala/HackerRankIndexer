package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.Security.model.AuthenticationRequest;
import de.yalama.hackerrankindexer.Security.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
        log.info(userDetails.getPassword());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        log.info(jwt);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    private void checkIfPasswordIsCorrect(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
            this.authenticationManager.authenticate(token);
        } catch (BadCredentialsException e){
            throw new RuntimeException("Incorrect username or password",e);
        }
    }
}
