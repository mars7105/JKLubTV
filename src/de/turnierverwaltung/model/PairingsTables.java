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
public class PairingsTables {

	private Group gruppe;
	private Game[] partien;
	private Player[] spieler;
	private int spielerAnzahl;
	private int rundenAnzahl;
	private int partienAnzahl;
	private String[] paarungen;

	/**
	 * 
	 * @param gruppe
	 */
	public PairingsTables(Group gruppe) {
		this.gruppe = gruppe;
		initValues();
		createPaarungsTafel();
		createPartien();
	}

	private void createPaarungsTafel() {
		paarungen = new String[rundenAnzahl];
		int caseInt = spielerAnzahl - (1 - (spielerAnzahl % 2));
		switch (caseInt) {
		case 3:
			paarungen[0] = "1-4 2-3";
			paarungen[1] = "4-3 1-2";
			paarungen[2] = "2-4 3-1";
			break;

		case 5:
			paarungen[0] = "1-6 2-5 3-4";
			paarungen[1] = "6-4 5-3 1-2";
			paarungen[2] = "2-6 3-1 4-5";
			paarungen[3] = "6-5 1-4 2-3";
			paarungen[4] = "3-6 4-2 5-1";
			break;

		case 7:
			paarungen[0] = "1-8 2-7 3-6 4-5";
			paarungen[1] = "8-5 6-4 7-3 1-2";
			paarungen[2] = "2-8 3-1 4-7 5-6";
			paarungen[3] = "8-6 7-5 1-4 2-3";
			paarungen[4] = "3-8 4-2 5-1 6-7";
			paarungen[5] = "8-7 1-6 2-5 3-4";
			paarungen[6] = "4-8 5-3 6-2 7-1";
			break;

		case 9:
			paarungen[0] = "1-10 2-9 3-8 4-7 5-6";
			paarungen[1] = "10-6 7-5 8-4 9-3 1-2";
			paarungen[2] = "2-10 3-1 4-9 5-8 6-7";
			paarungen[3] = "10-7 8-6 9-5 1-4 2-3";
			paarungen[4] = "3-10 4-2 5-1 6-9 7-8";
			paarungen[5] = "10-8 9-7 1-6 2-5 3-4";
			paarungen[6] = "4-10 5-3 6-2 7-1 8-9";
			paarungen[7] = "10-9 1-8 2-7 3-6 4-5";
			paarungen[8] = "5-10 6-4 7-3 8-2 9-1";
			break;

		case 11:
			paarungen[0] = "1-12 2-11 3-10 4-9 5-8 6-7";
			paarungen[1] = "12-7 8-6 9-5 10-4 11-3 1-2";
			paarungen[2] = "2-12 3-1 4-11 5-10 6-9 7-8";
			paarungen[3] = "12-8 9-7 10-6 11-5 1-4 2-3";
			paarungen[4] = "3-12 4-2 5-1 6-11 7-10 8-9";
			paarungen[5] = "12-9 10-8 11-7 1-6 2-5 3-4";
			paarungen[6] = "4-12 5-3 6-2 7-1 8-11 9-10";
			paarungen[7] = "12-10 11-9 1-8 2-7 3-6 4-5";
			paarungen[8] = "5-12 6-4 7-3 8-2 9-1 10-11";
			paarungen[9] = "12-11 1-10 2-9 3-8 4-7 5-6";
			paarungen[10] = "6-12 7-5 8-4 9-3 10-2 11-1";
			break;
		case 13:
			paarungen[0] = "1-14 2-13 3-12 4-11 5-10 6-9 7-8";
			paarungen[1] = "14-8 9-7 10-6 11-5 12-4 13-3 1-2";
			paarungen[2] = "2-14 3-1 4-13 5-12 6-11 7-10 8-9";
			paarungen[3] = "14-9 10-8 11-7 12-6 13-5 1-4 2-3";
			paarungen[4] = "3-14 4-2 5-1 6-13 7-12 8-11 9-10";
			paarungen[5] = "14-10 11-9 12-8 13-7 1-6 2-5 3-4";
			paarungen[6] = "4-14 5-3 6-2 7-1 8-13 9-12 10-11";
			paarungen[7] = "14-11 12-10 13-9 1-8 2-7 3-6 4-5";
			paarungen[8] = "5-14 6-4 7-3 8-2 9-1 10-13 11-12";
			paarungen[9] = "14-12 13-11 1-10 2-9 3-8 4-7 5-6";
			paarungen[10] = "6-14 7-5 8-4 9-3 10-2 11-1 12-13";
			paarungen[11] = "14-13 1-12 2-11 3-10 4-9 5-8 6-7";
			paarungen[12] = "7-14 8-6 9-5 10-4 11-3 12-2 13-1";
			break;
		case 15:
			paarungen[0] = "1-16 2-15 3-14 4-13 5-12 6-11 7-10 8-9";
			paarungen[1] = "16-9 10-8 11-7 12-6 13-5 14-4 15-3 1-2";
			paarungen[2] = "2-16 3-1 4-15 5-14 6-13 7-12 8-11 9-10";
			paarungen[3] = "16-10 11-9 12-8 13-7 14-6 15-5 1-4 2-3";
			paarungen[4] = "3-16 4-2 5-1 6-15 7-14 8-13 9-12 10-11";
			paarungen[5] = "16-11 12-10 13-9 14-8 15-7 1-6 2-5 3-4";
			paarungen[6] = "4-16 5-3 6-2 7-1 8-15 9-14 10-13 11-12";
			paarungen[7] = "16-12 13-11 14-10 15-9 1-8 2-7 3-6 4-5";
			paarungen[8] = "5-16 6-4 7-3 8-2 9-1 10-15 11-14 12-13";
			paarungen[9] = "16-13 14-12 15-11 1-10 2-9 3-8 4-7 5-6";
			paarungen[10] = "6-16 7-5 8-4 9-3 10-2 11-1 12-15 13-14";
			paarungen[11] = "16-14 15-13 1-12 2-11 3-10 4-9 5-8 6-7";
			paarungen[12] = "7-16 8-6 9-5 10-4 11-3 12-2 13-1 14-15";
			paarungen[13] = "16-15 1-14 2-13 3-12 4-11 5-10 6-9 7-8";
			paarungen[14] = "8-16 9-7 10-6 11-5 12-4 13-3 14-2 15-1";

			break;
		case 17:
			paarungen[0] = "1-18 2-17 3-16 4-15 5-14 6-13 7-12 8-11 9-10";
			paarungen[1] = "18-10 11-9 12-8 13-7 14-6 15-5 16-4 17-3 1-2";
			paarungen[2] = "2-18 3-1 4-17 5-16 6-15 7-14 8-13 9-12 10-11";
			paarungen[3] = "18-11 12-10 13-9 14-8 15-7 16-6 17-5 1-4 2-3";
			paarungen[4] = "3-18 4-2 5-1 6-17 7-16 8-15 9-14 10-13 11-12";
			paarungen[5] = "18-12 13-11 14-10 15-9 16-8 17-7 1-6 2-5 3-4";
			paarungen[6] = "4-18 5-3 6-2 7-1 8-17 9-16 10-15 11-14 12-13";
			paarungen[7] = "18-13 14-12 15-11 16-10 17-9 1-8 2-7 3-6 4-5";
			paarungen[8] = "5-18 6-4 7-3 8-2 9-1 10-17 11-16 12-15 13-14";
			paarungen[9] = "18-14 15-13 16-12 17-11 1-10 2-9 3-8 4-7 5-6";
			paarungen[10] = "6-18 7-5 8-4 9-3 10-2 11-1 12-17 13-16 14-15";
			paarungen[11] = "18-15 16-14 17-13 1-12 2-11 3-10 4-9 5-8 6-7";
			paarungen[12] = "7-18 8-6 9-5 10-4 11-3 12-2 13-1 14-17 15-16";
			paarungen[13] = "18-16 17-15 1-14 2-13 3-12 4-11 5-10 6-9 7-8";
			paarungen[14] = "8-18 9-7 10-6 11-5 12-4 13-3 14-2 15-1 16-17";
			paarungen[15] = "18-17 1-16 2-15 3-14 4-13 5-12 6-11 7-10 8-9";
			paarungen[16] = "9-18 10-8 11-7 12-6 13-5 14-4 15-3 16-2 17-1";
			break;
		case 19:
			paarungen[0] = "1-20 2-19 3-18 4-17 5-16 6-15 7-14 8-13 9-12 10-11";
			paarungen[1] = "20-11 12-10 13-9 14-8 15-7 16-6 17-5 18-4 19-3 1-2";
			paarungen[2] = "2-20 3-1 4-19 5-18 6-17 7-16 8-15 9-14 10-13 11-12";
			paarungen[3] = "20-12 13-11 14-10 15-9 16-8 17-7 18-6 19-5 1-4 2-3";
			paarungen[4] = "3-20 4-2 5-1 6-19 7-18 8-17 9-16 10-15 11-14 12-13";
			paarungen[5] = "20-13 14-12 15-11 16-10 17-9 18-8 19-7 1-6 2-5 3-4";
			paarungen[6] = "4-20 5-3 6-2 7-1 8-19 9-18 10-17 11-16 12-15 13-14";
			paarungen[7] = "20-14 15-13 16-12 17-11 18-10 19-9 1-8 2-7 3-6 4-5";
			paarungen[8] = "5-20 6-4 7-3 8-2 9-1 10-19 11-18 12-17 13-16 14-15";
			paarungen[9] = "20-15 16-14 17-13 18-12 19-11 1-10 2-9 3-8 4-7 5-6";
			paarungen[10] = "6-20 7-5 8-4 9-3 10-2 11-1 12-19 13-18 14-17 15-16";
			paarungen[11] = "20-16 17-15 18-14 19-13 1-12 2-11 3-10 4-9 5-8 6-7";
			paarungen[12] = "7-20 8-6 9-5 10-4 11-3 12-2 13-1 14-19 15-18 16-17";
			paarungen[13] = "20-17 18-16 19-15 1-14 2-13 3-12 4-11 5-10 6-9 7-8";
			paarungen[14] = "8-20 9-7 10-6 11-5 12-4 13-3 14-2 15-1 16-19 17-18";
			paarungen[15] = "20-18 19-17 1-16 2-15 3-14 4-13 5-12 6-11 7-10 8-9";
			paarungen[16] = "9-20 10-8 11-7 12-6 13-5 14-4 15-3 16-2 17-1 18-19";
			paarungen[17] = "20-19 1-18 2-17 3-16 4-15 5-14 6-13 7-12 8-11 9-10";
			paarungen[18] = "10-20 11-9 12-8 13-7 14-6 15-5 16-4 17-3 18-2 19-1";
		default:

		}

	}

