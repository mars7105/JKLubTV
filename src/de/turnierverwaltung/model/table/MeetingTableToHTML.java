package de.turnierverwaltung.model.table;

import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Messages;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.WebserverFileLink;

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

	private final String[][] tabellenMatrix;
	private final String turnierName;
	private final String startDatum;
	private final String endDatum;
	private final String gruppenName;
	private String htmlString;
	private int[] reihenfolge;
	private final String filename;
	private final WebserverFileLink fileLink;
	private final String webServerPath;
	private final String icsfilename;
	// private final int anzahlSpieler;
	private final String cssTableClass;
	private final int anzahlPartienProRunde;

	/**
	 *
	 * @param tabellenMatrix
	 * @param turnierName
	 * @param startDatum
	 * @param endDatum
	 * @param gruppenName
	 * @param icsfilename
	 */
	public MeetingTableToHTML(final String[][] tabellenMatrix, final Tournament turnier, final Group gruppe,
			final String webServerPath, final String filename, final String icsfilename, final Boolean showLink,
			final String cssTableClass) {
		super();
		this.cssTableClass = cssTableClass;
		this.tabellenMatrix = tabellenMatrix;
		turnierName = turnier.getTurnierName();
		startDatum = turnier.getStartDatum();
		endDatum = turnier.getEndDatum();
		gruppenName = gruppe.getGruppenName();
		// anzahlSpieler = gruppe.getSpielerAnzahl();
		anzahlPartienProRunde = gruppe.getPartienAnzahl() / gruppe.getRundenAnzahl();
		this.webServerPath = webServerPath;
		this.filename = filename;
		this.icsfilename = icsfilename;
		fileLink = new WebserverFileLink(this.webServerPath, this.filename, this.icsfilename, showLink);
	}

	private String getHTMLFooter() {
		final String footerString = "</body>\n</html>\n";
		return footerString;
	}

	private String getHTMLHeader() {
		final String headerString = "<!DOCTYPE html>\n" + "<html lang='de'>\n" + "<head>\n"
				+ "  <meta charset='utf-8'>\n"
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
	public String getHTMLTable(final Boolean ohneHeaderundFooter) {
		final int col = tabellenMatrix.length;
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
	public String getHTMLTableOnlyWithFooter(final Boolean ohneHeaderundFooter) {
		final int col = tabellenMatrix.length;
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

	private String makeTerminTabelle(final Boolean ohneHeaderundFooter) {
		final int col = tabellenMatrix.length;
		final int row = tabellenMatrix[0].length;
		String beginString1 = "";
		String beginString2 = "";
		for (int i = 1; i < 5; i++) {
			beginString2 += "<td>" + tabellenMatrix[i][0] + "</td>";
		}
		if (ohneHeaderundFooter == false) {
			htmlString = getHTMLHeader();
		} else {
			htmlString = "";
		}
		final String whiteImage = fileLink.getPathToUserImageWhite();
		final String blackImage = fileLink.getPathToUserImageBlack();

		htmlString += " <table class='" + cssTableClass + "'>\n";
		for (int y = 0; y < row; y++) {
			if (y == 0) {
				beginString1 = "    <thead>\n    <tr><th colspan='" + col + "'>"
						+ Messages.getString("TurnierTabelleToHTML.11") + " - " + gruppenName + " "
						+ tabellenMatrix[0][0] + " " + tabellenMatrix[y][1] + fileLink.getPathsForMeetingtable()
						+ "</th></tr>\n</thead>\n<tbody>\n<tr>\n";
				htmlString += beginString1 + beginString2;
			} else {

				htmlString += "<tr>\n";

				for (int x = 1; x < col; x++) {
					String ausgabeWert = tabellenMatrix[x][y];
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
				if (y + 1 >= anzahlPartienProRunde && y % anzahlPartienProRunde == 0 && y < row - 1) {
					htmlString += "</tbody></table>\n";

					htmlString += "<table class='" + cssTableClass + "'>\n";
					beginString1 = "    <thead>\n    <tr><th colspan='" + col + "'>"
							+ Messages.getString("TurnierTabelleToHTML.11") + " - " + gruppenName + " "
							+ tabellenMatrix[0][0] + " " + tabellenMatrix[0][y + 1] + fileLink.getPathsForMeetingtable()
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
