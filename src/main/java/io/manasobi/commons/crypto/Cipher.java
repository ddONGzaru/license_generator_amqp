package io.manasobi.commons.crypto;


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
