package de.turnierverwaltung.model;

public class JSONReceiveObject {
	private String md5sum;
	private String statusCode;

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

	public Boolean isStatusOk() {
		if (this.statusCode.equals("Ok")) {
			return true;
		} else {
			return false;
		}

	}
}