package io.manasobi.license;

import java.io.Serializable;
import java.util.Date;

public class LicenseDetails implements Serializable {

	private static final long serialVersionUID = -6929387917025803018L;

	private String genKey;
	
	private String userName;
	
	private Date createdDate;
	
	private String createdDateStr;
	
	private License license;

	private boolean generated = false;
	
	private String key;

	public String getGenKey() {
		return genKey;
	}

	public void setGenKey(String genKey) {
		this.genKey = genKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public License getLicense() {
		return license;
	}

	public void setLicense(License license) {
		this.license = license;
	}

	public boolean isGenerated() {
		return generated;
	}

	public void setGenerated(boolean generated) {
		this.generated = generated;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}
	
	
}
