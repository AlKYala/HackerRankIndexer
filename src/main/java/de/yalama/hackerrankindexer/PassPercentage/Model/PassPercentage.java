package de.yalama.hackerrankindexer.PassPercentage.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private User user;

    private long total;

    private long passed;

    private boolean created;

    public String toString() {
        return String.format("Language: %s, User: %s,  Total: %d, Passed: %d", pLanguage.toString(), user.getEmail(), total, passed);
    }

    public boolean equals(PassPercentage other) {
        return other.getPLanguage().getId() == this.getPLanguage().getId()
                && other.getUser().getId() == this.getUser().getId();
    }
}
