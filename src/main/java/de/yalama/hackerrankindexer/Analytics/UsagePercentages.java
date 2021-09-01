package de.yalama.hackerrankindexer.Analytics;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
/**
 * Maps in java and maps in typescript dont have good compatibility. This is a workaround
 */
public class UsagePercentages {
    private List<PLanguage> pLanguages;
    private List<Double> usagePercentages;

    public UsagePercentages() {
        this.pLanguages = new ArrayList<PLanguage>();
        this.usagePercentages = new ArrayList<Double>();
    }

    public int size() {
        return this.pLanguages.size();
    }
}
