package de.turnierverwaltung.model.rating;

import java.util.ArrayList;

public class CSVVerbaendeList {
	private ArrayList<CSVVerbaende> csvverbaende;

	public CSVVerbaendeList() {

		csvverbaende = new ArrayList<CSVVerbaende>();
	}

	public ArrayList<CSVVerbaende> getCsvverbaende() {
		return csvverbaende;
	}

	public void setCsvverbaende(ArrayList<CSVVerbaende> csvverbaende) {
		this.csvverbaende = csvverbaende;
	}

}
