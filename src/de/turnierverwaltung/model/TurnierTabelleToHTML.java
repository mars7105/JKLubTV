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

public class TurnierTabelleToHTML {

	private String[][] tabellenMatrix;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private String gruppenName;
	private String htmlString;
	private String infoString;
	private int[] reihenfolge;
	private Turnier turnier;

	public TurnierTabelleToHTML(String[][] tabellenMatrix, Turnier turnier,
			String gruppenName) {
		this.turnier = turnier;
		this.tabellenMatrix = tabellenMatrix;
		this.turnierName = turnier.getTurnierName();
		this.startDatum = turnier.getStartDatum();
		this.endDatum = turnier.getEndDatum();
		this.gruppenName = gruppenName;
		this.infoString = Messages.getString("TurnierTabelleToHTML.0"); //$NON-NLS-1$
	}

	private String getHTMLFooter() {
		String footerString = "</body>\n</html>\n"; //$NON-NLS-1$
		return footerString;
	}

	private String getHTMLHeader() {
		String headerString = "<!DOCTYPE html>\n" //$NON-NLS-1$
				+ "<html lang='de'>\n" //$NON-NLS-1$
				+ "<head>\n" //$NON-NLS-1$
				+ "  <meta charset='utf-8'>\n" //$NON-NLS-1$
				+ "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" //$NON-NLS-1$
				+ "  <link rel='stylesheet' href='style.css'>\n" + "  <title>" //$NON-NLS-1$ //$NON-NLS-2$
				+ turnierName + startDatum + Messages.getString("TurnierTabelleToHTML.9") + endDatum + "</title>\n" //$NON-NLS-1$ //$NON-NLS-2$
				+ "</head>\n" + "<body>\n" + "  <h1>" + turnierName + " " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ startDatum + Messages.getString("TurnierTabelleToHTML.15") + endDatum + "</h1>\n" + "  <h2>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ gruppenName + "</h2>\n"; //$NON-NLS-1$
		return headerString;
	}

	public String getHTMLTable() {
		int col = this.tabellenMatrix.length;
		reihenfolge = new int[col];

		reihenfolge[0] = col - 1;
		int x = 0;
		for (int i = 1; i < col - 1; i++) {
			if (x == 1) {
				x++;
			}
			reihenfolge[i] = x;
			x++;

		}
		return makeTurnierTabelle();

	}

	private String makeTurnierTabelle() {

		int col = this.tabellenMatrix.length - 1;
		int row = this.tabellenMatrix[0].length;
		Boolean ohneDWZ = turnier.getNoDWZCalc();
		Boolean ohneFolgeDWZ = turnier.getNoFolgeDWZCalc();
		if (turnier.getOnlyTables() == false) {
			htmlString = getHTMLHeader();
		} else {
			htmlString = ""; //$NON-NLS-1$
		}
		htmlString += "  <table>\n"; //$NON-NLS-1$
		for (int y = 0; y < row; y++) {
			if (y == 0) {
				htmlString += "    <thead>\n"; //$NON-NLS-1$
			}
			if (y == 1) {
				htmlString += "    <tbody>\n"; //$NON-NLS-1$
			}
			htmlString += "      <tr>\n"; //$NON-NLS-1$

			for (int x = 0; x < col; x++) {
				
				if ((ohneDWZ == true && x == 2) || (ohneFolgeDWZ == true && x == 3)) {

				} else {
					String ausgabeWert = this.tabellenMatrix[reihenfolge[x]][y];
					if (ausgabeWert != null && ausgabeWert != "" //$NON-NLS-1$
							&& ausgabeWert != " ") { //$NON-NLS-1$

						if (ausgabeWert == TurnierKonstanten.REMIS) {
							ausgabeWert = "&frac12;"; //$NON-NLS-1$
						}

						if (y == 0) {
							htmlString += "        <th>" + ausgabeWert //$NON-NLS-1$
									+ "</th>\n"; //$NON-NLS-1$
						} else {
							htmlString += "        <td>" + ausgabeWert //$NON-NLS-1$
									+ "</td>\n"; //$NON-NLS-1$
						}
					} else {
						if (y == 0) {
							htmlString += "        <th>" //$NON-NLS-1$
									+ TurnierKonstanten.HTML_LEERZEICHEN
									+ "</th>\n"; //$NON-NLS-1$
						} else {
							htmlString += "        <td>" //$NON-NLS-1$
									+ TurnierKonstanten.HTML_LEERZEICHEN
									+ "</td>\n"; //$NON-NLS-1$
						}
					}

				}
			}
			htmlString += "      </tr>\n"; //$NON-NLS-1$
			if (y == 0) {
				htmlString += "    </thead>\n"; //$NON-NLS-1$
			}
		}
		htmlString += "    </tbody>\n  </table>\n"; //$NON-NLS-1$
		if (infoString != "") { //$NON-NLS-1$
			htmlString += "  <p>" + infoString + "</p>\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (turnier.getOnlyTables() == false) {
			htmlString += getHTMLFooter();
		}
		return htmlString;

	}

}
