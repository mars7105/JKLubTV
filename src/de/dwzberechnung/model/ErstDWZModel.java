package de.dwzberechnung.model;

/**
 * @author mamuck
 *
 */
public class ErstDWZModel {
	private double erstDWZ;
	private double punkte;
	// private double punkterwartung;
	private int anzahlDerGegner;
	private double durchschnittderGegnerDWZ;

	public ErstDWZModel(double punkte, int anzahlDerGegner, double durchschnittderGegnerDWZ) {
		super();
		this.punkte = punkte;
		this.durchschnittderGegnerDWZ = durchschnittderGegnerDWZ; // Rc
		this.anzahlDerGegner = anzahlDerGegner;
		zusatzstufe1();
	}

	public double getErstDWZ() {

		return Math.round(erstDWZ);
	}

	private void zusatzstufe1() {
		double erfolgszahl = 0;
		if (punkte == 0) {
			erstDWZ = durchschnittderGegnerDWZ - 677;
		} else if (punkte == anzahlDerGegner) {
			erstDWZ = durchschnittderGegnerDWZ + 677;
		} else {
			WertungsdifferenzenPunktePartienModel wdTM = new WertungsdifferenzenPunktePartienModel();
			erfolgszahl = wdTM.getValue(punkte, anzahlDerGegner);

		}
		// System.out.println((durchschnittderGegnerDWZ + erfolgszahl) +
		// "DurchschnittsDWZ =" + durchschnittderGegnerDWZ
		// + "Punkte =" + punkte + " A Spieler:" + anzahlDerGegner);
		erstDWZ = durchschnittderGegnerDWZ + erfolgszahl;
	}

	// private int zusatzstufe2() {
	// return 0;
	// }
	//
	// private int gesamtiteration() {
	// // P(D) - Durchschnitt = (W - We) / n + 0,500
	// return 0;
	// }
}
