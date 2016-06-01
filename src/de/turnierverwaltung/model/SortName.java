package de.turnierverwaltung.model;

import java.util.Comparator;

public class SortName implements Comparator<Spieler> {

	@Override
	public int compare(Spieler arg0, Spieler arg1) {
		return arg0.getName().compareTo(arg1.getName());

	}

}
