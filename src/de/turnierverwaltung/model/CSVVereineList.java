package de.turnierverwaltung.model;

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

	/**
	 * 
	 * @param searchString
	 * @return
	 */
	public void loadVereine(String csvFilenameVereine) {

		if (checkifFileExist(csvFilenameVereine) == true) {

			String[] row = null;

			try {
				csvReader = new CSVReader(
						new BufferedReader(new InputStreamReader(new FileInputStream(csvFilenameVereine), "Cp1252")));
				while ((row = csvReader.readNext()) != null) {
					String csvZPS = row[0];
					String csvLV = row[1];
					String csvVerband = row[2];
					String csvVereinname = row[3];
					addVerein(new CSVVereine(csvZPS, csvLV, csvVerband, csvVereinname));
				}

				csvReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

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

	public ArrayList<CSVVereine> getCsvvereine() {
		return csvvereine;
	}

	public void setCsvvereine(ArrayList<CSVVereine> csvvereine) {
		this.csvvereine = csvvereine;
	}

	public void addVerein(String zps, CSVVereine csv_Verein) {

		csvVerein.put(zps, csv_Verein);

	}

	public void addVerein(CSVVereine csv_Verein) {

		csvvereine.add(csv_Verein);

	}

	public HashMap<String, CSVVereine> getCsvVerein() {
		return csvVerein;
	}

	public void setCsvVerein(HashMap<String, CSVVereine> csvVerein) {
		this.csvVerein = csvVerein;
	}

}
