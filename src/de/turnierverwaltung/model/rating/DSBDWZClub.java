package de.turnierverwaltung.model.rating;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.fahsel.dewis.Club;
import de.fahsel.dewis.DewisFacade;
import de.turnierverwaltung.model.SortLastName;

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
	public DSBDWZClub(final String zps) {
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
			for (final de.fahsel.dewis.Player player : players) {
				final int id = -1;

				final String forename = player.getFirstName();
				final String surname = player.getLastName();
				final String mglNumber = Integer.toString(player.getClubMembershipByZps(zps).getZps());
				final String kuerzel = player.getFirstName().substring(0, 1) + player.getLastName().substring(0, 1);

				int elo = -1;
				try {
					elo = player.getFideElo();
				} catch (final NullPointerException e) {
					elo = -1;
				}
				int fideid = -1;
				try {
					fideid = player.getFideId();
				} catch (final NullPointerException e) {
					fideid = -1;
				}
				int dwzIndex = -1;
				try {
					dwzIndex = player.getDwzIndex();
				} catch (final NullPointerException e) {
					dwzIndex = -1;
				}
				try {
					dwz = new Integer(player.getDwz()).toString();
				} catch (final NullPointerException e) {
					dwz = "";
				}

				final int age = 2;
				// test
				final Charset UTF8_CHARSET = Charset.forName("UTF-8");
				final byte[] bforename = forename.getBytes();
				final String forenameUTF8 = new String(bforename, UTF8_CHARSET);
				final byte[] bsurname = surname.getBytes();
				final String surenameUTF8 = new String(bsurname, UTF8_CHARSET);
				final byte[] bkuerzel = kuerzel.getBytes();
				final String kuerzelUTF8 = new String(bkuerzel, UTF8_CHARSET);

				spieler.add(new de.turnierverwaltung.model.Player(id, forenameUTF8, surenameUTF8, kuerzelUTF8, dwz,
						dwzIndex, age, zps, mglNumber, elo, fideid));
			}
			if (spieler.isEmpty()) {
				spieler = null;
			}
			return spieler;
		} catch (final IOException e) {
			return null;
		}

	}

	public String getZps() {
		return zps;
	}

	public void setZps(final String zps) {
		this.zps = zps;
	}

}
