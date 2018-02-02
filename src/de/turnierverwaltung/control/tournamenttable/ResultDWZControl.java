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

import de.dwzberechnung.model.MainModel;
import de.dwzberechnung.model.OpponentModel;
import de.dwzberechnung.model.PlayerModel;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.rating.EloRatingSystem;

public class ResultDWZControl {
	class EloOpponent {
		int rating;
		double score;

		public EloOpponent(final int rating, final double score) {
			super();
			this.rating = rating;
			this.score = score;
		}

		public int getRating() {
			return rating;
		}

		public void setRating(final int rating) {
			this.rating = rating;
		}

		public double getScore() {
			return score;
		}

		public void setScore(final double score) {
			this.score = score;
		}

	}

	private final Group gruppe;
	private final Game[] partien;
	private final int spielerAnzahl;
	private final int partienanzahl;
	private final Tournament turnier;
	private ArrayList<OpponentModel> opponents;
	private ArrayList<EloOpponent> eloOpponents;

	public ResultDWZControl(final Tournament turnier, final Group gruppe) {
		this.turnier = turnier;
		this.gruppe = gruppe;
		partien = this.gruppe.getPartien();
		spielerAnzahl = this.gruppe.getSpielerAnzahl();
		partienanzahl = this.gruppe.getPartienAnzahl();

	}

	public void caculateRatings() {

		for (int loop = 0; loop < 10; loop++) {

			for (int s = 0; s < spielerAnzahl; s++) {
				if (gruppe.getSpieler()[s].getSpielerId() != TournamentConstants.SPIELFREI_ID) {

					opponents = new ArrayList<OpponentModel>();
					eloOpponents = new ArrayList<EloOpponent>();
					final Player player = gruppe.getSpieler()[s];
					final double gesamtpunkte = 0;
					double ergebnis = 0;
					for (int i = 0; i < partienanzahl; i++) {

						if (partien[i].getSpielerWeiss().equals(player)
								&& partien[i].getSpielerSchwarz().getSpielerId() != TournamentConstants.SPIELFREI_ID) {
							ergebnis = 0;
							if (partien[i].getErgebnisWeiss().equals(TournamentConstants.GEWINN)) {
								ergebnis = 1;
								addOpponent(partien[i].getSpielerSchwarz(), ergebnis, gesamtpunkte);
							}
							if (partien[i].getErgebnisWeiss().equals(TournamentConstants.VERLUST)) {
								ergebnis = 0;
								addOpponent(partien[i].getSpielerSchwarz(), ergebnis, gesamtpunkte);
							}
							if (partien[i].getErgebnisWeiss().equals(TournamentConstants.REMIS)) {
								ergebnis = 0.5;
								addOpponent(partien[i].getSpielerSchwarz(), ergebnis, gesamtpunkte);
							}

						}
						if (partien[i].getSpielerSchwarz().equals(player)
								&& partien[i].getSpielerWeiss().getSpielerId() != TournamentConstants.SPIELFREI_ID) {
							ergebnis = 0;
							if (partien[i].getErgebnisSchwarz().equals(TournamentConstants.GEWINN)) {
								ergebnis = 1;
								addOpponent(partien[i].getSpielerWeiss(), ergebnis, gesamtpunkte);
							}
							if (partien[i].getErgebnisSchwarz().equals(TournamentConstants.VERLUST)) {
								ergebnis = 0;
								addOpponent(partien[i].getSpielerWeiss(), ergebnis, gesamtpunkte);
							}
							if (partien[i].getErgebnisSchwarz().equals(TournamentConstants.REMIS)) {
								ergebnis = 0.5;
								addOpponent(partien[i].getSpielerWeiss(), ergebnis, gesamtpunkte);
							}

						}
					}
					// DWZ
					if (opponents.size() > 0) {
						final PlayerModel playerdwz = new PlayerModel(player.getAge(), player.getDWZ(),
								opponents.size());
						playerdwz.setPunkte(gesamtpunkte);
						final MainModel mainModel = new MainModel(playerdwz, opponents);
						mainModel.calculateDWZ();
						player.setFolgeDWZ(playerdwz.getFolgeDWZ());
					}
					// ELO
					final EloRatingSystem eloSystem = new EloRatingSystem();
					int eloRating = player.getEloData().getRating();
					if (eloRating <= 0) {
						eloRating = player.getDwzData().getCsvFIDE_Elo();
					}
					if (eloOpponents.size() > 0 && eloRating > 0) {
						int rating = player.getEloData().getRating();
						for (final EloOpponent opponent : eloOpponents) {
							final int newElo = eloSystem.getNewRating(rating, opponent.getRating(), opponent.getScore(),
									false);
							rating = newElo;
						}
						player.setFolgeELO(rating);
					}
				}
			}
		}

	}

	private void addOpponent(final Player player, final double ergebnis, double gesamtpunkte) {
		// DWZ
		if (turnier.getNoFolgeDWZCalc() == false) {
			if (player.getDWZ() == 0) {
				if (player.getFolgeDWZ() > 0) {
					opponents.add(new OpponentModel(player.getFolgeDWZ(), ergebnis));
					gesamtpunkte += ergebnis;

				}
			} else {

				opponents.add(new OpponentModel(player.getDWZ(), ergebnis));
				gesamtpunkte += ergebnis;
			}
		}
		// ELO
		if (turnier.getNoFolgeELOCalc() == false) {
			int eloRating = player.getEloData().getRating();
			if (eloRating <= 0) {
				eloRating = player.getDwzData().getCsvFIDE_Elo();
			}
			if (eloRating > 0) {
				eloOpponents.add(new EloOpponent(eloRating, ergebnis));

			}
		}
	}

}
