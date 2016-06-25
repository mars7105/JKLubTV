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

	public Player() {
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
		spielerId = -1;
	}

	public Player(int id, String name, String kuerzel, String dwz, int age) {
		this.spielerId = id;
		this.name = name;
		this.kuerzel = kuerzel;
		this.dwz = dwz;
		this.age = age;
		this.punkte = 0;
		this.soberg = 0;
		this.platz = 1;
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

	public int getSort() {
		boolean loop = true;

		while (loop) {

			try {
				loop = false;
				sort = (int) (this.punkte * 1000000 + this.soberg * 100000 + Integer
						.parseInt(this.dwz));
			} catch (NumberFormatException e) {
				loop = true;
				this.dwz = TournamentConstants.KEINE_DWZ;
			}
		}
		return sort;
	}

	public int getSpielerId() {
		return spielerId;
	}

	public void setDwz(String dwz) {
		this.dwz = dwz;
	}

	public void setDwz(int intDWZ) {
		this.dwz = Integer.toString(intDWZ);
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

}
