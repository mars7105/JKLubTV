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

import de.turnierverwaltung.model.table.CrossTable;
import de.turnierverwaltung.model.table.MeetingTable;

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
		gruppeId = -1;
	}

	/**
	 *
	 * @param idGruppe
	 * @param gruppenName
	 */
	public Group(final int idGruppe, final String gruppenName) {
		gruppeId = idGruppe;
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
					&& spieler[i].getSoberg() == spieler[i + 1].getSoberg()) {

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
			wPunkte = weiss.getPunkte() + convertErgebnisStringToDouble(ergWeiss);
			partien[i].getSpielerWeiss().setPunkte(wPunkte);
			ergSchwarz = partien[i].getErgebnisSchwarz();
			sPunkte = schwarz.getPunkte() + convertErgebnisStringToDouble(ergSchwarz);
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
						if (partien[p].getSpielerWeiss().equals(spieler[s])
								&& partien[p].getSpielerSchwarz().equals(spieler[i])) {

							soberg += spieler[i].getPunkte()
									* convertErgebnisStringToDouble(partien[p].getErgebnisWeiss());
						}
						if (partien[p].getSpielerWeiss().equals(spieler[i])
								&& partien[p].getSpielerSchwarz().equals(spieler[s])) {

							soberg += spieler[i].getPunkte()
									* convertErgebnisStringToDouble(partien[p].getErgebnisSchwarz());
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
	private double convertErgebnisStringToDouble(final String erg) {
		double ergebniss = 0;
		if (erg.equals(TournamentConstants.REMIS)) {
			ergebniss = 0.5;
		}
		if (erg.equals(TournamentConstants.GEWINN)) {
			ergebniss = 1;
		}
		if (erg.equals(TournamentConstants.GEWINN_KAMPFLOS)) {
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
		if (partien == null) {
			partienAnzahl = spielerAnzahl * (spielerAnzahl - 1) / 2;
		} else {
			partienAnzahl = partien.length;
		}
		return partienAnzahl;
	}

	public int getRatedPlayersCount() {
		int ratedPlayers = 0;
		for (int i = 0; i < spielerAnzahl; i++) {
			if (spieler[i].getEloData().getRating() > 0) {
				ratedPlayers++;
			}
		}
		return ratedPlayers;
	}

	public int getRundenAnzahl() {
		try {
			if (partien == null) {
				if (spielerAnzahl % 2 == 0) {
					rundenAnzahl = spielerAnzahl - 1;
				} else {
					rundenAnzahl = spielerAnzahl;
				}
			} else {
				int pAnzahl = 0;
				for (final Game partie : partien) {
					if (partie.getRunde() > pAnzahl) {
						pAnzahl = partie.getRunde();
					}
				}
				rundenAnzahl = pAnzahl;
			}
		} catch (NullPointerException e1) {
			Formeln formeln = new Formeln();
			rundenAnzahl = formeln.getRundenAnzahl(spielerAnzahl);
		}
		return rundenAnzahl;
	}

	public Player[] getSpieler() {
		return spieler;
	}

	public int getSpielerAnzahl() {
		spielerAnzahl = spieler.length;
		return spielerAnzahl;
	}

	public MeetingTable getTeminTabelle() {
		return teminTabelle;
	}

	public CrossTable getTurnierTabelle() {
		return turnierTabelle;
	}

	public void setGruppeId(final int gruppeId) {
		this.gruppeId = gruppeId;
	}

	public void setGruppenName(final String gruppenName) {
		this.gruppenName = gruppenName;
	}

	public void setPartien(final Game[] partien) {
		this.partien = partien;
	}

	public void setPartienAnzahl(final int partienAnzahl) {
		this.partienAnzahl = partienAnzahl;
	}

	public void setRundenAnzahl(final int rundenAnzahl) {
		this.rundenAnzahl = rundenAnzahl;
	}

	public void setSpieler(final Player[] spieler) {
		this.spieler = spieler;
	}

	public void setSpielerAnzahl(final int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}

	public void setTeminTabelle(final MeetingTable teminTabelle) {
		this.teminTabelle = teminTabelle;
	}

	public void setTurnierTabelle(final CrossTable turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}
}
