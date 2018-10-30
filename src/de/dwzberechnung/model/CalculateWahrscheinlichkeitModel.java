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
/**
 * @author mars
 *
 */
public class CalculateWahrscheinlichkeitModel {
	private double[][] wahrscheinlichkeitsTabelle;
	private PlayerModel player;
	private ArrayList<OpponentModel> opponents;

	/**
	 * Konrtuktor mit Parameter die Wahrscheinlichkeitstabelle wird per Parameter
	 * übergeben
	 * 
	 * @param wahrscheinlichkeitsTabelle
	 */

	/**
	 * @param player
	 * @param opponents
	 */
	public CalculateWahrscheinlichkeitModel(PlayerModel player, ArrayList<OpponentModel> opponents) {
		super();
		this.player = player;
		this.opponents = opponents;
		WahrscheinlichkeitstabelleModel wt = new WahrscheinlichkeitstabelleModel();
		wahrscheinlichkeitsTabelle = wt.getWahrscheinlichkeitsTabelle();

		// Berechnung der Gewinnerwartung und der Punkterwartung
		double punkterwartung = 0;
		double gewinnerwartung = 0;
		for (OpponentModel opp : this.opponents) {
			gewinnerwartung = getWahrscheinlichkeit(this.player.getOldDWZ(), opp.getDwz());
			opp.setGewinnerwartung(gewinnerwartung);
			punkterwartung += gewinnerwartung;

		}
		this.player.setPunkterwartung(punkterwartung);
		// this.player.setNumberOfOpponents(this.opponents.size());
	}

	/**
	 * * Dies ist die Hauptroutine in der die Gewinnerwartung anhand der Tabelle vom
	 * DSB (http://www.schachbund.de/anhang-21.html) errechnet wird.
	 * 
	 * @param dwzPlayer
	 *            : Die DWZ des Spielers
	 * @param dwzOpponent
	 *            : Die DWZ des Gegners
	 * @return gewinnErwartungPD : Die prozentuale Gewinnerwartung
	 */
	private double getWahrscheinlichkeit(int dwzPlayer, int dwzOpponent) {
		// Für den Fall eines Fehlers initialisiere ich
		// die Variablen mit korrektenen Werten
		int spielerStaerke = KonstantenModel.DWZ_IST_BESSER;
		int dwzDifferenz = 0;
		double gewinnErwartungPD = 0.500;
		boolean allesInOrdnung = false;

		if (dwzPlayer > dwzOpponent) {
			// Wenn der Spieler bessser als sein Gegner ist...
			spielerStaerke = KonstantenModel.DWZ_IST_BESSER;
			dwzDifferenz = dwzPlayer - dwzOpponent;
			allesInOrdnung = true;
		} else {
			// Wenn der Spieler schlechter als sein Gegner ist...
			spielerStaerke = KonstantenModel.DWZ_IST_SCHLECHTER;
			dwzDifferenz = dwzOpponent - dwzPlayer;
			allesInOrdnung = true;
		}

		// Wenn ein Fehler auftritt soll eine Exception
		// ausgeworfen werden
		if (allesInOrdnung == false) {
			// TODO EXCEPTION auswerfen (allesInOrdnung == false)
		}
		// Wenn die DWZ Differenz kleiner als 736 ist, dann wird
		// die Gewinnerwartung in der Tabelle gesucht.
		if (dwzDifferenz <= KonstantenModel.DWZ_DIFFERENZ_MAXIMUM) {
			allesInOrdnung = false;
			// hier wird in der Tabelle die Gewinnerwartung anhand der DWZ
			// Differenz gesucht.
			for (int i = 0; i < KonstantenModel.TABELLENZEILEN; i++) {
				// Hier ist die Suchabfrage durch die DWZ Differenz
				if (dwzDifferenz >= wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][i]
						&& dwzDifferenz <= wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][i]) {

					gewinnErwartungPD = wahrscheinlichkeitsTabelle[spielerStaerke][i];

					allesInOrdnung = true;

				}

			}
		} else {
			// Wenn die DWZ Differenz größer als 735 ist, dann ist die
			// Wahrscheinlichkeit gleich eins (100%)
			if (spielerStaerke == KonstantenModel.DWZ_IST_BESSER) {
				gewinnErwartungPD = 1.0;
			} else {
				gewinnErwartungPD = 0.0;
			}
			allesInOrdnung = true;
		}

		// Wenn ein Fehler auftritt soll eine Exception
		// ausgeworfen werden
		if (allesInOrdnung == false) {
			// TODO EXCEPTION auswerfen (allesInOrdnung == false)
		}
		return gewinnErwartungPD;
	}

}
