package de.yalama.hackerrankindexer.UserData.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class UserDataFlat {
    private Date date;
    private String token;
}
