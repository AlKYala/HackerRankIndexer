package de.yalama.hackerrankindexer.Analytics.SupportModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
@RequiredArgsConstructor
public class PassPercentageChartEntry {

    public PassPercentageChartEntry(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private Integer value;
}
