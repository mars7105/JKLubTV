package de.turnierverwaltung.controller;

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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import de.turnierverwaltung.model.Partie;
import de.turnierverwaltung.model.SimpleTurnierTabelle;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.model.TurnierTabelle;
import de.turnierverwaltung.view.SimpleTurnierTabelleView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class TurnierTabelleControl {

	private MainControl mainControl;
	private TurnierTabelle[] turnierTabelle;
	private SimpleTurnierTabelleView[] simpleTableView;
	private SaveTurnierControl saveTurnierControl;
	private JTabbedPane hauptPanel;
	private TabAnzeigeView tabAnzeigeView;
	private TabAnzeigeView[] tabAnzeigeView2;
	private Turnier turnier;
	private MyTableModelListener tml[];
	private Dimension dimension[];
	private int[] spielerAnzahl;
	private ArrayList<Partie> changedPartien;
	private ImageIcon turniertabelleIcon = new ImageIcon(Toolkit
			.getDefaultToolkit().getImage(
					getClass().getResource("/images/x-office-spreadsheet.png")));

	public TurnierTabelleControl(MainControl mainControl) {

		this.mainControl = mainControl;

		hauptPanel = this.mainControl.getHauptPanel();

		if (this.mainControl.getTabAnzeigeView() == null) {
			this.mainControl.setTabAnzeigeView(new TabAnzeigeView(mainControl));
		}
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();
		// tabAnzeigeView.setBackground(new Color(249, 222, 112));
		int anzahlGruppen = mainControl.getTurnier().getAnzahlGruppen();
		tabAnzeigeView2 = new TabAnzeigeView[anzahlGruppen];
		dimension = new Dimension[anzahlGruppen];
		for (int i = 0; i < anzahlGruppen; i++) {
			tabAnzeigeView2[i] = new TabAnzeigeView(mainControl);
			dimension[i] = tabAnzeigeView2[i].getPreferredSize();
		}
		this.mainControl.setTabAnzeigeView2(tabAnzeigeView2);
		simpleTableView = new SimpleTurnierTabelleView[anzahlGruppen];
		tml = new MyTableModelListener[anzahlGruppen];
		this.turnierTabelle = new TurnierTabelle[anzahlGruppen];
		this.mainControl.setTurnierTabelle(turnierTabelle);
		this.saveTurnierControl = new SaveTurnierControl(this.mainControl);
		this.mainControl.setSaveTurnierControl(saveTurnierControl);
		spielerAnzahl = new int[anzahlGruppen];
		this.mainControl.getNaviView().getTabellenPanel().setVisible(true);
		if (this.mainControl.getChangedPartien() == null) {
			changedPartien = new ArrayList<Partie>();
			this.mainControl.setChangedPartien(changedPartien);
		} else {
			changedPartien = this.mainControl.getChangedPartien();

		}

	}

	public MyTableModelListener[] getTml() {
		return tml;
	}

	public TurnierTabelle[] getTurnierTabelle() {
		return turnierTabelle;
	}

	public void makeSimpleTableView(int gruppenNummer) {
		berechneFolgeDWZ(gruppenNummer);

		// mainControl.getMenueControl().setWarnHinweis(true);
		turnier = mainControl.getTurnier();
		if (tml[gruppenNummer] == null) {
			tml[gruppenNummer] = new MyTableModelListener(gruppenNummer);
		}
		Arrays.sort(turnier.getGruppe()[gruppenNummer].getPartien());
		spielerAnzahl[gruppenNummer] = turnier.getGruppe()[gruppenNummer]
				.getSpielerAnzahl();
		if (turnier.getGruppe()[gruppenNummer].getSpieler()[spielerAnzahl[gruppenNummer] - 1]
				.getSpielerId() == -2) {
			spielerAnzahl[gruppenNummer]--;
		}
		this.turnierTabelle[gruppenNummer] = new TurnierTabelle(turnier,
				mainControl.getTurnier().getGruppe()[gruppenNummer]);
		this.turnierTabelle[gruppenNummer].createMatrix();

		simpleTableView[gruppenNummer] = new SimpleTurnierTabelleView(
				new SimpleTurnierTabelle(this.turnierTabelle[gruppenNummer]));
		simpleTableView[gruppenNummer].setBackground(new Color(249, 222, 112));
		updatePunkteCol(this.turnierTabelle[gruppenNummer].getSpalte() - 3,
				gruppenNummer);
		updateSoBergCol(this.turnierTabelle[gruppenNummer].getSpalte() - 2,
				gruppenNummer);
		updatePlatzCol(this.turnierTabelle[gruppenNummer].getSpalte() - 1,
				gruppenNummer);

		simpleTableView[gruppenNummer].getTable().getModel()
				.addTableModelListener(tml[gruppenNummer]);
		simpleTableView[gruppenNummer]
				.setPreferredSize(dimension[gruppenNummer]);
		if (tabAnzeigeView2[gruppenNummer].getTabCount() < 1) {
			tabAnzeigeView2[gruppenNummer]
					.insertTab("Turniertabelle", turniertabelleIcon,
							simpleTableView[gruppenNummer], null, 0);

		} else {

			tabAnzeigeView2[gruppenNummer].setComponentAt(0,
					simpleTableView[gruppenNummer]);
		}

		mainControl.setSimpleTableView(simpleTableView);
		if (tabAnzeigeView.getTabCount() < 1) {
			tabAnzeigeView.insertTab(
					turnier.getGruppe()[gruppenNummer].getGruppenName(), null,
					tabAnzeigeView2[gruppenNummer], null, gruppenNummer);

		} else {

			tabAnzeigeView.setComponentAt(gruppenNummer,
					tabAnzeigeView2[gruppenNummer]);
		}

		hauptPanel.updateUI();
		checkDWZVisible(gruppenNummer);
		// enableDatenbankMenu();
	}

	public void checkDWZVisible(int i) {

		simpleTableView[i].getTable().getColumn("Kürzel").setMinWidth(0);

		simpleTableView[i].getTable().getColumn("Kürzel").setPreferredWidth(0);
		if (mainControl.getPropertiesControl().getNoDWZ() == true) {
			simpleTableView[i].getTable().getColumn("a.DWZ").setMinWidth(0);

			simpleTableView[i].getTable().getColumn("a.DWZ")
					.setPreferredWidth(0);

			simpleTableView[i].getTable().updateUI();
		}
		if (mainControl.getPropertiesControl().getNoFolgeDWZ() == true) {

			simpleTableView[i].getTable().getColumn("n.DWZ").setMinWidth(0);

			simpleTableView[i].getTable().getColumn("n.DWZ")
					.setPreferredWidth(0);

			simpleTableView[i].getTable().updateUI();
		}

	}

	private void berechneFolgeDWZ(int gruppenNummer) {
		FolgeDWZController folgeDWZ = new FolgeDWZController(
				mainControl.getTurnier(),
				mainControl.getTurnier().getGruppe()[gruppenNummer]);
		folgeDWZ.caculateDWZ();
		// Zweimal ausführen falls DWZ-lose Spieler dabei sind
		folgeDWZ.caculateDWZ();

	}

	public void okAction(int index) {

		turnier.getGruppe()[index].berechnePunkte();
		if (simpleTableView[index] == null) {
			makeSimpleTableView(index);
		}
		int spalte = simpleTableView[index].getTable().getModel()
				.getColumnCount();
		int zeile = simpleTableView[index].getTable().getModel().getRowCount();
		for (int x = 0; x < spalte; x++) {
			for (int y = 0; y < zeile; y++) {

				turnierTabelle[index].getTabellenMatrix()[x][y + 1] = (String) simpleTableView[index]
						.getTable().getValueAt(y, x);

			}
		}
		makeSimpleTableView(index);
	}

	public void readDataFromDatabase(int tID) {
		mainControl.getTurnierTableControl().getTurnier(tID);
		turnier = mainControl.getTurnier();

	}

	public void setTml(MyTableModelListener[] tml) {
		this.tml = tml;
	}

	public void setTurnierTabelle(TurnierTabelle[] turnierTabelle) {
		this.turnierTabelle = turnierTabelle;
	}

	private void updatePlatzCol(int col, int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer]
					.getTable()
					.getModel()
					.setValueAt(
							Integer.toString(turnier.getGruppe()[gruppenNummer]
									.getSpieler()[i].getPlatz()), i, col);

		}
	}

	private void updatePunkteCol(int col, int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer]
					.getTable()
					.getModel()
					.setValueAt(
							Double.toString(turnier.getGruppe()[gruppenNummer]
									.getSpieler()[i].getPunkte()), i, col);

		}
	}

	private void updateSoBergCol(int col, int gruppenNummer) {
		for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
			simpleTableView[gruppenNummer]
					.getTable()
					.getModel()
					.setValueAt(
							Double.toString(turnier.getGruppe()[gruppenNummer]
									.getSpieler()[i].getSoberg()), i, col);

		}
	}

	class MyTableModelListener implements TableModelListener {
		private int gruppenNummer;

		public MyTableModelListener(int gruppenNummer) {
			this.gruppenNummer = gruppenNummer;
		}

		@Override
		public void tableChanged(TableModelEvent e) {
			simpleTableView[gruppenNummer].getTable().getModel()
					.removeTableModelListener(tml[gruppenNummer]);

			int row = e.getFirstRow();
			int col = e.getColumn();

			int colCount = simpleTableView[gruppenNummer].getTable().getModel()
					.getColumnCount();
			int spielery = row;
			int spielerx = col - 4;
			int invertRow = spielerx;
			int invertCol = spielery + 4;
			String ergebniss = (String) simpleTableView[gruppenNummer]
					.getTable().getModel().getValueAt(row, col);

			String invertErgebniss = " ";
			int invertErgebnissInt = 0;
			int ergebnissInt = 0;

			if (ergebniss == TurnierKonstanten.KEIN_ERGEBNIS) {
				invertErgebniss = TurnierKonstanten.KEIN_ERGEBNIS;
				ergebnissInt = TurnierKonstanten.MYSQL_KEIN_ERGEBNIS;
				invertErgebnissInt = TurnierKonstanten.MYSQL_KEIN_ERGEBNIS;
			}
			if (ergebniss == TurnierKonstanten.VERLUST) {
				invertErgebniss = TurnierKonstanten.GEWINN;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_SCHWARZ;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_WEISS;

			}
			if (ergebniss == TurnierKonstanten.REMIS) {
				invertErgebniss = TurnierKonstanten.REMIS;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_REMIS;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_REMIS;
			}
			if (ergebniss == TurnierKonstanten.GEWINN) {
				invertErgebniss = TurnierKonstanten.VERLUST;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_WEISS;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_SCHWARZ;
			}
			if (ergebniss == TurnierKonstanten.VERLUST_KAMPFLOS) {
				invertErgebniss = TurnierKonstanten.GEWINN_KAMPFLOS;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS;
			}
			if (ergebniss == TurnierKonstanten.GEWINN_KAMPFLOS) {
				invertErgebniss = TurnierKonstanten.VERLUST_KAMPFLOS;
				ergebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_WEISS;
				invertErgebnissInt = TurnierKonstanten.MYSQL_PARTIE_GEWINN_KAMPFLOS_SCHWARZ;
			}
			simpleTableView[gruppenNummer].getTable().getModel()
					.setValueAt(invertErgebniss, invertRow, invertCol);
			simpleTableView[gruppenNummer].getTable().getModel()
					.setValueAt(ergebniss, row, col);
			Spieler spy = turnier.getGruppe()[gruppenNummer].getSpieler()[spielery];
			Spieler spx = turnier.getGruppe()[gruppenNummer].getSpieler()[spielerx];
			if ((spielerx >= 0 && spielerx < turnier.getGruppe()[gruppenNummer]
					.getSpielerAnzahl())
					&& (spielery >= 0 && spielery < turnier.getGruppe()[gruppenNummer]
							.getSpielerAnzahl()) && (spielerx != spielery)) {

				for (int i = 0; i < turnier.getGruppe()[gruppenNummer]
						.getPartienAnzahl(); i++) {
					if (turnier.getGruppe()[gruppenNummer].getPartien()[i]
							.getSpielerWeiss() == spx
							&& turnier.getGruppe()[gruppenNummer].getPartien()[i]
									.getSpielerSchwarz() == spy) {

						turnier.getGruppe()[gruppenNummer].getPartien()[i]
								.setErgebnis(invertErgebnissInt);

						updatePunkteCol(colCount - 3);
						updateSoBergCol(colCount - 2);
						changedPartien.add(turnier.getGruppe()[gruppenNummer]
								.getPartien()[i]);

					}

					if (turnier.getGruppe()[gruppenNummer].getPartien()[i]
							.getSpielerWeiss() == spy
							&& turnier.getGruppe()[gruppenNummer].getPartien()[i]
									.getSpielerSchwarz() == spx) {
						turnier.getGruppe()[gruppenNummer].getPartien()[i]
								.setErgebnis(ergebnissInt);

						updatePunkteCol(colCount - 3);
						updateSoBergCol(colCount - 2);
						updatePlatzCol(colCount - 1);
						changedPartien.add(turnier.getGruppe()[gruppenNummer]
								.getPartien()[i]);

					}
				}
			}
			mainControl.getTerminTabelleControl().makeSimpleTableView(
					gruppenNummer);
			simpleTableView[gruppenNummer].getTable().getModel()
					.addTableModelListener(tml[gruppenNummer]);
			mainControl.getRundenEingabeFormularControl().getChangedGroups()[gruppenNummer][NaviController.TURNIERTABELLE] = NaviController.STANDARD;

		}

		private void updatePunkteCol(int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer]
						.getTable()
						.getModel()
						.setValueAt(
								Double.toString(turnier.getGruppe()[gruppenNummer]
										.getSpieler()[i].getPunkte()), i, col);
			}
		}

		private void updateSoBergCol(int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer]
						.getTable()
						.getModel()
						.setValueAt(
								Double.toString(turnier.getGruppe()[gruppenNummer]
										.getSpieler()[i].getSoberg()), i, col);
			}
		}

		private void updatePlatzCol(int col) {
			for (int i = 0; i < spielerAnzahl[gruppenNummer]; i++) {
				simpleTableView[gruppenNummer]
						.getTable()
						.getModel()
						.setValueAt(
								Integer.toString(turnier.getGruppe()[gruppenNummer]
										.getSpieler()[i].getPlatz()), i, col);

			}
		}
	}
}
