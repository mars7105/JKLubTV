package de.turnierverwaltung.model.rating;

import de.dwzberechnung.model.OpponentModel;

public class ELOOpponent extends OpponentModel {

	private int elo;

	public ELOOpponent(final int dwz, final double ergebnis) {
		super(dwz, ergebnis);
		elo = dwz;

	}

	public int getElo() {
		return elo;
	}

	public void setElo(final int elo) {
		this.elo = elo;
	}

}
