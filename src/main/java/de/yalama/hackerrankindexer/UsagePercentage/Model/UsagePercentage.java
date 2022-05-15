package de.yalama.hackerrankindexer.UsagePercentage.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class UsagePercentage extends BaseEntity {

    //unidirectional
    @ManyToOne
    private PLanguage pLanguage;

    @OneToOne
    @JsonIgnore
    private UserData userData;

    private Integer percentage;

    private Long total;
}

/**
 * NOTE: UsageData.java is used for transferring json objects to frontend because
 * they are easier to work with
 */