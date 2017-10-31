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
public class MeetingTableToHTML {

	private String[][] tabellenMatrix;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private String gruppenName;
	private String htmlString;
	private int[] reihenfolge;
	private String filename;
	private WebserverFileLink fileLink;
	private String webServerPath;
	private String icsfilename;
	private int anzahlSpieler;

	/**
	 * 
	 * @param tabellenMatrix
	 * @param turnierName
	 * @param startDatum
	 * @param endDatum
	 * @param gruppenName
	 * @param icsfilename
	 */
	public MeetingTableToHTML(String[][] tabellenMatrix, Tournament turnier, Group gruppe, String webServerPath,
			String filename, String icsfilename, Boolean showLink) {
		super();
		this.tabellenMatrix = tabellenMatrix;
		this.turnierName = turnier.getTurnierName();
		this.startDatum = turnier.getStartDatum();
		this.endDatum = turnier.getEndDatum();
		this.gruppenName = gruppe.getGruppenName();
		this.anzahlSpieler = gruppe.getSpielerAnzahl();
		this.webServerPath = webServerPath;
		this.filename = filename;
		this.icsfilename = icsfilename;
		this.fileLink = new WebserverFileLink(this.webServerPath, this.filename, this.icsfilename, showLink);
	}

	private String getHTMLFooter() {
		String footerString = "</body>\n</html>\n";
		return footerString;
	}

	private String getHTMLHeader() {
		String headerString = "<!DOCTYPE html>\n" + "<html lang='de'>\n" + "<head>\n" + "  <meta charset='utf-8'>\n"
				+ "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n"
				+ "  <link rel='stylesheet' href='style.css'>\n" + "  <title>" + turnierName + startDatum + " bis "
				+ endDatum + "</title>\n" + "</head>\n" + "<body>\n" + "  <h1>" + turnierName + " " + startDatum
				+ " bis " + endDatum + "</h1>\n";
		return headerString;
	}

	/**
	 * 
	 * @param ohneHeaderundFooter
	 * @return
	 */
	public String getHTMLTable(Boolean ohneHeaderundFooter) {
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
		return makeTerminTabelle(ohneHeaderundFooter);

	}

	/**
	 * 
	 * @param ohneHeaderundFooter
	 * @return
	 */
	public String getHTMLTableOnlyWithFooter(Boolean ohneHeaderundFooter) {
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
		if (ohneHeaderundFooter) {
			return makeTerminTabelle(true);
		} else {
			return makeTerminTabelle(true) + getHTMLFooter();
		}
	}

	private String makeTerminTabelle(Boolean ohneHeaderundFooter) {
		int col = this.tabellenMatrix.length;
		int row = this.tabellenMatrix[0].length;
		String beginString1 = "";
		String beginString2 = "";
		for (int i = 1; i < 5; i++) {
			beginString2 += "<td>" + this.tabellenMatrix[i][0] + "</td>";
		}
		if (ohneHeaderundFooter == false) {
			htmlString = getHTMLHeader();
		} else {
			htmlString = "";
		}
		String whiteImage = fileLink.getPathToUserImageWhite();
		String blackImage = fileLink.getPathToUserImageBlack();

		htmlString += " <table>\n";
		for (int y = 0; y < row; y++) {
			if (y == 0) {
				beginString1 = "    <thead>\n    <tr><th colspan='" + col + "'>"
						+ Messages.getString("TurnierTabelleToHTML.11") + " - " + gruppenName + " "
						+ this.tabellenMatrix[0][0] + " " + this.tabellenMatrix[y][1] + "<br />"
						+ fileLink.getPathToPDF() + "&nbsp;&nbsp;&nbsp;" + fileLink.getPathToICS()
						+ "</th></tr>\n</thead>\n<tbody>\n<tr>\n";
				htmlString += beginString1 + beginString2;
			} else {

				htmlString += "<tr>\n";

				for (int x = 1; x < col; x++) {
					String ausgabeWert = this.tabellenMatrix[x][y];
					String playerIcon = "";
					if (ausgabeWert != null && !ausgabeWert.equals("") && !ausgabeWert.equals(" ")) {
						if (ausgabeWert.equals(TournamentConstants.PARTIE_REMIS)) {
							ausgabeWert = "&frac12; - &frac12;";
						}
						if (x == 1) {
							playerIcon = whiteImage;
						}
						if (x == 2) {
							playerIcon = blackImage;
						}

						htmlString += "        <td>" + playerIcon + ausgabeWert + "</td>\n";

					} else {

						htmlString += "        <td>" + TournamentConstants.HTML_LEERZEICHEN + "</td>\n";

					}

				}
				htmlString += "</tr>\n";
				if (y + 1 >= anzahlSpieler / 2 && y % (anzahlSpieler / 2) == 0 && y < row - 1) {
					htmlString += "</tbody></table>\n";

					htmlString += "<table>\n";
					beginString1 = "    <thead>\n    <tr><th colspan='" + col + "'>"
							+ Messages.getString("TurnierTabelleToHTML.11") + " - " + gruppenName + " "
							+ this.tabellenMatrix[0][0] + " " + this.tabellenMatrix[0][y + 1] + "<br />"
							+ fileLink.getPathToPDF() + "&nbsp;&nbsp;&nbsp;" + fileLink.getPathToICS()
							+ "</th></tr>\n</thead>\n<tbody>\n<tr>\n";
					htmlString += beginString1 + beginString2;

				}

			}
		}
		htmlString += "    </tbody>\n  </table>\n";

		if (ohneHeaderundFooter == false) {
			htmlString += getHTMLFooter();
		}
		htmlString = htmlString.replaceAll("\u00e4", "&auml;");
		htmlString = htmlString.replaceAll("\u00f6", "&ouml;");
		htmlString = htmlString.replaceAll("\u00fc", "&uuml;");
		htmlString = htmlString.replaceAll("\u00df", "&slig;");
		htmlString = htmlString.replaceAll("\u00c4", "&Auml;");
		htmlString = htmlString.replaceAll("\u00d6", "&Ouml;");
		htmlString = htmlString.replaceAll("\u00dc", "&Uuml;");
		return htmlString;

	}

}
