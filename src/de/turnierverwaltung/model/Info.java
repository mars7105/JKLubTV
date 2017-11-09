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

	public String getInfoname() {
		return infoname;
	}

	public String getVersion() {
		return version;
	}

	public String getInfonotice() {
		return infonotice;
	}

	public String getDatum() {
		return datum;
	}

	public void setInfoname(String infoname) {
		this.infoname = infoname;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setInfonotice(String infonotice) {
		this.infonotice = infonotice;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

}
