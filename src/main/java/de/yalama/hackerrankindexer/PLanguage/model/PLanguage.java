package de.yalama.hackerrankindexer.PLanguage.model;

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
public class PLanguage extends BaseEntity {

    private String language;

    @OneToMany(mappedBy = "language")
    private Set<Submission> submissions;
}
