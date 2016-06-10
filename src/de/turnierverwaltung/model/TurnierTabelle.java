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
		if (spielfrei) {
			tabellenMatrix = new String[this.spielerAnzahl + 5][this.spielerAnzahl];
		} else {
			tabellenMatrix = new String[this.spielerAnzahl + 6][this.spielerAnzahl + 1];
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
		tabellenMatrix[0][0] = TurnierKonstanten.TABLE_COLUMN_PLAYER; //$NON-NLS-1$
		tabellenMatrix[1][0] = TurnierKonstanten.TABLE_COLUMN_OLD_DWZ;

		tabellenMatrix[2][0] = TurnierKonstanten.TABLE_COLUMN_NEW_DWZ; //$NON-NLS-1$
		for (int i = 3; i < sp + 3; i++) {
			if (spieler[i - 3].getKuerzel().length() >= 2) {
				tabellenMatrix[i][0] = spieler[i - 3].getKuerzel().substring(0,
						1)
						+ "<br />" + spieler[i - 3].getKuerzel().substring(1); //$NON-NLS-1$
			} else {
				tabellenMatrix[i][0] = spieler[i - 3].getKuerzel();
			}

		}

		tabellenMatrix[3 + sp][0] = TurnierKonstanten.TABLE_COLUMN_POINTS;
		tabellenMatrix[4 + sp][0] = TurnierKonstanten.TABLE_COLUMN_SONNEBORNBERGER;
		tabellenMatrix[5 + sp][0] = TurnierKonstanten.TABLE_COLUMN_RANKING;

		for (int i = 0; i < sp; i++) {
			tabellenMatrix[0][i + 1] = spieler[i].getName();
			if (spieler[i].getDwz() != TurnierKonstanten.KEINE_DWZ) {
				tabellenMatrix[1][i + 1] = spieler[i].getDwz();
			} else {
				tabellenMatrix[1][i + 1] = ""; //$NON-NLS-1$
			}
			if (spieler[i].getFolgeDWZ() > 0) {
				tabellenMatrix[2][i + 1] = new Integer(spieler[i].getFolgeDWZ())
						.toString();
			} else {
				tabellenMatrix[3][i + 1] = ""; //$NON-NLS-1$
			}
		}
		for (int x = 3; x < sp + 3; x++) {
			for (int y = 1; y < sp + 1; y++) {
				if (x == y + 2) {
					tabellenMatrix[x][y] = "--"; //$NON-NLS-1$
				} else {
					if (spieler[x - 3].getSpielerId() > -2
							&& spieler[y - 1].getSpielerId() > -2) {
						for (int i = 0; i < partienAnzahl; i++) {

							if (partien[i].getSpielerWeiss() == spieler[x - 3]
									&& partien[i].getSpielerSchwarz() == spieler[y - 1]) {
								tabellenMatrix[x][y] = partien[i]
										.getErgebnisSchwarz();

							}
							if (partien[i].getSpielerSchwarz() == spieler[x - 3]
									&& partien[i].getSpielerWeiss() == spieler[y - 1]) {
								tabellenMatrix[x][y] = partien[i]
										.getErgebnisWeiss();

							}
						}
					}

				}

			}
		}

	}

	public void removeDWZColumn() {
		int v = 0;
		String[][] temp = new String[tabellenMatrix.length - 1][tabellenMatrix[0].length];
		for (int x = 0; x < tabellenMatrix.length - 1; x++) {
			for (int y = 0; y < tabellenMatrix[0].length; y++) {
				if (tabellenMatrix[x][0]
						.equals(TurnierKonstanten.TABLE_COLUMN_OLD_DWZ)) {
					v = 1;

				}
				temp[x][y] = tabellenMatrix[x + v][y];

			}
		}
		tabellenMatrix = temp;
	}

	public void removeFolgeDWZColumn() {
		int v = 0;
		String[][] temp = new String[tabellenMatrix.length - 1][tabellenMatrix[0].length];
		for (int x = 0; x < tabellenMatrix.length - 1; x++) {
			for (int y = 0; y < tabellenMatrix[0].length; y++) {
				if (tabellenMatrix[x][0]
						.equals(TurnierKonstanten.TABLE_COLUMN_NEW_DWZ)) {
					v = 1;

				}
				temp[x][y] = tabellenMatrix[x + v][y];

			}
		}
		tabellenMatrix = temp;
	}

	public String getHTMLTable(Boolean ohneHeaderundFooter) {
		turnierTabelleToHTML = new TurnierTabelleToHTML(tabellenMatrix,
				turnier, gruppe.getGruppenName());
		return turnierTabelleToHTML.getHTMLTable(ohneHeaderundFooter);
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

	public void setTurnierTabelleToHTML(
			TurnierTabelleToHTML turnierTabelleToHTML) {
		this.turnierTabelleToHTML = turnierTabelleToHTML;
	}

	public void setZeile(int zeile) {
		this.zeile = zeile;
	}

}
