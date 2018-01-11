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
public class OpponentModel {
	private int dwz;
	private double ergebnis;
	private double gewinnerwartung;

	public OpponentModel(int dwz, double ergebnis) {
		super();
		this.dwz = dwz;
		this.ergebnis = ergebnis;
	}

	public int getDwz() {
		return dwz;
	}

	public double getErgebnis() {
		return ergebnis;
	}

	public double getGewinnerwartung() {
		return gewinnerwartung;
	}

	public void setDwz(int dwz) {
		this.dwz = dwz;
	}

	public void setErgebnis(double ergebnis) {
		this.ergebnis = ergebnis;
	}

	public void setGewinnerwartung(double gewinnerwartung) {
		this.gewinnerwartung = gewinnerwartung;
	}

}
