package de.yalama.hackerrankindexer.User.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseEntity {

    @OneToMany(mappedBy = "user")
    private List<UserData> userData;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String passwordHashed;

    private boolean isVerified;

    /**
    Verification token for Email Verification
     */
    private String token;
}
