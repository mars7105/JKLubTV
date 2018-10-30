package de.turnierverwaltung.model.rating;

import java.time.LocalDateTime;

import de.turnierverwaltung.model.Player;

public class CSVPlayer {

	private DWZData dwzData;

	public CSVPlayer(String csvZPS, String csvMgl_Nr, String csvStatus, String csvSpielername, String csvGeschlecht,
			String csvSpielberechtigung, String csvGeburtsjahr, String csvLetzte_Auswertung, String csvDWZ,
			String csvIndex, String csvFIDE_Elo, String csvFIDE_Titel, String csvFIDE_ID, String csvFIDE_Land) {
		super();
		dwzData = new DWZData();
		dwzData.setCsvZPS(csvZPS);
		dwzData.setCsvMgl_Nr(csvMgl_Nr);
		dwzData.setCsvStatus(csvStatus);
		dwzData.setCsvSpielername(csvSpielername);
		dwzData.setCsvGeschlecht(csvGeschlecht);
		dwzData.setCsvSpielberechtigung(csvSpielberechtigung);
		try {
			dwzData.setCsvGeburtsjahr(Integer.parseInt(csvGeburtsjahr));
		} catch (NumberFormatException e0) {
			dwzData.setCsvGeburtsjahr(1970);
		}
		try {
			dwzData.setCsvLetzte_Auswertung(Integer.parseInt(csvLetzte_Auswertung));
		} catch (NumberFormatException e1) {
			dwzData.setCsvLetzte_Auswertung(-1);
		}
		try {
			dwzData.setCsvDWZ(Integer.parseInt(csvDWZ));
		} catch (NumberFormatException e2) {
			dwzData.setCsvDWZ(-1);
		}
		try {
			dwzData.setCsvIndex(Integer.parseInt(csvIndex));
		} catch (NumberFormatException e3) {
			dwzData.setCsvIndex(-1);
		}
		try {
			dwzData.setCsvFIDE_Elo(Integer.parseInt(csvFIDE_Elo));
		} catch (NumberFormatException e4) {
			dwzData.setCsvFIDE_Elo(-1);
		}
		try {
			dwzData.setCsvFIDE_ID(Integer.parseInt(csvFIDE_ID));
		} catch (NumberFormatException e5) {
			dwzData.setCsvFIDE_ID(-1);
		}
		dwzData.setCsvFIDE_Titel(csvFIDE_Titel);

		dwzData.setCsvFIDE_Land(csvFIDE_Land);

	}

	public Player getPlayer() {
		Player player = new Player();
		int age = 0;
		LocalDateTime now = LocalDateTime.now();

		age = now.getYear() - dwzData.getCsvGeburtsjahr();

		if (age < 20) {
			player.setAge(0);
		}
		if (age >= 20 && age <= 25) {
			player.setAge(1);
		}
		if (age > 25) {
			player.setAge(2);
		}

		player.setDwzData(dwzData);

		player.setName(dwzData.getCsvSpielername());
//		player.extractNameToForenameAndSurename();
//		player.extractNameToKuerzel();
		return player;
	}

}
