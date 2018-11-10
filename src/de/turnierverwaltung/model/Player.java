package de.turnierverwaltung.model;

import de.turnierverwaltung.model.rating.DWZData;
import de.turnierverwaltung.model.rating.ELOData;

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
/**
 * Class Spieler
 */
public class Player implements Comparable<Object> {
	public static int cutFname;
	public static int cutSname;
	private String name;
	private String neueDWZ;
	private String kuerzel;
	private Game[] partie;
	private int spielerId;
	private double punkte;
	private int sort;
	private double soberg;
	private int platz;
	private int folgeDWZ;
	private int folgeELO;
	private int age;
	private String forename;
	private String surname;
	private DWZData dwzData;
	private ELOData eloData;

	public Player() {
		dwzData = new DWZData();
		eloData = new ELOData();
		name = "";
		forename = "";
		surname = "";
		kuerzel = "";
		age = 0;
		dwzData.setCsvIndex(-1);
		punkte = 0;
		soberg = 0;
		platz = 1;
		spielerId = -1;
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");

	}

	public Player(final DWZData dwzData) {
		this.dwzData = dwzData;
		eloData = new ELOData();
		spielerId = dwzData.getSpielerId();
		forename = "";
		surname = "";
		kuerzel = "";
		setName(this.dwzData.getCsvSpielername());

		age = this.dwzData.getAge();
		punkte = 0;
		soberg = 0;
		platz = 1;

	}

	public Player(final ELOData eloData2) {
		eloData = eloData2;

		dwzData = new DWZData();
		dwzData.setCsvIndex(-1);
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");
		dwzData.setCsvFIDE_Elo(eloData.getRating());
		dwzData.setCsvFIDE_ID(eloData.getFideid());
		spielerId = eloData2.getSpielerId();
		forename = "";
		surname = "";
		kuerzel = "";
		setName(eloData.getName());

		age = eloData.getAge();
		punkte = 0;
		soberg = 0;
		platz = 1;

	}

	public Player(final int idSpieler, final String name2, final String kuerzel2, final int age2) {
		dwzData = new DWZData();
		eloData = new ELOData();
		forename = "";
		surname = "";
		kuerzel = "";
		setName(name2);

		kuerzel = kuerzel2;
		age = age2;
		dwzData.setCsvIndex(-1);
		punkte = 0;
		soberg = 0;
		platz = 1;
		spielerId = idSpieler;
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");

	}

	public Player(final int idSpieler, final String foreName2, final String surName2, final String kuerzel2,
			final int age2) {
		dwzData = new DWZData();
		eloData = new ELOData();
		forename = "";
		surname = "";
		kuerzel = "";
		setName(surName2 + "," + foreName2);
		kuerzel = kuerzel2;
		age = age2;
		dwzData.setCsvIndex(-1);
		punkte = 0;
		soberg = 0;
		platz = 1;
		spielerId = idSpieler;
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");

	}

	/**
	 *
	 * @param id
	 * @param name
	 * @param kuerzel
	 * @param dwz
	 * @param age
	 * @param dwzindex2
	 */
	public Player(final int id, final String name, final String kuerzel, final String dwz, final int age,
			final String zps, final String mgl, final int dwzindex2) {
		if (id <= TournamentConstants.SPIELFREI_ID) {
			this.name = name;
			spielerId = id;
		} else {
			dwzData = new DWZData();
			eloData = new ELOData();
			spielerId = id;
			dwzData.setSpielerId(id);
			eloData.setSpielerId(id);
			forename = "";
			surname = "";
			this.kuerzel = kuerzel;
			setName(name);

			dwzData.setCsvSpielername(this.name);

			this.age = age;
			punkte = 0;
			soberg = 0;
			platz = 1;
			dwzData.setCsvZPS(zps);
			dwzData.setCsvMgl_Nr(mgl);
			dwzData.setCsvIndex(-1);
			correctMGLNumber();

			setDwz(dwz);
		}
	}

