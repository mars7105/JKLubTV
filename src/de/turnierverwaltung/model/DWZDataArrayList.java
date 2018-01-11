package de.turnierverwaltung.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class DWZDataArrayList {
	private ArrayList<DWZData> dwzDataArray;
	private CSVReader csvReader;

	public DWZDataArrayList() {

		dwzDataArray = new ArrayList<DWZData>();
	}

	public void adddwzData(final DWZData csv_Player) {

		dwzDataArray.add(csv_Player);

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

	public ArrayList<DWZData> getAlldwzData() {

		return dwzDataArray;

	}

	public ArrayList<DWZData> getdwzDataArray() {
		return dwzDataArray;
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

					final DWZData dwzData = new DWZData();
					dwzData.setCsvZPS(csvZPS);
					dwzData.setCsvMgl_Nr(csvMgl_Nr);
					dwzData.setCsvStatus(csvStatus);

					// String[] getrennt = csvSpielername.split(",",2);
					// String name = csvSpielername;
					// if (getrennt.length >= 1) {
					// name = (getrennt[1] + " " + getrennt[0]).replaceAll(",", "").trim();
					// }
					dwzData.setCsvSpielername(csvSpielername);
					dwzData.setCsvGeschlecht(csvGeschlecht);
					dwzData.setCsvSpielberechtigung(csvSpielberechtigung);
					try {
						dwzData.setCsvGeburtsjahr(Integer.parseInt(csvGeburtsjahr));
					} catch (final NumberFormatException e0) {
						dwzData.setCsvGeburtsjahr(1970);
					}
					try {
						dwzData.setCsvLetzte_Auswertung(Integer.parseInt(csvLetzte_Auswertung));
					} catch (final NumberFormatException e1) {
						dwzData.setCsvLetzte_Auswertung(-1);
					}
					try {
						dwzData.setCsvDWZ(Integer.parseInt(csvDWZ));
					} catch (final NumberFormatException e2) {
						dwzData.setCsvDWZ(-1);
					}
					try {
						dwzData.setCsvIndex(Integer.parseInt(csvIndex));
					} catch (final NumberFormatException e3) {
						dwzData.setCsvIndex(-1);
					}
					try {
						dwzData.setCsvFIDE_Elo(Integer.parseInt(csvFIDE_Elo));
					} catch (final NumberFormatException e4) {
						dwzData.setCsvFIDE_Elo(-1);
					}
					try {
						dwzData.setCsvFIDE_ID(Integer.parseInt(csvFIDE_ID));
					} catch (final NumberFormatException e5) {
						dwzData.setCsvFIDE_ID(-1);
					}
					dwzData.setCsvFIDE_Titel(csvFIDE_Titel);

					dwzData.setCsvFIDE_Land(csvFIDE_Land);
					dwzDataArray.add(dwzData);
				}
			}
			csvReader.close();

		}
	}

	public void setdwzDataArray(final ArrayList<DWZData> csvPlayer) {
		dwzDataArray = csvPlayer;
	}
}
