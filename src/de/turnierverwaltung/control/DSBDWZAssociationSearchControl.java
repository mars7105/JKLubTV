package de.turnierverwaltung.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;

import de.turnierverwaltung.model.CSVPlayer;
import de.turnierverwaltung.model.CSVPlayerList;

/**
 * 
 * @author mars
 *
 */
public class DSBDWZAssociationSearchControl {

	private CSVReader csvReader;
	private String csvFilenameVereine;
	private MainControl mainControl;
	private String csvFilenameSpieler;

	/**
	 * 
	 * @param mainControl
	 */
	public DSBDWZAssociationSearchControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;

		csvFilenameVereine = this.mainControl.getPropertiesControl().getPathToVereineCVS();
		csvFilenameSpieler = this.mainControl.getPropertiesControl().getPathToPlayersCSV();
	}

	/**
	 * 
	 * @param searchString
	 * @return
	 */
	public ArrayList<String[]> searchForVerein(String searchString) {
		ArrayList<String[]> foundStringList = new ArrayList<String[]>();

		if (checkifFileExist() == true) {

			String[] row = null;

			try {
				csvReader = new CSVReader(
						new BufferedReader(new InputStreamReader(new FileInputStream(csvFilenameVereine), "Cp1252")));
				while ((row = csvReader.readNext()) != null) {
					String paramLower = row[3].toLowerCase();
					if (paramLower.contains(searchString.toLowerCase())) {
						foundStringList.add(row);
					}

				}

				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return foundStringList;
	}

	public CSVPlayerList loadPlayerCSVList() {
		CSVPlayerList playerlist = new CSVPlayerList();

		if (checkifFileExist() == true) {

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
						playerlist.addPlayer(csvZPS, csvMgl_Nr,
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
		return playerlist;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean checkifFileExist() {
		if (csvFilenameVereine.equals("")) {
			return false;
		}
		File f = new File(csvFilenameVereine);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
	public Boolean checkifSpielerFileExist() {
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