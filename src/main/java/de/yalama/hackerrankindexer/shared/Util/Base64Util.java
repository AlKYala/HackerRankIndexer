package de.yalama.hackerrankindexer.shared.Util;

import java.util.Base64;

public class Base64Util {

    public static String stringToBase64(String s) {
        return Base64Util.byteArrayToBase64(s.getBytes());
    }

    public static String byteArrayToBase64(byte[] bytes) {
        byte[] base64Bytes = Base64.getEncoder().encode(bytes);
        return new String(base64Bytes);
    }
}
