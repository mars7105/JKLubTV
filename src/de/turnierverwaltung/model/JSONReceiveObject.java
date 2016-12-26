package de.turnierverwaltung.model;

public class JSONReceiveObject {
	private String md5sum;
	private String statusCode;
	private String version;
	private String phpModul;

	public JSONReceiveObject() {

	}

	public String getMd5sum() {
		return md5sum;
	}

	public void setMd5sum(String md5sum) {
		this.md5sum = md5sum;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getVersion() {
		if (version == null) {
			return "";
		} else {
			return version;
		}
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Boolean isStatusOk() {
		if (statusCode == null) {
			return false;
		}
		if (statusCode.equals("Ok")) {
			return true;
		} else {
			return false;
		}

	}

	public String getPhpModul() {
		return phpModul;
	}

	public void setPhpModul(String phpModul) {
		this.phpModul = phpModul;
	}

}