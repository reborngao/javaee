package com.visabao.machine.util;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *  基础加密组件 
 * @author Administrator
 *
 */
public  abstract class Coder {
	
	
	 /**
     * RSA最大加密明文大小
     */
	protected static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    protected static final int MAX_DECRYPT_BLOCK = 128;
	
	 public static final String KEY_SHA = "SHA";  
	 public static final String KEY_MD5 = "MD5";  
	  /** 
	     * MAC算法可选以下多种算法 
	     *  
	     * <pre> 
	     * HmacMD5  
	     * HmacSHA1  
	     * HmacSHA256  
	     * HmacSHA384  
	     * HmacSHA512 
	     * </pre> 
	     */  
	    public static final String KEY_MAC = "HmacMD5";  
	    
	    /** 
	     * BASE64解密 
	     *  
	     * @param key 
	     * @return 
	     * @throws Exception 
	     */  
	    public static byte[] decryptBASE64(String key) throws Exception {  
	    	
	    	
	        return /*(new bas()).decodeBuffer(key)*/  Base64.decode(key);
	    }  
	  
	    /** 
	     * BASE64加密 
	     *  
	     * @param key 
	     * @return 
	     * @throws Exception 
	     */  
	    public static String encryptBASE64(byte[] key) throws Exception {  
	        return /*(new BASE64Encoder()).encodeBuffer(key)*/  Base64.encode(key);  
	    } 
	    
		/***
		 * 分段加密
		 * @param data
		 * @param cipher
		 * @param length   
		 * @return
		 * @throws Exception
		 */
		public static byte[]  doFinalData(byte[] data,Cipher cipher,int length) throws Exception{
			  int inputLen = data.length;
			 ByteArrayOutputStream out = new ByteArrayOutputStream();
		        int offSet = 0;
		        byte[] cache;
		        int i = 0;
		        // 对数据分段解密
		        while (inputLen - offSet > 0) {
		            if (inputLen - offSet > length) {
		                cache = cipher.doFinal(data, offSet, length);
		            } else {
		                cache = cipher.doFinal(data, offSet, inputLen - offSet);
		            }
		            out.write(cache, 0, cache.length);
		            i++;
		            offSet = i * length;
		        }
		        byte[] decryptedData = out.toByteArray();
		        out.close();
		        return decryptedData;
		}
	  
	    /** 
	     * MD5加密 
	     *  
	     * @param data 
	     * @return 
	     * @throws Exception 
	     */  
	    public static byte[] encryptMD5(byte[] data) throws Exception {  
	  
	        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);  
	        md5.update(data);  
	  
	        return md5.digest();  
	  
	    }  
	  
	    /** 
	     * SHA加密 
	     *  
	     * @param data 
	     * @return 
	     * @throws Exception 
	     */  
	    public static byte[] encryptSHA(byte[] data) throws Exception {  
	  
	        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
	        sha.update(data);  
	  
	        return sha.digest();  
	  
	    }  
	  
	    /** 
	     * 初始化HMAC密钥 
	     *  
	     * @return 
	     * @throws Exception 
	     */  
	    public static String initMacKey() throws Exception {  
	        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);  
	  
	        SecretKey secretKey = keyGenerator.generateKey();  
	        return encryptBASE64(secretKey.getEncoded());  
	    }  
	  
	    /** 
	     * HMAC加密 
	     *  
	     * @param data 
	     * @param key 
	     * @return 
	     * @throws Exception 
	     */  
	    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {  
	  
	        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);  
	        Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
	        mac.init(secretKey);  
	  
	        return mac.doFinal(data);  
	  
	    }  
}
