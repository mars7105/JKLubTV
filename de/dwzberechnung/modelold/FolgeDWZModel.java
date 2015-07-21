package de.dwzberechnung.modelold;
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
public class FolgeDWZModel {
	// 4.9 Die Berechnung der Folge-DWZ
	// 4.9.1 Die Berechnungsformel
	//
	// Bei Spielern, die bereits eine DWZ besessen haben oder auch ein
	// FIDE-Rating
	// bzw. eine anerkannte, vergleichbare nationale Wertung,
	// wird die alte Wertungszahl Ro mit Hilfe der errechneten Punkterwartung We
	// und einem
	// Entwicklungskoeffizienten E in die neue DWZ = Rn umgerechnet:
	// Rn = Ro + 800 x (W - We) / (E + n)
	int oldDWZ;
	double folgeDWZ;
	double punkte;
	double punkterwartung;
	double entwicklungskoeffizient;
	double anzahlDerGegner;
	public FolgeDWZModel(int oldDWZ, double punkte, double punkterwartung, double entwicklungskoeffizient,
			double anzahlDerGegner) {
		super();
		this.oldDWZ = oldDWZ;
		this.punkte = punkte;
		this.punkterwartung = punkterwartung;
		this.entwicklungskoeffizient = entwicklungskoeffizient;
		this.anzahlDerGegner = anzahlDerGegner;
		// Rn = Ro + 800 x (W - We) / (E + n)
		folgeDWZ = oldDWZ + 800 * (punkte - punkterwartung) / (entwicklungskoeffizient + anzahlDerGegner);
	}
	public double getFolgeDWZ() {
		return folgeDWZ;
	}
	
	

}
