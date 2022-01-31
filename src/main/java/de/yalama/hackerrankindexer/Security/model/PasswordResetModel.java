package de.yalama.hackerrankindexer.Security.model;

import lombok.Getter;

@Getter
public class PasswordResetModel {
    private String token;
    private String email;
}
