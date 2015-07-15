package io.manasobi.commons.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CryptoConfig {

	@Bean(name = "secretKeyCryptoService")
	public StandardPBEStringEncryptor standardPBEStringEncryptor() {
		
		SimplePBEConfig pbeConf = new SimplePBEConfig();
		
		pbeConf.setAlgorithm("PBEWithMD5AndDES");
		pbeConf.setPassword("dlvkvlfntm");
		
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		
		encryptor.setConfig(pbeConf);
		
		return encryptor;
	}
}
