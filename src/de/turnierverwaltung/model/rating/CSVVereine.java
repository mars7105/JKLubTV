package de.turnierverwaltung.model.rating;

public class CSVVereine {
	private String csvZPS;
	private String csvLV;
	private String csvVerband;
	private String csvVereinname;

	public CSVVereine(String csvZPS, String csvLV, String csvVerband, String csvVereinname) {
		super();
		this.csvZPS = csvZPS;
		this.csvLV = csvLV;
		this.csvVerband = csvVerband;
		this.csvVereinname = csvVereinname;
	}

	public String getCsvLV() {
		return csvLV;
	}

	public String getCsvVerband() {
		return csvVerband;
	}

	public String getCsvVereinname() {
		return csvVereinname;
	}

	public String getCsvZPS() {
		return csvZPS;
	}

	public void setCsvLV(String csvLV) {
		this.csvLV = csvLV;
	}

	public void setCsvVerband(String csvVerband) {
		this.csvVerband = csvVerband;
	}

	public void setCsvVereinname(String csvVereinname) {
		this.csvVereinname = csvVereinname;
	}

	public void setCsvZPS(String csvZPS) {
		this.csvZPS = csvZPS;
	}

}
