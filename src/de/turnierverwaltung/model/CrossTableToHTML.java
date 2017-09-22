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

public class CrossTableToHTML {

	private String[][] tabellenMatrix;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private String gruppenName;
	private String htmlString;
	private String infoString;
	private int[] reihenfolge;
	private Tournament turnier;
	private String webServerPath;
	private String filename;
	private WebserverFileLink fileLink;
	private String icsfilename;

	/**
	 * 
	 * @param tabellenMatrix
	 * @param turnier
	 * @param gruppenName
	 * @param infoString
	 */
	public CrossTableToHTML(String[][] tabellenMatrix, Tournament turnier, String gruppenName, String infoString,
			String webServerPath, String filename, String icsfilename, Boolean showLink) {
		this.turnier = turnier;
		this.tabellenMatrix = tabellenMatrix;
		this.turnierName = turnier.getTurnierName();
		this.startDatum = turnier.getStartDatum();
		this.endDatum = turnier.getEndDatum();
		this.gruppenName = gruppenName;
		this.infoString = infoString;
		this.webServerPath = webServerPath;
		this.filename = filename;
		this.icsfilename = icsfilename;
		this.fileLink = new WebserverFileLink(this.webServerPath, this.filename, this.icsfilename, showLink);
	}

	public CrossTableToHTML(String[][] tabellenMatrix2, Tournament turnier2, String gruppenName2, String infoString2,
			String path, String filename2, Boolean showLink) {
		this.turnier = turnier2;
		this.tabellenMatrix = tabellenMatrix2;
		this.turnierName = turnier2.getTurnierName();
		this.startDatum = turnier2.getStartDatum();
		this.endDatum = turnier2.getEndDatum();
		this.gruppenName = gruppenName2;
		this.infoString = infoString2;
		this.webServerPath = path;
		this.filename = filename2;
		this.icsfilename = "";
		this.fileLink = new WebserverFileLink(this.webServerPath, this.filename, showLink);

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
				+ startDatum + Messages.getString("TurnierTabelleToHTML.15") + endDatum + "</h1>\n";
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
	private String makeTurnierTabelle(Boolean ohneHeaderundFooter) {

		int col = this.tabellenMatrix.length;
		int row = this.tabellenMatrix[0].length;

		if (ohneHeaderundFooter == false) {
			htmlString = getHTMLHeader();
		} else {
			htmlString = "";
		}
		htmlString += "  <table>\n"; //$NON-NLS-1$
		for (int y = 0; y < row; y++) {
			if (y == 0) {
				htmlString += "    <thead>\n"; //$NON-NLS-1$
				htmlString += "    <tr><th colspan='" + col + "'>" + " " + "&nbsp;"
						+ Messages.getString("TurnierTabelleToHTML.10") + " - " + gruppenName + fileLink.getPathToPDF()
						+ "</th></tr>\n</thead>\n<tbody>\n";
			}

			htmlString += "      <tr>\n"; //$NON-NLS-1$

			for (int x = 0; x < col; x++) {

				String ausgabeWert = this.tabellenMatrix[reihenfolge[x]][y];
				if (ausgabeWert != null && !ausgabeWert.equals("") // $NON-NLS-1$
						&& !ausgabeWert.equals(" ")) { //$NON-NLS-1$

					if (ausgabeWert.equals(TournamentConstants.REMIS)) {
						ausgabeWert = "&frac12;"; //$NON-NLS-1$
					}

					htmlString += "        <td>" + ausgabeWert //$NON-NLS-1$
							+ "</td>\n"; //$NON-NLS-1$

				} else {

					htmlString += "        <td>" //$NON-NLS-1$
							+ TournamentConstants.HTML_LEERZEICHEN + "</td>\n"; //$NON-NLS-1$

				}

			}
			htmlString += "      </tr>\n"; //$NON-NLS-1$

		}
		if (infoString.length() > 0) {
			htmlString += "    </tbody>\n<tfoot><tr><td colspan='" + col + "'>" + infoString + "</td></tr></tfoot>\n";
		}
		htmlString += "     </table>\n"; //$NON-NLS-1$

		if (turnier.getOnlyTables() == false) {
			htmlString += getHTMLFooter();
		}
		htmlString = htmlString.replaceAll("\u00e4", "&auml;");
		htmlString = htmlString.replaceAll("\u00f6", "&ouml;");
		htmlString = htmlString.replaceAll("\u00fc", "&uuml;");
		htmlString = htmlString.replaceAll("\u00df", "&slig;");
		htmlString = htmlString.replaceAll("\u00c4", "&Auml;");
		htmlString = htmlString.replaceAll("\u00d6", "&Ouml;");
		htmlString = htmlString.replaceAll("\u00dc", "&Uuml;");
//		htmlString = htmlString.replaceAll("\u2666", "&diams;");
		return htmlString;

	}

}
