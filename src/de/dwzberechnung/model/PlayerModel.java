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

	public double getFolgeDWZ() {
		return folgeDWZ;
	}

	public void setFolgeDWZ(double folgeDWZ) {
		this.folgeDWZ = folgeDWZ;
		// Math.round(this.folgeDWZ);
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getOldDWZ() {
		return oldDWZ;
	}

	public void setOldDWZ(int oldDWZ) {
		this.oldDWZ = oldDWZ;
	}

	public int getNumberOfOpponents() {
		return numberOfOpponents;
	}

	public void setNumberOfOpponents(int numberOfOpponents) {
		this.numberOfOpponents = numberOfOpponents;
	}

	public double getPunkterwartung() {
		return punkterwartung;
	}

	public void setPunkterwartung(double punkterwartung) {
		this.punkterwartung = punkterwartung;
	}

	public double getDurchschnittderGegnerDWZ() {
		return durchschnittderGegnerDWZ;
	}

	public void setDurchschnittderGegnerDWZ(double durchschnittderGegnerDWZ) {
		this.durchschnittderGegnerDWZ = durchschnittderGegnerDWZ;
	}

	public double getPunkte() {
		return punkte;
	}

	public void setPunkte(double punkte) {
		this.punkte = punkte;
	}

	public double getLeistungsDWZ() {
		return leistungsDWZ;
	}

	public void setLeistungsDWZ(double leistungsDWZ) {
		this.leistungsDWZ = leistungsDWZ;
	}

	public double getPunkterwartung_temp() {
		return punkterwartung_temp;
	}

	public void setPunkterwartung_temp(double punkterwartung_temp) {
		this.punkterwartung_temp = punkterwartung_temp;
	}

	public double getEntwicklungskoeffizient() {
		return entwicklungskoeffizient;
	}

	public void setEntwicklungskoeffizient(double entwicklungskoeffizient) {
		this.entwicklungskoeffizient = entwicklungskoeffizient;
	}

}
