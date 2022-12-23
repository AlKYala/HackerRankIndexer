package de.yalama.hackerrankindexer.shared.Logger.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogItem {

    private Date date;

    private String message;

    public LogItem(String message) {
        this.date = new Date();
        this.message = (message != null) ? message : "No message available";
    }

    public String toString() {
        return String.format("%s - %s", this.date.toString(), this.message);
    }
}
