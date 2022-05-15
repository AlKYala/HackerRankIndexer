package de.yalama.hackerrankindexer.UserData.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.UsagePercentage.Model.UsagePercentage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Idea:
 * User does not have submissions in his JSON
 * Submissions dont have the user in JSON
 * This Class is used to send all stats of user
 *
 * Other endpoints that take all submissions by user still work
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserData extends BaseEntity {

    @OneToMany
    private List<Submission> submissionList;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    @JsonIgnore
    private User user;

    //unidirectional n:m
    @ManyToMany
    @JsonIgnore
    private Set<PLanguage> usedPLanguages;

    @OneToOne
    private GeneralPercentage generalPercentage;

    @OneToMany
    private Set<UsagePercentage> usagePercentages;

    @OneToMany
    private Set<PassPercentage> passPercentages;
}