	/**
	 *
	 * @param id
	 * @param forename
	 * @param surname
	 * @param kuerzel
	 * @param dwz
	 * @param age
	 * @param elo
	 * @param fideid
	 * @param age2
	 */
	public Player(final int id, final String forename, final String surname, final String kuerzel, final String dwz,
			final int dwzindex, final int age, final String zps, final String mgl, final int elo, final int fideid) {
		dwzData = new DWZData();
		eloData = new ELOData();
		spielerId = id;
		dwzData.setSpielerId(id);
		dwzData.setCsvFIDE_Elo(elo);
		dwzData.setCsvFIDE_ID(fideid);
		eloData.setSpielerId(id);
		eloData.setRating(elo);
		eloData.setFideid(fideid);
		this.forename = forename;
		this.surname = surname;
		setName(surname + "," + forename);
		this.age = age;
		dwzData.setCsvIndex(dwzindex);
		punkte = 0;
		soberg = 0;
		platz = 1;
		dwzData.setCsvZPS(zps);
		dwzData.setCsvMgl_Nr(mgl);

		correctMGLNumber();
		dwzData.setCsvSpielername(name);
		setDwz(dwz);

	}

	@Override
	public int compareTo(final Object o) {

		final int compareQuantity = ((Player) o).getSort();

		// ascending order
		return compareQuantity - getSort();

		// descending order
	}

	public void copyDWZDataToELOData() {
		eloData = new ELOData();
		eloData.setAge(dwzData.getAge());
		eloData.setFideid(dwzData.getCsvFIDE_ID());
		eloData.setRating(dwzData.getCsvFIDE_Elo());
		eloData.setCountry(dwzData.getCsvFIDE_Land());
		eloData.setName(dwzData.getCsvSpielername());
		eloData.setSpielerId(dwzData.getSpielerId());
		eloData.setTitle(dwzData.getCsvFIDE_Titel());
		eloData.setBirthday(dwzData.getCsvGeburtsjahr());
		// eloData.setSpielerId(dwzData.getSpielerId());
	}

	public void copyDWZDataToPlayer() {
		setName(dwzData.getCsvSpielername());

		age = dwzData.getAge();

		// spielerId = dwzData.getSpielerId();
	}

	public void copyELODataToPlayer() {
		setName(eloData.getName());

		age = eloData.getAge();

		// spielerId = eloData.getSpielerId();
	}

	private void correctMGLNumber() {
		final int length = dwzData.getCsvMgl_Nr().length();
		if (length > 0 && length < 4) {
			final StringBuffer sb = new StringBuffer(dwzData.getCsvMgl_Nr());
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
			dwzData.setCsvMgl_Nr(sb.toString());
		}
		if (dwzData.getCsvMgl_Nr().equals("0000")) {
			dwzData.setCsvMgl_Nr("");

		}
	}

	public void cutForename() {
		if (forename.length() > cutFname) {
			forename = new String(forename.substring(0, cutFname));
		}
		if (cutFname < 3) {
			forename += ".";
		}
	}

	public void cutSurname() {
		if (surname.length() > cutSname) {
			surname = new String(surname.substring(0, cutSname));
		}

	}

	public void extractNameToForenameAndSurename() {
		if (dwzData.getCsvSpielername().length() > 0) {
			name = dwzData.getCsvSpielername();
		}
		if (spielerId > TournamentConstants.SPIELFREI_ID && name.length() > 0) {

			int i = 0;
			final String fullname = name.replaceAll(",", " ").replaceAll("\"", "");
			for (final String retval : fullname.split("\\s")) {
				if (i == 0 && surname.length() == 0) {
					surname = retval;
				}

				if (i == 1 && forename.length() == 0) {
					forename = retval;
				}
				if (i > 1 && forename.length() == 0) {
					forename += " " + retval;
				}
				i++;
			}

		} else {
			forename = "";
			surname = "";
		}

	}

	public void extractNameToKuerzel() {
		if (forename.length() > 0 && surname.length() > 0 && kuerzel.length() == 0) {
			kuerzel = forename.substring(0, 1) + surname.substring(0, 1);
		}

	}

	public int getAge() {
		return age;
	}

	public String getDwz() {
		if (dwzData.getCsvDWZ() > 0) {
			return Integer.toString(dwzData.getCsvDWZ());
		} else {
			return "";
		}
	}

