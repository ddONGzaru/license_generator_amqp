package io.manasobi.commons.crypto;

import javax.annotation.Resource;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Component;

import com.epapyrus.sdp.commons.logger.SdpLogger;
import com.epapyrus.sdp.commons.utils.ByteUtils;
import com.epapyrus.sdp.commons.utils.CryptoUtils;
import com.epapyrus.sdp.commons.utils.StringUtils;

import io.manasobi.commons.logger.CommonLogger;

/**
 * DESC : 
 * 
 * @Company ePapyrus, Inc.
 * @author taewook.jang
 * @Date 2013. 3. 2. 오후 4:39:12
 */
@Component("DESCipher")
public class DESCipher implements Cipher {

	/** 암호화에 사용되는 알고리즘 - 기본 TripleDES */
    private static final String ALGORITHM = "DESede";

    @Resource(name = "secretKeyCryptoService")
    private StandardPBEStringEncryptor secretKeyCryptoService;
    
    private static final String SECRET_KEY = "GHv1dr2lIOd5FJ3TMEX/Rt3jIALmZXlrcoETfOnT3BMTho99MZvcn07q/KouLQOi4BzA5JGKyyOULE1yx5Z3WA==";
   
    
	@Override
	public byte[] getKey() {
		
    	String secretKey = secretKeyCryptoService.decrypt(SECRET_KEY);
		
		return ByteUtils.toByte(secretKey);
	}

	@Override
	public byte[] generateKey() {
		
		byte[] rawKey = null;
        
        try {
            
        	KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            
        	SecretKey secretKey = keyGenerator.generateKey();
            
        	SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            
        	DESedeKeySpec desEdeSpec = (DESedeKeySpec) secretKeyFactory.getKeySpec(secretKey,
                            javax.crypto.spec.DESedeKeySpec.class);

            rawKey = desEdeSpec.getKey();
            
        } catch (Exception e) {
        	CommonLogger.error("DESCipher-generateKey ERROR: 키 생성에 실패하였습니다. [" + e.getMessage() + "]", CryptoUtils.class);
        	return null;
        }
        
        String rawKeyStr = new String(Hex.encodeHex(rawKey));
        
        return ByteUtils.toByte(rawKeyStr);
	}

	@Override
	public byte[] encrypt(byte[] key, byte[] data) {
		
		SecretKey secretKey = getSecretDESKeyFromHex(key);
        
        byte[] encryptedData = null;

        try {
        	
            javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);
            
            cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, secretKey);
            
            encryptedData = cipher.doFinal(data);
            
        } catch (Exception e) {
        	SdpLogger.error("DESCipher-encrypt ERROR: 암호화에 실패하였습니다. [" + e.getMessage() + "]", CryptoUtils.class);
        	return null;
        }
        
        return encryptedData;
	}

	@Override
	public String encrypt(byte[] key, String data) {
		return encrypt(key, data, null);
	}

	@Override
	public String encrypt(byte[] key, String data, String charset) {
		
		String encryptedString = null;
        
        try {
        	
        	byte[] encryptedData = null;
        	
        	if (StringUtils.isEmpty(charset)) {
        		encryptedData = encrypt(key, data.getBytes());
        	} else {
        		encryptedData = encrypt(key, data.getBytes(charset));
        	}
        	
        	encryptedString = new String(Hex.encodeHex(encryptedData));
            
        } catch (Exception e) {
        	SdpLogger.error("DESCipher-encrypt ERROR: 암호화에 실패하였습니다. [" + e.getMessage() + "]", CryptoUtils.class);
        	return null;
        }
        
        return encryptedString;
	}
	
	@Override
	public byte[] decrypt(byte[] key, byte[] data) {
		
		 SecretKey secretKey = getSecretDESKeyFromHex(key);
	        
	        byte[] decryptedData = null;

	        try {
	        	
	        	javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(ALGORITHM);
	            
	            cipher.init(javax.crypto.Cipher.DECRYPT_MODE, secretKey);
	            
	            decryptedData = cipher.doFinal(data);

	        } catch (Exception e) {
	        	SdpLogger.error("DESCipher-decrypt ERROR: 복호화에 실패하였습니다. [" + e.getMessage() + "]", CryptoUtils.class);
	        	return null;
	        }

	        return decryptedData;
	}

	@Override
	public String decrypt(byte[] key, String data) {
		return decrypt(key, data, null);
	}

	@Override
	public String decrypt(byte[] key, String data, String charset) {
		
		String decryptedString = null;
		
        try {
        	
            byte[] decodedHex = Hex.decodeHex(data.toCharArray());
            
            byte[] decryptedData = decrypt(key, decodedHex);
            
            if (StringUtils.isEmpty(charset)) {
            	decryptedString = new String(decryptedData);            	
            } else {
            	decryptedString = new String(decryptedData, charset);
            }

        } catch (Exception e) {
        	SdpLogger.error("DESCipher-decrypt ERROR: 복호화에 실패하였습니다. [" + e.getMessage() + "]", CryptoUtils.class);
        	return null;
        }
        
        return decryptedString;
	}
	
	/**
     * hex 문자열화된 키에서 SecretKey 객체를 생성한다.
     *
     * @param keyHex
     * 			  generateHexKey 메소드에 의해 생성된 Hex 문자열화 된 키		
     * @return 생성된 SecretKey 객체
     */
    private SecretKey getSecretDESKeyFromHex(byte[] keyHex) {

    	SecretKey key = null;

        try {
        	
            byte[] keyBytes = Hex.decodeHex(ByteUtils.toString(keyHex).toCharArray());
            
            key = new SecretKeySpec(keyBytes, ALGORITHM);
            
        } catch (Exception e) {
        	SdpLogger.error("DESCipher-getSecretDESKeyFromHex ERROR: Secret Key를 가져오는 것에 실패하였습니다. [" + e.getMessage() + "]", CryptoUtils.class);
        	return null;
        }
        
        return key;
    }

}
