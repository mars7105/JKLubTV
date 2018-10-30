package de.turnierverwaltung.control.tournamenttable;

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

import java.util.ArrayList;

import de.dwzberechnung.model.OpponentModel;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;

public class ResultControl implements Ratingsmethods {

	private final Group gruppe;
	private final Game[] partien;
	private final int spielerAnzahl;
	private final int partienanzahl;
	private ArrayList<OpponentModel> opponents;

	public ResultControl(final Group gruppe) {
		this.gruppe = gruppe;
		partien = this.gruppe.getPartien();
		spielerAnzahl = this.gruppe.getSpielerAnzahl();
		partienanzahl = this.gruppe.getPartienAnzahl();

	}

	public void caculateRatings() {

		for (int loop = 0; loop < 10; loop++) {

			for (int s = 0; s < spielerAnzahl; s++) {
				final Player player = gruppe.getSpieler()[s];
				if (player.getSpielerId() > TournamentConstants.SPIELFREI_ID) {

					opponents = new ArrayList<OpponentModel>();

					final double gesamtpunkte = 0;
					double ergebnis = 0;
					for (int i = 0; i < partienanzahl; i++) {
						final Player whitePlayer = partien[i].getSpielerWeiss();
						final Player blackPlayer = partien[i].getSpielerSchwarz();
						final String ergebnisWhite = partien[i].getErgebnisWeiss();
						final String ergebnisBlack = partien[i].getErgebnisSchwarz();
						if (whitePlayer.equals(player)
								&& blackPlayer.getSpielerId() > TournamentConstants.SPIELFREI_ID) {
							ergebnis = 0;
							if (ergebnisWhite.equals(TournamentConstants.GEWINN)) {
								ergebnis = 1;
								addOpponent(blackPlayer, ergebnis, gesamtpunkte);
							}
							if (ergebnisWhite.equals(TournamentConstants.VERLUST)) {
								ergebnis = 0;
								addOpponent(blackPlayer, ergebnis, gesamtpunkte);
							}
							if (ergebnisWhite.equals(TournamentConstants.REMIS)) {
								ergebnis = 0.5;
								addOpponent(blackPlayer, ergebnis, gesamtpunkte);
							}

						}
						if (blackPlayer.equals(player)
								&& whitePlayer.getSpielerId() > TournamentConstants.SPIELFREI_ID) {
							ergebnis = 0;
							if (ergebnisBlack.equals(TournamentConstants.GEWINN)) {
								ergebnis = 1;
								addOpponent(whitePlayer, ergebnis, gesamtpunkte);
							}
							if (ergebnisBlack.equals(TournamentConstants.VERLUST)) {
								ergebnis = 0;
								addOpponent(whitePlayer, ergebnis, gesamtpunkte);
							}
							if (ergebnisBlack.equals(TournamentConstants.REMIS)) {
								ergebnis = 0.5;
								addOpponent(whitePlayer, ergebnis, gesamtpunkte);
							}

						}
					}

					calcRating(player, gesamtpunkte);

				}
			}
		}

	}

	@Override
	public void addOpponent(final Player player, final double ergebnis, final double gesamtpunkte) {

	}

	@Override
	public void calcRating(final Player player, final double gesamtpunkte) {

	}

	public ArrayList<OpponentModel> getOpponents() {
		return opponents;
	}

	public void setOpponents(final ArrayList<OpponentModel> opponents) {
		this.opponents = opponents;
	}

}
