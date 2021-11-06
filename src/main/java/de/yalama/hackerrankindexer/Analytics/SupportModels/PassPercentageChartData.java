package de.yalama.hackerrankindexer.Analytics.SupportModels;

import de.yalama.hackerrankindexer.shared.models.PassData;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class PassPercentageChartData {
    private String name;
    private List<PassPercentageChartEntry> series;

    private PassPercentageChartData() {
        this.series = new ArrayList<PassPercentageChartEntry>();
    }

    public static PassPercentageChartData generateFromPassData(PassData passData) {
        PassPercentageChartData temp = new PassPercentageChartData();
        temp.setName(passData.getLanguageName());
        PassPercentageChartEntry percentageChartEntryFailed = new PassPercentageChartEntry("failed", passData.getFailed());
        PassPercentageChartEntry percentageChartEntryPassed = new PassPercentageChartEntry("passed", passData.getPassed());

        temp.getSeries().add(percentageChartEntryPassed);
        temp.getSeries().add(percentageChartEntryFailed);

        return temp;
    }
}
