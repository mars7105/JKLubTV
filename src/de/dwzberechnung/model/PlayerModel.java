package de.dwzberechnung.model;

//DWZ Rechner - Ein Programm zum Berechnen von DWZ Zahlen von Schach Turnieren
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
public class PlayerModel {
	private int age;
	private int oldDWZ;
	private double folgeDWZ;
	private int numberOfOpponents;
	private double punkterwartung;
	private double durchschnittderGegnerDWZ;
	private double punkte;
	private double leistungsDWZ;
	private double punkterwartung_temp;
	private double entwicklungskoeffizient;

	public PlayerModel(int age, int oldDWZ, int numberOfOpponents) {
		super();
		this.age = age;
		this.oldDWZ = oldDWZ;
		this.numberOfOpponents = numberOfOpponents;
	}

	public int getAge() {
		return age;
	}

	public double getDurchschnittderGegnerDWZ() {
		return durchschnittderGegnerDWZ;
	}

	public double getEntwicklungskoeffizient() {
		return entwicklungskoeffizient;
	}

	public double getFolgeDWZ() {
		return folgeDWZ;
	}

	public double getLeistungsDWZ() {
		return leistungsDWZ;
	}

	public int getNumberOfOpponents() {
		return numberOfOpponents;
	}

	public int getOldDWZ() {
		return oldDWZ;
	}

	public double getPunkte() {
		return punkte;
	}

	public double getPunkterwartung() {
		return punkterwartung;
	}

	public double getPunkterwartung_temp() {
		return punkterwartung_temp;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setDurchschnittderGegnerDWZ(double durchschnittderGegnerDWZ) {
		this.durchschnittderGegnerDWZ = durchschnittderGegnerDWZ;
	}

	public void setEntwicklungskoeffizient(double entwicklungskoeffizient) {
		this.entwicklungskoeffizient = entwicklungskoeffizient;
	}

	public void setFolgeDWZ(double folgeDWZ) {
		this.folgeDWZ = folgeDWZ;
		// Math.round(this.folgeDWZ);
	}

	public void setLeistungsDWZ(double leistungsDWZ) {
		this.leistungsDWZ = leistungsDWZ;
	}

	public void setNumberOfOpponents(int numberOfOpponents) {
		this.numberOfOpponents = numberOfOpponents;
	}

	public void setOldDWZ(int oldDWZ) {
		this.oldDWZ = oldDWZ;
	}

	public void setPunkte(double punkte) {
		this.punkte = punkte;
	}

	public void setPunkterwartung(double punkterwartung) {
		this.punkterwartung = punkterwartung;
	}

	public void setPunkterwartung_temp(double punkterwartung_temp) {
		this.punkterwartung_temp = punkterwartung_temp;
	}

}
