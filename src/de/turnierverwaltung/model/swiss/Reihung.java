package de.turnierverwaltung.model.swiss;

import java.util.ArrayList;
import java.util.Comparator;

import de.turnierverwaltung.model.FideNorm;

public class Reihung {
	/**
	 * Für die Auslosung werden die Spieler folgend gereiht: a. Punkte b.
	 * Wertungszahl c. FIDE-Titel (GM IM WGM WIM FM WFM CM WCM– ohne Titel) d.
	 * alphabetisch (es sei denn, es wurde im Voraus angekündigt, dass andere
	 * Kriterien verwendet werden). Die erstellte Reihung vor der ersten Runde
	 * (wenn alle Punkte null sind) wird zur Festsetzung der Paarungsnummern
	 * verwendet: der bestgereihte Spieler erhält #1, usw
	 */
	private ArrayList<SwissPlayer> swissPlayers;

	public Reihung() {
		super();
		swissPlayers = new ArrayList<SwissPlayer>();
	}

	public void addPlayer(SwissPlayer player) {
		swissPlayers.add(player);

	}

	public void sortSwissPlayers() {
		sortSwissPlayersToAlphabetical();
		sortSwissPlayersToFideTitle();
		sortSwissPlayersToTWZ();
		sortSwissPlayersToPoints();
	}

	private void sortSwissPlayersToPoints() {
		swissPlayers.sort(new Comparator<SwissPlayer>() {
			@Override
			public int compare(SwissPlayer o1, SwissPlayer o2) {
				return (int) (10 * (o1.getPunkte() - o2.getPunkte()));
			}
		});

	}

	private void sortSwissPlayersToTWZ() {
		swissPlayers.sort(new Comparator<SwissPlayer>() {
			@Override
			public int compare(SwissPlayer o1, SwissPlayer o2) {
				return o1.getDWZ() - o2.getDWZ();
			}
		});

	}

	private void sortSwissPlayersToFideTitle() {
		swissPlayers.sort(new Comparator<SwissPlayer>() {
			@Override
			public int compare(SwissPlayer o1, SwissPlayer o2) {
				int fideSort1 = 0;
				int fideSort2 = 0;
				FideNorm fideNorm = new FideNorm();
				String[] fideNormArray = fideNorm.getFideNorm();
				Boolean chechFideNorm1 = false;
				Boolean chechFideNorm2 = false;
				for (int i = 0; i < 9; i++) {
					if (o1.getFideTitle().equals(fideNormArray[i])) {
						fideSort1 = i;
						chechFideNorm1 = true;
					}
					if (o2.getFideTitle().equals(fideNormArray[i])) {
						fideSort2 = i;
						chechFideNorm2 = true;
					}
				}
				if (chechFideNorm1 == true && chechFideNorm2 == true) {

				} else {
					if (o1.getFideTitle() == null) {
						fideSort1 = 0;

					}
					if (o2.getFideTitle() == null) {
						fideSort2 = 0;

					}

				}
				return fideSort1 - fideSort2;
			}

		});

	}

	private void sortSwissPlayersToAlphabetical() {
		swissPlayers.sort(new Comparator<SwissPlayer>() {
			@Override
			public int compare(SwissPlayer o1, SwissPlayer o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

	}

	public ArrayList<SwissPlayer> getSwissPlayers() {
		return swissPlayers;
	}

	public void setSwissPlayers(ArrayList<SwissPlayer> swissPlayers) {
		this.swissPlayers = swissPlayers;
	}

}
