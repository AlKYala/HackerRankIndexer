package de.yalama.hackerrankindexer.DocumentGenerator.Service;

import de.yalama.hackerrankindexer.DocumentGenerator.Model.DownloadFile;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.SubmissionFlat.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.SubmissionFlat.Repository.SubmissionFlatRepository;
import de.yalama.hackerrankindexer.Submission.Service.SubmissionService;
import de.yalama.hackerrankindexer.SubmissionFlat.Service.SubmissionFlatService;
import de.yalama.hackerrankindexer.shared.Util.Base64Util;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DocumentGeneratorServiceImpl extends DocumentGeneratorService {

    private SubmissionService submissionService;
    private SubmissionFlatService submissionFlatService;

    public DocumentGeneratorServiceImpl(SubmissionService submissionService,
                                        SubmissionFlatService submissionFlatService) {
        this.submissionService = submissionService;
        this.submissionFlatService = submissionFlatService;
    }

    @Override
    public byte[] submissionCodeToBytes(Submission submission) {
        return String.format("%s\n\n%s", generateInfo(submission), submission.getCode()).getBytes();
    }

    private String generateInfo(Submission submission) {
        SubmissionFlat submissionFlat = this.submissionFlatService.findById(submission.getId());
        return String.format("/**\nPowered by HackerrankIndexer by Ali Yalama 2021-2022\nhttps://github.com/AlKYala/HackerRankIndexer\nFile created: %s\nChallenge name: %s\nAuthor: %s\n*/\n",
               getCurrentDateAsString(), submissionFlat.getChallenge().getChallengeName(), submissionFlat.getUserData().getUser().getEmail());
    }

    private String getCurrentDateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime creationDate = LocalDateTime.now();
        return formatter.format(creationDate);
    }

    @Override
    public List<DownloadFile> downloadSubmissionCollection(Collection<SubmissionFlat> submissions) {
        List<DownloadFile> submissionsAsDownloadFiles = new ArrayList<DownloadFile>();
        submissions
                .forEach(submission -> submissionsAsDownloadFiles.add(this.getDownloadFileInstanceFromSubmission(submission)));
        return submissionsAsDownloadFiles;
    }

    @Override
    public List<DownloadFile> downloadSubmissionsFromCollection(Collection<Long> submissionIDs) {
        Collection <SubmissionFlat> submissions = this.submissionFlatService.getSubmissionsFromIDs(submissionIDs);
        return this.downloadSubmissionCollection(submissions);
    }

    @Override
    public DownloadFile getDownloadFileInstanceFromSubmission(SubmissionFlat submissionFlat) {
        Submission submission = this.submissionService.findById(submissionFlat.getId());
        DownloadFile downloadFile = new DownloadFile();
        String fileName = String.format("%s.%s", submissionFlat.getChallenge().getChallengeName(), submissionFlat.getLanguage().getFileExtension());
        String base64   = this.getSubmissionAsBase64(submission);
        downloadFile.setFileName(fileName);
        downloadFile.setChallengeName(submissionFlat.getChallenge().getChallengeName());
        downloadFile.setBase64(base64);
        return downloadFile;
    }

    @Override
    public String getSubmissionAsBase64(Submission submission) {
        byte[] submissionBytes = this.submissionCodeToBytes(submission);
        return Base64Util.byteArrayToBase64(submissionBytes);
    }
}
