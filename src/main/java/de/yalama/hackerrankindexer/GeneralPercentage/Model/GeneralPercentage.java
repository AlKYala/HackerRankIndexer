package de.yalama.hackerrankindexer.GeneralPercentage.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Getter
@Setter
@Entity
public class GeneralPercentage extends BaseEntity {

    @OneToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    @JsonIgnore
    private UserData userData;

    private Integer percentageChallengesSolved;

    private Integer percentageSubmissionsPassed;

    private boolean calculated;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    private PLanguage favouriteLanguage;
}
