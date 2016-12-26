package de.turnierverwaltung.model;

import java.util.ArrayList;
import java.util.Comparator;

import de.turnierverwaltung.model.roundrobin.CrossTable;
import de.turnierverwaltung.model.roundrobin.MeetingTable;

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
	private ArrayList<Player> spieler;
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
		spieler.sort(new Comparator<Player>() {
			public int compare(Player o1, Player o2) {
				int compareQuantity = o2.getSort() - o1.getSort();

				// ascending order
				return compareQuantity;

			}

		});
		for (int i = 0; i < getSpielerAnzahl(); i++) {
			spieler.get(i).setPlatz(i + 1);
		}
		for (int i = 0; i < getSpielerAnzahl() - 1; i++) {
			if (spieler.get(i).getPunkte() == spieler.get(i + 1).getPunkte()
					&& spieler.get(i).getSoberg() == spieler.get(i + 1).getSoberg()) {

				spieler.get(i + 1).setPlatz(spieler.get(i).getPlatz());
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
			spieler.get(i).setPunkte(0);
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
						if (partien[p].getSpielerWeiss() == spieler.get(s)
								&& partien[p].getSpielerSchwarz() == spieler.get(i)) {

							soberg += spieler.get(i).getPunkte()
									* convertErgebnisStringToDouble(partien[p].getErgebnisWeiss());
						}
						if (partien[p].getSpielerWeiss() == spieler.get(i)
								&& partien[p].getSpielerSchwarz() == spieler.get(s)) {

							soberg += spieler.get(i).getPunkte()
									* convertErgebnisStringToDouble(partien[p].getErgebnisSchwarz());
						}
					}
				}
			}
			spieler.get(s).setSoberg(soberg);

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

	public ArrayList<Player> getSpieler() {
		return spieler;
	}

	public int getSpielerAnzahl() {
		return spieler.size();
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

	public void setSpieler(ArrayList<Player> spieler) {
		this.spieler = spieler;
		spielerAnzahl = this.spieler.size();
	}

	public void setTeminTabelle(MeetingTable teminTabelle) {
		this.teminTabelle = teminTabelle;
	}

	public void setTurnierTabelle(CrossTable turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}
}
