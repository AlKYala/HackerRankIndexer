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
    private PLanguage mostUsed;
    private boolean created;

    public UsageStatistics() {
        this.pLanguages = new ArrayList<PLanguage>();
        this.numberSubmissions = new ArrayList<Integer>();
        this.created = false;
    }

    public int size() {
        return this.pLanguages.size();
    }

    @Override
    public void clear() {
        this.pLanguages.clear();
        this.numberSubmissions.clear();
    }

    public PLanguage getMostUsed() {
        return (this.mostUsed == null) ? this.findMostUsedLanguage() : this.mostUsed;
    }

    private PLanguage findMostUsedLanguage() {
        int mostPopularIndex = -1;
        int highest = Integer.MIN_VALUE;

        for(int i = 0; i < this.numberSubmissions.size(); i++) {
            if(highest < this.numberSubmissions.get(i)) {
                highest = this.numberSubmissions.get(i);
                mostPopularIndex = i;
            }
        }

        if(mostPopularIndex != -1) {
            this.mostUsed = this.pLanguages.get(mostPopularIndex);
        }
        return this.mostUsed;
    }
}
