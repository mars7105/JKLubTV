package de.turnierverwaltung.control;

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

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;
import java.util.prefs.Preferences;

import de.turnierverwaltung.model.TurnierKonstanten;

public class PropertiesControl {

	public static final String ONLYTABLES = "onlyTables";
	public static final String NODWZ = "noDWZ";
	public static final String NOFOLGEDWZ = "noFolgeDWZ";
	public static final String PATHTODATABASE = "PathToDatabase";
	public static final String ZPS = "ZPS";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String LANGUAGE = "language";
	public static final String PATHTOVEREINECSV = "PathToCSV";
	public static final String DEFAULTPATH = "defaultPath";
	public static final String TURNIEREPROTAB = "TurniereproTab";
	public static final String SPIELERPROTAB = "SpielerproTab";
	public static final String PACKAGE = "/de/jklubtv";
	public static final String TABLE_COLUMN_OLD_DWZ = "tablecolumn-olddwz";
	public static final String TABLE_COLUMN_NEW_DWZ = "tablecolumn-newdwz";
	public static final String TABLE_COLUMN_POINTS = "tablecolumn-points";
	public static final String TABLE_COLUMN_SONNEBORNBERGER = "tablecolumn-sonnebornberger";
	public static final String TABLE_COLUMN_RANKING = "tablecolumn-ranking";
	public static final String TABLE_COLUMN_WHITE = "tablecolumn-white";
	public static final String TABLE_COLUMN_BLACK = "tablecolumn-black";
	public static final String TABLE_COLUMN_RESULT = "tablecolumn-result";
	public static final String TABLE_COLUMN_MEETING = "tablecolumn-meeting";
	public static final String TABLE_COLUMN_PLAYER = "tablecolumn-player";
	private Properties prop;
	private Boolean NoWritableProperties;
	private Preferences prefs;
	private MainControl mainControl;

