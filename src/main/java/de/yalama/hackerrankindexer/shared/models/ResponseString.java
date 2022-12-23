package de.yalama.hackerrankindexer.shared.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * The purpose of this class is to send wrapped strings in json
 */
@Getter
@Setter
public class ResponseString {
    private String body;

    public ResponseString() {}

    public ResponseString(String body) {
        this.body = body;
    }
}