	private void createPartien() {
		if (spielerAnzahl % 2 == 1) {
			Player[] spielerneu = new Player[spielerAnzahl + 1];
			for (int i = 0; i < spielerAnzahl; i++) {
				spielerneu[i] = spieler[i];
			}
			spielerneu[spielerAnzahl] = new Player();
			spielerneu[spielerAnzahl].setName("Spielfrei");
			spielerneu[spielerAnzahl].setKuerzel("SF");
			spielerneu[spielerAnzahl].setDwz("0000");
			spielerneu[spielerAnzahl].setSpielerId(TournamentConstants.SPIELFREI_ID);
			spieler = spielerneu;
			gruppe.setSpieler(spieler);
			spielerAnzahl++;
			gruppe.setSpielerAnzahl(spielerAnzahl);
			partienAnzahl = spielerAnzahl * (spielerAnzahl - 1) / 2;
			Game[] partienNeu = new Game[partienAnzahl];
			partien = partienNeu;
			gruppe.setPartien(partien);
		}
		int partienNummer = 0;
		int spielerWeiss = 0;
		int spielerSchwarz = 0;
		String[] spielerNummer;
		for (int i = 0; i < rundenAnzahl; i++) {
			String[] paarungsFormel = paarungen[i].split("\\s+");
			for (int y = 0; y < paarungsFormel.length; y++) {
				spielerNummer = paarungsFormel[y].split("\\-");
				spielerWeiss = Integer.parseInt(spielerNummer[0]) - 1;
				spielerSchwarz = Integer.parseInt(spielerNummer[1]) - 1;
				partien[partienNummer] = new Game();
				partien[partienNummer].setSpielerWeiss(spieler[spielerWeiss]);
				partien[partienNummer].setSpielerSchwarz(spieler[spielerSchwarz]);
				partien[partienNummer].setRunde(i + 1);
				partienNummer++;
			}

		}
		gruppe.setPartien(partien);
	}

