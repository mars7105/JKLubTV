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
import java.util.Arrays;
/**
 * 
 * @author mars
 *
 */
public class Group {

	
	private int spielerAnzahl;
	private int partienAnzahl;
	private int rundenAnzahl;
	private int gruppeId;
	private String gruppenName;
	private Player[] spieler;
	private Game[] partien;

	private MeetingTable teminTabelle;
	private CrossTable turnierTabelle;
/**
 * 
 */
	public Group() {
		this.gruppeId = -1;
	}
	/**
	 * 
	 * @param idGruppe
	 * @param gruppenName
	 */
	public Group(int idGruppe, String gruppenName) {
		this.gruppeId = idGruppe;
		this.gruppenName = gruppenName;
	}
	/**
	 * 
	 */
	private void berechnePlatz() {
		Arrays.sort(spieler);
		for (int i = 0; i < getSpielerAnzahl(); i++) {
			spieler[i].setPlatz(i + 1);
		}
		for (int i = 0; i < getSpielerAnzahl() - 1; i++) {
			if (spieler[i].getPunkte() == spieler[i + 1].getPunkte()
					&& spieler[i].getSoberg() == spieler[i + 1].getSoberg()
					) {
				
				spieler[i + 1].setPlatz(spieler[i].getPlatz());
			}
		}

	}
	/**
	 * 
	 */
	public void berechnePunkte() {
		Player weiss;
		Player schwarz;
		String ergWeiss = "";
		String ergSchwarz = "";
		double wPunkte = 0;
		double sPunkte = 0;
		for (int i = 0; i < getSpielerAnzahl(); i++) {
			spieler[i].setPunkte(0);
		}

		for (int i = 0; i < partienAnzahl; i++) {
			weiss = partien[i].getSpielerWeiss();
			schwarz = partien[i].getSpielerSchwarz();
			ergWeiss = partien[i].getErgebnisWeiss();
			wPunkte = weiss.getPunkte()
					+ convertErgebnisStringToDouble(ergWeiss);
			partien[i].getSpielerWeiss().setPunkte(wPunkte);
			ergSchwarz = partien[i].getErgebnisSchwarz();
			sPunkte = schwarz.getPunkte()
					+ convertErgebnisStringToDouble(ergSchwarz);
			partien[i].getSpielerSchwarz().setPunkte(sPunkte);
		}
		berechneSoBerg();
		berechnePlatz();
	}
	/**
	 * 
	 */
	private void berechneSoBerg() {
		double soberg = 0;
		for (int s = 0; s < getSpielerAnzahl(); s++) {
			soberg = 0;
			for (int i = 0; i < getSpielerAnzahl(); i++) {
				for (int p = 0; p < partienAnzahl; p++) {
					if (s != i) {
						if (partien[p].getSpielerWeiss() == spieler[s]
								&& partien[p].getSpielerSchwarz() == spieler[i]) {

							soberg += spieler[i].getPunkte()
									* convertErgebnisStringToDouble(partien[p]
											.getErgebnisWeiss());
						}
						if (partien[p].getSpielerWeiss() == spieler[i]
								&& partien[p].getSpielerSchwarz() == spieler[s]) {

							soberg += spieler[i].getPunkte()
									* convertErgebnisStringToDouble(partien[p]
											.getErgebnisSchwarz());
						}
					}
				}
			}
			spieler[s].setSoberg(soberg);

		}
	}
	/**
	 * 
	 * @param erg
	 * @return
	 */
	private double convertErgebnisStringToDouble(String erg) {
		double ergebniss = 0;
		if (erg == TournamentConstants.REMIS) {
			ergebniss = 0.5;
		}
		if (erg == "1") {
			ergebniss = 1;
		}
		if (erg == "+") {
			ergebniss = 1;
		}
		return ergebniss;
	}

	public int getGruppeId() {
		return gruppeId;
	}

	public String getGruppenName() {
		return gruppenName;
	}

	public Game[] getPartien() {
		return partien;
	}

	public int getPartienAnzahl() {
		partienAnzahl = spielerAnzahl * (spielerAnzahl - 1) / 2;
		return partienAnzahl;
	}

	public int getRundenAnzahl() {
		if (spielerAnzahl % 2 == 0) {
			this.rundenAnzahl = spielerAnzahl - 1;
		} else {
			this.rundenAnzahl = spielerAnzahl;
		}

		return rundenAnzahl;
	}

	public Player[] getSpieler() {
		return spieler;
	}

	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}

	public MeetingTable getTeminTabelle() {
		return teminTabelle;
	}

	public CrossTable getTurnierTabelle() {
		return turnierTabelle;
	}

	public void setGruppeId(int gruppeId) {
		this.gruppeId = gruppeId;
	}

	public void setGruppenName(String gruppenName) {
		this.gruppenName = gruppenName;
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

	public void setTeminTabelle(MeetingTable teminTabelle) {
		this.teminTabelle = teminTabelle;
	}

	public void setTurnierTabelle(CrossTable turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}
}
