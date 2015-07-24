package io.manasobi.license;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.jongo.MongoCollection;
import org.jongo.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.WriteResult;

import io.manasobi.commons.crypto.Cipher;
import io.manasobi.commons.logger.CommonLogger;
import io.manasobi.utils.DateUtils;

public interface LicenseJobHandler {

	void process(License license);
	
	@Component
	public class LicenseJobHandlerImpl implements LicenseJobHandler {

		@Resource(name = "DESCipher")
		private Cipher cipher;
		
		@Autowired
		private MongoCollection licenseDetailsRepo;
		
		private static final String EXPIRATION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		
		@Override
		public void process(License license) {
			
			String licenseKey = generateKey(license);
			
			updateLicenseDetails(license.getId(), licenseKey);
		}
		
		private String generateKey(License license) {
			
			String expirationDate = StringUtils.equals(license.getExpirationDate(), "-") ? "1900-12-12 12:12:12" : license.getExpirationDate();
			
			String type = null;
			
			if (StringUtils.equals(license.getType(), "Production")) {
				type = "01";
			} else if (StringUtils.equals(license.getType(), "Trial")) {
				type = "02";
			} else if (StringUtils.equals(license.getType(), "Developer")) {
				type = "03";
			}
			
			StringBuilder sb = new StringBuilder();

			sb.append(String.format("%012x", System.currentTimeMillis()));
			sb.append("-");
			sb.append(type);
			sb.append("-");
			sb.append(String.format("%012x", DateUtils.convertStringToTimeMills(expirationDate, EXPIRATION_DATE_FORMAT)));
			sb.append("-");			
			sb.append(stringToHex(license.getHostName()));
			
			return cipher.encrypt(cipher.getKey(), sb.toString(), "UTF-8");
		}
		
		private String stringToHex(String str) {

			String inStr = str;

			char[] inChar = inStr.toCharArray();
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < inChar.length; i++) {
				String hex = Integer.toHexString((int) inChar[i]);
				if (hex.length() == 2) {
					hex = "00" + hex;
				}
				sb.append(hex);
			}
			return sb.toString();
		}
		
		private void updateLicenseDetails(String id, String licenseKey) {
			
			Update update = licenseDetailsRepo.update("{genKey: #}", id);
			
			WriteResult result = update.with("{$set: {key: #, generated: true}}", licenseKey);
			
			CommonLogger.info(String.valueOf(result.getN()), this.getClass());
		}
		
	}
}
