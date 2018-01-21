package de.turnierverwaltung.model.rating;

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

	public void adddwzData(DWZData csv_Player) {

		dwzDataArray.add(csv_Player);

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

	public ArrayList<DWZData> getAlldwzData() {

		return dwzDataArray;

	}

	public ArrayList<DWZData> getdwzDataArray() {
		return dwzDataArray;
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

					DWZData dwzData = new DWZData();
					dwzData.setCsvZPS(csvZPS);
					dwzData.setCsvMgl_Nr(csvMgl_Nr);
					dwzData.setCsvStatus(csvStatus);
					
//					String[] getrennt = csvSpielername.split(",",2);
//					String name = csvSpielername;
//					if (getrennt.length >= 1) {
//						name = (getrennt[1] + " " + getrennt[0]).replaceAll(",", "").trim();
//					}
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
					dwzDataArray.add(dwzData);
				}
			}
			csvReader.close();

		}
	}

	public void setdwzDataArray(ArrayList<DWZData> csvPlayer) {
		this.dwzDataArray = csvPlayer;
	}
}
