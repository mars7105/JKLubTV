package de.turnierverwaltung.model.swiss;

import java.util.ArrayList;
import java.util.Comparator;

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

	public void sortSwissPlayersToPoints() {
		swissPlayers.sort(new Comparator<SwissPlayer>() {
			@Override
			public int compare(SwissPlayer o1, SwissPlayer o2) {
				return (int) (10 * (o1.getPunkte() - o2.getPunkte()));
			}
		});

	}

	public void sortSwissPlayersToTWZ() {
		swissPlayers.sort(new Comparator<SwissPlayer>() {
			@Override
			public int compare(SwissPlayer o1, SwissPlayer o2) {
				return (int) (10 * (o1.getDWZ() - o2.getDWZ()));
			}
		});

	}

	public void sortSwissPlayersToFideTitle() {
		swissPlayers.sort(new Comparator<SwissPlayer>() {
			@Override
			public int compare(SwissPlayer o1, SwissPlayer o2) {
				int fideSort1 = 0;
				int fideSort2 = 0;
				String[] fideNormArray = new String[9];
				fideNormArray[0] = "";
				fideNormArray[1] = "WCM";
				fideNormArray[2] = "CM";
				fideNormArray[3] = "WFM";
				fideNormArray[4] = "FM";
				fideNormArray[5] = "WIM";
				fideNormArray[6] = "WGM";
				fideNormArray[7] = "IM";
				fideNormArray[8] = "GM";
				for (int i = 0; i < 9; i++) {
					if (o1.getFideTitle().equals(fideNormArray[i])) {
						fideSort1 = i;
					}
					if (o2.getFideTitle().equals(fideNormArray[i])) {
						fideSort2 = i;
					}
				}

				return fideSort1 - fideSort2;
			}
		});

	}
}
