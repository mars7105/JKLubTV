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
public class TurnierTabelle {
	private Turnier turnier;
	private Gruppe gruppe;
	private Spieler[] spieler;
	private Partie[] partien;
	private int spielerAnzahl;
	private int partienAnzahl;
	private String tabellenMatrix[][];
	private int zeile;
	private int spalte;
	private TurnierTabelleToHTML turnierTabelleToHTML;
	private Boolean spielfrei;

	public TurnierTabelle(Turnier turnier, Gruppe gruppe) {
		this.turnier = turnier;
		this.gruppe = gruppe;
		this.spieler = gruppe.getSpieler();
		this.partien = gruppe.getPartien();
		this.spielerAnzahl = gruppe.getSpielerAnzahl();
		this.partienAnzahl = gruppe.getPartienAnzahl();
		spielfrei = false;
		checkForSpielfrei();
		// if (spielfrei) {
		// tabellenMatrix = new String[this.spielerAnzahl +
		// 5][this.spielerAnzahl];
		// } else {
		// tabellenMatrix = new String[this.spielerAnzahl +
		// 6][this.spielerAnzahl + 1];
		// }
		if (spielfrei) {
			tabellenMatrix = new String[this.spielerAnzahl + 6][this.spielerAnzahl];
		} else {
			tabellenMatrix = new String[this.spielerAnzahl + 7][this.spielerAnzahl + 1];
		}
	}

	private void checkForSpielfrei() {
		for (int i = 0; i < spielerAnzahl; i++) {

			if (this.spieler[i].getSpielerId() == TurnierKonstanten.SPIELFREI_ID) {

				spielfrei = true;

			} else {
				spielfrei = false;
			}

		}
	}

	public void createMatrix() {
		int sp = 0;
		if (spielfrei) {
			sp = spielerAnzahl - 1;
		} else {
			sp = spielerAnzahl;
		}
		tabellenMatrix[0][0] = "Spieler";
		tabellenMatrix[1][0] = "Kürzel";
		tabellenMatrix[2][0] = "aDWZ";

		tabellenMatrix[3][0] = "nDWZ";
		for (int i = 4; i < sp + 4; i++) {
			tabellenMatrix[i][0] = spieler[i - 4].getKuerzel();
		}
		// for (int i = 3; i < sp + 3; i++) {
		// tabellenMatrix[i][0] = spieler[i - 3].getKuerzel();
		// }
		tabellenMatrix[4 + sp][0] = "Punkte";
		tabellenMatrix[5 + sp][0] = "SoBerg";
		tabellenMatrix[6 + sp][0] = "Platz";

		for (int i = 0; i < sp; i++) {
			tabellenMatrix[0][i + 1] = spieler[i].getName();
			tabellenMatrix[1][i + 1] = spieler[i].getKuerzel();
			tabellenMatrix[2][i + 1] = spieler[i].getDwz();
			tabellenMatrix[3][i + 1] = new Integer(spieler[i].getFolgeDWZ()).toString();
		}
		for (int x = 4; x < sp + 4; x++) {
			for (int y = 1; y < sp + 1; y++) {
				if (x == y + 3) {
					tabellenMatrix[x][y] = "--";
				} else {
					if (spieler[x - 4].getSpielerId() > -2 && spieler[y - 1].getSpielerId() > -2) {
						for (int i = 0; i < partienAnzahl; i++) {

							if (partien[i].getSpielerWeiss() == spieler[x - 4]
									&& partien[i].getSpielerSchwarz() == spieler[y - 1]) {
								tabellenMatrix[x][y] = partien[i].getErgebnisSchwarz();

							}
							if (partien[i].getSpielerSchwarz() == spieler[x - 4]
									&& partien[i].getSpielerWeiss() == spieler[y - 1]) {
								tabellenMatrix[x][y] = partien[i].getErgebnisWeiss();

							}
						}
					}

				}

			}
		}

	}

	public String getHTMLTable() {
		turnierTabelleToHTML = new TurnierTabelleToHTML(tabellenMatrix, turnier.getTurnierName(),
				turnier.getStartDatum(), turnier.getEndDatum(), gruppe.getGruppenName());
		return turnierTabelleToHTML.getHTMLTable();
	}

	public int getSpalte() {
		spalte = tabellenMatrix.length;
		return spalte;
	}

	public String[][] getTabellenMatrix() {
		return tabellenMatrix;
	}

	public TurnierTabelleToHTML getTurnierTabelleToHTML() {
		return turnierTabelleToHTML;
	}

	public int getZeile() {
		zeile = tabellenMatrix[0].length;
		return zeile;
	}

	public void setSpalte(int spalte) {
		this.spalte = spalte;
	}

	public void setTabellenMatrix(String[][] tabellenMatrix) {
		this.tabellenMatrix = tabellenMatrix;
	}

	public void setTurnierTabelleToHTML(TurnierTabelleToHTML turnierTabelleToHTML) {
		this.turnierTabelleToHTML = turnierTabelleToHTML;
	}

	public void setZeile(int zeile) {
		this.zeile = zeile;
	}

}
