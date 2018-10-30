package de.turnierverwaltung.model;

import java.util.Comparator;

/**
 * 
 * @author mars
 *
 */
public class SortName implements Comparator<Player> {

	@Override
	public int compare(Player arg0, Player arg1) {
		return arg0.getName().compareTo(arg1.getName());

	}

}
