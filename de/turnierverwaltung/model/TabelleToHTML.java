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
public class TabelleToHTML {
	private String[][] tabellenMatrix;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private String gruppenName;
	private String htmlString;

	public TabelleToHTML(String[][] tabellenMatrix, String turnierName, String startDatum, String endDatum,
			String gruppenName) {
		super();
		this.tabellenMatrix = tabellenMatrix;
		this.turnierName = turnierName;
		this.startDatum = startDatum;
		this.endDatum = endDatum;
		this.gruppenName = gruppenName;
	}

	private String getHTMLFooter() {
		String footerString = "</body>\n</html>\n";
		return footerString;
	}

	private String getHTMLHeader() {
		String headerString = "<!doctype html>\n" + "<head>\n" + "  <meta charset='utf-8'>\n"
				+ "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n"
				+ "  <link rel='stylesheet' href='style.css'>\n" + "  <title>" + turnierName + startDatum + " bis "
				+ endDatum + "</title>\n" + "</head>\n" + "<body>\n" + "  <h1>" + turnierName + " " + startDatum
				+ " bis " + endDatum + "</h1>\n" + "  <h2>" + gruppenName + "</h2>\n";
		return headerString;
	}

	public String getHTMLTable() {
		int col = this.tabellenMatrix.length;
		int row = this.tabellenMatrix[0].length;
		htmlString = getHTMLHeader();
		htmlString += "  <table border='1'>\n    <tbody>\n";
		for (int y = 0; y < row; y++) {
			htmlString += "      <tr>\n";

			for (int x = 0; x < col; x++) {
				if (x != 1) {
					String bgcolor = "";
					if (this.tabellenMatrix[x][0] == "Folge-DWZ" && y > 0) {
						String s1 = this.tabellenMatrix[x - 1][y];
						String s2 = this.tabellenMatrix[x][y];
						int sz1 = Integer.parseInt(s1);
						int sz2 = Integer.parseInt(s2);
						if (sz1 > sz2) {
							bgcolor = " bgcolor=#F5D0A9";
						}
						if (sz2 > sz1) {
							bgcolor = " bgcolor=#D0F5A9";
						}

					}
					if (this.tabellenMatrix[x][y] != null && this.tabellenMatrix[x][y] != ""
							&& this.tabellenMatrix[x][y] != " ") {
						htmlString += "        <td" + bgcolor + ">" + this.tabellenMatrix[x][y] + "</td>\n";
					} else {
						htmlString += "        <td>" + TurnierKonstanten.HTML_LEERZEICHEN + "</td>\n";
					}
				}
			}
			htmlString += "      </tr>\n";
		}
		htmlString += "    </tbody>\n  </table>\n";
		htmlString += getHTMLFooter();
		return htmlString;

	}
}
