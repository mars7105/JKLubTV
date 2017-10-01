package de.turnierverwaltung.model;

import java.time.LocalDateTime;

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
	private String dwz;
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
	private String dsbZPSNumber;
	private String dsbMGLNumber;
	private Boolean showPlayer;
	private int dwzindex;

	private String fideid;
	private String country;
	private String sex;
	private String title;
	private String w_title;
	private String o_title;
	private String foa_title;
	private String eloRating;
	private String eloGames;
	private String k;
	private String birthday;
	private String flag;

	public Player() {
		this.name = "";
		this.forename = "";
		this.surname = "";
		this.kuerzel = "";
		this.dwz = "";
		this.age = 0;
		this.dwzindex = -1;
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.spielerId = -1;
		this.dsbZPSNumber = "";
		this.dsbMGLNumber = "";
		this.showPlayer = true;
	}

	public Player(String name, String fideid, String country, String sex, String title, String w_title, String o_title,
			String foa_title, String eloRating, String eloGames, String k, String birthday, String flag) {
		super();
		this.name = name;
		this.fideid = fideid;
		this.country = country;
		this.sex = sex;
		this.title = title;
		this.w_title = w_title;
		this.o_title = o_title;
		this.foa_title = foa_title;
		this.eloRating = eloRating;
		this.eloGames = eloGames;
		this.k = k;
		this.birthday = birthday;
		this.flag = flag;
		LocalDateTime now = LocalDateTime.now();
		int years = now.getYear() - Integer.parseInt(birthday);
		if (years < 20) {
			age = 0;
		}
		if (years >= 20 && years <= 25) {
			age = 1;
		}
		if (years > 25) {
			age = 2;
		}
		this.forename = "";
		this.surname = "";
		this.kuerzel = "";
		this.dwz = "";
		this.dwzindex = -1;
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.spielerId = -1;
		this.dsbZPSNumber = "";
		this.dsbMGLNumber = "";
		this.showPlayer = true;
		extractNameToForenameAndSurename();
		extractNameToKuerzel();

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
		this.spielerId = id;
		this.name = name;
		this.forename = "";
		this.surname = "";
		this.kuerzel = kuerzel;
		this.dwz = dwz;
		this.age = age;
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.dsbZPSNumber = zps;
		this.dsbMGLNumber = mgl;
		this.showPlayer = true;
		this.dwzindex = -1;
		correctMGLNumber();
	}

	/**
	 * 
	 * @param id
	 * @param forename
	 * @param surname
	 * @param kuerzel
	 * @param dwz
	 * @param age
	 * @param age2
	 */
	public Player(int id, String forename, String surname, String kuerzel, String dwz, int dwzindex, int age,
			String zps, String mgl) {
		this.spielerId = id;
		this.forename = forename;
		this.surname = surname;
		this.name = "";
		this.kuerzel = kuerzel;
		this.dwz = dwz;
		this.age = age;
		this.dwzindex = dwzindex;
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		this.dsbZPSNumber = zps;
		this.dsbMGLNumber = mgl;
		this.showPlayer = true;
		extractForenameAndSurenameToName();
		extractNameToKuerzel();
		correctMGLNumber();

	}

	private void correctMGLNumber() {
		int length = dsbMGLNumber.length();
		if (length > 0 && length < 4) {
			StringBuffer sb = new StringBuffer(dsbMGLNumber);
			for (int i = length; i < 4; i++) {
				sb.insert(0, "0");
			}
			dsbMGLNumber = sb.toString();

		}
		if (dsbMGLNumber.equals("0000")) {
			dsbMGLNumber = "";

		}
	}

	@Override
	public int compareTo(Object o) {

		// return this.getName().compareTo(((Spieler) o).getName());

		int compareQuantity = ((Player) o).getSort();

		// ascending order
		return compareQuantity - getSort();

		// descending order
		// return compareQuantity - this.quantity;
	}

	public String getDwz() {
		return dwz;
	}

	public int getDWZ() {
		try {
			return Integer.parseInt(dwz);
		} catch (NumberFormatException e) {
			return 0;
		}
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

	public Boolean getShowPlayer() {
		return showPlayer;
	}

	public void setShowPlayer(Boolean showPlayer) {
		this.showPlayer = showPlayer;
	}

	/**
	 * 
	 * @return
	 */
	public int getSort() {
		boolean loop = true;

		while (loop) {

			try {
				loop = false;
				sort = (int) (this.punkte * 1000000 + this.soberg * 100000 + Integer.parseInt(this.dwz));
			} catch (NumberFormatException e) {
				loop = true;
				this.dwz = TournamentConstants.KEINE_DWZ;
			}
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

	public void cutForename(int count) {
		if (forename.length() > count) {
			forename = new String(forename.substring(0, count));
		}
		if (count < 3) {
			forename += ".";
		}
	}

	public void cutSurname(int count) {
		if (surname.length() > count) {
			surname = new String(surname.substring(0, count));
		}

	}

	public int getSpielerId() {
		return spielerId;
	}

	public void setDwz(String dwz) {
		this.dwz = dwz;
	}

	public void setDwz(int intDWZ) {
		if (intDWZ > 0) {
			this.dwz = Integer.toString(intDWZ);
		}
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public void setName(String name) {
		this.name = name;

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
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDsbZPSNumber() {
		return dsbZPSNumber;
	}

	public void setDsbZPSNumber(String dsbZPSNumber) {
		this.dsbZPSNumber = dsbZPSNumber;
	}

	public String getDsbMGLNumber() {
		return dsbMGLNumber;
	}

	public void setDsbMGLNumber(String dsbMGLNumber) {
		this.dsbMGLNumber = dsbMGLNumber;
		correctMGLNumber();
	}

	public int getDwzindex() {
		return dwzindex;
	}

	public void setDwzindex(int dwzindex) {
		this.dwzindex = dwzindex;
	}

	public boolean equals(Object other) {
		if (spielerId == ((Player) other).spielerId && spielerId >= 0) {
			return true;
		} else {
			return false;
		}

	}

	public String getFideid() {
		return fideid;
	}

	public void setFideid(String fideid) {
		this.fideid = fideid;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getW_title() {
		return w_title;
	}

	public void setW_title(String w_title) {
		this.w_title = w_title;
	}

	public String getO_title() {
		return o_title;
	}

	public void setO_title(String o_title) {
		this.o_title = o_title;
	}

	public String getFoa_title() {
		return foa_title;
	}

	public void setFoa_title(String foa_title) {
		this.foa_title = foa_title;
	}

	public String getEloRating() {
		return eloRating;
	}

	public void setEloRating(String eloRating) {
		this.eloRating = eloRating;
	}

	public String getEloGames() {
		return eloGames;
	}

	public void setEloGames(String eloGames) {
		this.eloGames = eloGames;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
