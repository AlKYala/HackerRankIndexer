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

    /**
     * 1 is all passed
     * 2 is all failed
     * 3 is most recent passed
     */
    private int mode;
    private Set<Integer> languageIDs;
}
