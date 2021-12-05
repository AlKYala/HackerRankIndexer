package de.yalama.hackerrankindexer.Security;

import io.jsonwebtoken.SignatureAlgorithm;

public class SecurityConstants {
    public static final String SECRET_KEY = "DasKriegtKarimNieGeknackt";
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String SIGN_UP_ENDPOINT = "/authenticate";
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
}
