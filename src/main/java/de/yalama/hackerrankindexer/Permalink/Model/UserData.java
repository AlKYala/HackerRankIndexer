package de.yalama.hackerrankindexer.Permalink.Model;

import de.yalama.hackerrankindexer.Submission.Model.Submission;
import de.yalama.hackerrankindexer.User.Model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Idea:
 * User does not have submissions in his JSON
 * Submissions dont have the user in JSON
 * This Class is used to send all stats of user
 *
 * Other endpoints that take all submissions by user still work
 */
@Getter
@Setter
public class UserData {

    private List<Submission> submissionList;
    private User user;

    public UserData(User user) {
        this.user = user;
        this.submissionList = user.getSubmittedEntries();
    }
}
