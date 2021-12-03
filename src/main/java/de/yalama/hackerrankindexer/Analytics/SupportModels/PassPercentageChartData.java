package de.yalama.hackerrankindexer.Analytics.SupportModels;

import de.yalama.hackerrankindexer.shared.models.PassData;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PassPercentageChartData {
    private String name;
    private List<ChartEntry> series;

    private PassPercentageChartData() {
        this.series = new ArrayList<ChartEntry>();
    }

    public static PassPercentageChartData generateFromPassData(PassData passData) {
        PassPercentageChartData temp = new PassPercentageChartData();
        temp.setName(passData.getLanguageName());
        ChartEntry percentageChartEntryFailed = new ChartEntry("failed", passData.getFailed());
        ChartEntry percentageChartEntryPassed = new ChartEntry("passed", passData.getPassed());

        temp.getSeries().add(percentageChartEntryPassed);
        temp.getSeries().add(percentageChartEntryFailed);

        return temp;
    }
}
