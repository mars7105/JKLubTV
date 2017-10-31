package de.turnierverwaltung.control;

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

public class ResultDWZControl {
	private Group gruppe;
	private Game[] partien;
	private int spielerAnzahl;
	private int partienanzahl;
	private Tournament turnier;

	public ResultDWZControl(Tournament turnier, Group gruppe) {
		this.turnier = turnier;
		this.gruppe = gruppe;
		this.partien = this.gruppe.getPartien();
		this.spielerAnzahl = this.gruppe.getSpielerAnzahl();
		this.partienanzahl = this.gruppe.getPartienAnzahl();

	}

	public void caculateDWZ() {
		if (turnier.getNoFolgeDWZCalc() == false) {

			for (int loop = 0; loop < 10; loop++) {

				for (int s = 0; s < this.spielerAnzahl; s++) {
					if (this.gruppe.getSpieler()[s].getSpielerId() != TournamentConstants.SPIELFREI_ID) {

						ArrayList<OpponentModel> opponents = new ArrayList<OpponentModel>();
						Player player = this.gruppe.getSpieler()[s];
						double gesamtpunkte = 0;
						double ergebnis = 0;
						Boolean check = false;
						for (int i = 0; i < this.partienanzahl; i++) {

							if (partien[i].getSpielerWeiss().equals(player) && partien[i].getSpielerSchwarz()
									.getSpielerId() != TournamentConstants.SPIELFREI_ID) {
								ergebnis = 0;
								check = false;
								if (this.partien[i].getErgebnisWeiss().equals(TournamentConstants.GEWINN)) {
									ergebnis = 1;
									check = true;
								}
								if (this.partien[i].getErgebnisWeiss().equals(TournamentConstants.VERLUST)) {
									ergebnis = 0;
									check = true;
								}
								if (this.partien[i].getErgebnisWeiss().equals(TournamentConstants.REMIS)) {
									ergebnis = 0.5;
									check = true;
								}

								if (check == true) {
									if (this.partien[i].getSpielerSchwarz().getDWZ() == 0) {
										if (this.partien[i].getSpielerSchwarz().getFolgeDWZ() > 0) {
											opponents.add(new OpponentModel(
													this.partien[i].getSpielerSchwarz().getFolgeDWZ(), ergebnis));
											gesamtpunkte += ergebnis;

										}
									} else {

										opponents.add(new OpponentModel(this.partien[i].getSpielerSchwarz().getDWZ(),
												ergebnis));
										gesamtpunkte += ergebnis;
									}
								}
							}
							if (partien[i].getSpielerSchwarz().equals(player) && partien[i].getSpielerWeiss()
									.getSpielerId() != TournamentConstants.SPIELFREI_ID) {
								ergebnis = 0;
								check = false;
								if (this.partien[i].getErgebnisSchwarz().equals(TournamentConstants.GEWINN)) {
									ergebnis = 1;
									check = true;
								}
								if (this.partien[i].getErgebnisSchwarz().equals(TournamentConstants.VERLUST)) {
									ergebnis = 0;
									check = true;
								}
								if (this.partien[i].getErgebnisSchwarz().equals(TournamentConstants.REMIS)) {
									ergebnis = 0.5;
									check = true;
								}

								if (check == true) {
									if (this.partien[i].getSpielerWeiss().getDWZ() == 0) {
										if (this.partien[i].getSpielerWeiss().getFolgeDWZ() > 0) {
											opponents.add(new OpponentModel(
													this.partien[i].getSpielerWeiss().getFolgeDWZ(), ergebnis));
											gesamtpunkte += ergebnis;

										}
									} else {

										opponents.add(new OpponentModel(this.partien[i].getSpielerWeiss().getDWZ(),
												ergebnis));
										gesamtpunkte += ergebnis;
									}
								}
							}
						}

						PlayerModel playerdwz = new PlayerModel(player.getAge(), player.getDWZ(), opponents.size());
						playerdwz.setPunkte(gesamtpunkte);
						MainModel mainModel = new MainModel(playerdwz, opponents);
						mainModel.calculateDWZ();
						player.setFolgeDWZ(playerdwz.getFolgeDWZ());

					}
				}
			}
		}
	}
}
