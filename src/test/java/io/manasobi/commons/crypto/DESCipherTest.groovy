package io.manasobi.commons.crypto

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimplePBEConfig
import org.springframework.test.context.ContextConfiguration

import io.manasobi.utils.RandomUtils;
import spock.lang.Shared
import spock.lang.Specification

class DESCipherTest extends Specification {

	@Shared DESCipher cipher
	@Shared StandardPBEStringEncryptor secretKeyCryptoService
	@Shared SimplePBEConfig pbeConf
	
	private def setupSpec() {
		
		SimplePBEConfig pbeConf = new SimplePBEConfig();
		
		pbeConf.setAlgorithm("PBEWithMD5AndDES");
		pbeConf.setPassword("dlvkvlfntm");
		
		StandardPBEStringEncryptor secretKeyCryptoService = new StandardPBEStringEncryptor();
		secretKeyCryptoService.setConfig(pbeConf)
		
		cipher = new DESCipher()
		cipher.secretKeyCryptoService = secretKeyCryptoService
	}
	
	def "getKey()"() {
		
		when:
			byte[] bytes = cipher.getKey()
		then:
			bytes instanceof byte[]
			notThrown()		
	}
	
	def "generateKey()"() {
		
		when:
			byte[] bytes = cipher.generateKey()
		then:
			bytes instanceof byte[]
			notThrown()
	}
	
	def "encrypt() :: args [key:byte[], data:byte[]]"() {
		
		setup:
			byte[] key = cipher.getKey()
			byte[] data = 'DESCipher Test'.bytes
			byte[] bytes
		
		when:
			bytes = cipher.encrypt(key, data)
		then:
			bytes instanceof byte[]
			notThrown()
			
		when: 'key가 null'
			bytes = cipher.encrypt(null, data)
		then:
			bytes == null
		
		when: '유효하지 않은 길이의 key'
			def invalidKey = RandomUtils.getString(10).bytes
			bytes = cipher.encrypt(invalidKey, data)
		then:
			bytes == null
			
		when: 'data가 null'
			bytes = cipher.encrypt(key, null)
		then:
			thrown(GroovyRuntimeException)
	}
	
	def "encrypt() :: args [key:byte[], data:String]"() {
		
		setup:
			byte[] key = cipher.getKey()
			String data = 'DESCipher Test'
	
		when:
			byte[] bytes = cipher.encrypt(key, data)
		then:
			bytes instanceof byte[]
			notThrown()
	}
	
	def "encrypt() :: args [key:byte[], data:String, charset:String]"() {
		
		setup:
			byte[] key = cipher.getKey()
			String data = 'DESCipher Test'
			
		when:
			byte[] bytes = cipher.encrypt(key, data, 'UTF-8')
		then:
			bytes instanceof byte[]
			notThrown()
			
		when: 'key가 null'
			bytes = cipher.encrypt(null, data, 'UTF-8')
		then:
			bytes == null
		
		when: '유효하지 않은 길이의 key'
			def invalidKey = RandomUtils.getString(10).bytes
			bytes = cipher.encrypt(invalidKey, data, 'UTF-8')
		then:
			bytes == null
			
		when: 'data가 null'
			bytes = cipher.encrypt(key, null, 'UTF-8')
		then:
			bytes == null
			
		when: '지원하지 않는 charset'
			bytes = cipher.encrypt(key, data, 'no-charset')
		then:
			bytes == null
	}
	
	def "decrypt() :: args [key:byte[], data:byte[]]"() {
		
		setup:
			byte[] key = cipher.getKey()
			byte[] data = 'DESCipher Test'.bytes
			byte[] bytes
			
		when:
			bytes = cipher.decrypt(key, cipher.encrypt(key, data))
		then:
			bytes instanceof byte[]
			notThrown()
							
		when: 'key가 null'
			bytes = cipher.decrypt(null, data)
		then:
			bytes == null
									
		when: '유효하지 않은 길이의 key'
			def invalidKey = RandomUtils.getString(10).bytes
			bytes = cipher.decrypt(invalidKey, data)
		then:
			bytes == null
									
		when: 'data가 null'
			bytes = cipher.decrypt(key, null)
		then:
			thrown(GroovyRuntimeException)
	}
	
	def "decrypt() :: args [key:byte[], data:String]"() {
		
		setup:
			byte[] key = cipher.getKey()
			String data = 'DESCipher Test'
		when:
			byte[] bytes = cipher.decrypt(key, data)
		then:
			bytes == null
	}
	
	def "decrypt() :: args [key:byte[], data:String, charset:String]"() {
		
		setup:
			byte[] key = cipher.getKey()
			String data = 'DESCipher Test'
			
		when:
			byte[] bytes = cipher.decrypt(key, data, 'UTF-8')
		then:
			bytes == null
			notThrown()
		
		when: 'key가 null'
			bytes = cipher.decrypt(null, data, 'UTF-8')
		then:
			bytes == null
			notThrown()
			
		when: '유효하지 않은 길이의 key'
			def invalidKey = RandomUtils.getString(10).bytes
			bytes = cipher.decrypt(invalidKey, data)
		then:
			bytes == null
			
		when: 'data가 null'
			bytes = cipher.decrypt(key, null, 'UTF-8')
		then:
			bytes == null
			
		when: '지원하지 않는 charset'
			bytes = cipher.decrypt(key, data, 'no-charset')
		then:
			bytes == null		

		when: 'charset이 null'
			bytes = cipher.decrypt(key, data, null)
		then:
			bytes == null		
	}
	
	def "encrypt() && decrypt()"() {
		
		setup:
			byte[] key = cipher.getKey()
			String originText = '이파피루스 spock 테스트'
		
		when:
			String encryptedText = cipher.encrypt(key, originText)
			String result = cipher.decrypt(key, encryptedText)
		then:
			encryptedText.size() == 64
			result == originText			
	}
	
}
