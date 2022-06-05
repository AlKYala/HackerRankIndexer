package de.yalama.hackerrankindexer.UserData.Service;

import de.yalama.hackerrankindexer.PLanguage.model.PLanguage;
import de.yalama.hackerrankindexer.Submission.Model.SubmissionFlat;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionFlatRepository;
import de.yalama.hackerrankindexer.Submission.Repository.SubmissionRepository;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.UserData.Model.UserDataFlat;
import de.yalama.hackerrankindexer.UserData.Repository.UserDataRepository;
import de.yalama.hackerrankindexer.shared.exceptions.HackerrankIndexerException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDataServiceImpl extends UserDataService {

    private UserDataRepository      userDataRepository;
    private SubmissionRepository    submissionRepository;
    private SubmissionFlatRepository submissionFlatRepository;

    public UserDataServiceImpl(UserDataRepository userDataRepository,
                               SubmissionRepository submissionRepository,
                               SubmissionFlatRepository submissionFlatRepository) {
        this.userDataRepository     = userDataRepository;
        this.submissionRepository   = submissionRepository;
        this.submissionFlatRepository = submissionFlatRepository;
    }

    @Override
    public UserData findById(Long id) throws HackerrankIndexerException {
        return this.userDataRepository.findById(id).get();
    }

    @Override
    public List<UserData> findAll() throws HackerrankIndexerException {
        return this.userDataRepository.findAll();
    }

    @Override
    public UserData save(UserData instance) throws HackerrankIndexerException {
        return this.userDataRepository.save(instance);
    }

    @Override
    public UserData update(Long id, UserData instance) throws HackerrankIndexerException {
        UserData toUpdate = this.findById(id);

        toUpdate.setGeneralPercentage(instance.getGeneralPercentage());
        toUpdate.setPassPercentages(instance.getPassPercentages());
        toUpdate.setUsagePercentages(instance.getUsagePercentages());

        toUpdate.setUsedPLanguages(instance.getUsedPLanguages());
        toUpdate.setSubmissionList(instance.getSubmissionList());

        return toUpdate;
    }

    @Override
    public Long deleteById(Long id) throws HackerrankIndexerException {
        this.userDataRepository.deleteById(id);
        return id;
    }

    @Override
    public List<UserData> findByUser(User user) {
        return this.userDataRepository.getByUser(user.getId());
    }

    @Override
    public Collection<SubmissionFlat> findSubmissionsOfUserOfPlanguage(UserData userData, PLanguage pLanguage) {
        return this.submissionFlatRepository.getSubmissionsByPlanguageIdAndUserDataId(pLanguage.getId(), userData.getId());
    }

    @Override
    public UserData findUserDataByToken(String token) {
        return this.userDataRepository.getByUserDataToken(token);
    }

    @Override
    public List<UserDataFlat> getUserDataFlat(User user) {
        List<UserDataFlat> userDataFlat = new ArrayList<UserDataFlat>();
        this.findByUser(user).forEach((userData -> {
            UserDataFlat temp = new UserDataFlat(userData.getDateCreated(), userData.getToken());
            userDataFlat.add(temp);
        }));
        return userDataFlat;
    }

    @Override
    public UserData generateQRCode(Long userDataId) {
        UserData userData = this.findById(userDataId);
        if(userData == null)
            return null;
        String token = String.format("%d%d", userData.hashCode(), userData.getDateCreated().hashCode());
        userData.setToken(token);
        return this.update(userDataId, userData);
    }
}
