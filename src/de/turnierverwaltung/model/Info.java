package de.turnierverwaltung.model;

public class Info {

	private String infoname;
	private String version;
	private String infonotice;
	private String datum;
	private int infoId;

	public Info(String infoname, String version, String infonotice, String datum, int infoId) {
		super();
		this.infoname = infoname;
		this.version = version;
		this.infonotice = infonotice;
		this.datum = datum;
	}

	public String getDatum() {
		return datum;
	}

	public int getInfoId() {
		return infoId;
	}

	public String getInfoname() {
		return infoname;
	}

	public String getInfonotice() {
		return infonotice;
	}

	public String getVersion() {
		return version;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public void setInfoname(String infoname) {
		this.infoname = infoname;
	}

	public void setInfonotice(String infonotice) {
		this.infonotice = infonotice;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
