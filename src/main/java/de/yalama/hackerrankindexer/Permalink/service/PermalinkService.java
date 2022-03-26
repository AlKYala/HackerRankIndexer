package de.yalama.hackerrankindexer.Permalink.service;

import de.yalama.hackerrankindexer.Permalink.Model.UserData;
import de.yalama.hackerrankindexer.User.Model.User;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class PermalinkService {

    public abstract String getPermalinkForUser(User user) throws NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
            InvalidKeySpecException, BadPaddingException, InvalidKeyException, IOException;
}
