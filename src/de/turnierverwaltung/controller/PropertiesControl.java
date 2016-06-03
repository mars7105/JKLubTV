package de.turnierverwaltung.controller;

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
import java.util.Locale;
import java.util.Properties;
import java.util.prefs.Preferences;

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
	public static final String TURNIEREPROTAB = "TurniereproTab";
	public static final String SPIELERPROTAB = "SpielerproTab";

	private Properties prop;
	private Boolean NoWritableProperties;
	private Preferences prefs;

	public PropertiesControl() {
		super();
		prefs = Preferences.userRoot();
		prop = new Properties();
		prop.setProperty(PATHTODATABASE, "");
		prop.setProperty(ONLYTABLES, FALSE);
		prop.setProperty(NODWZ, FALSE);
		prop.setProperty(NOFOLGEDWZ, FALSE);
		prop.setProperty(ZPS, "");
		prop.setProperty(LANGUAGE, "english");
		prop.setProperty(PATHTOVEREINECSV, "");
		prop.setProperty(TURNIEREPROTAB, "1");
		prop.setProperty(SPIELERPROTAB, "1");

	}

	private void checkProperties() {
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
		if (!(prop.getProperty(LANGUAGE).equals("english") || prop.getProperty(
				LANGUAGE).equals("german"))) {
			prop.setProperty(LANGUAGE, "english");
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
		// checkProperties();
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

	public void setLanguageToEnglish() {
		prop.setProperty(LANGUAGE, "english");
		de.turnierverwaltung.view.Messages.setLocale(new Locale("en", "US"));
		de.turnierverwaltung.controller.Messages.setLocale(new Locale("en",
				"US"));
		de.turnierverwaltung.model.Messages.setLocale(new Locale("en", "US"));
	}

	public void setLanguageToGerman() {
		prop.setProperty(LANGUAGE, "german");
		de.turnierverwaltung.view.Messages.setLocale(new Locale("de", "DE"));
		de.turnierverwaltung.controller.Messages.setLocale(new Locale("de",
				"DE"));
		de.turnierverwaltung.model.Messages.setLocale(new Locale("de", "DE"));

	}

	public void setPathToCVS(String absolutePath) {
		prop.setProperty(PATHTOVEREINECSV, absolutePath);
	}

	public String getPathToCVS() {
		return prop.getProperty(PATHTOVEREINECSV);
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
}
