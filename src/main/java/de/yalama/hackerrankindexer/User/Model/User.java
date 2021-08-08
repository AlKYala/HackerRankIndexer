package de.yalama.hackerrankindexer.User.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends BaseEntity {

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Submission> submittedEntries;

    @NotNull
    private String username;

    @NotNull
    private String email;
}
