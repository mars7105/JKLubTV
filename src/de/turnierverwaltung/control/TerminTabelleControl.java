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

import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.turnierverwaltung.model.Partie;
import de.turnierverwaltung.model.SimpleTerminTabelle;
import de.turnierverwaltung.model.TerminTabelle;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.view.SimpleTerminTabelleView;
import de.turnierverwaltung.view.SimpleTurnierTabelleView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class TerminTabelleControl {

	private MainControl mainControl;
	private TerminTabelle[] terminTabelle;
	private TabAnzeigeView[] tabAnzeigeView2;
	private SimpleTerminTabelleView[] simpleTableView;
	private SimpleTurnierTabelleView[] simpleTurnierTabelleView;
	private JTabbedPane hauptPanel;
	private Turnier turnier;
	private MyTableModelListener tml[];
	private ArrayList<Partie> changedPartien;
	private ImageIcon terminTabelleIcon = new ImageIcon(Toolkit
			.getDefaultToolkit().getImage(
					getClass().getResource("/images/accessories-date.png"))); //$NON-NLS-1$

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
		if (this.mainControl.getChangedPartien() == null) {
			changedPartien = new ArrayList<Partie>();
			this.mainControl.setChangedPartien(changedPartien);
		} else {
			changedPartien = this.mainControl.getChangedPartien();

		}
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
			int abstand = mainControl.getPropertiesControl()
					.getTabellenAbstand();
			tml[gruppenNummer] = new MyTableModelListener(gruppenNummer,
					abstand);
		}

		simpleTableView[gruppenNummer].getTable().getModel()
				.addTableModelListener(tml[gruppenNummer]);

		if (tabAnzeigeView2[gruppenNummer].getTabCount() == 1) {
			tabAnzeigeView2[gruppenNummer]
					.insertTab(
							Messages.getString("TerminTabelleControl.1"), terminTabelleIcon, //$NON-NLS-1$
							simpleTableView[gruppenNummer], null, 1);
		} else if (tabAnzeigeView2[gruppenNummer].getTabCount() > 1) {

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
		private int abstand;

		public MyTableModelListener(int gruppenNummer, int abstand) {
			this.gruppenNummer = gruppenNummer;
			this.abstand = abstand;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			// mainControl.getRundenEingabeFormularControl().getChangedGroups()[gruppenNummer][NaviController.TERMINTABELLE]
			// = NaviController.STANDARD;

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
										.getTable()
										.getModel()
										.setValueAt(ergebnissSet, i,
												y + abstand);
							}
						}

					}
				}

			}
		}

	}
}
