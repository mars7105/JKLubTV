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
	private Boolean colorMatrix[][];
	private int zeile;
	private int spalte;
	private CrossTableToHTML turnierTabelleToHTML;
	private String infoString;

	/**
	 * 
	 */
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

	/**
	 * 
	 * @param playerColumnName
	 * @param oldDWZColumnName
	 * @param newDWZColumnName
	 * @param poinsColumnName
	 * @param sbbColumnName
	 * @param rankingColumnName
	 * @param ohneDWZ
	 * @param ohneFolgeDWZ
	 */
	public void createMatrix(String playerColumnName, String oldDWZColumnName, String newDWZColumnName,
			String oldELOColumnName, String newELOColumnName, String poinsColumnName, String sbbColumnName,
			String rankingColumnName, Boolean ohneDWZ, Boolean ohneFolgeDWZ, Boolean ohneELO, Boolean ohneFolgeELO) {

		String ohneDWZString = oldDWZColumnName.replaceAll("<br />", "") + " = "
				+ Messages.getString("TurnierTabelleToHTML.1");
		String ohneFolgeDWZString = newDWZColumnName.replaceAll("<br />", "") + " = "
				+ Messages.getString("TurnierTabelleToHTML.2");
		String ohneELOString = oldELOColumnName.replaceAll("<br />", "") + " = "
				+ Messages.getString("TurnierTabelleToHTML.12");
		String ohneFolgeELOString = newELOColumnName.replaceAll("<br />", "") + " = "
				+ Messages.getString("TurnierTabelleToHTML.13");
		int dwzabstand = 0;
		int eloabstand = 0;
		if (ohneDWZ == false) {
			dwzabstand++;

		} else {
			ohneDWZString = "";
		}
		if (ohneFolgeDWZ == false) {
			dwzabstand++;

		} else {
			ohneFolgeDWZString = "";
		}
		if (ohneELO == false) {
			eloabstand++;

		} else {
			ohneELOString = "";

		}
		if (ohneFolgeELO == false) {
			eloabstand++;

		} else {
			ohneFolgeELOString = "";
		}
		int gesamtabstand = dwzabstand + eloabstand;
		this.infoString = rankingColumnName + " = " + Messages.getString("TurnierTabelleToHTML.0") + ohneDWZString
				+ ohneFolgeDWZString + ohneELOString + ohneFolgeELOString + poinsColumnName + " = "
				+ Messages.getString("TurnierTabelleToHTML.3") + sbbColumnName + " = " //$NON-NLS-2$
				+ Messages.getString("TurnierTabelleToHTML.4");
		int sp = 0;
		if (checkForSpielfrei() == true) {
			tabellenMatrix = new String[this.spielerAnzahl + gesamtabstand + 3][this.spielerAnzahl];
			colorMatrix = new Boolean[this.spielerAnzahl + gesamtabstand + 3][this.spielerAnzahl];
			sp = spielerAnzahl - 1;
		} else {
			tabellenMatrix = new String[this.spielerAnzahl + gesamtabstand + 4][this.spielerAnzahl + 1];
			colorMatrix = new Boolean[this.spielerAnzahl + gesamtabstand + 4][this.spielerAnzahl + 1];
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

		if (ohneELO == false && ohneFolgeELO == false) {
			tabellenMatrix[1 + dwzabstand][0] = oldELOColumnName;
			tabellenMatrix[2 + dwzabstand][0] = newELOColumnName; // $NON-NLS-1$

		}
		if (ohneELO == false && ohneFolgeELO == true) {
			tabellenMatrix[1 + dwzabstand][0] = oldELOColumnName;
		}
		for (int i = 0; i < sp; i++) {
			if (spieler[i].getKuerzel().length() >= 2) {
				tabellenMatrix[i + gesamtabstand + 1][0] = spieler[i].getKuerzel().substring(0, 1) + "<br />"
						+ spieler[i].getKuerzel().substring(1, 2);
			} else {
				tabellenMatrix[i + gesamtabstand + 1][0] = spieler[i].getKuerzel();
			}

		}

		tabellenMatrix[gesamtabstand + 1 + sp][0] = poinsColumnName;
		tabellenMatrix[gesamtabstand + 2 + sp][0] = sbbColumnName;
		tabellenMatrix[gesamtabstand + 3 + sp][0] = rankingColumnName;
		for (int i = 0; i < sp; i++) {
			if (spieler[i].getSurname().length() > 0) {
				spieler[i].cutForename();
				spieler[i].cutSurname();
				spieler[i].extractForenameAndSurenameToName();
			}
			tabellenMatrix[0][i + 1] = spieler[i].getName();
			if (ohneDWZ == false) {
				if (!spieler[i].getDwz().equals(TournamentConstants.KEINE_DWZ)) {
					tabellenMatrix[1][i + 1] = spieler[i].getDwz();
				} else {
					tabellenMatrix[1][i + 1] = ""; //$NON-NLS-1$
				}
			}
			if (ohneFolgeDWZ == false) {
				if (spieler[i].getFolgeDWZ() > 0) {
					String diff = diffDWZ(spieler[i].getDWZ(), spieler[i].getFolgeDWZ());

					tabellenMatrix[2][i + 1] = Integer.toString(spieler[i].getFolgeDWZ()) + diff;
				} else {
					tabellenMatrix[2][i + 1] = ""; //$NON-NLS-1$
				}
			}
			if (ohneELO == false) {
				int rating1 = spieler[i].getDwzData().getCsvFIDE_Elo();
				int rating2 = spieler[i].getEloData().getRating();
				if (rating1 > 0) {

					tabellenMatrix[1 + dwzabstand][i + 1] = Integer.toString(rating1);
				} else {
					tabellenMatrix[1 + dwzabstand][i + 1] = "";
				}
				if (rating2 > 0) {

					tabellenMatrix[1 + dwzabstand][i + 1] = Integer.toString(rating2);
				} else {
					tabellenMatrix[1 + dwzabstand][i + 1] = "";
				}
			}
			if (ohneFolgeELO == false) {
				int rating1 = spieler[i].getDwzData().getCsvFIDE_Elo();
				int rating2 = spieler[i].getEloData().getRating();
				if (spieler[i].getFolgeELO() > 0) {
					String diff = "";
					if (rating1 > 0) {
						diff = diffDWZ(rating1, spieler[i].getFolgeELO());
					}
					if (rating2 > 0) {
						diff = diffDWZ(rating2, spieler[i].getFolgeELO());
					}

					tabellenMatrix[2 + dwzabstand][i + 1] = Integer.toString(spieler[i].getFolgeELO()) + diff;
				} else {
					tabellenMatrix[2 + dwzabstand][i + 1] = ""; //$NON-NLS-1$
				}
			}
		}
		for (int x = 0; x < sp; x++) {
			if (spieler[x].getSpielerId() != TournamentConstants.SPIELFREI_ID) {
				for (int y = 0; y < sp; y++) {
					if (spieler[y].getSpielerId() != TournamentConstants.SPIELFREI_ID) {

						if (x == y) {
							tabellenMatrix[x + gesamtabstand + 1][y + 1] = TournamentConstants.LEERE_MENGE;

						} else {
							for (int i = 0; i < partienAnzahl; i++) {

								if (partien[i].getSpielerWeiss().equals(spieler[x])
										&& partien[i].getSpielerSchwarz().equals(spieler[y])) {
									tabellenMatrix[x + gesamtabstand + 1][y + 1] = partien[i].getErgebnisSchwarz();
									colorMatrix[x + gesamtabstand + 1][y + 1] = false;

								}
								if (partien[i].getSpielerSchwarz().equals(spieler[x])
										&& partien[i].getSpielerWeiss().equals(spieler[y])) {
									tabellenMatrix[x + gesamtabstand + 1][y + 1] = partien[i].getErgebnisWeiss();
									colorMatrix[x + gesamtabstand + 1][y + 1] = true;

								}
							}

						}
					}

				}
			}
		}

	}

	private String diffDWZ(int altDWZ, int neuDWZ) {
		String differenz = "";
		int diff = neuDWZ - altDWZ;
		if (diff < 0) {
			differenz = " (" + Integer.toString(diff) + ")";
		} else {
			differenz = " (+" + Integer.toString(diff) + ")";
		}

		return differenz;
	}

	/**
	 * 
	 * @param ohneHeaderundFooter
	 * @return
	 */
	public String getHTMLTable(Boolean ohneHeaderundFooter, String path, String filename, Boolean showLink) {
		turnierTabelleToHTML = new CrossTableToHTML(tabellenMatrix, turnier, gruppe.getGruppenName(), infoString, path,
				filename, showLink, colorMatrix);
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

	public Boolean[][] getColorMatrix() {
		return colorMatrix;
	}

	public void setColorMatrix(Boolean[][] colorMatrix) {
		this.colorMatrix = colorMatrix;
	}

}
