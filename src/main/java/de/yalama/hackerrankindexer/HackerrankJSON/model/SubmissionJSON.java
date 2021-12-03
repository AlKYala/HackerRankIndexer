package de.yalama.hackerrankindexer.HackerrankJSON.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmissionJSON {
    public String challenge;
    public String code;
    public String contest;
    public String language;
    public double score;
    public String user;
}
