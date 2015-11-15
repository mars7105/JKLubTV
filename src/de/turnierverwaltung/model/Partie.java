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
public class Partie implements Comparable<Object> {

	private String spielDatum;
	private Spieler spielerWeiss;
	private Spieler spielerSchwarz;
	private String ergebnisWeiss;
	private String ergebnisSchwarz;
	private int ergebnis;
	private String status;
	private int runde;
	private int partieId;
	private int sort;

	public Partie() {
		partieId = -1;
	}

	public Partie(int idPartie, String spielDatum, int ergebnis, int runde, Spieler spielerWeiss,
			Spieler spielerSchwarz) {
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
		int compareQuantity = ((Partie) o).getSort();

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
		if (this.spielerWeiss.getSpielerId() == TurnierKonstanten.SPIELFREI_ID
				|| this.spielerSchwarz.getSpielerId() == TurnierKonstanten.SPIELFREI_ID) {
			sort = this.runde * 10;
		} else {
			sort = this.runde * 11;
		}
		return sort;

	}

	public String getSpielDatum() {
		return spielDatum;
	}

	public Spieler getSpielerSchwarz() {
		return spielerSchwarz;
	}

	public Spieler getSpielerWeiss() {
		return spielerWeiss;
	}

	public String getStatus() {
		return status;
	}

	private void makeErgebnisse() {
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_GEWINN_WEISS) {
			this.ergebnisWeiss = TurnierKonstanten.GEWINN;
			this.ergebnisSchwarz = TurnierKonstanten.VERLUST;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_GEWINN_SCHWARZ) {
			this.ergebnisWeiss = TurnierKonstanten.VERLUST;
			this.ergebnisSchwarz = TurnierKonstanten.GEWINN;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS) {
			this.ergebnisWeiss = TurnierKonstanten.GEWINN_KAMPFLOS;
			this.ergebnisSchwarz = TurnierKonstanten.VERLUST_KAMPFLOS;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
			this.ergebnisWeiss = TurnierKonstanten.VERLUST_KAMPFLOS;
			this.ergebnisSchwarz = TurnierKonstanten.GEWINN_KAMPFLOS;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_REMIS) {
			this.ergebnisWeiss = TurnierKonstanten.REMIS;
			this.ergebnisSchwarz = TurnierKonstanten.REMIS;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_KEIN_ERGEBNIS) {
			this.ergebnisWeiss = TurnierKonstanten.KEIN_ERGEBNIS;
			this.ergebnisSchwarz = TurnierKonstanten.KEIN_ERGEBNIS;
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

	public void setSpielerSchwarz(Spieler spielerSchwarz) {
		this.spielerSchwarz = spielerSchwarz;
	}

	public void setSpielerWeiss(Spieler spielerWeiss) {
		this.spielerWeiss = spielerWeiss;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
