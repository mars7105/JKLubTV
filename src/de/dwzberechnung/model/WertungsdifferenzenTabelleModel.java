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
public class WertungsdifferenzenTabelleModel {
	public static final int P = 0;
	public static final int D = 1;
	private double[][] wertungsdifferenzenTabelle;

	public WertungsdifferenzenTabelleModel() {
		wertungsdifferenzenTabelle = new double[2][100];
		tabelleFuellen();
	}

	public double[][] getWertungsdifferenzenTabelle() {
		return wertungsdifferenzenTabelle;
	}

	public void setWertungsdifferenzenTabelle(final double[][] wertungsdifferenzenTabelle) {
		this.wertungsdifferenzenTabelle = wertungsdifferenzenTabelle;
	}

	private void tabelleFuellen() {
		wertungsdifferenzenTabelle[D][1] = -677;
		wertungsdifferenzenTabelle[D][2] = -589;
		wertungsdifferenzenTabelle[D][3] = -538;
		wertungsdifferenzenTabelle[D][4] = -501;
		wertungsdifferenzenTabelle[D][5] = -470;
		wertungsdifferenzenTabelle[D][6] = -444;
		wertungsdifferenzenTabelle[D][7] = -422;
		wertungsdifferenzenTabelle[D][8] = -401;
		wertungsdifferenzenTabelle[D][9] = -383;
		wertungsdifferenzenTabelle[D][10] = -366;
		wertungsdifferenzenTabelle[D][11] = -351;
		wertungsdifferenzenTabelle[D][12] = -336;
		wertungsdifferenzenTabelle[D][13] = -322;
		wertungsdifferenzenTabelle[D][14] = -309;
		wertungsdifferenzenTabelle[D][15] = -296;
		wertungsdifferenzenTabelle[D][16] = -284;
		wertungsdifferenzenTabelle[D][17] = -273;
		wertungsdifferenzenTabelle[D][18] = -262;
		wertungsdifferenzenTabelle[D][19] = -251;
		wertungsdifferenzenTabelle[D][20] = -240;
		wertungsdifferenzenTabelle[D][21] = -230;
		wertungsdifferenzenTabelle[D][22] = -220;
		wertungsdifferenzenTabelle[D][23] = -211;
		wertungsdifferenzenTabelle[D][24] = -202;
		wertungsdifferenzenTabelle[D][25] = -193;
		wertungsdifferenzenTabelle[D][26] = -184;
		wertungsdifferenzenTabelle[D][27] = -175;
		wertungsdifferenzenTabelle[D][28] = -166;
		wertungsdifferenzenTabelle[D][29] = -158;
		wertungsdifferenzenTabelle[D][30] = -149;
		wertungsdifferenzenTabelle[D][31] = -141;
		wertungsdifferenzenTabelle[D][32] = -133;
		wertungsdifferenzenTabelle[D][33] = -125;
		wertungsdifferenzenTabelle[D][34] = -117;
		wertungsdifferenzenTabelle[D][35] = -110;
		wertungsdifferenzenTabelle[D][36] = -102;
		wertungsdifferenzenTabelle[D][37] = -95;
		wertungsdifferenzenTabelle[D][38] = -87;
		wertungsdifferenzenTabelle[D][39] = -80;
		wertungsdifferenzenTabelle[D][40] = -72;
		wertungsdifferenzenTabelle[D][41] = -65;
		wertungsdifferenzenTabelle[D][42] = -57;
		wertungsdifferenzenTabelle[D][43] = -50;
		wertungsdifferenzenTabelle[D][44] = -43;
		wertungsdifferenzenTabelle[D][45] = -36;
		wertungsdifferenzenTabelle[D][46] = -29;
		wertungsdifferenzenTabelle[D][47] = -21;
		wertungsdifferenzenTabelle[D][48] = -14;
		wertungsdifferenzenTabelle[D][49] = -7;
		wertungsdifferenzenTabelle[D][50] = 0;
		wertungsdifferenzenTabelle[D][51] = 7;
		wertungsdifferenzenTabelle[D][52] = 14;
		wertungsdifferenzenTabelle[D][53] = 21;
		wertungsdifferenzenTabelle[D][54] = 29;
		wertungsdifferenzenTabelle[D][55] = 36;
		wertungsdifferenzenTabelle[D][56] = 43;
		wertungsdifferenzenTabelle[D][57] = 50;
		wertungsdifferenzenTabelle[D][58] = 57;
		wertungsdifferenzenTabelle[D][59] = 65;
		wertungsdifferenzenTabelle[D][60] = 72;
		wertungsdifferenzenTabelle[D][61] = 80;
		wertungsdifferenzenTabelle[D][62] = 87;
		wertungsdifferenzenTabelle[D][63] = 95;
		wertungsdifferenzenTabelle[D][64] = 102;
		wertungsdifferenzenTabelle[D][65] = 110;
		wertungsdifferenzenTabelle[D][66] = 117;
		wertungsdifferenzenTabelle[D][67] = 125;
		wertungsdifferenzenTabelle[D][68] = 133;
		wertungsdifferenzenTabelle[D][69] = 141;
		wertungsdifferenzenTabelle[D][70] = 149;
		wertungsdifferenzenTabelle[D][71] = 158;
		wertungsdifferenzenTabelle[D][72] = 166;
		wertungsdifferenzenTabelle[D][73] = 175;
		wertungsdifferenzenTabelle[D][74] = 184;
		wertungsdifferenzenTabelle[D][75] = 193;
		wertungsdifferenzenTabelle[D][76] = 202;
		wertungsdifferenzenTabelle[D][77] = 211;
		wertungsdifferenzenTabelle[D][78] = 220;
		wertungsdifferenzenTabelle[D][79] = 230;
		wertungsdifferenzenTabelle[D][80] = 240;
		wertungsdifferenzenTabelle[D][81] = 251;
		wertungsdifferenzenTabelle[D][82] = 262;
		wertungsdifferenzenTabelle[D][83] = 273;
		wertungsdifferenzenTabelle[D][84] = 284;
		wertungsdifferenzenTabelle[D][85] = 296;
		wertungsdifferenzenTabelle[D][86] = 309;
		wertungsdifferenzenTabelle[D][87] = 322;
		wertungsdifferenzenTabelle[D][88] = 336;
		wertungsdifferenzenTabelle[D][89] = 351;
		wertungsdifferenzenTabelle[D][90] = 366;
		wertungsdifferenzenTabelle[D][91] = 383;
		wertungsdifferenzenTabelle[D][92] = 401;
		wertungsdifferenzenTabelle[D][93] = 422;
		wertungsdifferenzenTabelle[D][94] = 444;
		wertungsdifferenzenTabelle[D][95] = 470;
		wertungsdifferenzenTabelle[D][96] = 501;
		wertungsdifferenzenTabelle[D][97] = 538;
		wertungsdifferenzenTabelle[D][98] = 589;
		wertungsdifferenzenTabelle[D][99] = 677;

		wertungsdifferenzenTabelle[P][1] = 0.01;
		wertungsdifferenzenTabelle[P][2] = 0.02;
		wertungsdifferenzenTabelle[P][3] = 0.03;
		wertungsdifferenzenTabelle[P][4] = 0.04;
		wertungsdifferenzenTabelle[P][5] = 0.05;
		wertungsdifferenzenTabelle[P][6] = 0.06;
		wertungsdifferenzenTabelle[P][7] = 0.07;
		wertungsdifferenzenTabelle[P][8] = 0.08;
		wertungsdifferenzenTabelle[P][9] = 0.09;
		wertungsdifferenzenTabelle[P][10] = 0.10;
		wertungsdifferenzenTabelle[P][11] = 0.11;
		wertungsdifferenzenTabelle[P][12] = 0.12;
		wertungsdifferenzenTabelle[P][13] = 0.13;
		wertungsdifferenzenTabelle[P][14] = 0.14;
		wertungsdifferenzenTabelle[P][15] = 0.15;
		wertungsdifferenzenTabelle[P][16] = 0.16;
		wertungsdifferenzenTabelle[P][17] = 0.17;
		wertungsdifferenzenTabelle[P][18] = 0.18;
		wertungsdifferenzenTabelle[P][19] = 0.19;
		wertungsdifferenzenTabelle[P][20] = 0.20;
		wertungsdifferenzenTabelle[P][21] = 0.21;
		wertungsdifferenzenTabelle[P][22] = 0.22;
		wertungsdifferenzenTabelle[P][23] = 0.23;
		wertungsdifferenzenTabelle[P][24] = 0.24;
		wertungsdifferenzenTabelle[P][25] = 0.25;
		wertungsdifferenzenTabelle[P][26] = 0.26;
		wertungsdifferenzenTabelle[P][27] = 0.27;
		wertungsdifferenzenTabelle[P][28] = 0.28;
		wertungsdifferenzenTabelle[P][29] = 0.29;
		wertungsdifferenzenTabelle[P][30] = 0.30;
		wertungsdifferenzenTabelle[P][31] = 0.31;
		wertungsdifferenzenTabelle[P][32] = 0.32;
		wertungsdifferenzenTabelle[P][33] = 0.33;
		wertungsdifferenzenTabelle[P][34] = 0.34;
		wertungsdifferenzenTabelle[P][35] = 0.35;
		wertungsdifferenzenTabelle[P][36] = 0.36;
		wertungsdifferenzenTabelle[P][37] = 0.37;
		wertungsdifferenzenTabelle[P][38] = 0.38;
		wertungsdifferenzenTabelle[P][39] = 0.39;
		wertungsdifferenzenTabelle[P][40] = 0.40;
		wertungsdifferenzenTabelle[P][41] = 0.41;
		wertungsdifferenzenTabelle[P][42] = 0.42;
		wertungsdifferenzenTabelle[P][43] = 0.43;
		wertungsdifferenzenTabelle[P][44] = 0.44;
		wertungsdifferenzenTabelle[P][45] = 0.45;
		wertungsdifferenzenTabelle[P][46] = 0.46;
		wertungsdifferenzenTabelle[P][47] = 0.47;
		wertungsdifferenzenTabelle[P][48] = 0.48;
		wertungsdifferenzenTabelle[P][49] = 0.49;
		wertungsdifferenzenTabelle[P][50] = 0.50;
		wertungsdifferenzenTabelle[P][51] = 0.51;
		wertungsdifferenzenTabelle[P][52] = 0.52;
		wertungsdifferenzenTabelle[P][53] = 0.53;
		wertungsdifferenzenTabelle[P][54] = 0.54;
		wertungsdifferenzenTabelle[P][55] = 0.55;
		wertungsdifferenzenTabelle[P][56] = 0.56;
		wertungsdifferenzenTabelle[P][57] = 0.57;
		wertungsdifferenzenTabelle[P][58] = 0.58;
		wertungsdifferenzenTabelle[P][59] = 0.59;
		wertungsdifferenzenTabelle[P][60] = 0.60;
		wertungsdifferenzenTabelle[P][61] = 0.61;
		wertungsdifferenzenTabelle[P][62] = 0.62;
		wertungsdifferenzenTabelle[P][63] = 0.63;
		wertungsdifferenzenTabelle[P][64] = 0.64;
		wertungsdifferenzenTabelle[P][65] = 0.65;
		wertungsdifferenzenTabelle[P][66] = 0.66;
		wertungsdifferenzenTabelle[P][67] = 0.67;
		wertungsdifferenzenTabelle[P][68] = 0.68;
		wertungsdifferenzenTabelle[P][69] = 0.69;
		wertungsdifferenzenTabelle[P][70] = 0.70;
		wertungsdifferenzenTabelle[P][71] = 0.71;
		wertungsdifferenzenTabelle[P][72] = 0.72;
		wertungsdifferenzenTabelle[P][73] = 0.73;
		wertungsdifferenzenTabelle[P][74] = 0.74;
		wertungsdifferenzenTabelle[P][75] = 0.75;
		wertungsdifferenzenTabelle[P][76] = 0.76;
		wertungsdifferenzenTabelle[P][77] = 0.77;
		wertungsdifferenzenTabelle[P][78] = 0.78;
		wertungsdifferenzenTabelle[P][79] = 0.79;
		wertungsdifferenzenTabelle[P][80] = 0.80;
		wertungsdifferenzenTabelle[P][81] = 0.81;
		wertungsdifferenzenTabelle[P][82] = 0.82;
		wertungsdifferenzenTabelle[P][83] = 0.83;
		wertungsdifferenzenTabelle[P][84] = 0.84;
		wertungsdifferenzenTabelle[P][85] = 0.85;
		wertungsdifferenzenTabelle[P][86] = 0.86;
		wertungsdifferenzenTabelle[P][87] = 0.87;
		wertungsdifferenzenTabelle[P][88] = 0.88;
		wertungsdifferenzenTabelle[P][89] = 0.89;
		wertungsdifferenzenTabelle[P][90] = 0.90;
		wertungsdifferenzenTabelle[P][91] = 0.91;
		wertungsdifferenzenTabelle[P][92] = 0.92;
		wertungsdifferenzenTabelle[P][93] = 0.93;
		wertungsdifferenzenTabelle[P][94] = 0.94;
		wertungsdifferenzenTabelle[P][95] = 0.95;
		wertungsdifferenzenTabelle[P][96] = 0.96;
		wertungsdifferenzenTabelle[P][97] = 0.97;
		wertungsdifferenzenTabelle[P][98] = 0.98;
		wertungsdifferenzenTabelle[P][99] = 0.99;

	}

}
