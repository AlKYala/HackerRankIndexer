package de.yalama.hackerrankindexer.DocumentGenerator;

import de.yalama.hackerrankindexer.Submission.Model.Submission;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A Util Class to generate Documents. Returns a file instance
 */
public class DocumentGeneratorUtil {

    public static byte[] generateDocumentBytesFromSubmission(Submission submission) {
        return String.format("%s\n\n%s", generateInfo(submission), submission.getCode()).getBytes();
    }

    private static String generateInfo(Submission submission) {
        return String.format("/**\nPowered by HackerrankIndexer by Ali Yalama 2021\nhttps://github.com/AlKYala/HackerRankIndexer\nFile created: %s\nChallenge name: %s\nAuthor: %s\n*/\n",
                getCurrentDateAsString(), submission.getChallenge().getChallengeName(), submission.getWriter().getUsername());
    }

    private static String getCurrentDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime creationDate = LocalDateTime.now();
        return formatter.format(creationDate);
    }

}
