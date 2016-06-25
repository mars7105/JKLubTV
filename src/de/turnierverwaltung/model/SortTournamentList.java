package de.turnierverwaltung.model;

import java.util.Comparator;

public class SortTournamentList implements Comparator<Tournament> {

	@Override
	public int compare(Tournament arg0, Tournament arg1) {

		return arg1.getTurnierId() - arg0.getTurnierId();

	}

}
