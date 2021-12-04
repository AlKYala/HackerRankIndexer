package de.yalama.hackerrankindexer.Security.service;

import com.sun.mail.iap.Response;
import de.yalama.onlineshopbackend.Security.model.AuthenticationRequest;
import de.yalama.onlineshopbackend.Security.model.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtil jwtTokenUtil;

    public ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest) {
        this.checkIfPasswordIsCorrect(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    private void checkIfPasswordIsCorrect(String email, String password) {
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e){
            throw new RuntimeException("Incorrect username or password",e);
        }
    }
}
