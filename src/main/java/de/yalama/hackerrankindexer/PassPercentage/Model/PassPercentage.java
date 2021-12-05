package de.yalama.hackerrankindexer.PassPercentage.Model;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class PassPercentage extends BaseEntity {

    //unidirectional
    @ManyToOne
    private PLanguage pLanguage;

    @OneToOne
    private User user;

    private double percentage;

    private long total;

    private long passed;
}
