package de.yalama.hackerrankindexer.HackerrankJSON.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is used for mapping all the information in the HackerrankJSON
 */
@Getter
@Setter
@NoArgsConstructor
public class HackerrankJSON {
    private String username;
    private String email;
    private SubmissionJSON[] submissions;
}
