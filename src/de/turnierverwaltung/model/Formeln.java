package de.turnierverwaltung.model;

public class Formeln {

	public Formeln() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getRundenAnzahl(int spielerAnzahl) {
		int rundenAnzahl = 0;
		if (spielerAnzahl % 2 == 0) {
			rundenAnzahl = spielerAnzahl - 1;
		} else {
			rundenAnzahl = spielerAnzahl;
		}
		return rundenAnzahl;
	}

	public int getPartienanzahl(int spielerAnzahl) {
		int partienAnzahl = 0;
		if (spielerAnzahl % 2 == 0) {
			partienAnzahl = (spielerAnzahl / 2) * (spielerAnzahl - 1);
		} else {
			partienAnzahl = (spielerAnzahl - 1) / 2;
		}
		return partienAnzahl;
	}

	public int getSpielerAnzahl(int partienAnzahl) {
		for (int s = 0; s < 40; s++) {

			if ((s * (s - 1)) == (partienAnzahl * 2)) {
				return s;
			}
		}
		return 0;
	}
}
