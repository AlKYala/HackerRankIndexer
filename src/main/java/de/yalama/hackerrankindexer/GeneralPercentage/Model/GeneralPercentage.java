package de.yalama.hackerrankindexer.GeneralPercentage.Model;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sun.istack.NotNull;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@Entity
public class GeneralPercentage extends BaseEntity {

    @OneToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    private User user;

    private Double percentageChallengesSolved;

    private Double percentageSubmissionsPassed;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @NotNull
    @JoinColumn
    private PLanguage favouriteLanguage;
}
