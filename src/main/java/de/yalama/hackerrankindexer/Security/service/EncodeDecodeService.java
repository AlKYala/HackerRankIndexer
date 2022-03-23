package de.yalama.hackerrankindexer.Security.service;

import de.yalama.hackerrankindexer.Security.SecurityConstants;
import de.yalama.hackerrankindexer.shared.HashingAlgorithms.HashingAlgorithm;
import de.yalama.hackerrankindexer.shared.Util.Base64Util;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

// https://www.baeldung.com/java-aes-encryption-decryption

@Service
public class EncodeDecodeService {

    public String encryptString(String value) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        String salt = SecurityConstants.SECRET_KEY.substring(0, 10);
        byte[] valueAsBytes = value.getBytes();
        IvParameterSpec initVector   = this.getInitVector();
        SecretKey sk    = this.getKeyWithSalt(SecurityConstants.SECRET_KEY, salt, 256);
        Cipher cipher   = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sk, initVector);
        byte[] cipherBytes = cipher.doFinal(value.getBytes());
        return Base64Util.byteArrayToBase64(cipherBytes);

    }

    public String decrypt(String encrypted) throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        String salt = SecurityConstants.SECRET_KEY.substring(0, 10);
        byte[] valueAsBytes = encrypted.getBytes();
        IvParameterSpec initVector   = this.getInitVector();
        SecretKey sk    = this.getKeyWithSalt(SecurityConstants.SECRET_KEY, salt, 256);
        Cipher cipher   = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sk, initVector);
        byte[] plainBytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(plainBytes);
    }

    /*private SecretKey generateKey(int bits) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(bits);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }*/

    private SecretKey getKeyWithSalt(String key, String salt, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory    factory         = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec             keySpec         = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, keyLength);
        System.out.printf("Key: %s\n", keySpec.hashCode());

        byte[]              factorySecret   = factory.generateSecret(keySpec).getEncoded();
        SecretKey           secretKey       = new SecretKeySpec(factorySecret, "AES");
        return secretKey;
    }

    private IvParameterSpec getInitVector() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }


    public String hashValue(Object obj, HashingAlgorithm algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest   = MessageDigest.getInstance(algorithm.getValue());
        byte[]          objByteArr      = this.castObjectToByteArray(obj);
        byte[]          digest          = messageDigest.digest(objByteArr);
        return DatatypeConverter.printHexBinary(digest);
    }

    public byte[] castObjectToByteArray(Object obj) throws IOException {
        if(obj instanceof String) {
            return ((String) obj).getBytes();
        }

        ByteArrayOutputStream   bos = new ByteArrayOutputStream();
        ObjectOutputStream      oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
        byte[] bytes = bos.toByteArray();
        oos.close();
        return bytes;
    }
}
