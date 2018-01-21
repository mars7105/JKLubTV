package de.turnierverwaltung.model.rating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

import de.turnierverwaltung.model.Player;

public class CSVPlayerList {
	private HashMap<String, CSVPlayer> csvPlayer;
	private CSVReader csvReader;

	public CSVPlayerList() {

		csvPlayer = new HashMap<String, CSVPlayer>();
	}

	public void addPlayer(final String key, final CSVPlayer csv_Player) {

		csvPlayer.put(key, csv_Player);

	}

	/**
	 *
	 * @return
	 */
	public Boolean checkifSpielerFileExist(final String csvFilenameSpieler) {
		if (csvFilenameSpieler.equals("")) {
			return false;
		}
		final File f = new File(csvFilenameSpieler);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public HashMap<String, CSVPlayer> getCsvPlayer() {
		return csvPlayer;
	}

	public CSVPlayer getPlayer(final String zps, final String mgl) {
		final String key = keyGenerator(zps, mgl);

		return csvPlayer.get(key);

	}

	public ArrayList<Player> getPlayerOfVerein(final String zps) {
		final ArrayList<Player> temp = new ArrayList<Player>();

		for (int i = 0; i < 9999; i++) {
			final String number = Integer.toString(i);
			final String key = keyGenerator(zps, number);
			if (csvPlayer.containsKey(key)) {
				temp.add(csvPlayer.get(key).getPlayer());
			}
		}

		return temp;

	}

	private String keyGenerator(final String zps, final String mgl) {
		final int length = mgl.length();
		String output = "";
		if (length > 0 && length < 4) {
			final StringBuffer sb = new StringBuffer(mgl);
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
			output = sb.toString();

		}
		if (output.equals("0000")) {
			output = "";

		}

		return zps + output;
	}

	public void loadPlayerCSVList(final String csvFilenameSpieler) throws IOException, ArrayIndexOutOfBoundsException {

		if (checkifSpielerFileExist(csvFilenameSpieler) == true) {

			String[] row = null;

			csvReader = new CSVReader(
					new BufferedReader(new InputStreamReader(new FileInputStream(csvFilenameSpieler), "Cp1252")));
			while ((row = csvReader.readNext()) != null) {
				if (row[0].equals("ZPS") == false) {
					final String csvZPS = new String(row[0]);
					final String csvMgl_Nr = new String(row[1]);
					final String csvStatus = new String(row[2]);
					final String csvSpielername = new String(row[3]);
					final String csvGeschlecht = new String(row[4]);

					final String csvSpielberechtigung = new String(row[5]);
					final String csvGeburtsjahr = new String(row[6]);
					final String csvLetzte_Auswertung = new String(row[7]);
					final String csvDWZ = new String(row[8]);

					final String csvIndex = new String(row[9]);
					final String csvFIDE_Elo = new String(row[10]);
					final String csvFIDE_Titel = new String(row[11]);
					final String csvFIDE_ID = new String(row[12]);
					final String csvFIDE_Land = new String(row[13]);
					// String[] getrennt = csvSpielername.split(",",2);
					// String name = "";
					// if (getrennt.length >= 1) {
					// name = (getrennt[1] + " " + getrennt[0]).replaceAll(",", "").trim();
					// }
					final String key = keyGenerator(csvZPS, csvMgl_Nr);

					if (key.length() > 0) {
						addPlayer(key,
								new CSVPlayer(csvZPS, csvMgl_Nr, csvStatus, csvSpielername, csvGeschlecht,
										csvSpielberechtigung, csvGeburtsjahr, csvLetzte_Auswertung, csvDWZ, csvIndex,
										csvFIDE_Elo, csvFIDE_Titel, csvFIDE_ID, csvFIDE_Land));
					}
				}
			}
			csvReader.close();

		}
	}

	public void setCsvPlayer(final HashMap<String, CSVPlayer> csvPlayer) {
		this.csvPlayer = csvPlayer;
	}
}
