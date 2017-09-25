package de.turnierverwaltung.model;

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
	public String getCsvZPS() {
		return csvZPS;
	}
	public void setCsvZPS(String csvZPS) {
		this.csvZPS = csvZPS;
	}
	public String getCsvLV() {
		return csvLV;
	}
	public void setCsvLV(String csvLV) {
		this.csvLV = csvLV;
	}
	public String getCsvVerband() {
		return csvVerband;
	}
	public void setCsvVerband(String csvVerband) {
		this.csvVerband = csvVerband;
	}
	public String getCsvVereinname() {
		return csvVereinname;
	}
	public void setCsvVereinname(String csvVereinname) {
		this.csvVereinname = csvVereinname;
	}

}
