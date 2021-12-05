package de.yalama.hackerrankindexer.User.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends BaseEntity {

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Submission> submittedEntries;

    //unidirectional n:m
    @ManyToMany
    @JsonIgnore
    private Set<PLanguage> usedPLanguages;

    @OneToOne
    @JsonIgnore
    private GeneralPercentage generalPercentage;

    @Column(unique = true)
    @NotNull
    private String username;

    @NotNull
    private String passwordHashed;


}
