package de.turnierverwaltung.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class DewisDialogVereinsSucheController {
	CSVReader csvReader;

	public ArrayList<String[]> searchForVerein(String searchString) {
		String csvFilename = "vereine.csv";
		
		ArrayList<String[]> foundStringList = new ArrayList<String[]>();
		try {
			csvReader = new CSVReader(new FileReader(csvFilename));

			String[] row = null;

			while ((row = csvReader.readNext()) != null) {


				if (row[3].contains(searchString)) {
					foundStringList.add(row);
				}
			}
			// ...
			csvReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foundStringList;
	}
}