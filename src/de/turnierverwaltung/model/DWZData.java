package de.turnierverwaltung.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DWZData {
	private String csvZPS;
	private String csvMgl_Nr;
	private String csvStatus;
	private String csvSpielername;
	private String csvGeschlecht;
	private String csvSpielberechtigung;
	private String csvGeburtsjahr;
	private String csvLetzte_Auswertung;
	private String csvDWZ;
	private String csvIndex;
	private String csvFIDE_Elo;
	private String csvFIDE_Titel;
	private String csvFIDE_ID;
	private String csvFIDE_Land;
	private int age;

	public DWZData(String csvZPS, String csvMgl_Nr, String csvStatus, String csvSpielername, String csvGeschlecht,
			String csvSpielberechtigung, String csvGeburtsjahr, String csvLetzte_Auswertung, String csvDWZ,
			String csvIndex, String csvFIDE_Elo, String csvFIDE_Titel, String csvFIDE_ID, String csvFIDE_Land) {
		super();
		this.csvZPS = csvZPS;
		this.csvMgl_Nr = csvMgl_Nr;
		this.csvStatus = csvStatus;
		this.csvSpielername = csvSpielername;
		this.csvGeschlecht = csvGeschlecht;
		this.csvSpielberechtigung = csvSpielberechtigung;
		this.csvGeburtsjahr = csvGeburtsjahr;
		this.csvLetzte_Auswertung = csvLetzte_Auswertung;
		this.csvDWZ = csvDWZ;
		this.csvIndex = csvIndex;
		this.csvFIDE_Elo = csvFIDE_Elo;
		this.csvFIDE_Titel = csvFIDE_Titel;
		this.csvFIDE_ID = csvFIDE_ID;
		this.csvFIDE_Land = csvFIDE_Land;
	}

	public DWZData() {
		this.csvZPS = "";
		this.csvMgl_Nr = "";
		this.csvStatus = "";
		this.csvSpielername = "";
		this.csvGeschlecht = "";
		this.csvSpielberechtigung = "";
		this.csvGeburtsjahr = "";
		this.csvLetzte_Auswertung = "";
		this.csvDWZ = "";
		this.csvIndex = "";
		this.csvFIDE_Elo = "";
		this.csvFIDE_Titel = "";
		this.csvFIDE_ID = "";
		this.csvFIDE_Land = "";
	}

	public String getCsvZPS() {
		return csvZPS;
	}

	public void setCsvZPS(String csvZPS) {
		this.csvZPS = csvZPS;
	}

	public String getCsvMgl_Nr() {
		return csvMgl_Nr;
	}

	public void setCsvMgl_Nr(String csvMgl_Nr) {
		this.csvMgl_Nr = csvMgl_Nr;
	}

	public String getCsvStatus() {
		return csvStatus;
	}

	public void setCsvStatus(String csvStatus) {
		this.csvStatus = csvStatus;
	}

	public String getCsvSpielername() {
		return csvSpielername;
	}

	public void setCsvSpielername(String csvSpielername) {
		this.csvSpielername = csvSpielername;
	}

	public String getCsvGeschlecht() {
		return csvGeschlecht;
	}

	public void setCsvGeschlecht(String csvGeschlecht) {
		this.csvGeschlecht = csvGeschlecht;
	}

	public String getCsvSpielberechtigung() {
		return csvSpielberechtigung;
	}

	public void setCsvSpielberechtigung(String csvSpielberechtigung) {
		this.csvSpielberechtigung = csvSpielberechtigung;
	}

	public String getCsvGeburtsjahr() {
		return csvGeburtsjahr;
	}

	public void setCsvGeburtsjahr(String csvGeburtsjahr) {
		this.csvGeburtsjahr = csvGeburtsjahr;
	}

	public String getCsvLetzte_Auswertung() {
		return csvLetzte_Auswertung;
	}

	public void setCsvLetzte_Auswertung(String csvLetzte_Auswertung) {
		this.csvLetzte_Auswertung = csvLetzte_Auswertung;
	}

	public String getCsvDWZ() {
		return csvDWZ;
	}

	public void setCsvDWZ(String csvDWZ) {
		this.csvDWZ = csvDWZ;
	}

	public String getCsvIndex() {
		return csvIndex;
	}

	public void setCsvIndex(String csvIndex) {
		this.csvIndex = csvIndex;
	}

	public String getCsvFIDE_Elo() {
		return csvFIDE_Elo;
	}

	public void setCsvFIDE_Elo(String csvFIDE_Elo) {
		this.csvFIDE_Elo = csvFIDE_Elo;
	}

	public String getCsvFIDE_Titel() {
		return csvFIDE_Titel;
	}

	public void setCsvFIDE_Titel(String csvFIDE_Titel) {
		this.csvFIDE_Titel = csvFIDE_Titel;
	}

	public String getCsvFIDE_ID() {
		return csvFIDE_ID;
	}

	public void setCsvFIDE_ID(String csvFIDE_ID) {
		this.csvFIDE_ID = csvFIDE_ID;
	}

	public String getCsvFIDE_Land() {
		return csvFIDE_Land;
	}

	public void setCsvFIDE_Land(String csvFIDE_Land) {
		this.csvFIDE_Land = csvFIDE_Land;
	}

	public int getAge() {
		String timeStamp = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		try {
			int year = Integer.parseInt(timeStamp) - Integer.parseInt(csvGeburtsjahr);
			if (year < 20) {
				age = 0;
			}
			if (year >= 20 && age <= 25) {
				age = 1;
			}
			if (year > 25) {
				age = 2;
			}
		} catch (NumberFormatException e) {
			age = 2;
		}
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
