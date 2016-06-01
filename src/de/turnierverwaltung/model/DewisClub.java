package de.turnierverwaltung.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
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
			Collections.sort(players, new SortLastName());

			spieler = new ArrayList<Spieler>();
			// Iteriert über alle Spieler
			String dwz;
			for (Player player : players) {
				int id = -1;

				String name = player.getFirstName().substring(0, 1) + ". "
						+ player.getLastName();

				String kuerzel = player.getFirstName().substring(0, 1)
						+ player.getLastName().substring(0, 1);
				try {
					dwz = new Integer(player.getDwz()).toString();
				} catch (NullPointerException e) {
					dwz = "";
				}
				int age = 2;
				// test
				Charset UTF8_CHARSET = Charset.forName("UTF-8");
				byte[] bname = name.getBytes();
				String nameUTF8 = new String(bname, UTF8_CHARSET);
				byte[] bkuerzel = kuerzel.getBytes();
				String kuerzelUTF8 = new String(bkuerzel, UTF8_CHARSET);
				// Test
				spieler.add(new Spieler(id, nameUTF8, kuerzelUTF8, dwz, age));
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
