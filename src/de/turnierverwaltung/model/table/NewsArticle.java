package de.turnierverwaltung.model.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class NewsArticle {
	private String tabellenMatrix[][];
	private String groupName;
	private String cssTableClass;
	private TreeMap<String, ArrayList<String[]>> tMap;

	public NewsArticle(String[][] allNewsArticleMatrix, String groupName, String cssTableClass) {
		super();
		this.setTabellenMatrix(allNewsArticleMatrix);
		this.groupName = groupName;
		this.cssTableClass = cssTableClass;
		makeHTMLArticles();
	}

	public NewsArticle(TreeMap<String, String[][]> allNewsArticleMatrix, String groupName, String cssTable) {
		super();
		this.groupName = groupName;
		this.cssTableClass = cssTable;
		int x = 0;
		int y = 0;
		String[] gnames = new String[allNewsArticleMatrix.size()];
		int index = 0;
		for (String key : allNewsArticleMatrix.keySet()) {
			gnames[index] = key;
			index++;
		}
		for (String[][] matrix : allNewsArticleMatrix.values()) {
			x = matrix.length;
			y += matrix[0].length;

		}
		this.tabellenMatrix = new String[x + 1][y];
//		System.out.println("x=" + x + "y=" + y);
		int yIndex = 0;
		index = 0;
		for (String[][] matrix : allNewsArticleMatrix.values()) {

			for (int ym = 0; ym < matrix[0].length; ym++) {
				this.tabellenMatrix[0][yIndex] = gnames[index];
				for (int xm = 0; xm < matrix.length; xm++) {

					this.tabellenMatrix[xm + 1][yIndex] = matrix[xm][ym];
//					System.out.println("yIndex=" + yIndex + " ym=" + ym + "xm" + xm + "inhalt=" + matrix[xm][ym]);

				}

				yIndex++;

			}
			index++;
		}

		x = this.tabellenMatrix.length;
		y = this.tabellenMatrix[0].length;

		// Gruppierung der Temine nach Datum
		HashMap<String, ArrayList<String[]>> hashMap = new HashMap<String, ArrayList<String[]>>();

		for (int i = 1; i < y; i++) {
			String[] event = new String[x];
			for (int z = 0; z < x; z++) {
				event[z] = tabellenMatrix[z][i];

			}
			String dateKey = "";
			// um zu sortieren braucht man das Datumsformat yyyymmdd
			if (!event[5].equals("") && event[5].length() == 10) {
				dateKey = event[5].substring(6, 10) + event[5].substring(3, 5) + event[5].substring(0, 2);
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
		tMap = new TreeMap<String, ArrayList<String[]>>();
		tMap.putAll(hashMap);
		// Erstellen der HTML Tabelle }

	}

	private void makeHTMLArticles() {
		int x = tabellenMatrix.length;
		int y = tabellenMatrix[0].length;

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
		tMap = new TreeMap<String, ArrayList<String[]>>();
		tMap.putAll(hashMap);
		// Erstellen der HTML Tabelle

	}

	private String getEventTable(ArrayList<String[]> eventList) {
		int x = tabellenMatrix.length;
		int versatz = 0;
		if (tabellenMatrix.length == 6) {
			versatz = 1;
		}
		String roundColumnName = tabellenMatrix[0 + versatz][0];
		String whiteColumnName = tabellenMatrix[1 + versatz][0];
		String blackColumnName = tabellenMatrix[2 + versatz][0];
		String resultColumnName = tabellenMatrix[3 + versatz][0];
		String meetingColumnName = tabellenMatrix[4 + versatz][0];

		String htmlContent = "";
		htmlContent += "<table class='" + cssTableClass + "'>\n";
		// table header
		htmlContent += "<tr>\n";

		htmlContent += "<th colspan='" + tabellenMatrix.length + "'>" + groupName + "</th>";
		htmlContent += "</tr>\n";

		htmlContent += "<tr>\n";
		if (tabellenMatrix.length == 6) {
			htmlContent += "<th>Gruppe</th>";
		} else {

		}
		htmlContent += "<th>" + roundColumnName + "</th>";
		htmlContent += "<th>" + whiteColumnName + "</th>";
		htmlContent += "<th>" + blackColumnName + "</th>";
		htmlContent += "<th>" + resultColumnName + "</th>";
		htmlContent += "<th>" + meetingColumnName + "</th>";

		htmlContent += "</tr>\n";
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
		return htmlContent;
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

	public String getHtmlContent(final Boolean ohneHeaderundFooter, String selectedItem) {
		String dateKey;
		if (!selectedItem.equals("") && selectedItem.length() == 10) {
			dateKey = selectedItem.substring(6, 10) + selectedItem.substring(3, 5) + selectedItem.substring(0, 2);
		} else {
			dateKey = "";
		}
		String htmlString;
		if (ohneHeaderundFooter == false) {
			htmlString = getHTMLHeader();
		} else {
			htmlString = "";
		}
//		System.out.println(selectedItem + " " + dateKey);
		htmlString += getEventTable(tMap.get(dateKey));
//		htmlString += htmlContent;
		if (ohneHeaderundFooter == false) {
			htmlString += getHTMLFooter();
		}
		return htmlString;
	}

//	public void setHtmlContent(String htmlContent) {
//		this.htmlContent = htmlContent;
//	}

	public String[][] getTabellenMatrix() {
		return tabellenMatrix;
	}

	public void setTabellenMatrix(String[][] allNewsArticleMatrix) {
		this.tabellenMatrix = allNewsArticleMatrix;
	}

	public ArrayList<String> getDateList() {
		ArrayList<String> dateList = new ArrayList<String>();
		for (String dateKey : tMap.keySet()) {
			String dKey;
			if (!dateKey.equals("") && dateKey.length() == 8) {
				dKey = dateKey.substring(6, 8) + "." + dateKey.substring(4, 6) + "." + dateKey.substring(0, 4);
			} else {
				dKey = "";
			}
//			System.out.println(dKey);
			dateList.add(dKey);
		}
		return dateList;
	}

	public String getHtmlContent(Boolean ohneHeaderundFooter) {
		String htmlString;
		if (ohneHeaderundFooter == false) {
			htmlString = getHTMLHeader();
		} else {
			htmlString = "";
		}
		for (ArrayList<String[]> list : tMap.values()) {
			htmlString += getEventTable(list);
		}

		if (ohneHeaderundFooter == false) {
			htmlString += getHTMLFooter();
		}
		return htmlString;
	}
}
