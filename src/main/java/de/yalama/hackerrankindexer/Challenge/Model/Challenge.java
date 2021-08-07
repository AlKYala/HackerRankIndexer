package de.yalama.hackerrankindexer.Challenge.Model;

import com.sun.istack.NotNull;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Challenge extends BaseEntity {

    @OneToMany(mappedBy= "challenge")
    private Set<Submission> challenge;

    private String challengeName;
}
