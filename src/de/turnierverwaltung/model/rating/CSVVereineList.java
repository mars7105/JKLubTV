package de.turnierverwaltung.model.rating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.opencsv.CSVReader;

public class CSVVereineList {
	private ArrayList<CSVVereine> csvvereine;
	private HashMap<String, CSVVereine> csvVerein;
	private CSVReader csvReader;

	public CSVVereineList() {
		csvvereine = new ArrayList<CSVVereine>();
		csvVerein = new HashMap<String, CSVVereine>();
	}

	public void addVerein(CSVVereine csv_Verein) {

		csvvereine.add(csv_Verein);

	}

	public void addVerein(String zps, CSVVereine csv_Verein) {

		csvVerein.put(zps, csv_Verein);

	}

	/**
	 * 
	 * @return
	 */
	public Boolean checkifFileExist(String csvFilenameVereine) {
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

	public HashMap<String, CSVVereine> getCsvVerein() {
		return csvVerein;
	}

	public ArrayList<CSVVereine> getCsvvereine() {
		return csvvereine;
	}

	/**
	 * 
	 * @param searchString
	 * @return
	 */
	public void loadVereine(String csvFilenameVereine) throws IOException, ArrayIndexOutOfBoundsException {

		if (checkifFileExist(csvFilenameVereine) == true) {

			String[] row = null;

			csvReader = new CSVReader(
					new BufferedReader(new InputStreamReader(new FileInputStream(csvFilenameVereine), "Cp1252")));
			while ((row = csvReader.readNext()) != null) {
				if (row[0].equals("ZPS") == false) {
					String csvZPS = row[0];
					String csvLV = row[1];
					String csvVerband = row[2];
					String csvVereinname = row[3];
					addVerein(new CSVVereine(csvZPS, csvLV, csvVerband, csvVereinname));
				}
			}

			csvReader.close();

		}

	}

	public void setCsvVerein(HashMap<String, CSVVereine> csvVerein) {
		this.csvVerein = csvVerein;
	}

	public void setCsvvereine(ArrayList<CSVVereine> csvvereine) {
		this.csvvereine = csvvereine;
	}

}
