package de.yalama.hackerrankindexer.Security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;

    public AuthenticationRequest() {}

    public AuthenticationRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }
}
