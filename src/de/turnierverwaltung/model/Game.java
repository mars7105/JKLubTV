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
	private Player spielerWeiss;
	private Player spielerSchwarz;
	private String ergebnisWeiss;
	private String ergebnisSchwarz;
	private int ergebnis;
	private String status;
	private int runde;
	private int partieId;
	private int sort;
	public static Boolean sortMeetingTable;

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
	public Game(int idPartie, String spielDatum, int ergebnis, int runde, Player spielerWeiss, Player spielerSchwarz) {
		this.partieId = idPartie;
		this.spielDatum = spielDatum;
		this.spielerWeiss = spielerWeiss;
		this.spielerSchwarz = spielerSchwarz;
		this.runde = runde;
		this.ergebnis = ergebnis;
		makeErgebnisse();
	}

	@Override
	public int compareTo(Object o) {
		int compareQuantity = ((Game) o).getSortToDate();

		// ascending order
		if (Game.sortMeetingTable) {
			return getSortToDate() - compareQuantity;
		} else {
			return compareQuantity + getSort();
		}

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
		if (this.spielerWeiss.getSpielerId() == TournamentConstants.SPIELFREI_ID
				|| this.spielerSchwarz.getSpielerId() == TournamentConstants.SPIELFREI_ID) {
			sort = this.runde * 10;
		} else {
			sort = this.runde * 11;
		}
		return sort;

	}

	public int getSortToDate() {
		if (this.spielerWeiss.getSpielerId() == TournamentConstants.SPIELFREI_ID
				|| this.spielerSchwarz.getSpielerId() == TournamentConstants.SPIELFREI_ID) {
			sort = this.runde * 10;
		} else {
			sort = this.runde * 11;
		}
		int[] dateInt = new int[3];

		int index = 0;
		if (spielDatum != null) {
			if (!spielDatum.equals("")) {
				for (String zahlenWerte : spielDatum.split("\\.")) { //$NON-NLS-1$

					dateInt[index] = Integer.parseInt(zahlenWerte);

					index++;

				}
				dateInt[1] = dateInt[1] - 1;
				sort += (dateInt[0] * 100 + dateInt[1] * 10000 + dateInt[2] * 100);
			}
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
			this.ergebnisWeiss = TournamentConstants.GEWINN;
			this.ergebnisSchwarz = TournamentConstants.VERLUST;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_SCHWARZ) {
			this.ergebnisWeiss = TournamentConstants.VERLUST;
			this.ergebnisSchwarz = TournamentConstants.GEWINN;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS) {
			this.ergebnisWeiss = TournamentConstants.GEWINN_KAMPFLOS;
			this.ergebnisSchwarz = TournamentConstants.VERLUST_KAMPFLOS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
			this.ergebnisWeiss = TournamentConstants.VERLUST_KAMPFLOS;
			this.ergebnisSchwarz = TournamentConstants.GEWINN_KAMPFLOS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_REMIS) {
			this.ergebnisWeiss = TournamentConstants.REMIS;
			this.ergebnisSchwarz = TournamentConstants.REMIS;
		}
		if (ergebnis == TournamentConstants.MYSQL_KEIN_ERGEBNIS) {
			this.ergebnisWeiss = TournamentConstants.KEIN_ERGEBNIS;
			this.ergebnisSchwarz = TournamentConstants.KEIN_ERGEBNIS;
		}
	}

	public void setErgebnis(int ergebnis) {
		this.ergebnis = ergebnis;
		makeErgebnisse();

	}

	public void setErgebnisSchwarz(String ergebnisSchwarz) {
		this.ergebnisSchwarz = ergebnisSchwarz;
	}

	public void setErgebnisWeiss(String ergebnisWeiss) {
		this.ergebnisWeiss = ergebnisWeiss;
	}

	public void setPartieId(int partieId) {
		this.partieId = partieId;
	}

	public void setRunde(int runde) {
		this.runde = runde;
	}

	public void setSpielDatum(String spielDatum) {
		this.spielDatum = spielDatum;
	}

	public void setSpielerSchwarz(Player spielerSchwarz) {
		this.spielerSchwarz = spielerSchwarz;
	}

	public void setSpielerWeiss(Player spielerWeiss) {
		this.spielerWeiss = spielerWeiss;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
