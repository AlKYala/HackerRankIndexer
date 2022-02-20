package de.yalama.hackerrankindexer.Submission.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * A helper class to help filter requests - except namefiltering - which is done in frontend
 */
@Getter
@Setter
public class FilterRequest {

    private int mode;
    private Set<Integer> languageIDs;

}
