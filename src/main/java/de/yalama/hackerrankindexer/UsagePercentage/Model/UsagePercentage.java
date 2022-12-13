package de.yalama.hackerrankindexer.UsagePercentage.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class UsagePercentage extends BaseEntity {

    //unidirectional
    @ManyToOne
    private PLanguage pLanguage;


    //TODO noch ist es moeglich, z.B. zu einem UserDataDatensatz die sprache mehrmals zu haben
    @ManyToOne
    @JsonIgnore
    @JoinColumn //Give child @JoinColumn to prevent making relation tables
    private UserData userData;

    private Double percentage;

    private Long total;

    public void setPercentage(Integer total, Integer freq) {
        Double percentage = (double) freq / (double) total;
        this.setPercentage(percentage);
    }
}

/**
 * NOTE: UsageData.java is used for transferring json objects to frontend because
 * they are easier to work with
 */