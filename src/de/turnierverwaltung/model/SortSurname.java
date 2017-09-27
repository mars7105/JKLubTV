package de.turnierverwaltung.model;

import java.util.Comparator;

public class SortSurname implements Comparator<Player> {

	@Override
	public int compare(Player arg0, Player arg1) {
		return arg0.getSurname().compareTo(arg1.getSurname());
	}

}
