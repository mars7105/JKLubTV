package de.turnierverwaltung.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class CSVPlayerList {
	private HashMap<String, CSVPlayer> csvPlayer;
	private CSVReader csvReader;

	public CSVPlayerList() {

		csvPlayer = new HashMap<String, CSVPlayer>();
	}

	public void addPlayer(String key, CSVPlayer csv_Player) {

		csvPlayer.put(key, csv_Player);

	}

	/**
	 * 
	 * @return
	 */
	public Boolean checkifSpielerFileExist(String csvFilenameSpieler) {
		if (csvFilenameSpieler.equals("")) {
			return false;
		}
		File f = new File(csvFilenameSpieler);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public HashMap<String, CSVPlayer> getCsvPlayer() {
		return csvPlayer;
	}

	public CSVPlayer getPlayer(String zps, String mgl) {
		String key = keyGenerator(zps, mgl);

		return csvPlayer.get(key);

	}

	public ArrayList<Player> getPlayerOfVerein(String zps) {
		ArrayList<Player> temp = new ArrayList<Player>();

		for (int i = 0; i < 9999; i++) {
			String number = Integer.toString(i);
			String key = keyGenerator(zps, number);
			if (csvPlayer.containsKey(key)) {
				temp.add(csvPlayer.get(key).getPlayer());
			}
		}

		return temp;

	}

	private String keyGenerator(String zps, String mgl) {
		int length = mgl.length();
		if (length > 0 && length < 4) {
			StringBuffer sb = new StringBuffer(mgl);
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
			mgl = sb.toString();

		}
		if (mgl.equals("0000")) {
			mgl = "";

		}

		return zps + mgl;
	}

	public void loadPlayerCSVList(String csvFilenameSpieler) throws IOException, ArrayIndexOutOfBoundsException {

		if (checkifSpielerFileExist(csvFilenameSpieler) == true) {

			String[] row = null;

			csvReader = new CSVReader(
					new BufferedReader(new InputStreamReader(new FileInputStream(csvFilenameSpieler), "Cp1252")));
			while ((row = csvReader.readNext()) != null) {
				if (row[0].equals("ZPS") == false) {
					String csvZPS = new String(row[0]);
					String csvMgl_Nr = new String(row[1]);
					String csvStatus = new String(row[2]);
					String csvSpielername = new String(row[3]);
					String csvGeschlecht = new String(row[4]);

					String csvSpielberechtigung = new String(row[5]);
					String csvGeburtsjahr = new String(row[6]);
					String csvLetzte_Auswertung = new String(row[7]);
					String csvDWZ = new String(row[8]);

					String csvIndex = new String(row[9]);
					String csvFIDE_Elo = new String(row[10]);
					String csvFIDE_Titel = new String(row[11]);
					String csvFIDE_ID = new String(row[12]);
					String csvFIDE_Land = new String(row[13]);
//					String[] getrennt = csvSpielername.split(",",2);
//					String name = "";
//					if (getrennt.length >= 1) {
//						name = (getrennt[1] + " " + getrennt[0]).replaceAll(",", "").trim();
//					}
					String key = keyGenerator(csvZPS, csvMgl_Nr);

					if (key.length() > 0) {
						addPlayer(key,
								new CSVPlayer(csvZPS, csvMgl_Nr, csvStatus, csvSpielername, csvGeschlecht, csvSpielberechtigung,
										csvGeburtsjahr, csvLetzte_Auswertung, csvDWZ, csvIndex, csvFIDE_Elo,
										csvFIDE_Titel, csvFIDE_ID, csvFIDE_Land));
					}
				}
			}
			csvReader.close();

		}
	}

	public void setCsvPlayer(HashMap<String, CSVPlayer> csvPlayer) {
		this.csvPlayer = csvPlayer;
	}
}
