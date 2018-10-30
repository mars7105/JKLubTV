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
import java.awt.Toolkit;

import de.turnierverwaltung.sqlite.DAOFactory;

/**
 *
 * @author mars
 *
 */

public class TournamentConstants {
	public static final int MAX_PLAYERS = 32;
	public static final int MAX_GROUPS = 15;
	public static final String KEIN_ERGEBNIS = " ";
	public static final String GEWINN = "1";
	public static final String VERLUST = "0";
	public static final String REMIS = "\u00BD";
	public static final String LEERE_MENGE = "*";
	public static final String TRENNZEICHEN = " - ";
	public static final String GEWINN_KAMPFLOS = "+";
	public static final String VERLUST_KAMPFLOS = "-";
	public static final String VERLUST_KAMPFLOS_BEIDE = "--";
	public static final String TRENNZEICHEN_KAMPFLOS = " / ";
	public static final String PARTIE_GEWINN_WEISS = "1 - 0";
	public static final String PARTIE_GEWINN_SCHWARZ = "0 - 1";
	public static final String PARTIE_GEWINN_PLAYER = PARTIE_GEWINN_WEISS;
	public static final String PARTIE_GEWINN_OPPONENT = PARTIE_GEWINN_SCHWARZ;
	public static final String PARTIE_REMIS = "\u00BD - \u00BD";
	public static final String PARTIE_GEWINN_KAMPFLOS_WEISS = "+ / -";
	public static final String PARTIE_GEWINN_KAMPFLOS_SCHWARZ = "- / +";
	public static final String PARTIE_GEWINN_KAMPFLOS_PLAYER = PARTIE_GEWINN_KAMPFLOS_WEISS;
	public static final String PARTIE_GEWINN_KAMPFLOS_OPPONENT = PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
	public static final String PARTIE_VERLUST_KAMPFLOS_BEIDE = "- / -";
	public static final String KEINE_DWZ = "0000";
	public static final String HTML_LEERZEICHEN = "&nbsp;";
	public static final int MYSQL_PARTIE_GEWINN_WEISS = 1;
	public static final int MYSQL_PARTIE_GEWINN_SCHWARZ = 2;
	public static final int MYSQL_PARTIE_REMIS = 3;
	public static final int MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS = 4;
	public static final int MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ = 5;
	public static final int MYSQL_PARTIE_VERLUST_KAMPFLOS_BEIDE = 6;
	public static final int MYSQL_KEIN_ERGEBNIS = 0;
	public static final int DATABASE_DRIVER = DAOFactory.SQLITE;
	public static final int WINDOW_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 150;
	public static final int WINDOW_WIDTH = setWidth();
	public static final int WINDOW_BOUNDS_X = (Toolkit.getDefaultToolkit().getScreenSize().width
			- TournamentConstants.WINDOW_WIDTH) / 2;
	public static final int WINDOW_BOUNDS_Y = (Toolkit.getDefaultToolkit().getScreenSize().height
			- TournamentConstants.WINDOW_HEIGHT) / 2;
	public static final int SPIELFREI_ID = -2;
	// public static final int TAB_INFO = 0;
	// public static final int TAB_PROPERTIES = 1;
	public static final int TAB_PLAYER_LIST = 0;
	public static final int TAB_TOURNAMENTS_LIST = 1;
	public static final int TAB_ACTIVE_TOURNAMENT = 2;
	public static final String CUT_FORENAME = "20";
	public static final String CUT_SURNAME = "20";
	public static String SPIELFREI = Messages.getString("TurnierKonstanten.13");
	public static String TABLE_COLUMN_PLAYER = Messages.getString("TurnierKonstanten.0");
	public static String TABLE_COLUMN_OLD_DWZ = Messages.getString("TurnierKonstanten.1");
	public static String TABLE_COLUMN_NEW_DWZ = Messages.getString("TurnierKonstanten.2");
	public static String TABLE_COLUMN_OLD_ELO = Messages.getString("TurnierKonstanten.11");
	public static String TABLE_COLUMN_NEW_ELO = Messages.getString("TurnierKonstanten.12");
	public static String TABLE_COLUMN_POINTS = Messages.getString("TurnierKonstanten.3");
	public static String TABLE_COLUMN_SONNEBORNBERGER = Messages.getString("TurnierKonstanten.4");
	public static String TABLE_COLUMN_RANKING = Messages.getString("TurnierKonstanten.5");
	public static String TABLE_COLUMN_ROUND = Messages.getString("TurnierKonstanten.6");
	public static String TABLE_COLUMN_WHITE = Messages.getString("TurnierKonstanten.7");
	public static String TABLE_COLUMN_BLACK = Messages.getString("TurnierKonstanten.8");
	public static String TABLE_COLUMN_RESULT = Messages.getString("TurnierKonstanten.9");
	public static String TABLE_COLUMN_MEETING = Messages.getString("TurnierKonstanten.10");
	public static int TEXTFIELD_WIDTH = 150;
	public static int TEXTFIELD_HEIGHT = 40;
	public static String STANDARD_RATING_LIST_DATE = "";

	public static void setConstantLanguage() {
		TABLE_COLUMN_PLAYER = Messages.getString("TurnierKonstanten.0");
		TABLE_COLUMN_OLD_DWZ = Messages.getString("TurnierKonstanten.1");
		TABLE_COLUMN_NEW_DWZ = Messages.getString("TurnierKonstanten.2");
		TABLE_COLUMN_POINTS = Messages.getString("TurnierKonstanten.3");
		TABLE_COLUMN_SONNEBORNBERGER = Messages.getString("TurnierKonstanten.4");
		TABLE_COLUMN_RANKING = Messages.getString("TurnierKonstanten.5");
		TABLE_COLUMN_ROUND = Messages.getString("TurnierKonstanten.6");
		TABLE_COLUMN_WHITE = Messages.getString("TurnierKonstanten.7");
		TABLE_COLUMN_BLACK = Messages.getString("TurnierKonstanten.8");
		TABLE_COLUMN_RESULT = Messages.getString("TurnierKonstanten.9");
		TABLE_COLUMN_MEETING = Messages.getString("TurnierKonstanten.10");
		TABLE_COLUMN_OLD_ELO = Messages.getString("TurnierKonstanten.11");
		TABLE_COLUMN_NEW_ELO = Messages.getString("TurnierKonstanten.12");
		SPIELFREI = Messages.getString("TurnierKonstanten.13");
	}

	private static int setWidth() {

		final int widthAllowed = Toolkit.getDefaultToolkit().getScreenSize().width;
		final int widthDreamSize = TournamentConstants.WINDOW_HEIGHT * 4 / 3;
		if (widthDreamSize < widthAllowed) {
			return widthDreamSize;
		} else {
			return widthAllowed;
		}

	}

	public static void setTextField(final int width, final int height) {
		TournamentConstants.TEXTFIELD_WIDTH = width;
		TournamentConstants.TEXTFIELD_HEIGHT = height;

	}

	public static void setSpielfrei(String spielfrei) {
		TournamentConstants.SPIELFREI = spielfrei;

	}
}
