package de.yalama.hackerrankindexer.Challenge.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
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
    @JsonIgnore
    private Set<Submission> submissions;

    private String challengeName;
}
