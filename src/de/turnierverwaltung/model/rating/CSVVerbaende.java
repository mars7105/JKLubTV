package de.turnierverwaltung.model.rating;

public class CSVVerbaende {
	private String csvVerband;
	private String csvLV;
	private String csvUebergeordnet;
	private String csvVerbandname;

	public CSVVerbaende(String csvVerband, String csvLV, String csvUebergeordnet, String csvVerbandname) {
		super();
		this.csvVerband = csvVerband;
		this.csvLV = csvLV;
		this.csvUebergeordnet = csvUebergeordnet;
		this.csvVerbandname = csvVerbandname;
	}

	public String getCsvLV() {
		return csvLV;
	}

	public String getCsvUebergeordnet() {
		return csvUebergeordnet;
	}

	public String getCsvVerband() {
		return csvVerband;
	}

	public String getCsvVerbandname() {
		return csvVerbandname;
	}

	public void setCsvLV(String csvLV) {
		this.csvLV = csvLV;
	}

	public void setCsvUebergeordnet(String csvUebergeordnet) {
		this.csvUebergeordnet = csvUebergeordnet;
	}

	public void setCsvVerband(String csvVerband) {
		this.csvVerband = csvVerband;
	}

	public void setCsvVerbandname(String csvVerbandname) {
		this.csvVerbandname = csvVerbandname;
	}

}
