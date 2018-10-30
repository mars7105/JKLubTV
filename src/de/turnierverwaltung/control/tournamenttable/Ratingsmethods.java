package de.turnierverwaltung.control.tournamenttable;

import de.turnierverwaltung.model.Player;

public interface Ratingsmethods {
	public void calcRating(final Player player, final double gesamtpunkte);

	public void addOpponent(final Player player, final double ergebnis, double gesamtpunkte);

}
