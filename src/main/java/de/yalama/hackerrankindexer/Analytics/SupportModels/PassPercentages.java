package de.yalama.hackerrankindexer.Analytics.SupportModels;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PassPercentages extends AbstractStatisticModel {
    private List<PLanguage> pLanguages;
    private List<Double> percentages;

    public PassPercentages() {
        this.percentages = new ArrayList<Double>();
        this.pLanguages = new ArrayList<PLanguage>();
    }

    public int size() {
        return this.pLanguages.size();
    }

    @Override
    public void clear() {
        this.pLanguages.clear();
        this.percentages.clear();
    }

    public String toString() {
        return String.format("%s\n%s", this.percentages.toString(), this.pLanguages.toString());
    }
}
