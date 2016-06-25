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
public class CrossTable {
	private Tournament turnier;
	private Group gruppe;
	private Player[] spieler;
	private Game[] partien;
	private int spielerAnzahl;
	private int partienAnzahl;
	private String tabellenMatrix[][];
	private int zeile;
	private int spalte;
	private CrossTableToHTML turnierTabelleToHTML;
	private String playerColumnName;
	private String oldDWZColumnName;
	private String newDWZColumnName;
	private String poinsColumnName;
	private String sbbColumnName;
	private String rankingColumnName;
	private String infoString;

	public CrossTable(Tournament turnier, Group gruppe) {
		this.turnier = turnier;
		this.gruppe = gruppe;
		this.spieler = gruppe.getSpieler();
		this.partien = gruppe.getPartien();
		this.spielerAnzahl = gruppe.getSpielerAnzahl();
		this.partienAnzahl = gruppe.getPartienAnzahl();

	}

	private Boolean checkForSpielfrei() {
		for (int i = 0; i < spielerAnzahl; i++) {

			if (this.spieler[i].getSpielerId() == TournamentConstants.SPIELFREI_ID) {
				return true;
			}

		}
		return false;
	}

	public void createMatrix(String playerColumnName, String oldDWZColumnName, String newDWZColumnName,
			String poinsColumnName, String sbbColumnName, String rankingColumnName, Boolean ohneDWZ,
			Boolean ohneFolgeDWZ) {
		this.playerColumnName = playerColumnName;
		this.oldDWZColumnName = oldDWZColumnName;
		this.newDWZColumnName = newDWZColumnName;
		this.poinsColumnName = poinsColumnName;
		this.sbbColumnName = sbbColumnName;
		this.rankingColumnName = rankingColumnName;

		String ohneDWZString = oldDWZColumnName.replaceAll("<br />", "") + " = "
				+ Messages.getString("TurnierTabelleToHTML.1");
		String ohneFolgeDWZString = newDWZColumnName.replaceAll("<br />", "") + " = "
				+ Messages.getString("TurnierTabelleToHTML.2");
		int abstand = 0;
		if (ohneDWZ == true) {
			abstand++;
			ohneDWZString = "";
		}
		if (ohneFolgeDWZ == true) {
			abstand++;
			ohneFolgeDWZString = "";
		}
		this.infoString = rankingColumnName + " = " + Messages.getString("TurnierTabelleToHTML.0") + ohneDWZString
				+ ohneFolgeDWZString + poinsColumnName + " = " + Messages.getString("TurnierTabelleToHTML.3")
				+ sbbColumnName + " = " //$NON-NLS-1$
				+ Messages.getString("TurnierTabelleToHTML.4");
		int sp = 0;
		if (checkForSpielfrei() == true) {
			tabellenMatrix = new String[this.spielerAnzahl + 5 - abstand][this.spielerAnzahl];
			sp = spielerAnzahl - 1;
		} else {
			tabellenMatrix = new String[this.spielerAnzahl + 6 - abstand][this.spielerAnzahl + 1];
			sp = spielerAnzahl;
		}

		tabellenMatrix[0][0] = playerColumnName; // $NON-NLS-1$
		if (ohneDWZ == false && ohneFolgeDWZ == false) {
			tabellenMatrix[1][0] = oldDWZColumnName;
			tabellenMatrix[2][0] = newDWZColumnName; // $NON-NLS-1$

		}
		if (ohneDWZ == false && ohneFolgeDWZ == true) {
			tabellenMatrix[1][0] = oldDWZColumnName;
		}

		int dwzAbstand = 3 - abstand;
		for (int i = dwzAbstand; i < (sp + dwzAbstand); i++) {
			if (spieler[i - dwzAbstand].getKuerzel().length() >= 2) {
				tabellenMatrix[i][0] = spieler[i - dwzAbstand].getKuerzel().substring(0, 1) + "<br />" //$NON-NLS-1$
						+ spieler[i - dwzAbstand].getKuerzel().substring(1);
			} else {
				tabellenMatrix[i][0] = spieler[i - dwzAbstand].getKuerzel();
			}

		}

		tabellenMatrix[3 + sp - abstand][0] = poinsColumnName;
		tabellenMatrix[4 + sp - abstand][0] = sbbColumnName;
		tabellenMatrix[5 + sp - abstand][0] = rankingColumnName;
		for (int i = 0; i < sp; i++) {
			tabellenMatrix[0][i + 1] = spieler[i].getName();
			if (ohneDWZ == false) {
				if (spieler[i].getDwz() != TournamentConstants.KEINE_DWZ) {
					tabellenMatrix[1][i + 1] = spieler[i].getDwz();
				} else {
					tabellenMatrix[1][i + 1] = ""; //$NON-NLS-1$
				}
			}
			if (ohneFolgeDWZ == false) {
				if (spieler[i].getFolgeDWZ() > 0) {
					tabellenMatrix[2][i + 1] = new Integer(spieler[i].getFolgeDWZ()).toString();
				} else {
					tabellenMatrix[2][i + 1] = ""; //$NON-NLS-1$
				}
			}
		}
		for (int x = 0; x < sp; x++) {
			if (spieler[x].getSpielerId() != TournamentConstants.SPIELFREI_ID) {
				for (int y = 0; y < sp; y++) {
					if (spieler[y].getSpielerId() != TournamentConstants.SPIELFREI_ID) {

						if (x == y) {
							tabellenMatrix[x + 3 - abstand][y + 1] = "--"; //$NON-NLS-1$
						} else {
							for (int i = 0; i < partienAnzahl; i++) {

								if (partien[i].getSpielerWeiss() == spieler[x]
										&& partien[i].getSpielerSchwarz() == spieler[y]) {
									tabellenMatrix[x + dwzAbstand][y + 1] = partien[i].getErgebnisSchwarz();

								}
								if (partien[i].getSpielerSchwarz() == spieler[x]
										&& partien[i].getSpielerWeiss() == spieler[y]) {
									tabellenMatrix[x + dwzAbstand][y + 1] = partien[i].getErgebnisWeiss();

								}
							}

						}
					}

				}
			}
		}

	}

