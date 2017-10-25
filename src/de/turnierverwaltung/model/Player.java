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
/**
 * Class Spieler
 */
public class Player implements Comparable<Object> {
	private String name;
	// private String dwz;
	private String neueDWZ;
	private String kuerzel;
	private Game[] partie;
	private int spielerId;
	private double punkte;
	private int sort;
	private double soberg;
	private int platz;
	private int folgeDWZ;
	private int age;
	private String forename;
	private String surname;
	public static int cutFname;
	public static int cutSname;

	private DWZData dwzData;
	private ELOData eloData;

	public Player() {
		dwzData = new DWZData();
		eloData = new ELOData();
		this.name = "";
		this.forename = "";
		this.surname = "";
		this.kuerzel = "";
		// this.dwz = "";
		this.age = 0;
		dwzData.setCsvIndex(-1);
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.spielerId = -1;
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");
		// this.showPlayer = true;

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
	public Player(int id, String name, String kuerzel, String dwz, int age, String zps, String mgl, int dwzindex2) {
		dwzData = new DWZData();
		eloData = new ELOData();
		this.spielerId = id;
		dwzData.setSpielerId(id);
		eloData.setSpielerId(id);
		this.name = name;
		dwzData.setCsvSpielername(name);
		this.forename = "";
		this.surname = "";
		this.kuerzel = kuerzel;
		// this.dwz = dwz;
		this.age = age;
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		dwzData.setCsvZPS(zps);
		dwzData.setCsvMgl_Nr(mgl);
		// this.showPlayer = true;
		dwzData.setCsvIndex(-1);
		correctMGLNumber();
		extractNameToForenameAndSurename();
		extractNameToKuerzel();
		setDwz(dwz);
		cutForename();
		cutSurname();
		extractForenameAndSurenameToName();
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
	public Player(int id, String forename, String surname, String kuerzel, String dwz, int dwzindex, int age,
			String zps, String mgl, int elo, int fideid) {
		dwzData = new DWZData();
		eloData = new ELOData();
		this.spielerId = id;
		dwzData.setSpielerId(id);
		dwzData.setCsvFIDE_Elo(elo);
		dwzData.setCsvFIDE_ID(fideid);
		eloData.setSpielerId(id);
		eloData.setRating(elo);
		eloData.setFideid(fideid);
		this.forename = forename;
		this.surname = surname;
		this.name = "";
		this.kuerzel = kuerzel;
		// this.dwz = dwz;
		this.age = age;
		dwzData.setCsvIndex(dwzindex);
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		dwzData.setCsvZPS(zps);
		dwzData.setCsvMgl_Nr(mgl);

		extractForenameAndSurenameToName();
		extractNameToKuerzel();
		correctMGLNumber();
		dwzData.setCsvSpielername(this.name);
		setDwz(dwz);
		cutForename();
		cutSurname();
		extractForenameAndSurenameToName();
	}

	public Player(ELOData eloData2) {
		dwzData = new DWZData();
		dwzData.setCsvIndex(-1);
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");

		eloData = eloData2;
		this.spielerId = -1;

		this.name = eloData.getName();
		this.forename = "";
		this.surname = "";
		this.kuerzel = "";
		extractNameToForenameAndSurename();
		extractNameToKuerzel();
		cutForename();
		cutSurname();
		extractForenameAndSurenameToName();
		// this.dwz = "";
		this.age = eloData.getAge();
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;

		// this.showPlayer = true;

	}

	public Player(DWZData dwzData) {
		this.dwzData = dwzData;
		eloData = new ELOData();
		this.spielerId = -1;

		this.name = this.dwzData.getCsvSpielername();
		extractNameToForenameAndSurename();
		extractNameToKuerzel();
		cutForename();
		cutSurname();
		extractForenameAndSurenameToName();
		// this.dwz = Integer.toString(this.dwzData.getCsvDWZ());
		this.age = this.dwzData.getAge();
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;

		// this.showPlayer = true;
	}

	public Player(int idSpieler, String name2, String kuerzel2, int age2) {
		dwzData = new DWZData();
		eloData = new ELOData();
		this.name = name2;
		this.forename = "";
		this.surname = "";
		this.kuerzel = kuerzel2;
		this.age = age2;
		dwzData.setCsvIndex(-1);
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.spielerId = idSpieler;
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");
		extractNameToForenameAndSurename();
		extractNameToKuerzel();
		cutForename();
		cutSurname();
		extractForenameAndSurenameToName();
	}

	public Player(int idSpieler, String foreName2, String surName2, String kuerzel2, int age2) {
		dwzData = new DWZData();
		eloData = new ELOData();
		this.name = "";
		this.forename = foreName2;
		this.surname = surName2;
		this.kuerzel = kuerzel2;
		this.age = age2;
		dwzData.setCsvIndex(-1);
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.spielerId = idSpieler;
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");
		extractForenameAndSurenameToName();
		extractNameToKuerzel();
		cutForename();
		cutSurname();
		extractForenameAndSurenameToName();

	}

	private void correctMGLNumber() {
		int length = dwzData.getCsvMgl_Nr().length();
		if (length > 0 && length < 4) {
			StringBuffer sb = new StringBuffer(dwzData.getCsvMgl_Nr());
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
			// dsbMGLNumber = sb.toString();
			dwzData.setCsvMgl_Nr(sb.toString());
		}
		if (dwzData.getCsvMgl_Nr().equals("0000")) {
			dwzData.setCsvMgl_Nr("");

		}
	}

	@Override
	public int compareTo(Object o) {

		int compareQuantity = ((Player) o).getSort();

		// ascending order
		return compareQuantity - getSort();

		// descending order
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

	public String getKuerzel() {
		return kuerzel;
	}

	public String getName() {
		return name;
	}

	public Game[] getPartie() {
		return partie;
	}

	public int getPlatz() {
		return platz;
	}

	public double getPunkte() {
		return this.punkte;
	}

	public double getSoberg() {
		return soberg;
	}

	/**
	 * 
	 * @return
	 */
	public int getSort() {
		boolean loop = true;

		while (loop) {

			loop = false;
			sort = (int) (this.punkte * 1000000 + this.soberg * 100000 + dwzData.getCsvDWZ());

		}
		return sort;
	}

	public void extractNameToForenameAndSurename() {

		if (spielerId > TournamentConstants.SPIELFREI_ID && name.length() > 0) {
			forename = "";
			surname = "";
			int i = 0;

			for (String retval : name.split("\\s")) {
				if (i == 0) {
					forename = retval;
				}

				if (i == 1) {
					surname = retval;
				}
				if (i > 1) {
					surname += " " + retval;
				}
				i++;
			}

		}

	}

	public void extractForenameAndSurenameToName() {
		if (spielerId > TournamentConstants.SPIELFREI_ID) {
			name = forename + " " + surname;

		}
	}

	public void extractNameToKuerzel() {
		if (forename.length() > 0 && surname.length() > 0 && kuerzel.length() == 0) {
			kuerzel = forename.substring(0, 1) + surname.substring(0, 1);
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

	public int getSpielerId() {
		return spielerId;
	}

	public void setDwz(String dwz) {
		try {
			dwzData.setCsvDWZ(Integer.parseInt(dwz));
		} catch (NumberFormatException e) {
			dwzData.setCsvDWZ(0);
		}
	}

	public void setDwz(int intDWZ) {
		// if (intDWZ > 0) {
		// this.dwz = Integer.toString(intDWZ);
		// }
		dwzData.setCsvDWZ(intDWZ);
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public void setName(String name) {
		this.name = name;
		cutForename();
		cutSurname();

	}

	public void setPartie(Game[] partie) {
		this.partie = partie;
	}

	public void setPlatz(int platz) {
		this.platz = platz;
	}

	public void setPunkte(double punkte) {
		this.punkte = punkte;
	}

	public void setSoberg(double soberg) {
		this.soberg = soberg;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public void setSpielerId(int spielerId) {
		this.spielerId = spielerId;
		dwzData.setSpielerId(spielerId);
		eloData.setSpielerId(spielerId);
	}

	public String getNeueDWZ() {

		return neueDWZ;
	}

	public void setNeueDWZ(String neueDWZ) {
		this.neueDWZ = neueDWZ;
	}

	public int getFolgeDWZ() {
		return folgeDWZ;
	}

	public void setFolgeDWZ(int folgeDWZ) {
		this.folgeDWZ = folgeDWZ;
	}

	public void setFolgeDWZ(double folgeDWZ) {
		this.folgeDWZ = (int) folgeDWZ;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
		cutForename();

	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;

		cutSurname();
	}

	public DWZData getDwzData() {
		return dwzData;
	}

	public void setDwzData(DWZData dwzData) {
		this.dwzData = dwzData;
		this.dwzData.setSpielerId(spielerId);

		this.age = this.dwzData.getAge();

	}

	public ELOData getEloData() {
		return eloData;
	}

	public void setEloData(ELOData eloData) {
		this.eloData = eloData;
		// dwzData = new DWZData();
		dwzData.setCsvIndex(-1);
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");
		this.eloData.setSpielerId(spielerId);

		this.name = eloData.getName();
		this.forename = "";
		this.surname = "";
		this.kuerzel = "";
		extractNameToForenameAndSurename();
		extractNameToKuerzel();

		this.age = eloData.getAge();
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;

	}

}
