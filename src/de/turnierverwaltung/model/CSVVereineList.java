package de.turnierverwaltung.model;

import java.util.ArrayList;

public class CSVVereineList {
	private ArrayList<CSVVereine> csvvereine;

	public CSVVereineList() {
		csvvereine = new ArrayList<CSVVereine>();
	}

	public ArrayList<CSVVereine> getCsvvereine() {
		return csvvereine;
	}

	public void setCsvvereine(ArrayList<CSVVereine> csvvereine) {
		this.csvvereine = csvvereine;
	}

}
