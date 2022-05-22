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
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
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

    @OneToMany(mappedBy = "userData") //have to add how column where parent (this) is referenced to prevent creation of relation table
    private List<Submission> submissionList = new ArrayList<Submission>();

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JsonIgnore
    private User user;

    //unidirectional n:m
    @ManyToMany
    private Set<PLanguage> usedPLanguages = new HashSet<PLanguage>();

    @OneToOne(mappedBy = "userData") //children have column that shows at parent - value is the name of column for parent
    private GeneralPercentage generalPercentage;

    @OneToMany(mappedBy = "userData")
    private Set<UsagePercentage> usagePercentages = new HashSet<UsagePercentage>();

    @OneToMany(mappedBy = "userData")
    private Set<PassPercentage> passPercentages = new HashSet<PassPercentage>();
}
