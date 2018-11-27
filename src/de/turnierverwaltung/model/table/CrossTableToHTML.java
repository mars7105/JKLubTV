package de.turnierverwaltung.model.table;

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

public class CrossTableToHTML {
	private final Boolean colorMatrix[][];
	private final String[][] tabellenMatrix;
	private final String turnierName;
	private final String startDatum;
	private final String endDatum;
	private final String gruppenName;
	private String htmlString;
	private final String infoString;
	private int[] reihenfolge;
	private final String webServerPath;
	private final String filename;
	private final WebserverFileLink fileLink;
	private final String icsfilename;
	private final String cssTableClass;

	public CrossTableToHTML(final String[][] tabellenMatrix2, final Tournament turnier2, final String gruppenName2,
			final String infoString2, final String path, final String filename2, final Boolean showLink,
			final Boolean colorMatrix[][], final String cssTableClass) {
		super();
		this.cssTableClass = cssTableClass;
		tabellenMatrix = tabellenMatrix2;
		turnierName = turnier2.getTurnierName();
		startDatum = turnier2.getStartDatum();
		endDatum = turnier2.getEndDatum();
		gruppenName = gruppenName2;
		infoString = infoString2;
		webServerPath = path;
		filename = filename2;
		icsfilename = "";
		fileLink = new WebserverFileLink(webServerPath, filename, showLink);
		this.colorMatrix = colorMatrix;
	}

	/**
	 *
	 * @param tabellenMatrix
	 * @param turnier
	 * @param gruppenName
	 * @param infoString
	 */
	public CrossTableToHTML(final String[][] tabellenMatrix, final Tournament turnier, final String gruppenName,
			final String infoString, final String webServerPath, final String filename, final String icsfilename,
			final Boolean showLink, final Boolean colorMatrix[][], final String cssTableClass) {
		super();
		this.cssTableClass = cssTableClass;
		this.tabellenMatrix = tabellenMatrix;
		turnierName = turnier.getTurnierName();
		startDatum = turnier.getStartDatum();
		endDatum = turnier.getEndDatum();
		this.gruppenName = gruppenName;
		this.infoString = infoString;
		this.webServerPath = webServerPath;
		this.filename = filename;
		this.icsfilename = icsfilename;
		fileLink = new WebserverFileLink(this.webServerPath, this.filename, this.icsfilename, showLink);
		this.colorMatrix = colorMatrix;
	}

	private String getHTMLFooter() {
		final String footerString = "</body>\n</html>\n"; //$NON-NLS-1$
		return footerString;
	}

	private String getHTMLHeader() {
		final String headerString = "<!DOCTYPE html>\n" //$NON-NLS-1$
				+ "<html lang='de'>\n" //$NON-NLS-1$
				+ "<head>\n" //$NON-NLS-1$
				+ "  <meta charset='utf-8'>\n" //$NON-NLS-1$
				+ "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n" //$NON-NLS-1$
				+ "  <link rel='stylesheet' href='style.css'>\n" + "  <title>" //$NON-NLS-1$ //$NON-NLS-2$
				+ turnierName + startDatum + Messages.getString("TurnierTabelleToHTML.9") + endDatum + "</title>\n" //$NON-NLS-1$ //$NON-NLS-2$
				+ "</head>\n" + "<body>\n" + "  <h1>" + turnierName + " " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ startDatum + Messages.getString("TurnierTabelleToHTML.15") + endDatum + "</h1>\n";
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
		for (int i = 1; i < col; i++) {

			reihenfolge[i] = x;
			x++;

		}
		return makeTurnierTabelle(ohneHeaderundFooter);

	}

	/**
	 *
	 * @param ohneHeaderundFooter
	 * @return
	 */
	public String getHTMLTableOnlyWithHeader(final Boolean ohneHeaderundFooter) {
		final int col = tabellenMatrix.length;
		reihenfolge = new int[col];

		reihenfolge[0] = col - 1;
		int x = 0;
		for (int i = 1; i < col; i++) {

			reihenfolge[i] = x;
			x++;

		}

		if (ohneHeaderundFooter) {
			return makeTurnierTabelle(true);
		} else {
			return getHTMLHeader() + makeTurnierTabelle(true);
		}
	}

	/**
	 *
	 * @param ohneHeaderundFooter
	 * @return
	 */
	private String makeTurnierTabelle(final Boolean ohneHeaderundFooter) {

		final int col = tabellenMatrix.length;
		final int row = tabellenMatrix[0].length;

		if (ohneHeaderundFooter == false) {
			htmlString = getHTMLHeader();
		} else {
			htmlString = "";
		}
		htmlString += "  <table class='" + cssTableClass + "'>\n"; //$NON-NLS-1$
		for (int y = 0; y < row; y++) {
			if (y == 0) {
				htmlString += "    <thead>\n"; //$NON-NLS-1$
				htmlString += "    <tr><th colspan='" + col + "'>" + " " + "&nbsp;"
						+ Messages.getString("TurnierTabelleToHTML.10") + " - " + gruppenName + fileLink.getPathToPDF()
						+ "</th></tr>\n</thead>\n<tbody>\n";
			}

			htmlString += "      <tr>\n"; //$NON-NLS-1$

			for (int x = 0; x < col; x++) {

				String ausgabeWert = tabellenMatrix[reihenfolge[x]][y];

				final Boolean color = colorMatrix[reihenfolge[x]][y];
				String cell = "        <td >";
				if (color != null) {
					if (color == true) {
						cell = "<td class='whitecolor'>";
					}
					if (color == false) {
						cell = "<td class='blackcolor'>";
					}
				}
				
				if (ausgabeWert != null && !ausgabeWert.equals("") && !ausgabeWert.equals(" ")) {

					if (ausgabeWert.equals(TournamentConstants.REMIS)) {
						ausgabeWert = "&frac12;";
					}
					if (ausgabeWert.equals(TournamentConstants.LEERE_MENGE)) {
						cell = "<td class='nogame'>";
					}
					htmlString += cell + ausgabeWert + "</td>\n";

				} else {

					htmlString += cell + TournamentConstants.HTML_LEERZEICHEN + "</td>\n";

				}

			}
			htmlString += "      </tr>\n"; //$NON-NLS-1$

		}
		if (infoString.length() > 0) {
			htmlString += "    </tbody>\n<tfoot><tr><td colspan='" + col + "'>" + infoString + "</td></tr></tfoot>\n";
		}
		htmlString += "     </table>\n"; //$NON-NLS-1$

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
		// htmlString = htmlString.replaceAll("\u2666", "&diams;");
		return htmlString;

	}

}
