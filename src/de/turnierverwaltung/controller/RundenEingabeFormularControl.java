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

import java.awt.Toolkit;
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
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.jdatepicker.impl.JDatePickerImpl;

import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.PaarungsTafeln;
import de.turnierverwaltung.model.Partie;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.TerminTabelle;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.view.PairingsLoadView;
import de.turnierverwaltung.view.RundenEingabeFormularView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class RundenEingabeFormularControl implements ActionListener {

	private static int pruefeObZahlKleinerEinsIst(int zahl)
			throws ZahlKleinerAlsN {
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
	private JButton changeColor[][];
	private int[] spielerAnzahl;
	private PaarungsTafeln[] paarungsTafeln;
	private TabAnzeigeView[] tabAnzeigeView2;
	private Boolean[] neuesTurnier;
	private ArrayList<Partie> changedPartien;
	private JDatePickerImpl[][] datePicker;
	private JComboBox<String>[][] rundenNummer;
	private PairingsLoadView[] pairingsLoadView;
	private int[][] changedGroups;
	private ImageIcon paarungenIcon = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(
					getClass().getResource(
							"/images/media-playlist-shuffle-3.png"))); //$NON-NLS-1$

	public RundenEingabeFormularControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.mainControl.getHauptPanel();
		this.mainControl.getTabAnzeigeView();

		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		gruppenAnzahl = turnier.getAnzahlGruppen();
		pairingsLoadView = new PairingsLoadView[gruppenAnzahl];
		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		makePairingsLoadPanel();

	}

	public RundenEingabeFormularControl(MainControl mainControl, int selectIndex) {
		this.mainControl = mainControl;
		this.mainControl.getHauptPanel();
		this.mainControl.getTabAnzeigeView();

		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		gruppenAnzahl = turnier.getAnzahlGruppen();
		pairingsLoadView = new PairingsLoadView[gruppenAnzahl];
		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		init();
		// makePairingsLoadPanel();
	}

	private void makePairingsLoadPanel() {
		for (int i = 0; i < gruppenAnzahl; i++) {
			pairingsLoadView[i] = new PairingsLoadView();
			pairingsLoadView[i].getLoadPairingsButton().addActionListener(this);

			if (tabAnzeigeView2[i].getComponentCount() == 2) {
				tabAnzeigeView2[i]
						.insertTab(
								Messages.getString("RundenEingabeFormularControl.5"), paarungenIcon, //$NON-NLS-1$
								pairingsLoadView[i], null, 2);
			} else {
				tabAnzeigeView2[i].setComponentAt(2, pairingsLoadView[i]);
				tabAnzeigeView2[i].setIconAt(2, paarungenIcon);
			}
			pairingsLoadView[i].updateUI();
		}
	}

	private void makePairingsLoadPanel(int index) {

		pairingsLoadView[index] = new PairingsLoadView();
		pairingsLoadView[index].getLoadPairingsButton().addActionListener(this);

		if (tabAnzeigeView2[index].getComponentCount() == 2) {
			tabAnzeigeView2[index]
					.insertTab(
							Messages.getString("RundenEingabeFormularControl.5"), paarungenIcon, //$NON-NLS-1$
							pairingsLoadView[index], null, 2);
		} else {
			tabAnzeigeView2[index].setComponentAt(2, pairingsLoadView[index]);
			tabAnzeigeView2[index].setIconAt(2, paarungenIcon);
		}
		pairingsLoadView[index].updateUI();

	}

	@SuppressWarnings("unchecked")
	private void init() {
		if (rundenEingabeFormularView == null) {
			if (mainControl.getTurnierTabelleControl() == null) {
				terminTabelle = new TerminTabelle[gruppenAnzahl];
				this.mainControl.setTerminTabelle(terminTabelle);
			} else {
				terminTabelle = this.mainControl.getTerminTabelle();
			}
			rundenEingabeFormularView = new RundenEingabeFormularView[gruppenAnzahl];
			neuesTurnier = new Boolean[gruppenAnzahl];
			changedGroups = new int[gruppenAnzahl][3];
			for (int i = 0; i < gruppenAnzahl; i++) {
				rundenEingabeFormularView[i] = new RundenEingabeFormularView(
						gruppe[i].getSpielerAnzahl());
				neuesTurnier[i] = false;
				for (int x = 0; x < 3; x++) {

					if (checkNewTurnier() == true) {
						changedGroups[i][x] = 1;
					} else {
						changedGroups[i][x] = 0;
					}
				}
			}
			this.mainControl
					.setRundenEingabeFormularView(rundenEingabeFormularView);
			changeColor = new JButton[gruppenAnzahl][];
			datePicker = new JDatePickerImpl[gruppenAnzahl][];
			rundenNummer = new JComboBox[gruppenAnzahl][];
			spielerAnzahl = new int[gruppenAnzahl];
			paarungsTafeln = new PaarungsTafeln[gruppenAnzahl];
			if (this.mainControl.getChangedPartien() == null) {
				changedPartien = new ArrayList<Partie>();
				this.mainControl.setChangedPartien(changedPartien);
			} else {
				changedPartien = this.mainControl.getChangedPartien();

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		init();
		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		int max;
		int anzahl;
		for (int index = 0; index < gruppenAnzahl; index++) {
			max = spielerAnzahl[index];
			anzahl = max * (max - 1) / 2;
			if (arg0.getSource() == pairingsLoadView[index]
					.getLoadPairingsButton()) {

				mainControl.getNaviView().getTabellenPanel().setVisible(false);
				for (int i = 0; i < gruppenAnzahl; i++) {
					tabAnzeigeView2[i].setEnabledAt(0, false);
					tabAnzeigeView2[i].setEnabledAt(1, false);
					makeRundenEditView(i);
					tabAnzeigeView2[i].setSelectedIndex(2);

				}

			}
			if (arg0.getSource() == rundenEingabeFormularView[index]
					.getReloadButton()) {

				saveAndReloadTurnier();
				setTabsEnable(true);
			}
			for (int i = 0; i < anzahl; i++) {

				if (arg0.getSource() == changeColor[index][i]) {
					changeColor(index, i);
					changedPartien.add(gruppe[index].getPartien()[i]);
					changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.STANDARD;
					int selectedTab = rundenEingabeFormularView[index]
							.getTabbedPane().getSelectedIndex();
					makeNewFormular(index);
					rundenEingabeFormularView[index].getTabbedPane()
							.setSelectedIndex(selectedTab);

				}
				if (arg0.getSource() == datePicker[index][i]
						.getJDateInstantPanel()) {

					changeWerte(index, i);
					changedPartien.add(gruppe[index].getPartien()[i]);
					changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.STANDARD;

				}
				if (arg0.getSource() == rundenNummer[index][i]) {
					changeWerte(index, i);
					changedPartien.add(gruppe[index].getPartien()[i]);
					changedGroups[index][NaviControl.PAARUNGSTABELLE] = NaviControl.SORTIEREN;

				}
			}

		}

	}

	public void changeWerte(int index, int nummer) {

		String datum;
		partien = gruppe[index].getPartien();
		int runde;

		try {
			datum = rundenEingabeFormularView[index].getDatum()[nummer]
					.getJFormattedTextField().getText();
			runde = pruefeObZahlKleinerEinsIst(Integer
					.parseInt((String) rundenEingabeFormularView[index]
							.getRundenNummer()[nummer].getSelectedItem()));
			partien[nummer].setSpielDatum(datum);
			partien[nummer].setRunde(runde);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl,
					Messages.getString("RundenEingabeFormularControl.1")); //$NON-NLS-1$

		} catch (ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(mainControl,
					Messages.getString("RundenEingabeFormularControl.2")); //$NON-NLS-1$

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
		String datum;
		int runde;
		try {
			datum = rundenEingabeFormularView[index].getDatum()[nummer]
					.getJFormattedTextField().getText();
			runde = pruefeObZahlKleinerEinsIst(Integer
					.parseInt((String) rundenEingabeFormularView[index]
							.getRundenNummer()[nummer].getSelectedItem()));
			partien[nummer].setSpielDatum(datum);
			partien[nummer].setRunde(runde);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(mainControl,
					Messages.getString("RundenEingabeFormularControl.3")); //$NON-NLS-1$

		} catch (ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(mainControl,
					Messages.getString("RundenEingabeFormularControl.4")); //$NON-NLS-1$

		}

	}

	@SuppressWarnings("unchecked")
	public void makeNewFormular(int index) {

		terminTabelle[index] = new TerminTabelle(turnier, gruppe[index]);
		gruppe[index].setTeminTabelle(terminTabelle[index]);
		terminTabelle[index].createTerminTabelle();

		String[][] terminMatrix = terminTabelle[index].getTabellenMatrix();
		rundenEingabeFormularView[index] = new RundenEingabeFormularView(
				spielerAnzahl[index]);
		this.mainControl
				.setRundenEingabeFormularView(rundenEingabeFormularView);

		rundenEingabeFormularView[index].makeZeilen(terminMatrix);

		changeColor[index] = rundenEingabeFormularView[index].getChangeColor();
		datePicker[index] = rundenEingabeFormularView[index].getDatum();
		rundenNummer[index] = rundenEingabeFormularView[index]
				.getRundenNummer();
		for (int i = 0; i < (spielerAnzahl[index] * (spielerAnzahl[index] - 1) / 2); i++) {

			changeColor[index][i].addActionListener(this);
			datePicker[index][i].getJDateInstantPanel().addActionListener(this);
			rundenNummer[index][i].addActionListener(this);
		}

		if (tabAnzeigeView2[index].getComponentCount() == 2) {
			tabAnzeigeView2[index]
					.insertTab(
							Messages.getString("RundenEingabeFormularControl.5"), paarungenIcon, //$NON-NLS-1$
							rundenEingabeFormularView[index], null, 2);
		} else {
			tabAnzeigeView2[index].setComponentAt(2,
					rundenEingabeFormularView[index]);
			tabAnzeigeView2[index].setIconAt(2, paarungenIcon);
		}
		rundenEingabeFormularView[index].getReloadButton().addActionListener(
				this);
		rundenEingabeFormularView[index].updateUI();
	}

	public void makeRundenEditView(int index) {
		neuesTurnier[index] = true;
		// Arrays.sort(gruppe[index].getPartien());
		partien = gruppe[index].getPartien();

		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();

		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		if (tabAnzeigeView2 != null) {
			if (tabAnzeigeView2[index].getTabCount() < 3) {
				tabAnzeigeView2[index]
						.insertTab(
								Messages.getString("RundenEingabeFormularControl.6"), null, //$NON-NLS-1$
								rundenEingabeFormularView[index], null, 2);
			} else {

				tabAnzeigeView2[index].setComponentAt(2,
						rundenEingabeFormularView[index]);
			}
		}
		makeNewFormular(index);
	}

	public void makeTerminTabelle(int index) {
		init();
		neuesTurnier[index] = true;
		paarungsTafeln[index] = new PaarungsTafeln(gruppe[index]);
		gruppe[index] = paarungsTafeln[index].getGruppe();
		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();

		TurnierTabelleControl turnierTabelleControl = new TurnierTabelleControl(
				mainControl);
		TerminTabelleControl terminTabelleControl = new TerminTabelleControl(
				mainControl);
		mainControl.setTurnierTabelleControl(turnierTabelleControl);
		mainControl.setTerminTabelleControl(terminTabelleControl);
		turnierTabelleControl.makeSimpleTableView(index);
		terminTabelleControl.makeSimpleTableView(index);
		neuesTurnier[index] = true;
		// Arrays.sort(gruppe[index].getPartien());
		partien = gruppe[index].getPartien();

		spielerAnzahl[index] = gruppe[index].getSpielerAnzahl();

		tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();
		makePairingsLoadPanel(index);

		mainControl
				.getNaviView()
				.setTabellenname(
						Messages.getString("RundenEingabeFormularControl.7") + mainControl.getTurnier().getTurnierName()); //$NON-NLS-1$
		tabAnzeigeView2[index].setEnabledAt(0, false);
		tabAnzeigeView2[index].setEnabledAt(1, false);
		tabAnzeigeView2[index].setEnabledAt(2, false);
		Boolean readyToSave = checkIfReadyToSave();
		if (readyToSave == true) {
			JOptionPane
					.showMessageDialog(null, "Das Turnier wird gespeichert."); //$NON-NLS-1$ //$NON-NLS-2$
			saveAndReloadTurnier();

		}
	}

	private Boolean checkIfReadyToSave() {
		Boolean readyToSave = false;
		for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {

			if (mainControl.getSpielerEingabeControl().getReadyToSave()[i] == true) {
				readyToSave = true;
			} else {
				return false;
			}
		}
		return readyToSave;
	}

	private Boolean saveAndReloadTurnier() {

		Boolean ok = true;
		try {
			ok = this.mainControl.getSaveTurnierControl().saveChangedPartien();
		} catch (SQLException e) {
			ok = false;
			e.printStackTrace();
		}
		return ok;
	}

	private void setTabsEnable(Boolean enable) {

		mainControl.getNaviView().getTabellenPanel().setVisible(enable);
		for (int i = 0; i < gruppenAnzahl; i++) {
			tabAnzeigeView2[i].setEnabledAt(0, enable);
			tabAnzeigeView2[i].setEnabledAt(1, enable);
		}
		mainControl.getTurnierListeLadenControl().reloadTurnier();

	}

	public Boolean[] getNeuesTurnier() {
		return neuesTurnier;
	}

	public void setNeuesTurnier(Boolean[] neuesTurnier) {
		this.neuesTurnier = neuesTurnier;
	}

	public int[][] getChangedGroups() {
		return changedGroups;
	}

	public void setChangedGroups(int[][] changedGroups) {
		this.changedGroups = changedGroups;
	}

	public Boolean checkNewTurnier() {
		Boolean ready = true;
		if (mainControl.getSpielerEingabeControl() != null) {
			for (int i = 0; i < mainControl.getTurnier().getAnzahlGruppen(); i++) {
				if (mainControl.getSpielerEingabeControl().getReadyToSave()[i] == false) {
					ready = false;
				}
			}
		}
		return ready;
	}
}
