package de.turnierverwaltung.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.fahsel.dewis.Club;
import de.fahsel.dewis.DewisFacade;

/**
 * Dieses Beispiel lädt einen Verein anhand seiner zps und gibt Informationen zu
 * seinen Spielern und deren Turnieren und Mitgliedschaften auf der Konsole aus.
 * 
 * @author Peter Fahsel
 *
 */
public class DSBDWZClub {
	private DewisFacade dewis;
	private Club club;
	private List<de.fahsel.dewis.Player> players;
	private ArrayList<de.turnierverwaltung.model.Player> spieler;
	private String zps;

	/**
	 * 
	 * @param zps
	 */
	public DSBDWZClub(String zps) {
		this.zps = zps;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<de.turnierverwaltung.model.Player> getSpieler() {
		try {
			// Erzeugt dei DewisFacade über die auf die API des Schachbundes
			// zugegriffen werden kann
			dewis = new DewisFacade();

			// Lädt den Verein mit der zps 62501 (SV Dinslaken 1923 e.V.)
			club = dewis.getClub(zps);

			// Lädt die Spieler des Vereins
			players = club.getPlayers();
			Collections.sort(players, new SortLastName());

			spieler = new ArrayList<de.turnierverwaltung.model.Player>();
			// Iteriert über alle Spieler
			String dwz;
			for (de.fahsel.dewis.Player player : players) {
				int id = -1;

				String forename = player.getFirstName();
				String surname = player.getLastName();
				String zpsNumber = Integer.toString(player.getClubMembershipByZps(zps).getZps());
				String kuerzel = player.getFirstName().substring(0, 1) + player.getLastName().substring(0, 1);
				int dwzIndex = -1;
				try {
					dwzIndex = player.getDwzIndex();
					// System.out.println(dwzIndex);
				} catch (NullPointerException e) {
					dwzIndex = -1;
				}
				try {
					dwz = new Integer(player.getDwz()).toString();
				} catch (NullPointerException e) {
					dwz = "";
				}

				int age = 2;
				// test
				Charset UTF8_CHARSET = Charset.forName("UTF-8");
				byte[] bforename = forename.getBytes();
				String forenameUTF8 = new String(bforename, UTF8_CHARSET);
				byte[] bsurname = surname.getBytes();
				String surenameUTF8 = new String(bsurname, UTF8_CHARSET);
				byte[] bkuerzel = kuerzel.getBytes();
				String kuerzelUTF8 = new String(bkuerzel, UTF8_CHARSET);
				// Test
				spieler.add(new de.turnierverwaltung.model.Player(id, forenameUTF8, surenameUTF8, kuerzelUTF8, dwz,
						dwzIndex, age, zps, zpsNumber));
			}
			if (spieler.isEmpty()) {
				spieler = null;
			}
			return spieler;
		} catch (IOException e) {
			return null;
		}

	}

	public String getZps() {
		return zps;
	}

	public void setZps(String zps) {
		this.zps = zps;
	}

}
