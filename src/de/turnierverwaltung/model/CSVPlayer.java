package de.turnierverwaltung.model;

import java.time.LocalDateTime;

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
		
		dwzData.setCsvGeburtsjahr(Integer.parseInt(csvGeburtsjahr));
		dwzData.setCsvLetzte_Auswertung(Integer.parseInt(csvLetzte_Auswertung));
		dwzData.setCsvDWZ(Integer.parseInt(csvDWZ));
		dwzData.setCsvIndex(Integer.parseInt(csvIndex));
		dwzData.setCsvFIDE_Elo(Integer.parseInt(csvFIDE_Elo));
		dwzData.setCsvFIDE_ID(Integer.parseInt(csvFIDE_ID));
		dwzData.setCsvFIDE_Titel(csvFIDE_Titel);
		
		dwzData.setCsvFIDE_Land(csvFIDE_Land);
		correctMGLNumber();

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

		// try {
		//// player.getDwzData().setDwzindex(Integer.parseInt(dwzData.getCsvIndex()));
		// } catch (NumberFormatException e) {
		// player.setDwzindex(-1);
		// }
		player.setName(dwzData.getCsvSpielername());
		player.extractNameToForenameAndSurename();
		player.extractNameToKuerzel();
		return player;
	}

	private void correctMGLNumber() {
		int length = dwzData.getCsvMgl_Nr().length();
		if (length > 0 && length < 4) {
			StringBuffer sb = new StringBuffer(dwzData.getCsvMgl_Nr());
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
			// csvMgl_Nr = sb.toString();
			dwzData.setCsvMgl_Nr(sb.toString());

		}
		if (dwzData.getCsvMgl_Nr().equals("0000")) {
			// csvMgl_Nr = "";
			dwzData.setCsvMgl_Nr("");

		}
	}
}
