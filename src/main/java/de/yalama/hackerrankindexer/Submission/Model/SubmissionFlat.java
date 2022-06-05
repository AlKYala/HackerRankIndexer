package de.yalama.hackerrankindexer.Submission.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import de.yalama.hackerrankindexer.Challenge.Model.Challenge;
import de.yalama.hackerrankindexer.Contest.Model.Contest;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * This class exists so it is placed in UserData::submissionList
 * When sending UserDataInstances the payload size is reduced
 * Submission is created everytime an instance of this class is created
 */

@Entity
@Getter
@Setter
public class SubmissionFlat extends BaseEntity {

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    @JsonIgnore
    private UserData userData;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    private PLanguage language;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    private Challenge challenge;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    private Contest contest;

    private double score;
}
