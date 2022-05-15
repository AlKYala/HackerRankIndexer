package de.yalama.hackerrankindexer.Analytics.service;

import de.yalama.hackerrankindexer.GeneralPercentage.Model.GeneralPercentage;
import de.yalama.hackerrankindexer.PassPercentage.Model.PassPercentage;
import de.yalama.hackerrankindexer.UserData.Model.UserData;
import de.yalama.hackerrankindexer.User.Model.User;
import de.yalama.hackerrankindexer.shared.models.UsageData;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

public abstract class AnalyticsService {

    /**
     * Checks if a user has existing user data.
     * @param user The user instance to check for
     * @return If a user has existing user data
     */
    public abstract boolean checkSubmissionsExist(User user);

    public abstract Long getNumberOfUsers();

    public abstract Long getNumberOfSubmissions();
}
