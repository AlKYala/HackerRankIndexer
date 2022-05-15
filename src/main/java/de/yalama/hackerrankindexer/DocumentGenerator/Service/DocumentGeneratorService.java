package de.yalama.hackerrankindexer.DocumentGenerator.Service;

import de.yalama.hackerrankindexer.DocumentGenerator.Model.DownloadFile;
import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;

import java.util.Collection;
import java.util.List;

public abstract class DocumentGeneratorService {

    /**
     * A method to take a submissions code and convert it to bytes, Needed for base 64 conversion
     * @param submission The submission instance
     * @return A byte Array of the submission instance
     */
    public abstract byte[] submissionCodeToBytes(Submission submission);

    /**
     * A method that takes a collection of sumbmissions and generates a list of
     * DownloadFile instances of it.
     * This can be used in an endpoint but isnt recommended. THis method is a must
     * in this service but the controller should take a collection of numbers instead
     * of whole instances
     * @param submissions A collection of Submission instances
     * @return A list of DownloadFile instances from the passed submissions
     */
    public abstract List<DownloadFile> downloadSubmissionCollection(Collection<Submission> submissions);

    /**
     * This is DocumentGeneratorService::downloadSubmissionCollection(Collection<Submission> submissions)
     * but it takes a collection of Long instead of submission instances to reduce load on the network
     * @param submissionIDs The ids of the submissions
     * @return a list of DownloadFile instances
     */
    public abstract List<DownloadFile> downloadSubmissionsFromCollection(Collection<Long> submissionIDs);

    /**
     * A method that generates a DownloadFile instance from the informaation of a submission instance
     * @param submission The submission instance
     * @return A DownloadFile insntance from a submission instance
     */
    public abstract DownloadFile getDownloadFileInstanceFromSubmission(Submission submission);

    /**
     * A method that generates a base64 from a submission
     * Basically what happens is:
     *
     * @param submission
     * @return
     */
    public abstract String getSubmissionAsBase64(Submission submission);
}
