package de.turnierverwaltung.model.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class NewsArticle {
	private String tabellenMatrix[][];
	private String htmlContent;
	private String groupName;
	private String cssTableClass;

	public NewsArticle(String[][] tabellenMatrix, String groupName, String cssTableClass) {
		super();
		this.setTabellenMatrix(tabellenMatrix);
		this.groupName = groupName;
		this.cssTableClass = cssTableClass;
		makeHTMLArticles();
	}

	public NewsArticle(ArrayList<String[][]> allNewsArticleMatrix, String groupName, String cssTable) {
		super();
		this.groupName = groupName;
		this.cssTableClass = cssTable;
		int x = 0;
		int y = 0;
		for (String[][] matrix : allNewsArticleMatrix) {
			x = matrix.length;
			y += matrix[0].length;
		}
		this.tabellenMatrix = new String[x][y];
		System.out.println("x=" + x + "y=" + y);
		int yIndex = 0;
		for (String[][] matrix : allNewsArticleMatrix) {
			for (int ym = 0; ym < matrix[0].length; ym++) {
				for (int xm = 0; xm < matrix.length; xm++) {

					this.tabellenMatrix[xm][yIndex] = matrix[xm][ym];
					System.out.println("yIndex=" + yIndex + " ym=" + ym + "xm" + xm + "inhalt=" + matrix[xm][ym]);

				}
				yIndex++;

			}

		}

		makeHTMLArticles();
	}

	private void makeHTMLArticles() {
		int x = tabellenMatrix.length;
		int y = tabellenMatrix[0].length;
		String roundColumnName = tabellenMatrix[0][0];
		String whiteColumnName = tabellenMatrix[1][0];
		String blackColumnName = tabellenMatrix[2][0];
		String resultColumnName = tabellenMatrix[3][0];
		String meetingColumnName = tabellenMatrix[4][0];
		// Gruppierung der Temine nach Datum
		HashMap<String, ArrayList<String[]>> hashMap = new HashMap<String, ArrayList<String[]>>();

		for (int i = 1; i < y; i++) {
			String[] event = new String[x];
			for (int z = 0; z < x; z++) {
				event[z] = tabellenMatrix[z][i];

			}
			String dateKey = "";
			// um zu sortieren braucht man das Datumsformat yyyymmdd
			if (!event[4].equals("") && event[4].length() == 10) {
				dateKey = event[4].substring(6, 10) + event[4].substring(3, 5) + event[4].substring(0, 2);
			} else {
				dateKey = "";
			}
//		System.out.println(dateKey);
			if (hashMap.containsKey(dateKey)) {
				hashMap.get(dateKey).add(event);
			} else {
				hashMap.put(dateKey, new ArrayList<String[]>());
				hashMap.get(dateKey).add(event);
			}
		}
		// Sortierung der HashMap nach key
		TreeMap<String, ArrayList<String[]>> tMap = new TreeMap<String, ArrayList<String[]>>();
		tMap.putAll(hashMap);
		// Erstellen der HTML Tabelle
		htmlContent = "";
		for (ArrayList<String[]> eventList : tMap.values()) {
			htmlContent += "<table class='" + cssTableClass + "'>\n";
			// table header
			htmlContent += "<tr><th colspan='5'>" + groupName + "</th></tr>";
			htmlContent += "<tr><td>" + roundColumnName + "</td>";
			htmlContent += "<td>" + whiteColumnName + "</td>";
			htmlContent += "<td>" + blackColumnName + "</td>";
			htmlContent += "<td>" + resultColumnName + "</td>";
			htmlContent += "<td>" + meetingColumnName + "</td></tr>";
			// table cells
			for (String[] event : eventList) {
				htmlContent += "  <tr>\n";
				for (int z = 0; z < x; z++) {
					htmlContent += "    <td>\n";
					htmlContent += "      " + event[z] + "\n";
					htmlContent += "    </td>\n";
				}
				htmlContent += "  </tr>\n";
			}

			htmlContent += "</table>\n";

		}
	}

	private String getHTMLFooter() {
		final String footerString = "</body>\n</html>\n";
		return footerString;
	}

	private String getHTMLHeader() {
		final String headerString = "<!DOCTYPE html>\n" + "<html lang='de'>\n" + "<head>\n"
				+ "  <meta charset='utf-8'>\n"
				+ "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>\n"
				+ "  <link rel='stylesheet' href='style.css'>\n" + "  <title>Tournament Date View</title>\n"
				+ "</head>\n" + "<body>\n";
		return headerString;
	}

	public String getHtmlContent(final Boolean ohneHeaderundFooter) {
		String htmlString;
		if (ohneHeaderundFooter == false) {
			htmlString = getHTMLHeader();
		} else {
			htmlString = "";
		}
		htmlString += htmlContent;
		if (ohneHeaderundFooter == false) {
			htmlString += getHTMLFooter();
		}
		return htmlString;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String[][] getTabellenMatrix() {
		return tabellenMatrix;
	}

	public void setTabellenMatrix(String tabellenMatrix[][]) {
		this.tabellenMatrix = tabellenMatrix;
	}

}
