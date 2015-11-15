package de.turnierverwaltung.model;

public class TurnierTabelleToHTML {

	private String[][] tabellenMatrix;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private String gruppenName;
	private String htmlString;
	private String infoString;
	private int[] reihenfolge;

	public TurnierTabelleToHTML(String[][] tabellenMatrix, String turnierName, String startDatum, String endDatum,
			String gruppenName) {

		this.tabellenMatrix = tabellenMatrix;
		this.turnierName = turnierName;
		this.startDatum = startDatum;
		this.endDatum = endDatum;
		this.gruppenName = gruppenName;
		this.infoString = "R = Rang<br />a. DWZ = aktuelle DWZ<br />n. DWZ = Folge DWZ<br />P* = Punkte<br />S* = Sonnebornberger<br />";
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
				+ " bis " + endDatum + "</h1>\n" + "  <h2>" + gruppenName + "</h2>\n";
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
		htmlString = getHTMLHeader();
		htmlString += "  <table>\n";
		for (int y = 0; y < row; y++) {
			if (y == 0) {
				htmlString += "    <thead>\n";
			}
			if (y == 1) {
				htmlString += "    <tbody>\n";
			}
			htmlString += "      <tr>\n";

			for (int x = 0; x < col; x++) {
				String ausgabeWert = this.tabellenMatrix[reihenfolge[x]][y];
				if (ausgabeWert != null && ausgabeWert != "" && ausgabeWert != " ") {

					if (ausgabeWert == TurnierKonstanten.REMIS) {
						ausgabeWert = "&frac12;";
					}

					if (y == 0) {
						htmlString += "        <th>" + ausgabeWert + "</th>\n";
					} else {
						htmlString += "        <td>" + ausgabeWert + "</td>\n";
					}
				} else {
					if (y == 0) {
						htmlString += "        <th>" + TurnierKonstanten.HTML_LEERZEICHEN + "</th>\n";
					} else {
						htmlString += "        <td>" + TurnierKonstanten.HTML_LEERZEICHEN + "</td>\n";
					}
				}

			}
			htmlString += "      </tr>\n";
			if (y == 0) {
				htmlString += "    </thead>\n";
			}
		}
		htmlString += "    </tbody>\n  </table>\n";
		if (infoString != "") {
			htmlString += "  <p>" + infoString + "</p>\n";
		}
		htmlString += getHTMLFooter();
		return htmlString;

	}

}
