package de.yalama.hackerrankindexer.Analytics.SupportModels;

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
public class UsageStatistics extends AbstractStatisticModel{
    private List<PLanguage> pLanguages;
    private List<Integer> numberSubmissions;

    public UsageStatistics() {
        this.pLanguages = new ArrayList<PLanguage>();
        this.numberSubmissions = new ArrayList<Integer>();
    }

    public int size() {
        return this.pLanguages.size();
    }

    @Override
    public void clear() {
        this.pLanguages.clear();
        this.numberSubmissions.clear();
    }
}
