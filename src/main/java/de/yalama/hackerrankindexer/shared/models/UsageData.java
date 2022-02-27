package de.yalama.hackerrankindexer.shared.models;


import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageData {

    private PLanguage   pLanguage;
    private Long        totalSubmissions;
    private Long        passedSubmissions;

    //TODO: Replace percentage with passed submissions and close PassPercentageEndpoint

    public UsageData() {}

    public UsageData(PLanguage pLanguage, Long totalSubmissions, Long passedSubmissions) {
        this.setPLanguage(pLanguage);
        this.setTotalSubmissions(totalSubmissions);
        this.setPassedSubmissions(passedSubmissions);
    }

    public String toString() {
        return String.format("Lang: %s\nNumSubmissions: %d\nPercentage: %d", pLanguage.getLanguage(), totalSubmissions, passedSubmissions);
    }

}
