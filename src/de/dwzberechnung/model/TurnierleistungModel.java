package de.dwzberechnung.model;

import java.util.ArrayList;

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
public class TurnierleistungModel {
	/**
	 * http://www.schachbund.de/id-48-die-berechnung-der-turnierleistung-rh.html
	 * Errechnung der Turnierleistungszahl
	 * 
	 */
	public TurnierleistungModel() {

	}

	/**
	 * 
	 * @param player
	 *            : Der Spieler, der seine Leistungszahl errechnen will
	 * @param opponents
	 *            : Alle Gegner
	 * @return : Die Leistungszahl
	 */
	public double calcTurnierLeistung(PlayerModel player, ArrayList<OpponentModel> opponents) {
		// 4.7.2.1.2 Extremresultate: Boolean extremResultat = false;
		// http://www.schachbund.de/id-47-die-berechnung-der-punkterwartung.html
		Boolean extremResultat = false;
		// Tabelle Anhang 2.2 Wertungsdifferenzen abhängig von
		// den Gewinnprozenten P
		// http://www.schachbund.de/anhang-22.html
		WertungsdifferenzenTabelleModel wdTabelle = new WertungsdifferenzenTabelleModel();
		double[][] wertungsdifferenzenTabelle = wdTabelle.getWertungsdifferenzenTabelle();

		// nach 4.7.2 We für bisher ungewertete Spieler
		// Quelle:
		// http://www.schachbund.de/id-47-die-berechnung-der-punkterwartung.html

		int playerdwz = player.getOldDWZ();
		if (player.getOldDWZ() == 0 && opponents.size() >= 5) {
			ErstDWZModel edwz = new ErstDWZModel(player.getPunkte(), player.getNumberOfOpponents(),
					player.getDurchschnittderGegnerDWZ());
			playerdwz = (int) edwz.getErstDWZ();
		}

		PlayerModel leistungszahlSpieler = new PlayerModel(player.getAge(), playerdwz, player.getNumberOfOpponents());
		leistungszahlSpieler.setAge(player.getAge());
		leistungszahlSpieler.setDurchschnittderGegnerDWZ(player.getDurchschnittderGegnerDWZ());
		leistungszahlSpieler.setPunkte(player.getPunkte());
		leistungszahlSpieler.setPunkterwartung(player.getPunkterwartung());

		// 100% Gewinn (4.7.2.1.2 Extremresultate)
		// http://www.schachbund.de/id-47-die-berechnung-der-punkterwartung.html
		if (player.getNumberOfOpponents() == player.getPunkte()) {
			leistungszahlSpieler.setOldDWZ((int) (player.getDurchschnittderGegnerDWZ()) + 677);
			extremResultat = true;
		}
		// 0% Gewinn (4.7.2.1.2 Extremresultate)
		// http://www.schachbund.de/id-47-die-berechnung-der-punkterwartung.html
		if (player.getPunkte() == 0) {
			leistungszahlSpieler.setOldDWZ((int) (player.getDurchschnittderGegnerDWZ()) - 677);
			extremResultat = true;
		}
		if (extremResultat == false) {
			double diff = 0;

			for (int i = 1; i < 100; i++) {
				if (leistungszahlSpieler
						.getPunkte() == wertungsdifferenzenTabelle[WertungsdifferenzenTabelleModel.P][i]) {
					diff = wertungsdifferenzenTabelle[WertungsdifferenzenTabelleModel.D][i];
				}
			}
			leistungszahlSpieler.setOldDWZ((int) Math.round(leistungszahlSpieler.getDurchschnittderGegnerDWZ() + diff));

			double pD = 0;
			int dwz = 0;
			int d = 0;
			// Dies ist die Iteration (laut 4.7.2.1.3 Verbesserte erste DWZ
			// durch Iteration)
			// hier für die Berechnung der Leistungszahl.
			// http://www.schachbund.de/id-47-die-berechnung-der-punkterwartung.html

			// do {
			for (int iteration = 0; iteration < 10; iteration++) {
				// Berechnung der Punkteerwartung nach Tabelle Anhang 2.1
				// Wahrscheinlichkeitstabelle
				// http://www.schachbund.de/anhang-21.html
				@SuppressWarnings("unused")
				CalculateWahrscheinlichkeitModel calc = new CalculateWahrscheinlichkeitModel(leistungszahlSpieler,
						opponents);
				dwz = leistungszahlSpieler.getOldDWZ();
				// P(D) - Durchschnitt = (W - We) / n + 0,500
				pD = (leistungszahlSpieler.getPunkte() - leistungszahlSpieler.getPunkterwartung()) / opponents.size()
						+ 0.5;
				pD = (Math.round(100.0 * pD)) / 100.0;
				// In der Tabelle Anhang 2.2 Wertungsdifferenzen abhängig von
				// den Gewinnprozenten P
				// nach der Differenz den Wert herraus suchen
				// http://www.schachbund.de/anhang-22.html
				for (int i = 1; i < 100; i++) {
					if (pD == wertungsdifferenzenTabelle[WertungsdifferenzenTabelleModel.P][i]) {
						d = (int) wertungsdifferenzenTabelle[WertungsdifferenzenTabelleModel.D][i];
					}
				}
				// Formel Ro' = Ro + D
				// http://www.schachbund.de/id-47-die-berechnung-der-punkterwartung.html
				leistungszahlSpieler.setOldDWZ(dwz + d);
				// Abbruchbedingung wenn W und We (annähernd) gleich sind
				// (Punkterwartung == Punkte)
				// verursachte Fehler, deshalb habe ich sie durch eine For
				// Schleife ersetzt
				// http://www.schachbund.de/id-47-die-berechnung-der-punkterwartung.html
				// } while ((Math.round(leistungszahlSpieler.getPunkte() * 100))
				// != (Math.round(leistungszahlSpieler.getPunkterwartung() *
				// 100)));
			}
		}
		// Rückgabe der Leistungszahl
		return leistungszahlSpieler.getOldDWZ();
	}
}
