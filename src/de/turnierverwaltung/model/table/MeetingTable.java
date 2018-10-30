package de.turnierverwaltung.model.table;

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.ICal;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;

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

	private final Tournament turnier;
	private final Group gruppe;
	private final Game[] partien;
	private final int spielerAnzahl;
	private int partienAnzahl;
	private int spaltenAnzahl;
	private int zeilenAnzahl;
	private final String tabellenMatrix[][];
	private MeetingTableToHTML terminTabelleToHTML;
	private String roundColumnName;
	private String whiteColumnName;
	private String blackColumnName;
	private String resultColumnName;
	private String meetingColumnName;
	private ICal iCalendar;

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
	public MeetingTable(final Tournament turnier, final Group gruppe, final String roundColumnName,
			final String whiteColumnName, final String blackColumnName, final String resultColumnName,
			final String meetingColumnName) {
		this.roundColumnName = roundColumnName;
		this.whiteColumnName = whiteColumnName;
		this.blackColumnName = blackColumnName;
		this.resultColumnName = resultColumnName;
		this.meetingColumnName = meetingColumnName;
		this.turnier = turnier;
		this.gruppe = gruppe;
		this.gruppe.getSpieler();
		// spielerAnzahl = this.gruppe.getSpielerAnzahl();
		partien = gruppe.getPartien();
		spielerAnzahl = gruppe.getPartienAnzahl() * 2 / gruppe.getRundenAnzahl();
		iCalendar = new ICal();
		calcRunden();
		calcAnzahlSpaltenZeilen();
		tabellenMatrix = new String[spaltenAnzahl][zeilenAnzahl];
		createTerminTabelle();

	}

	private void calcAnzahlSpaltenZeilen() {
		spaltenAnzahl = 5;
		zeilenAnzahl = partienAnzahl + 1;
	}

	private void calcRunden() {
		// if (spielerAnzahl % 2 == 0) {
		// } else {
		// }
		// partienAnzahl = (spielerAnzahl * (spielerAnzahl - 1) / 2);
		partienAnzahl = gruppe.getPartienAnzahl();
	}

	/**
	 *
	 * @param roundColumnName
	 * @param whiteColumnName
	 * @param blackColumnName
	 * @param resultColumnName
	 * @param meetingColumnName
	 */
	private void createTerminTabelle() {

		tabellenMatrix[0][0] = roundColumnName;
		tabellenMatrix[1][0] = whiteColumnName;
		tabellenMatrix[2][0] = blackColumnName;
		tabellenMatrix[3][0] = resultColumnName;
		tabellenMatrix[4][0] = meetingColumnName;
		int index = 0;

		for (int i = 0; i < spielerAnzahl - 1; i++) {
			for (int y = i + 1; y < spielerAnzahl; y++) {
				String event = "";
				if (partien[index].getSpielerWeiss().getSpielerId() <= TournamentConstants.SPIELFREI_ID) {
					partien[index].getSpielerWeiss().setName(TournamentConstants.SPIELFREI);
				}
				if (partien[index].getSpielerSchwarz().getSpielerId() <= TournamentConstants.SPIELFREI_ID) {
					partien[index].getSpielerSchwarz().setName(TournamentConstants.SPIELFREI);
				}
				tabellenMatrix[0][index + 1] = Integer.toString(partien[index].getRunde());
				tabellenMatrix[1][index + 1] = partien[index].getSpielerWeiss().getName();
				tabellenMatrix[2][index + 1] = partien[index].getSpielerSchwarz().getName();
				tabellenMatrix[3][index + 1] = getErgebnisToString(partien[index].getErgebnis());
				tabellenMatrix[4][index + 1] = partien[index].getSpielDatum();
				// System.out.println(tabellenMatrix[0][index + 1]);

				event = tabellenMatrix[0][index + 1] + ". Runde ";
				event += tabellenMatrix[1][index + 1] + " - ";
				event += tabellenMatrix[2][index + 1] + " ";
				if (tabellenMatrix[3][index + 1].length() > 0) {
					event += " Ergebnis: " + tabellenMatrix[3][index + 1];
				}
				if (tabellenMatrix[4][index + 1] == null) {
					tabellenMatrix[4][index + 1] = ""; //$NON-NLS-1$
				}
				final String datum = tabellenMatrix[4][index + 1];
				iCalendar.addEvent(datum, event);
				index++;
			}
		}
	}

	public String getBlackColumnName() {
		return blackColumnName;
	}

	private String getErgebnisToString(final int ergebnis) {
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
		if (ergebnis == TournamentConstants.MYSQL_PARTIE_VERLUST_KAMPFLOS_BEIDE) {
			ergebnisString = TournamentConstants.PARTIE_VERLUST_KAMPFLOS_BEIDE;
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
	public String getHTMLTable(final Boolean ohneHeaderundFooter, final String path, final String filename,
			final String icsfilename, final Boolean showLink, final String cssTableClass) {
		terminTabelleToHTML = new MeetingTableToHTML(tabellenMatrix, turnier, gruppe, path, filename, icsfilename,
				showLink, cssTableClass);
		return terminTabelleToHTML.getHTMLTable(ohneHeaderundFooter);
	}

	public String getHTMLTableOnlyWithFooter(final Boolean ohneHeaderundFooter, final String path,
			final String filename, final String icsfilename, final Boolean showLink, final String cssTableClass) {

		terminTabelleToHTML = new MeetingTableToHTML(tabellenMatrix, turnier, gruppe, path, filename, icsfilename,
				showLink, cssTableClass);
		return terminTabelleToHTML.getHTMLTableOnlyWithFooter(ohneHeaderundFooter);
	}

	public ICal getiCalendar() {
		return iCalendar;
	}

	public String getMeetingColumnName() {
		return meetingColumnName;
	}

	public String getResultColumnName() {
		return resultColumnName;
	}

	public String getRoundColumnName() {
		return roundColumnName;
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

	public String getWhiteColumnName() {
		return whiteColumnName;
	}

	public int getZeilenAnzahl() {
		return zeilenAnzahl;
	}

	public void setBlackColumnName(final String blackColumnName) {
		this.blackColumnName = blackColumnName;
	}

	public void setiCalendar(final ICal iCalendar) {
		this.iCalendar = iCalendar;
	}

	public void setMeetingColumnName(final String meetingColumnName) {
		this.meetingColumnName = meetingColumnName;
	}

	public void setResultColumnName(final String resultColumnName) {
		this.resultColumnName = resultColumnName;
	}

	public void setRoundColumnName(final String roundColumnName) {
		this.roundColumnName = roundColumnName;
	}

	public void setSpaltenAnzahl(final int spaltenAnzahl) {
		this.spaltenAnzahl = spaltenAnzahl;
	}

	// public void setTabellenMatrix(String[][] tabellenMatrix) {
	// this.tabellenMatrix = tabellenMatrix;
	// }

	public void setTerminTabelleToHTML(final MeetingTableToHTML terminTabelleToHTML) {
		this.terminTabelleToHTML = terminTabelleToHTML;
	}

	public void setWhiteColumnName(final String whiteColumnName) {
		this.whiteColumnName = whiteColumnName;
	}

	public void setZeilenAnzahl(final int zeilenAnzahl) {
		this.zeilenAnzahl = zeilenAnzahl;
	}

}