	public int getDWZ() {

		return dwzData.getCsvDWZ();

	}

	public DWZData getDwzData() {
		return dwzData;
	}

	public ELOData getEloData() {
		return eloData;
	}

	public int getFolgeDWZ() {
		return folgeDWZ;
	}

	public int getFolgeELO() {
		return folgeELO;
	}

	public String getForename() {
		return forename;
	}

	public String getKuerzel() {
		return kuerzel;
	}

	public String getName() {

		return name;
	}

	public String getNeueDWZ() {

		return neueDWZ;
	}

	public Game[] getPartie() {
		return partie;
	}

	public int getPlatz() {
		return platz;
	}

	public double getPunkte() {
		return punkte;
	}

	public double getSoberg() {
		return soberg;
	}

	/**
	 *
	 * @return
	 */
	public int getSort() {
//		boolean loop = true;
//
//		while (loop) {
//
//			loop = false;
		sort = (int) (punkte * 1000000 + soberg * 100000 + dwzData.getCsvDWZ());

//		}
		return sort;
	}

	public int getSpielerId() {
		return spielerId;
	}

	public String getSurname() {
		return surname;
	}

	public void setAge(final int age) {
		this.age = age;
	}

	public void setDwz(final int intDWZ) {

		dwzData.setCsvDWZ(intDWZ);
	}

	public void setDwz(final String dwz) {
		try {
			dwzData.setCsvDWZ(Integer.parseInt(dwz));
		} catch (final NumberFormatException e) {
			dwzData.setCsvDWZ(0);
		}
	}

	public void setDwzData(final DWZData dwzData) {
		this.dwzData = dwzData;
		if (spielerId >= 0) {
			this.dwzData.setSpielerId(spielerId);
		}
		age = this.dwzData.getAge();

	}

	public void setEloData(final ELOData eloData) {
		this.eloData = eloData;
		if (spielerId >= 0) {
			this.eloData.setSpielerId(spielerId);
		}
		dwzData.setCsvFIDE_Elo(eloData.getRating());
		dwzData.setCsvFIDE_ID(eloData.getFideid());

	}

	public void setFolgeDWZ(final double folgeDWZ) {
		this.folgeDWZ = (int) folgeDWZ;
	}

	public void setFolgeDWZ(final int folgeDWZ) {
		this.folgeDWZ = folgeDWZ;
	}

	public void setFolgeELO(final int folgeELO) {
		this.folgeELO = folgeELO;
	}

	public void setForename(final String forename) {
		this.forename = forename;
		cutForename();

	}

	public void setKuerzel(final String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public void setName(final String name) {
		this.name = name.replaceAll("\"", "");
		if (eloData != null) {
			if ((eloData.getName().length() > 0)) {
				this.name = eloData.getName().replaceAll("\"", "").replaceAll(" ", "");

			}
		}
		if (dwzData != null) {
			if (dwzData.getCsvSpielername().length() > 0) {
				this.name = dwzData.getCsvSpielername().replaceAll("\"", "");

			}
		}
		if (spielerId > TournamentConstants.SPIELFREI_ID) {
			extractNameToForenameAndSurename();

			cutForename();
			cutSurname();
			extractNameToKuerzel();
			this.name = surname + "," + forename;
		}
	}

	public void setNeueDWZ(final String neueDWZ) {
		this.neueDWZ = neueDWZ;
	}

	public void setPartie(final Game[] partie) {
		this.partie = partie;
	}

	public void setPlatz(final int platz) {
		this.platz = platz;
	}

	public void setPunkte(final double punkte) {
		this.punkte = punkte;
	}

	public void setSoberg(final double soberg) {
		this.soberg = soberg;
	}

	public void setSort(final int sort) {
		this.sort = sort;
	}

	public void setSpielerId(final int spielerId) {
		this.spielerId = spielerId;
		dwzData.setSpielerId(spielerId);
		eloData.setSpielerId(spielerId);
	}

	public void setSurname(final String surname) {
		this.surname = surname;

		cutSurname();
	}

}
