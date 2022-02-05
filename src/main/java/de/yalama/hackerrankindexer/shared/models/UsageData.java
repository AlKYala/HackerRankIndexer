package de.yalama.hackerrankindexer.shared.models;


import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsageData {

    private PLanguage   pLanguage;
    private Long        totalSubmissions;
    private Double        percentage;

    public UsageData() {}

    public UsageData(PLanguage pLanguage, Long totalSubmissions, Double percentage) {
        this.setPLanguage(pLanguage);
        this.setTotalSubmissions(totalSubmissions);
        this.setPercentage(percentage);
    }

}