	public String getHTMLTable(Boolean ohneHeaderundFooter) {
		turnierTabelleToHTML = new CrossTableToHTML(tabellenMatrix, turnier, gruppe.getGruppenName(), infoString);
		return turnierTabelleToHTML.getHTMLTable(ohneHeaderundFooter);
	}

	public int getSpalte() {
		spalte = tabellenMatrix.length;
		return spalte;
	}

	public String[][] getTabellenMatrix() {
		return tabellenMatrix;
	}

	public CrossTableToHTML getTurnierTabelleToHTML() {
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

	public void setTurnierTabelleToHTML(CrossTableToHTML turnierTabelleToHTML) {
		this.turnierTabelleToHTML = turnierTabelleToHTML;
	}

	public void setZeile(int zeile) {
		this.zeile = zeile;
	}

	public String getPlayerColumnName() {
		return playerColumnName;
	}

	public void setPlayerColumnName(String playerColumnName) {
		this.playerColumnName = playerColumnName;
	}

	public String getOldDWZColumnName() {
		return oldDWZColumnName;
	}

	public void setOldDWZColumnName(String oldDWZColumnName) {
		this.oldDWZColumnName = oldDWZColumnName;
	}

	public String getNewDWZColumnName() {
		return newDWZColumnName;
	}

	public void setNewDWZColumnName(String newDWZColumnName) {
		this.newDWZColumnName = newDWZColumnName;
	}

	public String getPoinsColumnName() {
		return poinsColumnName;
	}

	public void setPoinsColumnName(String poinsColumnName) {
		this.poinsColumnName = poinsColumnName;
	}

	public String getSbbColumnName() {
		return sbbColumnName;
	}

	public void setSbbColumnName(String sbbColumnName) {
		this.sbbColumnName = sbbColumnName;
	}

	public String getRankingColumnName() {
		return rankingColumnName;
	}

	public void setRankingColumnName(String rankingColumnName) {
		this.rankingColumnName = rankingColumnName;
	}

}
