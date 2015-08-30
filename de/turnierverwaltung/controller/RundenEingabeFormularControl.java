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
	private Boolean neuesTurnier;

	public RundenEingabeFormularControl(MainControl mainControl) {
		neuesTurnier = true;
		this.mainControl = mainControl;
		hauptPanel = this.mainControl.getHauptPanel();

		tabAnzeigeView = this.mainControl.getTabAnzeigeView();
		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		gruppenAnzahl = turnier.getAnzahlGruppen();
		terminTabelle = new TerminTabelle[gruppenAnzahl];
		rundenEingabeFormularView = new RundenEingabeFormularView[gruppenAnzahl];
		for (int i = 0; i < gruppenAnzahl; i++) {
			rundenEingabeFormularView[i] = new RundenEingabeFormularView(gruppe[i].getSpielerAnzahl());
		}
		changeColor = new JButton[gruppenAnzahl][];
		spielerAnzahl = new int[gruppenAnzahl];
		paarungsTafeln = new PaarungsTafeln[gruppenAnzahl];
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int max;

		for (int index = 0; index < gruppenAnzahl; index++) {
			max = spielerAnzahl[index];
			for (int i = 0; i < (max * (max - 1) / 2); i++) {
				if (arg0.getSource() == changeColor[index][i]) {
					changeWerte(index);
					changeColor(index, i);

					changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
					for (int y = 0; y < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); y++) {

						changeColor[index][y].addActionListener(this);
					}
				}
			}
			if (arg0.getSource() == rundenEingabeFormularView[index].getOkButton()) {
				partien = gruppe[index].getPartien();

				changeWerte(index);
				saveTurnier(index);
			}
			if (arg0.getSource() == rundenEingabeFormularView[index].getCancelButton()) {
				mainControl.resetApp();
				mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
				mainControl.getTurnierTableControl().loadTurnierListe();
				mainControl.setTurnierListeLadenControl(new TurnierListeLadenControl(mainControl));
				mainControl.getTurnierListeLadenControl().loadTurnier();
			}
		}

	}

	private void changeWerte(int index) {
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
				//partien[i].setErgebnis(TurnierKonstanten.MYSQL_KEIN_ERGEBNIS);
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Gruppenanzahl ist fehlerhaft!");

			} catch (ZahlKleinerAlsN e) {
				JOptionPane.showMessageDialog(null, "Zahl darf nicht kleiner als 1 sein!");

			}

		}
	}

	private void saveTurnier(int index) {
		if (neuesTurnier == true) {
			if (mainControl.getTurnierTabelleControl() == null) {
				TurnierTabelleControl turnierTabelleControl = new TurnierTabelleControl(mainControl);
				TerminTabelleControl terminTabelleControl = new TerminTabelleControl(mainControl);
				mainControl.setTurnierTabelleControl(turnierTabelleControl);
				mainControl.setTerminTabelleControl(terminTabelleControl);
				turnierTabelleControl.makeSimpleTableView(index);
				terminTabelleControl.makeSimpleTableView(index);
			} else {
				mainControl.getTurnierTabelleControl().makeSimpleTableView(index);
				mainControl.getTerminTabelleControl().makeSimpleTableView(index);

			}
		} else {
			SaveTurnierControl stC = new SaveTurnierControl(mainControl);
			stC.saveTurnier(index);
			hauptPanel.removeAll();
			TurnierListeLadenControl tllC = new TurnierListeLadenControl(mainControl);
			tllC.loadTurnier();

		}
	}

	private void changeColor(int index, int nummer) {

		Spieler tauscheFarbe1;
		Spieler tauscheFarbe2;
		partien = gruppe[index].getPartien();
		tauscheFarbe1 = partien[nummer].getSpielerWeiss();
		tauscheFarbe2 = partien[nummer].getSpielerSchwarz();
		partien[nummer].setSpielerWeiss(tauscheFarbe2);
		partien[nummer].setSpielerSchwarz(tauscheFarbe1);
		terminTabelle[index].createTerminTabelle();
		int spaltenAnzahl = terminTabelle[index].getSpaltenAnzahl();
		String[][] terminMatrix = terminTabelle[index].getTabellenMatrix();
		String[] zeile = new String[spaltenAnzahl];
		rundenEingabeFormularView[index].getWeissSpieler()[nummer].setText(tauscheFarbe2.getName());
		rundenEingabeFormularView[index].getSchwarzSpieler()[nummer].setText(tauscheFarbe1.getName());
		rundenEingabeFormularView[index] = new RundenEingabeFormularView(spielerAnzahl[index]);

		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2) + 1; i++) {
			zeile[0] = terminMatrix[0][i];
			zeile[1] = terminMatrix[1][i];
			zeile[2] = terminMatrix[2][i];
			zeile[3] = terminMatrix[3][i];
			zeile[4] = terminMatrix[4][i];
			rundenEingabeFormularView[index].makeZeile(zeile);
		}

		changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColor[index][i].addActionListener(this);
		}

		rundenEingabeFormularView[index].getOkButton().addActionListener(this);
		rundenEingabeFormularView[index].getCancelButton().addActionListener(this);
		// gruppe[index].setPartien(partien);
		tabAnzeigeView.setComponentAt(index, rundenEingabeFormularView[index]);
		rundenEingabeFormularView[index].updateUI();
		hauptPanel.updateUI();
	}

	public void makeRundenEditView(int index) {
		Arrays.sort(gruppe[index].getPartien());
		partien = gruppe[index].getPartien();

		neuesTurnier = false;
		// paarungsTafeln[index] = new PaarungsTafeln(gruppe[index]);
		// gruppe[index] = paarungsTafeln[index].getGruppe();
		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();
		terminTabelle[index] = new TerminTabelle(turnier, gruppe[index]);
		gruppe[index].setTeminTabelle(terminTabelle[index]);
		int spaltenAnzahl = terminTabelle[index].getSpaltenAnzahl();
		String[][] terminMatrix = terminTabelle[index].getTabellenMatrix();
		String[] zeile = new String[spaltenAnzahl];

		rundenEingabeFormularView[index] = new RundenEingabeFormularView(spielerAnzahl[index]);
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2) + 1; i++) {
			zeile[0] = terminMatrix[0][i];
			zeile[1] = terminMatrix[1][i];
			zeile[2] = terminMatrix[2][i];
			zeile[3] = terminMatrix[3][i];
			zeile[4] = terminMatrix[4][i];
			rundenEingabeFormularView[index].makeZeile(zeile);
		}

		changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColor[index][i].addActionListener(this);
		}
		rundenEingabeFormularView[index].getOkButton().addActionListener(this);
		rundenEingabeFormularView[index].getCancelButton().addActionListener(this);
		tabAnzeigeView.setComponentAt(index, rundenEingabeFormularView[index]);
		hauptPanel.add(tabAnzeigeView, BorderLayout.CENTER);
		hauptPanel.updateUI();
	}

	public void makeTerminTabelle(int index) {
		neuesTurnier = true;
		paarungsTafeln[index] = new PaarungsTafeln(gruppe[index]);
		gruppe[index] = paarungsTafeln[index].getGruppe();
		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();
		terminTabelle[index] = new TerminTabelle(turnier, gruppe[index]);
		gruppe[index].setTeminTabelle(terminTabelle[index]);
		int spaltenAnzahl = terminTabelle[index].getSpaltenAnzahl();
		String[][] terminMatrix = terminTabelle[index].getTabellenMatrix();
		String[] zeile = new String[spaltenAnzahl];

		rundenEingabeFormularView[index] = new RundenEingabeFormularView(spielerAnzahl[index]);
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2) + 1; i++) {
			zeile[0] = terminMatrix[0][i];
			zeile[1] = terminMatrix[1][i];
			zeile[2] = terminMatrix[2][i];
			zeile[3] = terminMatrix[3][i];
			zeile[4] = terminMatrix[4][i];
			rundenEingabeFormularView[index].makeZeile(zeile);
		}

		changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColor[index][i].addActionListener(this);
		}
		rundenEingabeFormularView[index].getOkButton().addActionListener(this);
		rundenEingabeFormularView[index].getCancelButton().addActionListener(this);
		tabAnzeigeView.setComponentAt(index, rundenEingabeFormularView[index]);
		hauptPanel.add(tabAnzeigeView, BorderLayout.CENTER);

		hauptPanel.updateUI();
	}

}