	public Group getGruppe() {
		return gruppe;
	}

	public String[] getPaarungen() {
		return paarungen;
	}

	public Game[] getPartien() {
		return partien;
	}

	public int getPartienAnzahl() {
		return partienAnzahl;
	}

	public int getRundenAnzahl() {
		return rundenAnzahl;
	}

	public Player[] getSpieler() {
		return spieler;
	}

	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}

	private void initValues() {

		this.spieler = gruppe.getSpieler();

		this.spielerAnzahl = gruppe.getSpielerAnzahl();
		this.rundenAnzahl = gruppe.getRundenAnzahl();
		this.partienAnzahl = gruppe.getPartienAnzahl();
		this.partien = new Game[partienAnzahl];
	}

	public void setGruppe(Group gruppe) {
		this.gruppe = gruppe;
	}

	public void setPaarungen(String[] paarungen) {
		this.paarungen = paarungen;
	}

	public void setPartien(Game[] partien) {
		this.partien = partien;
	}

	public void setPartienAnzahl(int partienAnzahl) {
		this.partienAnzahl = partienAnzahl;
	}

	public void setRundenAnzahl(int rundenAnzahl) {
		this.rundenAnzahl = rundenAnzahl;
	}

	public void setSpieler(Player[] spieler) {
		this.spieler = spieler;
	}

	public void setSpielerAnzahl(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}

}
