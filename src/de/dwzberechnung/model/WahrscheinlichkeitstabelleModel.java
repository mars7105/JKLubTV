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
/**
 * @author mars
 *
 */
public class WahrscheinlichkeitstabelleModel {
	// Die unten aufgeführte Wahrscheinlichkeitstabelle für
	// Gewinnerwartungen P(D)
	// beruht auf der sog. Normalverteilung. Sie ist mit der von der FIDE
	// verwendeten Tabelle
	// identisch.
	// Die DWZ-Differenzen ergeben sich aus dem Absolutwert der Differenz
	// zwischen eigener und gegnerischer DWZ. Ist der zu berechnende Spieler
	// besser
	// als sein Gegner, so findet man die Gewinnerwartung P(D) in der Spalte
	// "besser",
	// im umgekehrten Fall in der Spalte "schlechter".
	// Quelle: http://www.schachbund.de/anhang-21.html

	// Die Wahrscheinlichkeitstabelle:
	private double[][] wahrscheinlichkeitsTabelle;

	public WahrscheinlichkeitstabelleModel() {
		// Erstellen der Wahrscheinlichkeitstabelle
		wahrscheinlichkeitsTabelle = new double[KonstantenModel.TABELLENSPALTEN][KonstantenModel.TABELLENZEILEN];
		tabellemitDWZDifferenzenFuellen();
		tabellemitGewinnErwartungenFuellen();
	}

	public double[][] getWahrscheinlichkeitsTabelle() {
		return wahrscheinlichkeitsTabelle;
	}

	private void tabellemitDWZDifferenzenFuellen() {
		// DWZ Differenz von a bis b
		// 0 - 3
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][0] = 0;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][0] = 3;
		// 4 - 10
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][1] = 4;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][1] = 10;
		// 11 - 17
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][2] = 11;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][2] = 17;
		// 18 - 25
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][3] = 18;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][3] = 25;
		// 26 - 32
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][4] = 26;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][4] = 32;
		// 33 - 39
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][5] = 33;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][5] = 39;
		// 40 - 46
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][6] = 40;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][6] = 46;
		// 47 - 53
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][7] = 47;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][7] = 53;
		// 54 - 61
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][8] = 54;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][8] = 61;
		// 62 - 68
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][9] = 62;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][9] = 68;
		// 69 - 76
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][10] = 69;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][10] = 76;
		// 77 - 83
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][11] = 77;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][11] = 83;
		// 84 - 91
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][12] = 84;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][12] = 91;
		// 92 - 98
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][13] = 92;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][13] = 98;
		// 99 - 106
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][14] = 99;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][14] = 106;
		// 107 - 113
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][15] = 107;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][15] = 113;
		// 114 - 121
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][16] = 114;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][16] = 121;
		// 122 - 129
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][17] = 122;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][17] = 129;
		// 130 - 137
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][18] = 130;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][18] = 137;
		// 138 - 145
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][19] = 138;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][19] = 145;
		// 146 - 153
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][20] = 146;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][20] = 153;
		// 154 - 162
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][21] = 154;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][21] = 162;
		// 163 - 170
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][22] = 163;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][22] = 170;
		// 171 - 179
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][23] = 171;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][23] = 179;
		// 180 - 188
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][24] = 180;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][24] = 188;
		// 189 - 197
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][25] = 189;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][25] = 197;
		// 198 - 206
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][26] = 198;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][26] = 206;
		// 207 - 215
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][27] = 207;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][27] = 215;
		// 216 - 225
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][28] = 216;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][28] = 225;
		// 226 - 235
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][29] = 226;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][29] = 235;
		// 236 - 245
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][30] = 236;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][30] = 245;
		// 246 - 256
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][31] = 246;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][31] = 256;
		// 257 - 267
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][32] = 257;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][32] = 267;
		// 268 - 278
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][33] = 268;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][33] = 278;
		// 279 - 290
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][34] = 279;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][34] = 290;
		// 291 - 302
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][35] = 291;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][35] = 302;
		// 303 - 315
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][36] = 303;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][36] = 315;
		// 316 - 328
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][37] = 316;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][37] = 328;
		// 329 - 344
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][38] = 329;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][38] = 344;
		// 345 - 357
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][39] = 345;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][39] = 357;
		// 358 - 374
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][40] = 358;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][40] = 374;
		// 375 - 391
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][41] = 375;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][41] = 391;
		// 392 - 411
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][42] = 392;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][42] = 411;
		// 412 - 432
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][43] = 412;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][43] = 432;
		// 433 - 456
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][44] = 433;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][44] = 456;
		// 457 - 484
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][45] = 457;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][45] = 484;
		// 485 - 517
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][46] = 458;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][46] = 517;
		// 518 - 559
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][47] = 518;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][47] = 559;
		// 560 - 619
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][48] = 560;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][48] = 619;
		// 620 - 735
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_VON][49] = 620;
		wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_DIFFERENZ_BIS][49] = 735;

	}

	private void tabellemitGewinnErwartungenFuellen() {
		double counter = 0.50;
		// Wahrscheinlichkeiten
		for (int i = 0; i < KonstantenModel.TABELLENZEILEN; i++) {
			wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_IST_BESSER][i] = counter;
			wahrscheinlichkeitsTabelle[KonstantenModel.DWZ_IST_SCHLECHTER][i] = 1 - counter;
			counter = counter + 0.01;
		}
	}

}
