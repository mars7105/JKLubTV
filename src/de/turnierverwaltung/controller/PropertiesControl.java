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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import java.util.Properties;

public class PropertiesControl {

	public static final String ONLYTABLES = "onlyTables";
	public static final String NODWZ = "noDWZ";
	public static final String NOFOLGEDWZ = "noFolgeDWZ";
	public static final String PATH = "Path";
	public static final String ZPS = "ZPS";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String LANGUAGE = "language";

	private Properties prop;
	private OutputStream output;
	private FileInputStream input;
	private Boolean NoWritableProperties;

	public PropertiesControl() {
		super();
		prop = new Properties();
		prop.setProperty(PATH, "");
		prop.setProperty(ONLYTABLES, FALSE);
		prop.setProperty(NODWZ, FALSE);
		prop.setProperty(NOFOLGEDWZ, FALSE);
		prop.setProperty(ZPS, "");
		prop.setProperty(LANGUAGE, "english");

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
		try {

			output = new FileOutputStream("config.properties");

			prop.store(output, null);

		} catch (IOException io) {
			NoWritableProperties = true;
			return false;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					NoWritableProperties = true;
					return false;
				}
			}

		}
		NoWritableProperties = false;
		return true;
	}

	public Boolean readProperties() {
		try {

			input = new FileInputStream("config.properties");

			// load a properties file
			prop.load(input);

		} catch (IOException ex) {

			return false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					return false;
				}
			}
		}
		return true;
	}

	public Boolean checkPath() {
		String path = prop.getProperty(PATH);
		try {
			File fl = new File(path);
			return fl.exists();
		} catch (NullPointerException e) {
			return false;
		}

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

	public String getPath() {
		// TODO Auto-generated method stub
		return prop.getProperty(PATH);
	}

	public void setPath(String db_PATH) {
		prop.setProperty(PATH, db_PATH);
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
		de.turnierverwaltung.model.Messages.setLocale(new Locale("en",
				"US"));
	}

	public void setLanguageToGerman() {
		prop.setProperty(LANGUAGE, "german");
		de.turnierverwaltung.view.Messages.setLocale(new Locale("de", "DE"));
		de.turnierverwaltung.controller.Messages.setLocale(new Locale("de",
				"DE"));
		de.turnierverwaltung.model.Messages.setLocale(new Locale("de",
				"DE"));

	}
}
