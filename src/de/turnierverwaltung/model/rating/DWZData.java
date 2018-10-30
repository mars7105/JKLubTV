package de.turnierverwaltung.model.rating;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DWZData {
	private String csvZPS;
	private String csvMgl_Nr;
	private String csvStatus;
	private String csvSpielername;
	private String csvGeschlecht;
	private String csvSpielberechtigung;
	private int csvGeburtsjahr;
	private int csvLetzte_Auswertung;
	private int csvDWZ;
	private int csvIndex;
	private int csvFIDE_Elo;
	private String csvFIDE_Titel;
	private int csvFIDE_ID;
	private String csvFIDE_Land;
	private int age;
	private int spielerId;

	public DWZData() {
		this.csvZPS = "";
		this.csvMgl_Nr = "";
		this.csvStatus = "";
		this.csvSpielername = "";
		this.csvGeschlecht = "";
		this.csvSpielberechtigung = "";
		this.csvGeburtsjahr = 0;
		this.csvLetzte_Auswertung = 0;
		this.csvDWZ = 0;
		this.csvIndex = -1;
		this.csvFIDE_Elo = 0;
		this.csvFIDE_Titel = "";
		this.csvFIDE_ID = -1;
		this.csvFIDE_Land = "";
		spielerId = -1;
		
	}

	public DWZData(String csvZPS, String csvMgl_Nr, String csvStatus, String csvSpielername, String csvGeschlecht,
			String csvSpielberechtigung, int csvGeburtsjahr, int csvLetzte_Auswertung, int csvDWZ, int csvIndex,
			int csvFIDE_Elo, String csvFIDE_Titel, int csvFIDE_ID, String csvFIDE_Land) {
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
		spielerId = -1;
		correctMGLNumber();
	}

	private void correctMGLNumber() {
		int length = getCsvMgl_Nr().length();
		if (length > 0 && length < 4) {
			StringBuffer sb = new StringBuffer(getCsvMgl_Nr());
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
			// csvMgl_Nr = sb.toString();
			setCsvMgl_Nr(sb.toString());

		}
		if (getCsvMgl_Nr().equals("0000")) {
			// csvMgl_Nr = "";
			setCsvMgl_Nr("");

		}
	}

	public int getAge() {
		String timeStamp = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
		try {
			int year = Integer.parseInt(timeStamp) - csvGeburtsjahr;
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

	public int getCsvDWZ() {
		return csvDWZ;
	}

	public int getCsvFIDE_Elo() {
		return csvFIDE_Elo;
	}

	public int getCsvFIDE_ID() {
		return csvFIDE_ID;
	}

	public String getCsvFIDE_Land() {
		return csvFIDE_Land;
	}

	public String getCsvFIDE_Titel() {
		return csvFIDE_Titel;
	}

	public int getCsvGeburtsjahr() {
		return csvGeburtsjahr;
	}

	public String getCsvGeschlecht() {
		return csvGeschlecht;
	}

	public int getCsvIndex() {
		return csvIndex;
	}

	public int getCsvLetzte_Auswertung() {
		return csvLetzte_Auswertung;
	}

	public String getCsvMgl_Nr() {
		return csvMgl_Nr;
	}

	public String getCsvSpielberechtigung() {
		return csvSpielberechtigung;
	}

	public String getCsvSpielername() {
		return csvSpielername;
	}

	public String getCsvStatus() {
		return csvStatus;
	}

	public String getCsvZPS() {
		return csvZPS;
	}

	public int getSpielerId() {
		return spielerId;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setCsvDWZ(int csvDWZ) {
		this.csvDWZ = csvDWZ;
	}

	public void setCsvFIDE_Elo(int csvFIDE_Elo) {
		this.csvFIDE_Elo = csvFIDE_Elo;
	}

	public void setCsvFIDE_ID(int csvFIDE_ID) {
		this.csvFIDE_ID = csvFIDE_ID;
	}

	public void setCsvFIDE_Land(String csvFIDE_Land) {
		this.csvFIDE_Land = csvFIDE_Land;
	}

	public void setCsvFIDE_Titel(String csvFIDE_Titel) {
		this.csvFIDE_Titel = csvFIDE_Titel;
	}

	public void setCsvGeburtsjahr(int csvGeburtsjahr) {
		this.csvGeburtsjahr = csvGeburtsjahr;
	}

	public void setCsvGeschlecht(String csvGeschlecht) {
		this.csvGeschlecht = csvGeschlecht;
	}

	public void setCsvIndex(int csvIndex) {
		this.csvIndex = csvIndex;
	}

	public void setCsvLetzte_Auswertung(int csvLetzte_Auswertung) {
		this.csvLetzte_Auswertung = csvLetzte_Auswertung;
	}

	public void setCsvMgl_Nr(String csvMgl_Nr) {
		this.csvMgl_Nr = csvMgl_Nr;
		correctMGLNumber();
	}

	public void setCsvSpielberechtigung(String csvSpielberechtigung) {
		this.csvSpielberechtigung = csvSpielberechtigung;
	}

	public void setCsvSpielername(String csvSpielername) {
		this.csvSpielername = csvSpielername;
	}

	public void setCsvStatus(String csvStatus) {
		this.csvStatus = csvStatus;
	}

	public void setCsvZPS(String csvZPS) {
		this.csvZPS = csvZPS;
	}

	public void setSpielerId(int spielerId) {
		this.spielerId = spielerId;
	}
}
