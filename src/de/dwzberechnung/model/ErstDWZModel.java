package de.dwzberechnung.model;

public class ErstDWZModel {
	private double erstDWZ;
	private double punkte;
//	private double punkterwartung;
	private double anzahlDerGegner;
	private int durchschnittderGegnerDWZ;
	public ErstDWZModel(double punkte, double anzahlDerGegner,
			int durchschnittderGegnerDWZ) {
		super();
		this.punkte = punkte;
		this.durchschnittderGegnerDWZ = durchschnittderGegnerDWZ;
		this.anzahlDerGegner = anzahlDerGegner;
//		punkterwartung = 0;
	}

	public double getErstDWZ() {
		if (punkte == 0) {
			erstDWZ = durchschnittderGegnerDWZ - 677;
		} else if (punkte == anzahlDerGegner) {
			erstDWZ = durchschnittderGegnerDWZ + 677;
		} else {
//			WertungsdifferenzenTabelleModel wdTM = new WertungsdifferenzenTabelleModel();
//			int prozente =  (int)(100 * punkte / anzahlDerGegner);
//			punkterwartung = wdTM.getWertungsdifferenzenTabelle()[0][prozente];
		}
		return Math.round(erstDWZ);
	}

}
