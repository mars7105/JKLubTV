package de.turnierverwaltung.model;

import java.io.IOException;
import java.util.Comparator;

import de.fahsel.dewis.Player;

/**
 * 
 * @author mars
 *
 */
public class SortLastName implements Comparator<Player> {

	@Override
	public int compare(Player arg0, Player arg1) {
		try {
			return arg0.getLastName().compareTo(arg1.getLastName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;

	}

}
