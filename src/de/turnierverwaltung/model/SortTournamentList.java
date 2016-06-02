package de.turnierverwaltung.model;

import java.util.Comparator;

public class SortTournamentList implements Comparator<Turnier> {

	@Override
	public int compare(Turnier arg0, Turnier arg1) {

		return arg1.getTurnierId() - arg0.getTurnierId();

	}

}
