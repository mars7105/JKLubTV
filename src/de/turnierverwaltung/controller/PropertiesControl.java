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
import java.util.Properties;

public class PropertiesControl {
	private Properties prop;
	private OutputStream output;
	private FileInputStream input;
	private Boolean NoWritableProperties;

	public PropertiesControl() {
		super();
		prop = new Properties();
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
		String path = prop.getProperty("Path");
		File fl = new File(path);
		return fl.exists();

	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public void setProperties(String key, String value) {
		this.prop.setProperty(key, value);
	}

	public String getProperties(String key) {
		if (this.prop.getProperty(key) == null) {
			return "";
		} else {
			return this.prop.getProperty(key);
		}
	}

	public Boolean getNoWritableProperties() {
		return NoWritableProperties;
	}

	public void setNoWritableProperties(Boolean noWritableProperties) {
		NoWritableProperties = noWritableProperties;
	}

}
