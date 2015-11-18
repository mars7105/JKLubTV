package de.turnierverwaltung.controller;

import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import de.turnierverwaltung.model.SimpleTerminTabelle;
import de.turnierverwaltung.model.TerminTabelle;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.view.HTMLTabelleView;
import de.turnierverwaltung.view.SimpleTerminTabelleView;
import de.turnierverwaltung.view.SimpleTurnierTabelleView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class TerminTabelleControl  {

	private MainControl mainControl;
	private TerminTabelle[] terminTabelle;
	private TabAnzeigeView[] tabAnzeigeView2;
	private SimpleTerminTabelleView[] simpleTableView;
	private SimpleTurnierTabelleView[] simpleTurnierTabelleView;
	private JPanel hauptPanel;
	private Turnier turnier;
	private MyTableModelListener tml[];
	public TerminTabelleControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.turnier = mainControl.getTurnier();
		this.tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		int anzahlGruppen = mainControl.getTurnier().getAnzahlGruppen();
		simpleTableView = new SimpleTerminTabelleView[anzahlGruppen];
		this.mainControl.setSimpleTerminTabelleView(simpleTableView);
		this.terminTabelle = new TerminTabelle[anzahlGruppen];
		this.mainControl.setTerminTabelle(terminTabelle);
		hauptPanel = mainControl.getHauptPanel();
		tml = new MyTableModelListener[anzahlGruppen];
		new HTMLTabelleView();

	}



	public void makeSimpleTableView(int gruppenNummer) {

		this.terminTabelle[gruppenNummer] = new TerminTabelle(turnier,
				mainControl.getTurnier().getGruppe()[gruppenNummer]);
		this.terminTabelle[gruppenNummer].createTerminTabelle();
		simpleTableView[gruppenNummer] = new SimpleTerminTabelleView(
				new SimpleTerminTabelle(this.terminTabelle[gruppenNummer]));
		simpleTurnierTabelleView = mainControl.getSimpleTableView();
		mainControl.getTurnierTabelle();
		if (tml[gruppenNummer] == null) {
			tml[gruppenNummer] = new MyTableModelListener(gruppenNummer);
		}

		simpleTableView[gruppenNummer].getTable().getModel()
				.addTableModelListener(tml[gruppenNummer]);


		if (tabAnzeigeView2[gruppenNummer].getTabCount() == 1) {
			tabAnzeigeView2[gruppenNummer].insertTab("Termintabelle", null,
					simpleTableView[gruppenNummer], null, 1);
		} else {

			tabAnzeigeView2[gruppenNummer].setComponentAt(1,
					simpleTableView[gruppenNummer]);
		}

		hauptPanel.updateUI();
	}

	public TerminTabelle[] getTerminTabelle() {
		return terminTabelle;
	}

	public void setTerminTabelle(TerminTabelle[] terminTabelle) {
		this.terminTabelle = terminTabelle;
	}

	class MyTableModelListener implements TableModelListener {
		private int gruppenNummer;

		public MyTableModelListener(int gruppenNummer) {
			this.gruppenNummer = gruppenNummer;
		}

		@Override
		public void tableChanged(TableModelEvent e) {

			int row = e.getFirstRow();
			int col = e.getColumn();
			if (col == 3) {
				String ergebniss = (String) simpleTableView[gruppenNummer]
						.getTable().getModel().getValueAt(row, col);
				String spielerWeiss = (String) simpleTableView[gruppenNummer]
						.getTable().getModel().getValueAt(row, 1);
				String spielerSchwarz = (String) simpleTableView[gruppenNummer]
						.getTable().getModel().getValueAt(row, 2);

				String ergebnissSet = TurnierKonstanten.KEIN_ERGEBNIS;

				if (ergebniss == TurnierKonstanten.KEIN_ERGEBNIS) {
					ergebnissSet = TurnierKonstanten.KEIN_ERGEBNIS;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_GEWINN_SCHWARZ) {
					ergebnissSet = TurnierKonstanten.VERLUST;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_REMIS) {
					ergebnissSet = TurnierKonstanten.REMIS;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_GEWINN_WEISS) {
					ergebnissSet = TurnierKonstanten.GEWINN;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_GEWINN_KAMPFLOS_SCHWARZ) {
					ergebnissSet = TurnierKonstanten.VERLUST_KAMPFLOS;
				}
				if (ergebniss == TurnierKonstanten.PARTIE_GEWINN_KAMPFLOS_WEISS) {
					ergebnissSet = TurnierKonstanten.GEWINN_KAMPFLOS;
				}
				for (int i = 0; i < simpleTurnierTabelleView[gruppenNummer]
						.getTable().getModel().getRowCount(); i++) {
					if (simpleTurnierTabelleView[gruppenNummer].getTable()
							.getModel().getValueAt(i, 0) == spielerWeiss) {
						for (int y = 0; y < simpleTurnierTabelleView[gruppenNummer]
								.getTable().getModel().getRowCount(); y++) {
							if (simpleTurnierTabelleView[gruppenNummer]
									.getTable().getModel().getValueAt(y, 0) == spielerSchwarz) {
								simpleTurnierTabelleView[gruppenNummer]
										.getTable().getModel()
										.setValueAt(ergebnissSet, i, y + 4);
							}
						}

					}
				}

			}
		}

	}
}
