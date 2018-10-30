package de.turnierverwaltung.model;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.
/**
 * Class Partie
 */
public class Game implements Comparable<Object> {

	private String spielDatum;
	private EventDate eventDate;
	private Player spielerWeiss;
	private Player spielerSchwarz;
	private String ergebnisWeiss;
	private String ergebnisSchwarz;
	private int ergebnis;
	private String status;
	private int runde;
	private int partieId;
	private int sort;

	public Game() {
		partieId = -1;
	}

	/**
	 *
	 * @param idPartie
	 * @param spielDatum
	 * @param ergebnis
	 * @param runde
	 * @param spielerWeiss
	 * @param spielerSchwarz
	 */
	public Game(final int idPartie, final String spielDatum, final int ergebnis, final int runde,
			final Player spielerWeiss, final Player spielerSchwarz) {
		partieId = idPartie;
		eventDate = new EventDate(spielDatum);
		this.spielDatum = eventDate.getDateString();
		this.spielerWeiss = spielerWeiss;
		this.spielerSchwarz = spielerSchwarz;
		this.runde = runde;
		this.ergebnis = ergebnis;
		ergebnisWeiss = "";
		ergebnisSchwarz = "";
		status = "";
		sort = 0;
		makeErgebnisse();
	}

	@Override
	public int compareTo(final Object o) {
		final int compareQuantity = ((Game) o).getSort();

		// ascending order
		return getSort() - compareQuantity;

		// descending order
		// return compareQuantity - this.quantity;
	}

	public int getErgebnis() {
		return ergebnis;
	}

	public String getErgebnisSchwarz() {
		return ergebnisSchwarz;
	}

	public String getErgebnisWeiss() {
		return ergebnisWeiss;
	}

	public int getPartieId() {
		return partieId;
	}

	public int getRunde() {
		return runde;
	}

	public int getSort() {
		if (spielerWeiss.getSpielerId() <= TournamentConstants.SPIELFREI_ID
				|| spielerSchwarz.getSpielerId() <= TournamentConstants.SPIELFREI_ID) {
			sort = runde * 10;
		} else {
			sort = runde * 11;
		}
		return sort;

	}

	public String getSpielDatum() {
		return spielDatum;
	}

	public Player getSpielerSchwarz() {
		return spielerSchwarz;
	}

	public Player getSpielerWeiss() {
		return spielerWeiss;
	}

	public String getStatus() {
		return status;
	}

	/**
	 *
	 */
	private void makeErgebnisse() {
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_WEISS) {
			ergebnisWeiss = TournamentConstants.GEWINN;
			ergebnisSchwarz = TournamentConstants.VERLUST;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_SCHWARZ) {
			ergebnisWeiss = TournamentConstants.VERLUST;
			ergebnisSchwarz = TournamentConstants.GEWINN;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS) {
			ergebnisWeiss = TournamentConstants.GEWINN_KAMPFLOS;
			ergebnisSchwarz = TournamentConstants.VERLUST_KAMPFLOS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
			ergebnisWeiss = TournamentConstants.VERLUST_KAMPFLOS;
			ergebnisSchwarz = TournamentConstants.GEWINN_KAMPFLOS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_VERLUST_KAMPFLOS_BEIDE) {
			ergebnisWeiss = TournamentConstants.VERLUST_KAMPFLOS;
			ergebnisSchwarz = TournamentConstants.VERLUST_KAMPFLOS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_REMIS) {
			ergebnisWeiss = TournamentConstants.REMIS;
			ergebnisSchwarz = TournamentConstants.REMIS;
		}
		if (ergebnis == TournamentConstants.MYSQL_KEIN_ERGEBNIS) {
			ergebnisWeiss = TournamentConstants.KEIN_ERGEBNIS;
			ergebnisSchwarz = TournamentConstants.KEIN_ERGEBNIS;
		}
	}

	public void setErgebnis(final int ergebnis) {
		this.ergebnis = ergebnis;
		makeErgebnisse();

	}

	public void setErgebnisSchwarz(final String ergebnisSchwarz) {
		this.ergebnisSchwarz = ergebnisSchwarz;
	}

	public void setErgebnisWeiss(final String ergebnisWeiss) {
		this.ergebnisWeiss = ergebnisWeiss;
	}

	public void setPartieId(final int partieId) {
		this.partieId = partieId;
	}

	public void setRunde(final int runde) {
		this.runde = runde;
	}

	public void setSpielDatum(final String spielDatum) {
		eventDate = new EventDate(spielDatum);
		this.spielDatum = eventDate.getDateString();
	}

	public void setSpielerSchwarz(final Player spielerSchwarz) {
		this.spielerSchwarz = spielerSchwarz;
	}

	public void setSpielerWeiss(final Player spielerWeiss) {
		this.spielerWeiss = spielerWeiss;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
}
