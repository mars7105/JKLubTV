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
public class EntwicklungskoeffizientModel {
	// http://www.schachbund.de/id-49-die-berechnung-der-folge-dwz.html
	private int age;
	private double entwicklungskoeffizient;
	private double grundwert;
	private double beschleunigungsfaktor;
	private double bremszuschlag;
	private double oldDWZ;
	private double punkte;
	private double punkterwartung;

	public EntwicklungskoeffizientModel(int age, double oldDWZ, double punkte, double punkterwartung) {
		super();
		this.age = age;
		this.oldDWZ = oldDWZ;
		this.punkte = punkte;
		this.punkterwartung = punkterwartung;
		grundwert = grundwertBerechnen();
		beschleunigungsfaktor = beschleunigungsfaktorBerechnen();
		bremszuschlag = bremszuschlagBerechnen();
		entwicklungskoeffizient = grundwert * beschleunigungsfaktor + bremszuschlag;
		// Für E gelten folgende Begrenzungen:
		// Der Wert von E ist stets ganzzahlig gerundet anzusetzen.
		// Er ist abhängig vom Index und muss mindestens 5 betragen.
		// Sein Maximalwert ist 30 bzw. 5 x Index bei SBr = 0, er darf für SBr ≥
		// 0 den Wert von 150 #
		// nicht überschreiten.
		// Für Spieler ohne Wertungszahl wird bei der Ermittlung von E
		// der Index 1 verwendet. Es gilt:
		//
		// E ≥ 5 und E ≤ 30
		//
		// bzw.
		// 5 x Index (für SBr = 0)
		//
		// und
		// E ≤ 150 (SBr ≥ 0)
		begrenzeEntwicklungskoeffizient();
		// Der Wert von E ist stets ganzzahlig gerundet anzusetzen.

		entwicklungskoeffizient = Math.round(entwicklungskoeffizient);

	}

	private void begrenzeEntwicklungskoeffizient() {
		// TODO Auto-generated method stub
		if (entwicklungskoeffizient < 5) {
			entwicklungskoeffizient = 5;
		}
		if (bremszuschlag == 0) {
			// 5 x Index (für SBr = 0)
			// hier bräuchte ich die Index Zahl!
			if (entwicklungskoeffizient > 30) {
				entwicklungskoeffizient = 30;
			}

		} else {

			if (entwicklungskoeffizient > 150) {
				entwicklungskoeffizient = 150;
			}
		}

	}

	private double beschleunigungsfaktorBerechnen() {
		// fB = Ro / 2000 mit 0,5 ≤ fB ≤ 1,0
		// nur für Jugendliche bis 20 Jahre bei W ≥ We, sonst fB = 1.
		double fb = 1;
		if (age == 0 && punkte >= punkterwartung) {
			fb = oldDWZ / 2000;
			if (fb < 0.5 || fb > 1.0) {
				fb = 1;
			}
		}
		return fb;
	}

	private double bremszuschlagBerechnen() {
		// SBr = e (1300-Ro)/150 - 1
		//
		// nur für R0 < 1300 und W ≤ We, sonst SBr = 0.
		//
		// Anmerkung: e, genannt Eulersche Zahl, ist die Basiszahl des
		// natürlichen Logarithmus und hat als Größe die Zahl 2,7 und daran
		// anschließend zweimal das Gründungsjahr der Universität Dresden, also
		// 2,718281828.
		//
		// Für E gelten folgende Begrenzungen:
		// Der Wert von E ist stets ganzzahlig gerundet anzusetzen. Er ist
		// abhängig vom Index und muss mindestens 5 betragen. Sein Maximalwert
		// ist 30 bzw. 5 x Index bei SBr = 0, er darf für SBr ≥ 0 den Wert von
		// 150 nicht überschreiten. Für Spieler ohne Wertungszahl wird bei der
		// Ermittlung von E der Index 1 verwendet. Es gilt:
		// E ≥ 5 und E ≤ 30
		//
		// bzw.
		// 5 x Index (für SBr = 0)
		//
		// und
		// E ≤ 150 (SBr ≥ 0)
		double sBr = 0;
		if (oldDWZ < 1300 && punkte <= punkterwartung) {
			double DWZkleiner1300 = (1300 - oldDWZ) / 150;
			sBr = Math.pow(KonstantenModel.EULERSCHE_ZAHL, DWZkleiner1300) - 1;
		} else {
			sBr = 0;
		}
		return sBr;
	}

	public double getEntwicklungskoeffizient() {
		return entwicklungskoeffizient;
	}

	private double grundwertBerechnen() {
		// E0 = (Ro / 1000 )4 + J
		double j = 0;
		if (age == 0) {
			j = KonstantenModel.ALTER_BIS_20_JAHRE;
		}
		if (age == 1) {
			j = KonstantenModel.ALTER_VON_21_BIS_25_JAHRE;
		}
		if (age == 2) {
			j = KonstantenModel.ALTER_UEBER_25_JAHRE;
		}
		double ageD1000 = oldDWZ / 1000;
		return Math.pow(ageD1000, 4) + j;
	}

}
