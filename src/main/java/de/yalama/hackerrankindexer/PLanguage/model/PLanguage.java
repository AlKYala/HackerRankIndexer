package de.yalama.hackerrankindexer.PLanguage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.shared.models.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class PLanguage extends BaseEntity {

    @NotNull
    private String language;

    private String color;

    private String displayName;

    @OneToMany(mappedBy = "language")
    @JsonIgnore
    private Set<SubmissionFlat> submissions;

    private String fileExtension;

    public String toString() {
        return String.format("Language: %s, numSubmissions: %d", this.language, submissions.size());
    }
}
