package de.turnierverwaltung.controller;

import java.awt.BorderLayout;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.PaarungsTafeln;
import de.turnierverwaltung.model.Partie;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.TerminTabelle;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.view.RundenEingabeFormularView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class RundenEingabeFormularControl implements ActionListener {

	private static int pruefeObZahlKleinerEinsIst(int zahl) throws ZahlKleinerAlsN {
		if (zahl <= 0) {
			throw new ZahlKleinerAlsN();
		}
		return zahl;
	}

	private MainControl mainControl;
	private Turnier turnier;
	private Gruppe[] gruppe;
	private Partie[] partien;
	private TerminTabelle terminTabelle[];
	private int gruppenAnzahl;
	private RundenEingabeFormularView[] rundenEingabeFormularView;
	private JPanel hauptPanel;
	private TabAnzeigeView tabAnzeigeView;
	private JButton changeColor[][];
	private int[] spielerAnzahl;
	private PaarungsTafeln[] paarungsTafeln;
	private TabAnzeigeView[] tabAnzeigeView2;
	private Boolean[] neuesTurnier;

	public RundenEingabeFormularControl(MainControl mainControl) {
		this.mainControl = mainControl;
		hauptPanel = this.mainControl.getHauptPanel();
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();

		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		gruppenAnzahl = turnier.getAnzahlGruppen();

		if (mainControl.getTurnierTabelleControl() == null) {
			terminTabelle = new TerminTabelle[gruppenAnzahl];
			this.mainControl.setTerminTabelle(terminTabelle);
		} else {
			terminTabelle = this.mainControl.getTerminTabelle();
		}
		rundenEingabeFormularView = new RundenEingabeFormularView[gruppenAnzahl];
		neuesTurnier = new Boolean[gruppenAnzahl];
		for (int i = 0; i < gruppenAnzahl; i++) {
			rundenEingabeFormularView[i] = new RundenEingabeFormularView(gruppe[i].getSpielerAnzahl());
			neuesTurnier[i] = false;
		}
		this.mainControl.setRundenEingabeFormularView(rundenEingabeFormularView);
		changeColor = new JButton[gruppenAnzahl][];
		spielerAnzahl = new int[gruppenAnzahl];
		paarungsTafeln = new PaarungsTafeln[gruppenAnzahl];

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		int max;
		int anzahl;
		for (int index = 0; index < gruppenAnzahl; index++) {
			max = spielerAnzahl[index];
			anzahl = max * (max - 1) / 2;
			for (int i = 0; i < anzahl; i++) {
				if (arg0.getSource() == changeColor[index][i]) {
					changeWerte(index);
					changeColor(index, i);

					changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
					for (int y = 0; y < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); y++) {

						changeColor[index][y].addActionListener(this);
					}
				}
			
			}

		}

	}

	public void changeWerte(int index) {
		String datum;
		partien = gruppe[index].getPartien();
		int runde;
		for (int i = 0; i < mainControl.getTurnier().getGruppe()[index].getPartienAnzahl(); i++) {

			try {
				datum = rundenEingabeFormularView[index].getDatum()[i].getJFormattedTextField().getText();
				runde = pruefeObZahlKleinerEinsIst(Integer
						.parseInt((String) rundenEingabeFormularView[index].getRundenNummer()[i].getSelectedItem()));
				partien[i].setSpielDatum(datum);
				partien[i].setRunde(runde);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Gruppenanzahl ist fehlerhaft!");

			} catch (ZahlKleinerAlsN e) {
				JOptionPane.showMessageDialog(null, "Zahl darf nicht kleiner als 1 sein!");

			}

		}
	}

	public void saveTurnier(int index) {
		Boolean ok = false;

		SaveTurnierControl stC = new SaveTurnierControl(mainControl);
		ok = stC.saveTurnier();

		if (ok) {

			if (mainControl.getTurnierTabelleControl() == null) {
				TurnierTabelleControl turnierTabelleControl = new TurnierTabelleControl(mainControl);
				TerminTabelleControl terminTabelleControl = new TerminTabelleControl(mainControl);
				mainControl.setTurnierTabelleControl(turnierTabelleControl);
				mainControl.setTerminTabelleControl(terminTabelleControl);
				turnierTabelleControl.makeSimpleTableView(index);
				terminTabelleControl.makeSimpleTableView(index);
				makeRundenEditView(index);
			} else {
				mainControl.getTurnierTabelleControl().makeSimpleTableView(index);
				mainControl.getTerminTabelleControl().makeSimpleTableView(index);
				makeRundenEditView(index);
			}
		}

	}

	private void changeColor(int index, int nummer) {
		int selectedTab = rundenEingabeFormularView[index].getTabbedPane().getSelectedIndex();
		Spieler tauscheFarbe1;
		Spieler tauscheFarbe2;
		partien = gruppe[index].getPartien();
		tauscheFarbe1 = partien[nummer].getSpielerWeiss();
		tauscheFarbe2 = partien[nummer].getSpielerSchwarz();
		partien[nummer].setSpielerWeiss(tauscheFarbe2);
		partien[nummer].setSpielerSchwarz(tauscheFarbe1);
		terminTabelle[index].createTerminTabelle();

		String[][] terminMatrix = terminTabelle[index].getTabellenMatrix();
		rundenEingabeFormularView[index].getWeissSpieler()[nummer].setText(tauscheFarbe2.getName());
		rundenEingabeFormularView[index].getSchwarzSpieler()[nummer].setText(tauscheFarbe1.getName());
		rundenEingabeFormularView[index] = new RundenEingabeFormularView(spielerAnzahl[index]);
		this.mainControl.setRundenEingabeFormularView(rundenEingabeFormularView);

		rundenEingabeFormularView[index].makeZeilen(terminMatrix);

		changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColor[index][i].addActionListener(this);
		}

		if (tabAnzeigeView2 == null) {
			tabAnzeigeView.setComponentAt(index, rundenEingabeFormularView[index]);
		} else {
			tabAnzeigeView2[index].setComponentAt(2, rundenEingabeFormularView[index]);
		}
		rundenEingabeFormularView[index].updateUI();
		hauptPanel.updateUI();
		rundenEingabeFormularView[index].getTabbedPane().setSelectedIndex(selectedTab);
	}

	public void makeRundenEditView(int index) {
		neuesTurnier[index] = true;
		Arrays.sort(gruppe[index].getPartien());
		partien = gruppe[index].getPartien();

		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();

		terminTabelle[index] = new TerminTabelle(turnier, gruppe[index]);
		gruppe[index].setTeminTabelle(terminTabelle[index]);
		String[][] terminMatrix = terminTabelle[index].getTabellenMatrix();

		rundenEingabeFormularView[index] = new RundenEingabeFormularView(spielerAnzahl[index]);
		this.mainControl.setRundenEingabeFormularView(rundenEingabeFormularView);

		rundenEingabeFormularView[index].makeZeilen(terminMatrix);
		changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColor[index][i].addActionListener(this);
		}

		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		if (tabAnzeigeView2 != null) {
			if (tabAnzeigeView2[index].getTabCount() < 3) {
				tabAnzeigeView2[index].insertTab("Paarungen", null, rundenEingabeFormularView[index], null, 2);
			} else {

				tabAnzeigeView2[index].setComponentAt(2, rundenEingabeFormularView[index]);
			}
		}
		hauptPanel.add(tabAnzeigeView, BorderLayout.CENTER);
		hauptPanel.updateUI();
	}

	public void makeTerminTabelle(int index) {

		neuesTurnier[index] = true;
		paarungsTafeln[index] = new PaarungsTafeln(gruppe[index]);
		gruppe[index] = paarungsTafeln[index].getGruppe();
		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();

		terminTabelle[index] = new TerminTabelle(turnier, gruppe[index]);
		gruppe[index].setTeminTabelle(terminTabelle[index]);
		String[][] terminMatrix = terminTabelle[index].getTabellenMatrix();

		rundenEingabeFormularView[index] = new RundenEingabeFormularView(spielerAnzahl[index]);
		this.mainControl.setRundenEingabeFormularView(rundenEingabeFormularView);

		rundenEingabeFormularView[index].makeZeilen(terminMatrix);
		changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColor[index][i].addActionListener(this);
		}
		if (mainControl.getTurnierTabelleControl() == null) {
			TurnierTabelleControl turnierTabelleControl = new TurnierTabelleControl(mainControl);
			TerminTabelleControl terminTabelleControl = new TerminTabelleControl(mainControl);
			mainControl.setTurnierTabelleControl(turnierTabelleControl);
			mainControl.setTerminTabelleControl(terminTabelleControl);
			turnierTabelleControl.makeSimpleTableView(index);
			terminTabelleControl.makeSimpleTableView(index);
			makeRundenEditView(index);
		} else {
			mainControl.getTurnierTabelleControl().makeSimpleTableView(index);
			mainControl.getTerminTabelleControl().makeSimpleTableView(index);
			makeRundenEditView(index);
		}
		mainControl.getNaviView().setTabellenname("Turnier: " + mainControl.getTurnier().getTurnierName());

	}

	public Boolean[] getNeuesTurnier() {
		return neuesTurnier;
	}

	public void setNeuesTurnier(Boolean[] neuesTurnier) {
		this.neuesTurnier = neuesTurnier;
	}

}
