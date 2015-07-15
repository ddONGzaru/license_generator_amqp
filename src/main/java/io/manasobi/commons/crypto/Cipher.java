package io.manasobi.commons.crypto;

/**
 * DESC : 
 * 
 * @Company ePapyrus, Inc.
 * @author 
 * @Date 2013. 11. 24. 오후 2:27:10
 */
public interface Cipher {
	
	byte[] getKey();
	
	byte[] generateKey();
	
	byte[] encrypt(byte[] key, byte[] data);
	
	String encrypt(byte[] key, String data);
	
	String encrypt(byte[] key, String data, String charset);
	
	byte[] decrypt(byte[] key, byte[] data);
	
	String decrypt(byte[] key, String data);
	
	String decrypt(byte[] key, String data, String charset);

}
