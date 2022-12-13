package de.yalama.hackerrankindexer.Challenge.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Challenge extends BaseEntity {

    @OneToMany(mappedBy= "challenge")
    @JsonIgnore
    private Set<SubmissionFlat> submissions;

    private String challengeName;
}
