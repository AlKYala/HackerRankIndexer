package de.yalama.hackerrankindexer.DocumentGenerator.Service;

import de.yalama.hackerrankindexer.DocumentGenerator.Model.DownloadFile;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.Util.Base64Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Service
public class DocumentGeneratorServiceImpl extends DocumentGeneratorService {

    private SubmissionService submissionService;

    public DocumentGeneratorServiceImpl(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Override
    public byte[] submissionCodeToBytes(Submission submission) {
        return String.format("%s\n\n%s", generateInfo(submission), submission.getCode()).getBytes();
    }

    private String generateInfo(Submission submission) {
        return String.format("/**\nPowered by HackerrankIndexer by Ali Yalama 2021-2022\nhttps://github.com/AlKYala/HackerRankIndexer\nFile created: %s\nChallenge name: %s\nAuthor: %s\n*/\n",
                getCurrentDateAsString(), submission.getChallenge().getChallengeName(), submission.getWriter().getEmail());
    }

    private String getCurrentDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime creationDate = LocalDateTime.now();
        return formatter.format(creationDate);
    }

    @Override
    public List<DownloadFile> downloadAllSubmissionsFromUser(User user) {
        return this.downloadSubmissionCollection(user.getSubmittedEntries());
    }

    @Override
    public List<DownloadFile> downloadSubmissionCollection(Collection<Submission> submissions) {
        List<DownloadFile> submissionsAsDownloadFiles = new ArrayList<DownloadFile>();
        submissions
                .forEach(submission -> submissionsAsDownloadFiles.add(this.getDownloadFileInstanceFromSubmission(submission)));
        return submissionsAsDownloadFiles;
    }

    @Override
    public List<DownloadFile> downloadSubmissionsFromCollection(Collection<Long> submissionIDs) {
        Collection <Submission> submissions = this.submissionService.getSubmissionsFromIDs(submissionIDs);
        return this.downloadSubmissionCollection(submissions);
    }

    @Override
    public DownloadFile getDownloadFileInstanceFromSubmission(Submission submission) {
        DownloadFile downloadFile = new DownloadFile();
        String fileName = String.format("%s.%s", submission.getChallenge().getChallengeName(), submission.getLanguage().getFileExtension());
        String base64   = this.getSubmissionAsBase64(submission);
        downloadFile.setFileName(fileName);
        downloadFile.setChallengeName(submission.getChallenge().getChallengeName());
        downloadFile.setBase64(base64);
        return downloadFile;
    }

    @Override
    public String getSubmissionAsBase64(Submission submission) {
        byte[] submissionBytes = this.submissionCodeToBytes(submission);
        return Base64Util.byteArrayToBase64(submissionBytes);
    }
}
