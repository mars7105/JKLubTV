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
public class TerminTabelle {

	private Turnier turnier;
	private Gruppe gruppe;
	private Spieler[] spieler;
	private Partie[] partien;
	private int spielerAnzahl;
	private int partienAnzahl;
	private int runden;
	private int spaltenAnzahl;
	private int zeilenAnzahl;
	private String tabellenMatrix[][];
	private TerminTabelleToHTML terminTabelleToHTML;

	public TerminTabelle(Turnier turnier, Gruppe gruppe) {
		this.turnier = turnier;
		this.gruppe = gruppe;
		this.spieler = this.gruppe.getSpieler();
		this.spielerAnzahl = this.gruppe.getSpielerAnzahl();
		this.partien = gruppe.getPartien();
		calcRunden();
		calcAnzahlSpaltenZeilen();
		tabellenMatrix = new String[5][zeilenAnzahl];
		createTerminTabelle();

	}

	private void calcAnzahlSpaltenZeilen() {
		spaltenAnzahl = 5;
		zeilenAnzahl = partienAnzahl + 1;
	}

	private void calcRunden() {
		if (spielerAnzahl % 2 == 0) {
			runden = spielerAnzahl - 1;
		} else {
			runden = spielerAnzahl;
		}
		partienAnzahl = (spielerAnzahl * (spielerAnzahl - 1) / 2);

	}

	public void createMatrix() {
		// TODO Auto-generated method stub

	}

	public void createTerminTabelle() {
		tabellenMatrix[0][0] = "Runde";
		tabellenMatrix[1][0] = "Weiss";
		tabellenMatrix[2][0] = "Schwarz";
		tabellenMatrix[3][0] = "Ergebnis";
		tabellenMatrix[4][0] = "Termin";
		int index = 0;
		for (int i = 0; i < spielerAnzahl - 1; i++) {
			for (int y = i + 1; y < spielerAnzahl; y++) {

				tabellenMatrix[0][index + 1] = Integer.toString(partien[index]
						.getRunde());
				tabellenMatrix[1][index + 1] = partien[index].getSpielerWeiss()
						.getName();
				tabellenMatrix[2][index + 1] = partien[index]
						.getSpielerSchwarz().getName();
				tabellenMatrix[3][index + 1] = getErgebnisToString(partien[index]
						.getErgebnis());
				tabellenMatrix[4][index + 1] = partien[index].getSpielDatum();
				if (tabellenMatrix[4][index + 1] == null) {
					tabellenMatrix[4][index + 1] = "";
				}
				index++;
			}
		}
	}

	private String getErgebnisToString(int ergebnis) {
		String ergebnisString = TurnierKonstanten.KEIN_ERGEBNIS;
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_GEWINN_WEISS) {
			ergebnisString = TurnierKonstanten.PARTIE_GEWINN_WEISS;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_GEWINN_SCHWARZ) {
			ergebnisString = TurnierKonstanten.PARTIE_GEWINN_SCHWARZ;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_REMIS) {
			ergebnisString = TurnierKonstanten.PARTIE_REMIS;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS) {
			ergebnisString = TurnierKonstanten.PARTIE_GEWINN_KAMPFLOS_WEISS;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
			ergebnisString = TurnierKonstanten.PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
		}
		if (ergebnis == TurnierKonstanten.MYSQL_KEIN_ERGEBNIS) {
			ergebnisString = TurnierKonstanten.KEIN_ERGEBNIS;
		}
		return ergebnisString;
	}

	public String getHTMLTable() {
		terminTabelleToHTML = new TerminTabelleToHTML(tabellenMatrix,
				turnier.getTurnierName(), turnier.getStartDatum(),
				turnier.getEndDatum(), gruppe.getGruppenName());
		return terminTabelleToHTML.getHTMLTable();
	}

	public int getSpaltenAnzahl() {
		return spaltenAnzahl;
	}

	public String[][] getTabellenMatrix() {
		return tabellenMatrix;
	}

	public TerminTabelleToHTML getTerminTabelleToHTML() {
		return terminTabelleToHTML;
	}

	public int getZeilenAnzahl() {
		return zeilenAnzahl;
	}

	public void setSpaltenAnzahl(int spaltenAnzahl) {
		this.spaltenAnzahl = spaltenAnzahl;
	}

	public void setTabellenMatrix(String[][] tabellenMatrix) {
		this.tabellenMatrix = tabellenMatrix;
	}

	public void setTerminTabelleToHTML(TerminTabelleToHTML terminTabelleToHTML) {
		this.terminTabelleToHTML = terminTabelleToHTML;
	}

	public void setZeilenAnzahl(int zeilenAnzahl) {
		this.zeilenAnzahl = zeilenAnzahl;
	}

}
