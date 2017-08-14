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
    	String str = "";
    	for(int i = 0; i < bytes.length; i++){
    		String str2 = Integer.toHexString(Byte.toUnsignedInt(bytes[i]));
    		if(str2.length() == 1) {
    			str2 = "0".concat(str2);
    		}
    		str = str.concat(str2);
    	}
    	return str;
    }
    public static byte[] fromStr(String str){
    	char[] chars = str.toCharArray();
    	byte[] bytes = new byte[chars.length / 2];
    	for(int i = 0; i < chars.length; i += 2) {
    		bytes[i / 2] = (byte)Integer.valueOf(new String(new char[] {chars[i], chars[i+1]}), 16).intValue();
    	}
    	return bytes;
    }

}
