package de.yalama.hackerrankindexer.DocumentGenerator.Service;

import de.yalama.hackerrankindexer.DocumentGenerator.Model.DownloadFile;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;

import java.util.Collection;
import java.util.List;

public abstract class DocumentGeneratorService {

    public abstract byte[] generateDocumentBytesFromSubmission(Submission submission);

    public abstract List<DownloadFile> downloadAllSubmissionsFromUser(User user);

    public abstract List<DownloadFile> downloadSubmissionCollection(Collection<Submission> submissions);

    public abstract String getDocumentBase64String(byte[] byteArr);

    public abstract DownloadFile getDownloadFileInstanceFromSubmission(Submission submission);

    public abstract String getSubmissionAsBase64(Submission submission);
}
