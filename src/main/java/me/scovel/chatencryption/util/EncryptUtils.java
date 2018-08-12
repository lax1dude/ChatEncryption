package me.scovel.chatencryption.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

public class EncryptUtils {

	public static String encryptAES(String text, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Arrays.copyOf(DigestUtils.sha1(key), 16), "AES"));
        byte[] byteCipherText = aesCipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(byteCipherText);
	}
	
	public static String decryptAES(String text, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Arrays.copyOf(DigestUtils.sha1(key), 16), "AES"));
        byte[] bytePlainText = aesCipher.doFinal(Base64.decodeBase64(text));
        return new String(bytePlainText);
	}

}