	public PropertiesControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		prefs = Preferences.userRoot().node(PACKAGE);
		prop = new Properties();
		prop.setProperty(PATHTODATABASE, "");
		prop.setProperty(ONLYTABLES, FALSE);
		prop.setProperty(NODWZ, FALSE);
		prop.setProperty(NOFOLGEDWZ, FALSE);
		prop.setProperty(ZPS, "");
		prop.setProperty(LANGUAGE, "");
		prop.setProperty(PATHTOVEREINECSV, "");
		prop.setProperty(DEFAULTPATH, "");
		prop.setProperty(TURNIEREPROTAB, "1");
		prop.setProperty(SPIELERPROTAB, "1");
		prop.setProperty(TABLE_COLUMN_OLD_DWZ, "");
		prop.setProperty(TABLE_COLUMN_NEW_DWZ, "");
		prop.setProperty(TABLE_COLUMN_POINTS, "");
		prop.setProperty(TABLE_COLUMN_SONNEBORNBERGER, "");
		prop.setProperty(TABLE_COLUMN_RANKING, "");
		prop.setProperty(TABLE_COLUMN_WHITE, "");
		prop.setProperty(TABLE_COLUMN_BLACK, "");
		prop.setProperty(TABLE_COLUMN_RESULT, "");
		prop.setProperty(TABLE_COLUMN_MEETING, "");
		prop.setProperty(TABLE_COLUMN_PLAYER, "");
	}

	public void checkProperties() {
		Boolean saveChanges = false;
		int turniereProTab = 0;
		int spielerProTab = 0;
		try {
			turniereProTab = Integer.parseInt(prop.getProperty(TURNIEREPROTAB));
		} catch (NumberFormatException e) {
			prop.setProperty(TURNIEREPROTAB, "1");
			turniereProTab = 1;
			saveChanges = true;
		}

		try {
			spielerProTab = Integer.parseInt(prop.getProperty(SPIELERPROTAB));
		} catch (NumberFormatException e) {
			prop.setProperty(SPIELERPROTAB, "1");
			spielerProTab = 1;
			saveChanges = true;
		}
		if (prop.getProperty(PATHTODATABASE) != ""
				&& checkPathToDatabase() == false) {
			prop.setProperty(PATHTODATABASE, "");
			saveChanges = true;
		}
		if (prop.getProperty(PATHTOVEREINECSV) != ""
				&& checkPathToVereineCSV() == false) {
			prop.setProperty(PATHTOVEREINECSV, "");
			saveChanges = true;
		}

		if (prop.getProperty(DEFAULTPATH) != "" && checkDefaultPath() == false) {
			prop.setProperty(DEFAULTPATH, "");
			saveChanges = true;
		}
		if (!(prop.getProperty(ONLYTABLES).equals(TRUE) || prop.getProperty(
				ONLYTABLES).equals(FALSE))) {
			prop.setProperty(ONLYTABLES, FALSE);
			saveChanges = true;
		}
		if (!(prop.getProperty(NODWZ).equals(TRUE) || prop.getProperty(NODWZ)
				.equals(FALSE))) {
			prop.setProperty(NODWZ, FALSE);
			saveChanges = true;
		}
		if (!(prop.getProperty(NOFOLGEDWZ).equals(TRUE) || prop.getProperty(
				NOFOLGEDWZ).equals(FALSE))) {
			prop.setProperty(NOFOLGEDWZ, FALSE);
			saveChanges = true;
		}
		if (!(turniereProTab >= 0 && turniereProTab <= 3)) {
			prop.setProperty(TURNIEREPROTAB, "1");
			saveChanges = true;
		}
		if (!(spielerProTab >= 0 && spielerProTab <= 3)) {
			prop.setProperty(SPIELERPROTAB, "1");
			saveChanges = true;
		}
		if (mainControl.getLanguagePropertiesControl().checkLanguage() == false) {
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_OLD_DWZ) == "") {
			prop.setProperty(TABLE_COLUMN_OLD_DWZ,
					TurnierKonstanten.TABLE_COLUMN_OLD_DWZ);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_NEW_DWZ) == "") {
			prop.setProperty(TABLE_COLUMN_NEW_DWZ,
					TurnierKonstanten.TABLE_COLUMN_NEW_DWZ);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_POINTS) == "") {
			prop.setProperty(TABLE_COLUMN_POINTS,
					TurnierKonstanten.TABLE_COLUMN_POINTS);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_SONNEBORNBERGER) == "") {
			prop.setProperty(TABLE_COLUMN_SONNEBORNBERGER,
					TurnierKonstanten.TABLE_COLUMN_SONNEBORNBERGER);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_RANKING) == "") {
			prop.setProperty(TABLE_COLUMN_RANKING,
					TurnierKonstanten.TABLE_COLUMN_RANKING);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_WHITE) == "") {
			prop.setProperty(TABLE_COLUMN_WHITE,
					TurnierKonstanten.TABLE_COLUMN_WHITE);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_BLACK) == "") {
			prop.setProperty(TABLE_COLUMN_BLACK,
					TurnierKonstanten.TABLE_COLUMN_BLACK);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_RESULT) == "") {
			prop.setProperty(TABLE_COLUMN_RESULT,
					TurnierKonstanten.TABLE_COLUMN_RESULT);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_MEETING) == "") {
			prop.setProperty(TABLE_COLUMN_MEETING,
					TurnierKonstanten.TABLE_COLUMN_MEETING);
			saveChanges = true;
		}
		if (prop.getProperty(TABLE_COLUMN_PLAYER) == "") {
			prop.setProperty(TABLE_COLUMN_PLAYER,
					TurnierKonstanten.TABLE_COLUMN_PLAYER);
			saveChanges = true;
		}

		if (saveChanges == true) {
			writeProperties();
		}
	}

	public Boolean getOnlyTables() {
		if (prop.getProperty(ONLYTABLES).equals(TRUE)) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean getNoDWZ() {
		if (prop.getProperty(NODWZ).equals(TRUE)) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean getNoFolgeDWZ() {
		if (prop.getProperty(NOFOLGEDWZ).equals(TRUE)) {
			return true;
		} else {
			return false;
		}
	}

	public String getZPS() {
		return prop.getProperty(ZPS);
	}

	public Boolean writeProperties() {
		Boolean ok = true;

		// speichern
		StringWriter sw = new StringWriter();
		try {
			prop.store(sw, null);
			prefs.put("properties", sw.toString());
			ok = true;
			NoWritableProperties = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ok = false;
			NoWritableProperties = true;
		}

		return ok;
	}

	public Boolean readProperties() {
		Boolean ok = true;

		// auslesen
		try {
			prop.load(new StringReader(prefs.get("properties", null)));
			checkProperties();
			ok = true;
		} catch (IOException e) {

			ok = false;
		} catch (NullPointerException e) {
			checkProperties();
			writeProperties();
			ok = true;
		}
		return ok;
	}

	private Boolean checkPath(String path) {

		File f = new File(path);

		if (f.exists() && !f.isDirectory()) {
			return true;
		} else {
			return false;
		}

	}

	private Boolean checkDefaultPath() {
		String path = prop.getProperty(DEFAULTPATH);

		File f = new File(path);

		if (f.isDirectory()) {
			return true;
		} else {
			return false;
		}

	}

	public Boolean checkPathToDatabase() {
		String path = prop.getProperty(PATHTODATABASE);
		return checkPath(path);
	}

	public Boolean checkPathToVereineCSV() {
		String path = prop.getProperty(PATHTOVEREINECSV);
		return checkPath(path);

	}

	public String getPathToDatabase() {
		// TODO Auto-generated method stub
		return prop.getProperty(PATHTODATABASE);
	}

	public void setPathToDatabase(String db_PATH) {
		prop.setProperty(PATHTODATABASE, db_PATH);
	}

	public String getPathToVereineCSV() {
		// TODO Auto-generated method stub
		return prop.getProperty(PATHTOVEREINECSV);
	}

	public void setPathToVereineCSV(String vereinecsv_PATH) {
		prop.setProperty(PATHTOVEREINECSV, vereinecsv_PATH);
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public Boolean getNoWritableProperties() {
		return NoWritableProperties;
	}

	public void setNoWritableProperties(Boolean noWritableProperties) {
		NoWritableProperties = noWritableProperties;
	}

	public void setZPS(String zps) {
		prop.setProperty(ZPS, zps);

	}

	public void setTableComumnOldDWZ(String tableString) {
		prop.setProperty(TABLE_COLUMN_OLD_DWZ, tableString);

	}

	public String getTableComumnOldDWZ() {

		return prop.getProperty(TABLE_COLUMN_OLD_DWZ);
	}

	public void setTableComumnNewDWZ(String tableString) {
		prop.setProperty(TABLE_COLUMN_NEW_DWZ, tableString);

	}

	public String getTableComumnNewDWZ() {

		return prop.getProperty(TABLE_COLUMN_NEW_DWZ);
	}

	public void setTableComumnPoints(String tableString) {
		prop.setProperty(TABLE_COLUMN_POINTS, tableString);

	}

	public String getTableComumnPoints() {

		return prop.getProperty(TABLE_COLUMN_POINTS);
	}

	public void setTableComumnSonnebornBerger(String tableString) {
		prop.setProperty(TABLE_COLUMN_SONNEBORNBERGER, tableString);

	}

	public String getTableComumnSonnebornBerger() {

		return prop.getProperty(TABLE_COLUMN_SONNEBORNBERGER);
	}

	public void setTableComumnRanking(String tableString) {
		prop.setProperty(TABLE_COLUMN_RANKING, tableString);

	}

	public String getTableComumnRanking() {

		return prop.getProperty(TABLE_COLUMN_RANKING);
	}

	public void setTableComumnWhite(String tableString) {
		prop.setProperty(TABLE_COLUMN_WHITE, tableString);

	}

	public String getTableComumnWhite() {

		return prop.getProperty(TABLE_COLUMN_WHITE);
	}

	public void setTableComumnBlack(String tableString) {
		prop.setProperty(TABLE_COLUMN_BLACK, tableString);

	}

	public String getTableComumnBlack() {

		return prop.getProperty(TABLE_COLUMN_BLACK);
	}

	public void setTableComumnResult(String tableString) {
		prop.setProperty(TABLE_COLUMN_RESULT, tableString);

	}

	public String getTableComumnResult() {

		return prop.getProperty(TABLE_COLUMN_RESULT);
	}

	public void setTableComumnMeeting(String tableString) {
		prop.setProperty(TABLE_COLUMN_MEETING, tableString);

	}

	public String getTableComumnMeeting() {

		return prop.getProperty(TABLE_COLUMN_MEETING);
	}

	public void setTableComumnPlayer(String tableString) {
		prop.setProperty(TABLE_COLUMN_PLAYER, tableString);

	}

	public String getTableComumnPlayer() {

		return prop.getProperty(TABLE_COLUMN_PLAYER);
	}

	public void setOnlyTables(boolean b) {
		if (b == true) {
			prop.setProperty(ONLYTABLES, TRUE);
		} else {
			prop.setProperty(ONLYTABLES, FALSE);
		}

	}

	public void setNoDWZ(Boolean noDWZWert) {
		if (noDWZWert == true) {
			prop.setProperty(NODWZ, TRUE);
		} else {
			prop.setProperty(NODWZ, FALSE);
		}
	}

	public void setNoFolgeDWZ(Boolean noDWZWert) {
		if (noDWZWert == true) {
			prop.setProperty(NOFOLGEDWZ, TRUE);
		} else {
			prop.setProperty(NOFOLGEDWZ, FALSE);
		}
	}

	public String getLanguage() {
		// TODO Auto-generated method stub
		return prop.getProperty(LANGUAGE);
	}

	public void setPathToCVS(String absolutePath) {
		prop.setProperty(PATHTOVEREINECSV, absolutePath);
	}

	public String getPathToCVS() {
		return prop.getProperty(PATHTOVEREINECSV);
	}

	public void setDefaultPath(String absolutePath) {
		prop.setProperty(DEFAULTPATH, absolutePath);
	}

	public String getDefaultPath() {
		return prop.getProperty(DEFAULTPATH);
	}

	public void setTurniereProTab(int anzahlprotab) {
		prop.setProperty(TURNIEREPROTAB, new Integer(anzahlprotab).toString());
	}

	public int getTurniereProTab() {
		try {
			return Integer.parseInt(prop.getProperty(TURNIEREPROTAB));
		} catch (NumberFormatException e) {
			return 1;
		}
	}

	public void setSpielerProTab(int anzahlprotab) {

		prop.setProperty(SPIELERPROTAB, new Integer(anzahlprotab).toString());

	}

	public int getSpielerProTab() {
		try {
			return Integer.parseInt(prop.getProperty(SPIELERPROTAB));
		} catch (NumberFormatException e) {
			return 1;
		}
	}

	public void setLanguage(String language) {
		prop.setProperty(LANGUAGE, language);
	}

}
