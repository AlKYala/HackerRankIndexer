package de.yalama.hackerrankindexer.Security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LogInResponse {

    private String jwt;
    private boolean isVerifiedUser;
}
