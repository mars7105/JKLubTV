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
	private HashMap<Integer, CSVPlayer> csvPlayer;
	private CSVReader csvReader;

	public CSVPlayerList() {

		csvPlayer = new HashMap<Integer, CSVPlayer>();
	}

	public HashMap<Integer, CSVPlayer> getCsvPlayer() {
		return csvPlayer;
	}

	public void setCsvPlayer(HashMap<Integer, CSVPlayer> csvPlayer) {
		this.csvPlayer = csvPlayer;
	}

	public void addPlayer(String zps, String mgl, CSVPlayer csv_Player) {
		int key = decodeKey(zps, mgl);
		if (key >= 0) {
			csvPlayer.put(key, csv_Player);
		}
	}

	public ArrayList<Player> getPlayerOfVerein(String zps) {
		ArrayList<Player> temp = new ArrayList<Player>();
		int zpsi = 0;
		int key = 0;
		try {
			zpsi = Integer.parseInt(zps);
			key = zpsi * 10000;
			for (int i = 0; i < 9999; i++) {

				if (csvPlayer.containsKey(key + i)) {
					temp.add(csvPlayer.get(key + i).getPlayer());
				}
			}
		} catch (NumberFormatException e) {
			temp = null;
		}

		return temp;

	}

	public CSVPlayer getPlayer(String zps, String mgl) {
		int key = decodeKey(zps, mgl);
		if (key >= 0) {
			return csvPlayer.get(key);
		} else {
			return null;
		}
	}

	private int decodeKey(String zps, String mgl) {
		try {
			int zpsi = Integer.parseInt(zps);
			int mgli = Integer.parseInt(mgl);
			int key = (zpsi * 10000) + mgli;
			return key;
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	public void loadPlayerCSVList(String csvFilenameSpieler) {

		if (checkifSpielerFileExist(csvFilenameSpieler) == true) {

			String[] row = null;

			try {
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
						String[] getrennt = csvSpielername.split("\\,");
						String name = getrennt[1] + " " + getrennt[0];
						addPlayer(csvZPS, csvMgl_Nr,
								new CSVPlayer(csvZPS, csvMgl_Nr, csvStatus, name, csvGeschlecht, csvSpielberechtigung,
										csvGeburtsjahr, csvLetzte_Auswertung, csvDWZ, csvIndex, csvFIDE_Elo,
										csvFIDE_Titel, csvFIDE_ID, csvFIDE_Land));

					}
				}
				// ...
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
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
}
