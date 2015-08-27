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

import de.turnierverwaltung.mysql.DAOFactory;

public class TurnierKonstanten {
	public static final String KEIN_ERGEBNIS = " ";
	public static final String GEWINN = "1";
	public static final String VERLUST = "0";
	public static final String REMIS = "\u00BD";
	public static final String TRENNZEICHEN = " - ";
	public static final String GEWINN_KAMPFLOS = "+";
	public static final String VERLUST_KAMPFLOS = "-";
	public static final String TRENNZEICHEN_KAMPFLOS = " / ";
	public static final String PARTIE_GEWINN_WEISS = "1 - 0";
	public static final String PARTIE_GEWINN_SCHWARZ = "0 - 1";
	public static final String PARTIE_REMIS = "\u00BD - \u00BD";
	public static final String PARTIE_GEWINN_KAMPFLOS_WEISS = "+ / -";
	public static final String PARTIE_GEWINN_KAMPFLOS_SCHWARZ = "- / +";
	public static final String KEINE_DWZ = "0000";
	public static final String HTML_LEERZEICHEN = "&nbsp;";
	public static final int MYSQL_PARTIE_GEWINN_WEISS = 1;
	public static final int MYSQL_PARTIE_GEWINN_SCHWARZ = 2;
	public static final int MYSQL_PARTIE_REMIS = 3;
	public static final int MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS = 4;
	public static final int MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ = 5;
	public static final int MYSQL_KEIN_ERGEBNIS = 0;
	public static final int DATABASE_DRIVER = DAOFactory.SQLITE;
	public static final int WINDOW_HEIGHT = Toolkit.getDefaultToolkit()
			.getScreenSize().height - 150;
	public static final int WINDOW_WIDTH = setWidth();
	public static final int WINDOW_BOUNDS_X = (Toolkit.getDefaultToolkit()
			.getScreenSize().width - TurnierKonstanten.WINDOW_WIDTH) / 2;
	public static final int WINDOW_BOUNDS_Y = (Toolkit.getDefaultToolkit()
			.getScreenSize().height - TurnierKonstanten.WINDOW_HEIGHT) / 2;
	public static final int SPIELFREI_ID = -2;

	private static int setWidth() {

		int widthAllowed = Toolkit.getDefaultToolkit().getScreenSize().width;
		int widthDreamSize = TurnierKonstanten.WINDOW_HEIGHT * 4 / 3;
		if (widthDreamSize < widthAllowed) {
			return widthDreamSize;
		} else {
			return widthAllowed;
		}

	}
}
