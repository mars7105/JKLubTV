package de.turnierverwaltung.model.table;

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Messages;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;

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
	private final Tournament turnier;
	private final Group gruppe;
	private final Player[] spieler;
	private final Game[] partien;
	private final int spielerAnzahl;
	private final int partienAnzahl;
	private String tabellenMatrix[][];
	private Boolean colorMatrix[][];
	private int zeile;
	private int spalte;
	private CrossTableToHTML turnierTabelleToHTML;
	private String infoString;

	/**
	 *
	 */
	public CrossTable(final Tournament turnier, final Group gruppe) {
		this.turnier = turnier;
		this.gruppe = gruppe;
		spieler = gruppe.getSpieler();
		partien = gruppe.getPartien();
		spielerAnzahl = gruppe.getSpielerAnzahl();
		partienAnzahl = gruppe.getPartienAnzahl();

	}

	private Boolean checkForSpielfrei() {
		for (int i = 0; i < spielerAnzahl; i++) {

			if (spieler[i].getSpielerId() <= TournamentConstants.SPIELFREI_ID) {
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
	public void createMatrix(final String playerColumnName, final String oldDWZColumnName,
			final String newDWZColumnName, final String oldELOColumnName, final String newELOColumnName,
			final String poinsColumnName, final String sbbColumnName, final String rankingColumnName,
			final Boolean ohneDWZ, final Boolean ohneFolgeDWZ, final Boolean ohneELO, final Boolean ohneFolgeELO) {

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
		final int gesamtabstand = dwzabstand + eloabstand;
		infoString = rankingColumnName + " = " + Messages.getString("TurnierTabelleToHTML.0") + ohneDWZString
				+ ohneFolgeDWZString + ohneELOString + ohneFolgeELOString + poinsColumnName + " = "
				+ Messages.getString("TurnierTabelleToHTML.3") + sbbColumnName + " = " //$NON-NLS-2$
				+ Messages.getString("TurnierTabelleToHTML.4");
		int sp = 0;
		if (checkForSpielfrei() == true) {
			tabellenMatrix = new String[spielerAnzahl + gesamtabstand + 3][spielerAnzahl];
			colorMatrix = new Boolean[spielerAnzahl + gesamtabstand + 3][spielerAnzahl];
			sp = spielerAnzahl - 1;
		} else {
			tabellenMatrix = new String[spielerAnzahl + gesamtabstand + 4][spielerAnzahl + 1];
			colorMatrix = new Boolean[spielerAnzahl + gesamtabstand + 4][spielerAnzahl + 1];
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
			if (spieler[i].getKuerzel().length() >= 3) {
				tabellenMatrix[i + gesamtabstand + 1][0] = spieler[i].getKuerzel().substring(0, 1) + "<br />"
						+ spieler[i].getKuerzel().substring(1, 3);
			} else if (spieler[i].getKuerzel().length() == 2) {
				tabellenMatrix[i + gesamtabstand + 1][0] = spieler[i].getKuerzel().substring(0, 1) + "<br />"
						+ spieler[i].getKuerzel().substring(1, 2);
			} else if (spieler[i].getKuerzel().length() == 1){
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
				// spieler[i].extractForenameAndSurenameToName();
				spieler[i].setName(spieler[i].getSurname() + "," + spieler[i].getForename());
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
					final String diff = diffDWZ(spieler[i].getDWZ(), spieler[i].getFolgeDWZ());

					tabellenMatrix[2][i + 1] = Integer.toString(spieler[i].getFolgeDWZ()) + diff;
				} else {
					tabellenMatrix[2][i + 1] = ""; //$NON-NLS-1$
				}
			}
			if (ohneELO == false) {
				final int rating1 = spieler[i].getDwzData().getCsvFIDE_Elo();
				final int rating2 = spieler[i].getEloData().getRating();
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
				final int rating1 = spieler[i].getDwzData().getCsvFIDE_Elo();
				final int rating2 = spieler[i].getEloData().getRating();
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
			if (spieler[x].getSpielerId() > TournamentConstants.SPIELFREI_ID) {
				for (int y = 0; y < sp; y++) {
					if (spieler[y].getSpielerId() > TournamentConstants.SPIELFREI_ID) {

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

	private String diffDWZ(final int altDWZ, final int neuDWZ) {
		String differenz = "";
		final int diff = neuDWZ - altDWZ;
		if (diff < 0) {
			differenz = " (" + Integer.toString(diff) + ")";
		} else {
			differenz = " (+" + Integer.toString(diff) + ")";
		}

		return differenz;
	}

	public Boolean[][] getColorMatrix() {
		return colorMatrix;
	}

	/**
	 *
	 * @param ohneHeaderundFooter
	 * @return
	 */
	public String getHTMLTable(final Boolean ohneHeaderundFooter, final String path, final String filename,
			final Boolean showLink, final String cssTableClass) {
		turnierTabelleToHTML = new CrossTableToHTML(tabellenMatrix, turnier, gruppe.getGruppenName(), infoString, path,
				filename, showLink, colorMatrix, cssTableClass);
		return turnierTabelleToHTML.getHTMLTable(ohneHeaderundFooter);
	}

	public String getHTMLTableOnlyWithHeader(final Boolean ohneHeaderundFooter, final String webserverPath,
			final String webfilename1, final Boolean showLink, final String cssTableClass) {
		turnierTabelleToHTML = new CrossTableToHTML(tabellenMatrix, turnier, gruppe.getGruppenName(), infoString,
				webserverPath, webfilename1, showLink, colorMatrix, cssTableClass);
		return turnierTabelleToHTML.getHTMLTableOnlyWithHeader(ohneHeaderundFooter);
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

	public void setColorMatrix(final Boolean[][] colorMatrix) {
		this.colorMatrix = colorMatrix;
	}

	public void setSpalte(final int spalte) {
		this.spalte = spalte;
	}

	public void setTabellenMatrix(final String[][] tabellenMatrix) {
		this.tabellenMatrix = tabellenMatrix;
	}

	public void setTurnierTabelleToHTML(final CrossTableToHTML turnierTabelleToHTML) {
		this.turnierTabelleToHTML = turnierTabelleToHTML;
	}

	public void setZeile(final int zeile) {
		this.zeile = zeile;
	}

}
