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
		this.age = 0;
		dwzData.setCsvIndex(-1);
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.spielerId = -1;
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
	public Player(int id, String name, String kuerzel, String dwz, int age, String zps, String mgl, int dwzindex2) {
		if (id == TournamentConstants.SPIELFREI_ID) {
			this.name = name;
			this.spielerId = id;
		} else {
			dwzData = new DWZData();
			eloData = new ELOData();
			this.spielerId = id;
			dwzData.setSpielerId(id);
			eloData.setSpielerId(id);
			setName(name);

			dwzData.setCsvSpielername(this.name);

			this.age = age;
			this.punkte = 0;
			this.soberg = 0;
			this.platz = 1;
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

		setName(surname + "," + forename);
		this.age = age;
		dwzData.setCsvIndex(dwzindex);
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		dwzData.setCsvZPS(zps);
		dwzData.setCsvMgl_Nr(mgl);

		correctMGLNumber();
		dwzData.setCsvSpielername(this.name);
		setDwz(dwz);

	}

	public Player(ELOData eloData2) {
		eloData = eloData2;

		dwzData = new DWZData();
		dwzData.setCsvIndex(-1);
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");
		dwzData.setCsvFIDE_Elo(eloData.getRating());
		dwzData.setCsvFIDE_ID(eloData.getFideid());
		this.spielerId = eloData2.getSpielerId();
		setName(eloData.getName());

		this.age = eloData.getAge();
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;

	}

	public Player(DWZData dwzData) {
		this.dwzData = dwzData;
		eloData = new ELOData();
		this.spielerId = dwzData.getSpielerId();

		setName(this.dwzData.getCsvSpielername());

		this.age = this.dwzData.getAge();
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;

	}

	public Player(int idSpieler, String name2, String kuerzel2, int age2) {
		dwzData = new DWZData();
		eloData = new ELOData();
		setName(name2);

		this.kuerzel = kuerzel2;
		this.age = age2;
		dwzData.setCsvIndex(-1);
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.spielerId = idSpieler;
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");

	}

	public Player(int idSpieler, String foreName2, String surName2, String kuerzel2, int age2) {
		dwzData = new DWZData();
		eloData = new ELOData();
		setName(surName2 + "," + foreName2);

		this.age = age2;
		dwzData.setCsvIndex(-1);
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.spielerId = idSpieler;
		dwzData.setCsvZPS("");
		dwzData.setCsvMgl_Nr("");

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
//		eloData.setSpielerId(dwzData.getSpielerId());
	}

	public void copyELODataToPlayer() {
		setName(eloData.getName());

		this.age = eloData.getAge();

//		spielerId = eloData.getSpielerId();
	}

	public void copyDWZDataToPlayer() {
		setName(dwzData.getCsvSpielername());

		this.age = dwzData.getAge();

//		spielerId = dwzData.getSpielerId();
	}

	private void correctMGLNumber() {
		int length = dwzData.getCsvMgl_Nr().length();
		if (length > 0 && length < 4) {
			StringBuffer sb = new StringBuffer(dwzData.getCsvMgl_Nr());
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
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
		if (dwzData.getCsvSpielername().length() > 0) {
			name = dwzData.getCsvSpielername();
		}
		if (spielerId > TournamentConstants.SPIELFREI_ID && name.length() > 0) {
			forename = "";
			surname = "";
			int i = 0;
			String fullname = name.replaceAll(",", " ").replaceAll("\"", "");
			for (String retval : fullname.split("\\s")) {
				if (i == 0) {
					surname = retval;
				}

				if (i == 1) {
					forename = retval;
				}
				if (i > 1) {
					forename += " " + retval;
				}
				i++;
			}

		}

	}

	public void extractNameToKuerzel() {
		if (forename.length() > 0 && surname.length() > 0) {
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

		dwzData.setCsvDWZ(intDWZ);
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public void setName(String name) {
		this.name = name.replaceAll("\"", "");
		if ((eloData.getName().length() > 0)) {
			this.name = eloData.getName().replaceAll("\"", "").replaceAll(" ", "");

		}
		if (dwzData.getCsvSpielername().length() > 0) {
			this.name = dwzData.getCsvSpielername().replaceAll("\"", "");

		}

		extractNameToForenameAndSurename();
		cutForename();
		cutSurname();
		extractNameToKuerzel();
		this.name = surname + "," + forename;
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
		if (spielerId >= 0) {
			this.dwzData.setSpielerId(spielerId);
		}
		this.age = this.dwzData.getAge();

	}

	public ELOData getEloData() {
		return eloData;
	}

	public void setEloData(ELOData eloData) {
		this.eloData = eloData;
		if (spielerId >= 0) {
			this.eloData.setSpielerId(spielerId);
		}
		dwzData.setCsvFIDE_Elo(eloData.getRating());
		dwzData.setCsvFIDE_ID(eloData.getFideid());

	}

	public int getFolgeELO() {
		return folgeELO;
	}

	public void setFolgeELO(int folgeELO) {
		this.folgeELO = folgeELO;
	}

}
