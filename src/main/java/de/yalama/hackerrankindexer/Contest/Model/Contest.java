package de.yalama.hackerrankindexer.Contest.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class Contest extends BaseEntity {

    @OneToMany(mappedBy = "contest")
    @JsonIgnore
    private Set<SubmissionFlat> submissions;

    @NotNull
    private String name;
}
