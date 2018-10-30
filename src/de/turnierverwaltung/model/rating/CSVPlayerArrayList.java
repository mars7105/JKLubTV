package de.turnierverwaltung.model.rating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ListIterator;

import com.opencsv.CSVReader;

import de.turnierverwaltung.model.Player;

public class CSVPlayerArrayList {
	private ArrayList<CSVPlayer> csvPlayer;
	private CSVReader csvReader;

	public CSVPlayerArrayList() {

		csvPlayer = new ArrayList<CSVPlayer>();
	}

	public void addPlayer(CSVPlayer csv_Player) {

		csvPlayer.add(csv_Player);

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

	public ArrayList<CSVPlayer> getAllPlayer() {

		return csvPlayer;

	}

	public ArrayList<CSVPlayer> getCsvPlayer() {
		return csvPlayer;
	}

	public Player getPlayer(String zps, String mgl) {

		ListIterator<CSVPlayer> list = csvPlayer.listIterator();
		while (list.hasNext()) {
			Player tmp = list.next().getPlayer();
			if (tmp.getDwzData().getCsvZPS().equals(zps) && tmp.getDwzData().getCsvMgl_Nr().equals(mgl)) {
				return tmp;
			}
		}
		return null;
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
//					String[] getrennt = csvSpielername.split("\\,");
//					String name = getrennt[1] + " " + getrennt[0];
//					String[] getrennt = csvSpielername.split(",",2);
//					String name = csvSpielername;
//					if (getrennt.length >= 1) {
//						name = (getrennt[1] + " " + getrennt[0]).replaceAll(",", "").trim();
//					}
					addPlayer(new CSVPlayer(csvZPS, csvMgl_Nr, csvStatus, csvSpielername, csvGeschlecht, csvSpielberechtigung,
							csvGeburtsjahr, csvLetzte_Auswertung, csvDWZ, csvIndex, csvFIDE_Elo, csvFIDE_Titel,
							csvFIDE_ID, csvFIDE_Land));

				}
			}
			csvReader.close();

		}
	}

	public void setCsvPlayer(ArrayList<CSVPlayer> csvPlayer) {
		this.csvPlayer = csvPlayer;
	}
}
