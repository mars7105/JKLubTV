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
import java.util.ArrayList;
import java.util.ListIterator;

public class MainModel {
	private PlayerModel player;
	private ArrayList<OpponentModel> opponents;
	@SuppressWarnings("unused")
	private CalculateWahrscheinlichkeitModel calcWahrscheinlichkeit;

	public MainModel(PlayerModel player, ArrayList<OpponentModel> opponents) {
		super();
		this.player = player;
		this.opponents = opponents;
		this.player.setNumberOfOpponents(this.opponents.size());

	}

	public void calculateDWZ() {
		if (this.opponents.size() > 0) {
			for (int o = 0; o < 2; o++) {
				calcWahrscheinlichkeit = new CalculateWahrscheinlichkeitModel(this.player, this.opponents);
				double durchschnittsDWZ = 0;
				double punkte = 0;
				for (OpponentModel oppmod : this.opponents) {
					durchschnittsDWZ += oppmod.getDwz();
					punkte += oppmod.getErgebnis();
				}
				durchschnittsDWZ = Math.round(durchschnittsDWZ / opponents.size());
				this.player.setDurchschnittderGegnerDWZ(durchschnittsDWZ);
				this.player.setPunkte(punkte);
				TurnierleistungModel turnierleistungModel = new TurnierleistungModel();
				this.player.setLeistungsDWZ(turnierleistungModel.calcTurnierLeistung(this.player, this.opponents));
				ListIterator<OpponentModel> li = opponents.listIterator();
				int counter = 0;
				while (li.hasNext()) {
					OpponentModel temp = li.next();
					if (temp.getDwz() > 0) {
						counter++;
					}
				}

				if (player.getOldDWZ() == 0) {

					if (counter >= 5) {

						player.setOldDWZ((int) player.getLeistungsDWZ());
						EntwicklungskoeffizientModel ekM = new EntwicklungskoeffizientModel(this.player.getAge(),
								this.player.getOldDWZ(), this.player.getPunkte(), this.player.getPunkterwartung());
						double entwicklungskoeffizient = ekM.getEntwicklungskoeffizient();
						this.player.setEntwicklungskoeffizient(entwicklungskoeffizient);
						FolgeDWZModel fDWZ = new FolgeDWZModel(player.getOldDWZ(), player.getPunkte(),
								this.player.getPunkterwartung(), entwicklungskoeffizient,
								this.player.getNumberOfOpponents());
						this.player.setFolgeDWZ(fDWZ.getFolgeDWZ());
					}
				} else {
					EntwicklungskoeffizientModel ekM = new EntwicklungskoeffizientModel(this.player.getAge(),
							this.player.getOldDWZ(), this.player.getPunkte(), this.player.getPunkterwartung());
					double entwicklungskoeffizient = ekM.getEntwicklungskoeffizient();
					this.player.setEntwicklungskoeffizient(entwicklungskoeffizient);
					FolgeDWZModel fDWZ = new FolgeDWZModel(this.player.getOldDWZ(), this.player.getPunkte(),
							this.player.getPunkterwartung(), entwicklungskoeffizient,
							this.player.getNumberOfOpponents());
					this.player.setFolgeDWZ(fDWZ.getFolgeDWZ());
				}
			}
		}
	}

	public ArrayList<OpponentModel> getOpponents() {
		return opponents;
	}

	public PlayerModel getPlayer() {
		return player;
	}

	public void setOpponents(ArrayList<OpponentModel> opponents) {
		this.opponents = opponents;
	}

	public void setPlayer(PlayerModel player) {
		this.player = player;
	}

}
