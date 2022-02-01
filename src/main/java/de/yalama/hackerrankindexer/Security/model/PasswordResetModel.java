package de.yalama.hackerrankindexer.Security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetModel {
    private String token;
    private String email;
    private String password;
}
