package de.turnierverwaltung.controller;

import java.util.ArrayList;

import de.dwzberechnung.model.MainModel;
import de.dwzberechnung.model.OpponentModel;
import de.dwzberechnung.model.PlayerModel;
import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.Partie;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.TurnierKonstanten;

public class FolgeDWZController {
	private Gruppe gruppe;
	private Partie[] partien;
	private int spielerAnzahl;
	private int partienanzahl;


	public FolgeDWZController(Gruppe gruppe) {
		this.gruppe = gruppe;
		this.partien = this.gruppe.getPartien();
		this.spielerAnzahl = this.gruppe.getSpielerAnzahl();
		this.partienanzahl = this.gruppe.getPartienAnzahl();

	}

	public void caculateDWZ() {
		int spielfrei = -1;

		for (int i = 0; i < spielerAnzahl; i++) {
			if (this.gruppe.getSpieler()[i].getSpielerId() == TurnierKonstanten.SPIELFREI_ID) {
				spielfrei = i;

			}
		}
		for (int s = 0; s < this.spielerAnzahl; s++) {
			if (s != spielfrei) {

				ArrayList<OpponentModel> opponents = new ArrayList<OpponentModel>();
				Spieler player = this.gruppe.getSpieler()[s];
				int gesamtpunkte = 0;

				for (int i = 0; i < this.partienanzahl; i++) {

					if (partien[i].getSpielerWeiss() == player
							&& partien[i].getSpielerSchwarz().getSpielerId() != TurnierKonstanten.SPIELFREI_ID) {
						double ergebnis = 0;
						if (this.partien[i].getErgebnisWeiss() == TurnierKonstanten.GEWINN) {
							ergebnis = 1;
						}
						if (this.partien[i].getErgebnisWeiss() == TurnierKonstanten.VERLUST) {
							ergebnis = 0;
						}
						if (this.partien[i].getErgebnisWeiss() == TurnierKonstanten.REMIS) {
							ergebnis = 0.5;
						}
						if (this.partien[i].getErgebnisWeiss() == TurnierKonstanten.GEWINN_KAMPFLOS) {
							ergebnis = 1;
						}
						if (this.partien[i].getErgebnisWeiss() == TurnierKonstanten.VERLUST_KAMPFLOS) {
							ergebnis = 0;
						}
						if (this.partien[i].getErgebnisWeiss() != TurnierKonstanten.KEIN_ERGEBNIS) {
							if (this.partien[i].getSpielerSchwarz().getDwz() != TurnierKonstanten.KEINE_DWZ) {
								opponents
										.add(new OpponentModel(this.partien[i].getSpielerSchwarz().getDWZ(), ergebnis));
								gesamtpunkte += ergebnis;
							}
						}
					}
					if (partien[i].getSpielerSchwarz() == player
							&& partien[i].getSpielerWeiss().getSpielerId() != TurnierKonstanten.SPIELFREI_ID) {
						double ergebnis = 0;
						if (this.partien[i].getErgebnisSchwarz() == TurnierKonstanten.GEWINN) {
							ergebnis = 1;
						}
						if (this.partien[i].getErgebnisSchwarz() == TurnierKonstanten.VERLUST) {
							ergebnis = 0;
						}
						if (this.partien[i].getErgebnisSchwarz() == TurnierKonstanten.REMIS) {
							ergebnis = 0.5;
						}
						if (this.partien[i].getErgebnisSchwarz() == TurnierKonstanten.GEWINN_KAMPFLOS) {
							ergebnis = 1;
						}
						if (this.partien[i].getErgebnisSchwarz() == TurnierKonstanten.VERLUST_KAMPFLOS) {
							ergebnis = 0;
						}
						if (this.partien[i].getErgebnisSchwarz() != TurnierKonstanten.KEIN_ERGEBNIS) {
							if (this.partien[i].getSpielerWeiss().getDwz() != TurnierKonstanten.KEINE_DWZ) {
								opponents.add(new OpponentModel(this.partien[i].getSpielerWeiss().getDWZ(), ergebnis));
								gesamtpunkte += ergebnis;
							}
						}
					}
				}
				PlayerModel playerdwz = new PlayerModel(player.getAge(), player.getDWZ(), opponents.size());
				playerdwz.setPunkte(gesamtpunkte);
				MainModel mainModel = new MainModel(playerdwz, opponents);
				mainModel.calculateDWZ();
				player.setFolgeDWZ((int) playerdwz.getFolgeDWZ());

			}
		}
	}
}
