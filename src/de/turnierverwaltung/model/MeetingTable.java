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
public class MeetingTable {

	private Tournament turnier;
	private Group gruppe;
	private Game[] partien;
	private int spielerAnzahl;
	private int partienAnzahl;
	private int spaltenAnzahl;
	private int zeilenAnzahl;
	private String tabellenMatrix[][];
	private MeetingTableToHTML terminTabelleToHTML;
	private String roundColumnName;
	private String whiteColumnName;
	private String blackColumnName;
	private String resultColumnName;
	private String meetingColumnName;

	/**
	 * 
	 * @param turnier
	 * @param gruppe
	 * @param roundColumnName
	 * @param whiteColumnName
	 * @param blackColumnName
	 * @param resultColumnName
	 * @param meetingColumnName
	 */
	public MeetingTable(Tournament turnier, Group gruppe, String roundColumnName, String whiteColumnName,
			String blackColumnName, String resultColumnName, String meetingColumnName) {
		this.roundColumnName = roundColumnName;
		this.whiteColumnName = whiteColumnName;
		this.blackColumnName = blackColumnName;
		this.resultColumnName = resultColumnName;
		this.meetingColumnName = meetingColumnName;
		this.turnier = turnier;
		this.gruppe = gruppe;
		this.gruppe.getSpieler();
		this.spielerAnzahl = this.gruppe.getSpielerAnzahl();
		this.partien = gruppe.getPartien();
		calcRunden();
		calcAnzahlSpaltenZeilen();
		tabellenMatrix = new String[5][zeilenAnzahl];
		createTerminTabelle(roundColumnName, whiteColumnName, blackColumnName, resultColumnName, meetingColumnName);

	}

	private void calcAnzahlSpaltenZeilen() {
		spaltenAnzahl = 5;
		zeilenAnzahl = partienAnzahl + 1;
	}

	private void calcRunden() {
		if (spielerAnzahl % 2 == 0) {
		} else {
		}
		partienAnzahl = (spielerAnzahl * (spielerAnzahl - 1) / 2);

	}

	/**
	 * 
	 * @param roundColumnName
	 * @param whiteColumnName
	 * @param blackColumnName
	 * @param resultColumnName
	 * @param meetingColumnName
	 */
	public void createTerminTabelle(String roundColumnName, String whiteColumnName, String blackColumnName,
			String resultColumnName, String meetingColumnName) {
		this.roundColumnName = roundColumnName;
		this.whiteColumnName = whiteColumnName;
		this.blackColumnName = blackColumnName;
		this.resultColumnName = resultColumnName;
		this.meetingColumnName = meetingColumnName;
		// tabellenMatrix[0][0] = TurnierKonstanten.TABLE_COLUMN_ROUND;
		// tabellenMatrix[1][0] = TurnierKonstanten.TABLE_COLUMN_WHITE;
		// tabellenMatrix[2][0] = TurnierKonstanten.TABLE_COLUMN_BLACK;
		// tabellenMatrix[3][0] = TurnierKonstanten.TABLE_COLUMN_RESULT;
		// tabellenMatrix[4][0] = TurnierKonstanten.TABLE_COLUMN_MEETING;
		tabellenMatrix[0][0] = roundColumnName;
		tabellenMatrix[1][0] = whiteColumnName;
		tabellenMatrix[2][0] = blackColumnName;
		tabellenMatrix[3][0] = resultColumnName;
		tabellenMatrix[4][0] = meetingColumnName;
		int index = 0;
		for (int i = 0; i < spielerAnzahl - 1; i++) {
			for (int y = i + 1; y < spielerAnzahl; y++) {

				tabellenMatrix[0][index + 1] = Integer.toString(partien[index].getRunde());
				tabellenMatrix[1][index + 1] = partien[index].getSpielerWeiss().getName();
				tabellenMatrix[2][index + 1] = partien[index].getSpielerSchwarz().getName();
				tabellenMatrix[3][index + 1] = getErgebnisToString(partien[index].getErgebnis());
				tabellenMatrix[4][index + 1] = partien[index].getSpielDatum();
				if (tabellenMatrix[4][index + 1] == null) {
					tabellenMatrix[4][index + 1] = ""; //$NON-NLS-1$
				}
				index++;
			}
		}
	}

	private String getErgebnisToString(int ergebnis) {
		String ergebnisString = TournamentConstants.KEIN_ERGEBNIS;
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_WEISS) {
			ergebnisString = TournamentConstants.PARTIE_GEWINN_WEISS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_SCHWARZ) {
			ergebnisString = TournamentConstants.PARTIE_GEWINN_SCHWARZ;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_REMIS) {
			ergebnisString = TournamentConstants.PARTIE_REMIS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS) {
			ergebnisString = TournamentConstants.PARTIE_GEWINN_KAMPFLOS_WEISS;
		}
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
			ergebnisString = TournamentConstants.PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
		}
		if (ergebnis == TournamentConstants.MYSQL_KEIN_ERGEBNIS) {
			ergebnisString = TournamentConstants.KEIN_ERGEBNIS;
		}
		return ergebnisString;
	}

	/**
	 * 
	 * @param ohneHeaderundFooter
	 * @return
	 */
	public String getHTMLTable(Boolean ohneHeaderundFooter) {
		terminTabelleToHTML = new MeetingTableToHTML(tabellenMatrix, turnier.getTurnierName(), turnier.getStartDatum(),
				turnier.getEndDatum(), gruppe.getGruppenName());
		return terminTabelleToHTML.getHTMLTable(ohneHeaderundFooter);
	}

	public int getSpaltenAnzahl() {
		return spaltenAnzahl;
	}

	public String[][] getTabellenMatrix() {
		return tabellenMatrix;
	}

	public MeetingTableToHTML getTerminTabelleToHTML() {
		return terminTabelleToHTML;
	}

	public int getZeilenAnzahl() {
		return zeilenAnzahl;
	}

	public void setSpaltenAnzahl(int spaltenAnzahl) {
		this.spaltenAnzahl = spaltenAnzahl;
	}

	public void setTabellenMatrix(String[][] tabellenMatrix) {
		this.tabellenMatrix = tabellenMatrix;
	}

	public void setTerminTabelleToHTML(MeetingTableToHTML terminTabelleToHTML) {
		this.terminTabelleToHTML = terminTabelleToHTML;
	}

	public void setZeilenAnzahl(int zeilenAnzahl) {
		this.zeilenAnzahl = zeilenAnzahl;
	}

	public String getRoundColumnName() {
		return roundColumnName;
	}

	public void setRoundColumnName(String roundColumnName) {
		this.roundColumnName = roundColumnName;
	}

	public String getWhiteColumnName() {
		return whiteColumnName;
	}

	public void setWhiteColumnName(String whiteColumnName) {
		this.whiteColumnName = whiteColumnName;
	}

	public String getBlackColumnName() {
		return blackColumnName;
	}

	public void setBlackColumnName(String blackColumnName) {
		this.blackColumnName = blackColumnName;
	}

	public String getResultColumnName() {
		return resultColumnName;
	}

	public void setResultColumnName(String resultColumnName) {
		this.resultColumnName = resultColumnName;
	}

	public String getMeetingColumnName() {
		return meetingColumnName;
	}

	public void setMeetingColumnName(String meetingColumnName) {
		this.meetingColumnName = meetingColumnName;
	}

}
