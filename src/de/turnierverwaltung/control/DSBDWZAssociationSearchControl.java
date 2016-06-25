package de.turnierverwaltung.control;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class DSBDWZAssociationSearchControl {

	private CSVReader csvReader;
	private String csvFilename;
	private MainControl mainControl;

	public DSBDWZAssociationSearchControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;

		csvFilename = this.mainControl.getPropertiesControl().getPathToCVS();

	}

	public ArrayList<String[]> searchForVerein(String searchString) {
		ArrayList<String[]> foundStringList = new ArrayList<String[]>();

		if (checkifFileExist() == true) {

			String[] row = null;

			try {
				csvReader = new CSVReader(new FileReader(csvFilename));

				while ((row = csvReader.readNext()) != null) {
					String paramLower = row[3].toLowerCase();
					if (paramLower.contains(searchString.toLowerCase())) {
						foundStringList.add(row);
					}
				}

				// ...
				csvReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return foundStringList;
	}

	public Boolean checkifFileExist() {
		if (csvFilename.equals("")) {
			return false;
		}
		File f = new File(csvFilename);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}
}