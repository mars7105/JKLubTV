package de.turnierverwaltung.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.fahsel.dewis.Club;
import de.fahsel.dewis.DewisFacade;
import de.fahsel.dewis.Player;

/**
 * Dieses Beispiel lädt einen Verein anhand seiner zps und gibt Informationen zu
 * seinen Spielern und deren Turnieren und Mitgliedschaften auf der Konsole aus.
 * 
 * @author Peter Fahsel
 *
 */
public class DewisClub {
	DewisFacade dewis;
	Club club;
	List<Player> players;
	ArrayList<Spieler> spieler;
	String zps;

	public DewisClub(String zps) {
		this.zps = zps;
	}

	public ArrayList<Spieler> getSpieler() {
		try {
			// Erzeugt dei DewisFacade über die auf die API des Schachbundes
			// zugegriffen werden kann
			dewis = new DewisFacade();

			// Lädt den Verein mit der zps 62501 (SV Dinslaken 1923 e.V.)
			club = dewis.getClub(zps);

			// Lädt die Spieler des Vereins
			players = club.getPlayers();

			spieler = new ArrayList<Spieler>();
			// Iteriert über alle Spieler
			String dwz;
			for (Player player : players) {
				int id = -1;

				String name = player.getFirstName().charAt(0) + ". " + player.getLastName();
				String kuerzel = player.getFirstName().charAt(0) + "" + player.getLastName().charAt(0);
				try {
				 dwz = new Integer(player.getDwz()).toString();
				} catch (NullPointerException e) {
					 dwz = "";
				}
				int age = 2;
				spieler.add(new Spieler(id, name, kuerzel, dwz, age));
			}

		} catch (IOException e) {
			spieler = null;
		}
		if (spieler.isEmpty()) {
			spieler = null;
		}
		return spieler;
	}

}
