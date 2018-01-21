package de.turnierverwaltung.model.rating;

import java.util.Comparator;

public class SortCSVVereine implements Comparator<CSVVereine> {

	@Override
	public int compare(CSVVereine arg0, CSVVereine arg1) {

		return arg0.getCsvVereinname().compareTo(arg1.getCsvVereinname());

	}

}
