package de.turnierverwaltung.model;

import java.time.LocalDateTime;

public class CSVPlayer {
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

	public CSVPlayer(String csvZPS, String csvMgl_Nr, String csvStatus, String csvSpielername, String csvGeschlecht,
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
		correctMGLNumber();

	}

	public String getCsvZPS() {
		return csvZPS;
	}

	public int getCsvZPSInt() {
		try {
			return Integer.parseInt(csvZPS);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public void setCsvZPS(String csvZPS) {
		this.csvZPS = csvZPS;
	}

	public String getCsvMgl_Nr() {
		return csvMgl_Nr;
	}

	public int getCsvMGLInt() {
		try {
			return Integer.parseInt(csvMgl_Nr);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public void setCsvMgl_Nr(String csvMgl_Nr) {
		this.csvMgl_Nr = csvMgl_Nr;
		correctMGLNumber();
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

	public Player getPlayer() {
		Player player = new Player();
		int age = 0;
		LocalDateTime now = LocalDateTime.now();
		try {
			age = now.getYear() - Integer.parseInt(csvGeburtsjahr);

			if (age < 20) {
				player.setAge(0);
			}
			if (age >= 20 && age <= 25) {
				player.setAge(1);
			}
			if (age > 25) {
				player.setAge(2);
			}
		} catch (NumberFormatException e) {
			player.setAge(2);
		}

		player.setDsbMGLNumber(csvMgl_Nr);
		player.setDsbZPSNumber(csvZPS);
		player.setDwz(csvDWZ);
		try {
			player.setDwzindex(Integer.parseInt(csvIndex));
		} catch (NumberFormatException e) {
			player.setDwzindex(-1);
		}
		player.setName(csvSpielername);
		player.extractNameToForenameAndSurename();
		player.extractNameToKuerzel();
		return player;
	}

	private void correctMGLNumber() {
		int length = csvMgl_Nr.length();
		if (length < 4) {
			StringBuffer sb = new StringBuffer(csvMgl_Nr);
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
			csvMgl_Nr = sb.toString();
			System.out.println(csvMgl_Nr);

		}
	}
}
