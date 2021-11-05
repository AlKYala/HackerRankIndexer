package de.yalama.hackerrankindexer.shared.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Replaces Pass percentages, used for single languages
 */
@Getter
@Setter
public class PassData {
    private Long languageId;
    private String languageName;
    private Integer passed;
    private Integer failed;
}
