package de.yalama.hackerrankindexer.Analytics.SupportModels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Used for ngx-charts by swimlane
 */
@Getter
@Setter
@RequiredArgsConstructor
public class ChartEntry {

    public ChartEntry(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private Integer value;
}
