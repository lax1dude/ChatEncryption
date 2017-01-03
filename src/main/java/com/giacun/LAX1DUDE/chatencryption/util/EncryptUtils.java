package com.giacun.LAX1DUDE.chatencryption.util;

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

import org.apache.commons.lang3.ArrayUtils;

public class EncryptUtils {

	public static String encryptAES(String text, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Arrays.copyOf(sha1(key).getBytes(), 16), "AES"));
        byte[] byteCipherText = aesCipher.doFinal(text.getBytes());
        return new String(toStr(byteCipherText));
	}
	
	public static String decryptAES(String text, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Arrays.copyOf(sha1(key).getBytes(), 16), "AES"));
        byte[] bytePlainText = aesCipher.doFinal(fromStr(text));
        return new String(bytePlainText);
	}
	

    public static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }

    public static String toStr(byte[] bytes){
    	String str = Integer.toHexString(Byte.toUnsignedInt(bytes[0]));
    	for(int i = 1; i < bytes.length; i++){
    		str = str.concat("|").concat(Integer.toHexString(Byte.toUnsignedInt(bytes[i])));
    	}
    	return str;
    }
    public static byte[] fromStr(String str){
    	String[] str2 = str.split("\\|");
    	byte[] bytes = new byte[0];
    	for(int i = 0; i < str2.length; i++){
    		bytes = ArrayUtils.add(bytes, (byte)Integer.valueOf(str2[i], 16).intValue());
    	}
    	return bytes;
    }

}
