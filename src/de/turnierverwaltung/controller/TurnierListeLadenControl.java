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
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.view.TabAnzeigeView;
import de.turnierverwaltung.view.TurnierEditierenView;
import de.turnierverwaltung.view.TurnierListeLadenView;

public class TurnierListeLadenControl implements ActionListener {
	private MainControl mainControl;
	private TurnierListeLadenView turnierListeLadenView;
	private TurnierTableControl turnierTableControl;
	private TurnierEditierenView turnierEditierenView;
	private JTabbedPane hauptPanel;
	private int anzahlTurniere;
	private Turnier turnier;
	private TabAnzeigeView tabbedPaneView;
	private TabAnzeigeView[] tabbedPaneView2;
	private ArrayList<Turnier> turnierListe;
	private int turnierIndex;
	private ImageIcon turnierListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/games-highscores.png")));
	private ImageIcon turnierIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png")));
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png")));

	public TurnierListeLadenControl(MainControl mainControl) {
		turnierIndex = -1;
		anzahlTurniere = 0;

		this.mainControl = mainControl;

		this.mainControl.setTurnierListeLadenView(turnierListeLadenView);
		turnierTableControl = mainControl.getTurnierTableControl();

		this.mainControl.setTabAnzeigeControl(new TabAnzeigeControl(this.mainControl));

		hauptPanel = this.mainControl.getHauptPanel();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (turnierEditierenView != null) {

			if (arg0.getSource() == turnierEditierenView.getOkButton()) {
				String turnierName = turnierEditierenView.getTextFieldTurnierName().getText();
				String startDatum = turnierEditierenView.getStartDatumTextField().getJFormattedTextField().getText();
				String endDatum = turnierEditierenView.getEndDatumTextField().getJFormattedTextField().getText();
				int lastTab = hauptPanel.getTabCount() - 1;
				if (mainControl.getTurnier().getTurnierId() == turnierListe.get(turnierIndex).getTurnierId()) {
					hauptPanel.setTitleAt(lastTab, turnierName);
				}
				turnierListe.get(turnierIndex).setTurnierName(turnierName);
				turnierListe.get(turnierIndex).setStartDatum(startDatum);
				turnierListe.get(turnierIndex).setEndDatum(endDatum);

				turnierTableControl.updateTurnier(turnierListe.get(turnierIndex));

				GruppenTableControl gtC = new GruppenTableControl(mainControl);
				gtC.getGruppe();

				for (int i = 0; i < turnierListe.get(turnierIndex).getAnzahlGruppen(); i++) {

					String gEV = turnierEditierenView.getTextFieldGruppenName()[i].getText();
					if (mainControl.getTurnier().getTurnierId() == turnierListe.get(turnierIndex).getTurnierId()) {
						JTabbedPane temp = (JTabbedPane) hauptPanel.getComponentAt(lastTab);
						temp.setTitleAt(i, gEV);
					}
					turnierListe.get(turnierIndex).getGruppe()[i].setGruppenName(gEV);
					gtC.updateGruppe(i);
				}

				mainControl.setEnabled(true);
				turnierEditierenView.dispose();
				loadTurnierListe();

			}
			if (arg0.getSource() == turnierEditierenView.getCancelButton()) {
				mainControl.setEnabled(true);
				turnierEditierenView.dispose();
			}
		}

		for (int i = 0; i < anzahlTurniere; i++) {

			if (arg0.getSource() == turnierListeLadenView.getTurnierLadeButton()[i]) {
				loadTurnier(i);
			}

			if (arg0.getSource() == turnierListeLadenView.getTurnierBearbeitenButton()[i]) {
				turnierIndex = i;
				tabbedPaneView = new TabAnzeigeView(mainControl);

				mainControl.setTabAnzeigeView(tabbedPaneView);
				turnier = turnierListe.get(i);
				mainControl.setTurnier(turnier);
				mainControl.setGruppenTableControl(new GruppenTableControl(mainControl));
				mainControl.getGruppenTableControl().getGruppe();
				mainControl.setSpielerTableControl(new SpielerTableControl(mainControl));
				mainControl.getSpielerTableControl().getSpieler();

				mainControl.setPartienTableControl(new PartienTableControl(mainControl));
				for (int z = 0; z < mainControl.getTurnier().getAnzahlGruppen(); z++) {
					tabbedPaneView.insertTab("", null, new JPanel(), null, z);
					mainControl.getPartienTableControl().getPartien(z);
				}

				turnierEditierenView = new TurnierEditierenView(turnierListe.get(i));
				turnierEditierenView.getOkButton().addActionListener(this);
				turnierEditierenView.getCancelButton().addActionListener(this);
				// turnierEditierenView.getRundenEditierenButton().addActionListener(this);
				mainControl.setEnabled(false);

			}

			// Wichtig:
			// Diese Abfrage muss an letzter Stelle stehen,
			// da ansonsten eine ArraOutOfBounds Exception auftritt!
			if (arg0.getSource() == turnierListeLadenView.getTurnierLoeschenButton()[i]) {
				if (mainControl.getTurnier() != null) {
					if (mainControl.getTurnier().getTurnierId() == turnierListe.get(i).getTurnierId()) {
						JOptionPane.showMessageDialog(null,
								"Turnier kann nicht gelöscht werden\n da es gerade bearbeitet wird.");

					} else {
						deleteTurnier(i);
					}
				} else {
					deleteTurnier(i);
				}
			}

		}

	}

	private void deleteTurnier(int turnierId) {
		TurnierTableControl ttC = new TurnierTableControl(mainControl);
		ttC.loescheTurnier(turnierListe.get(turnierId));
		loadTurnierListe();
	}

	public void reloadTurnier() {
		if (turnier != null) {
			for (int i = 0; i < anzahlTurniere; i++) {
				if (turnierListe.get(i).getTurnierId() == turnier.getTurnierId()) {
					int selectedTab = hauptPanel.getSelectedIndex();
					loadTurnier(i);
					hauptPanel.setSelectedIndex(selectedTab);
				}
			}
		}
	}

	public void loadTurnier(int index) {
		mainControl.setSpielerEingabeControl(null);
		if (mainControl.getTurnier() != null) {
			int selectedIndex = hauptPanel.getTabCount() - 1;

			hauptPanel.remove(selectedIndex);

		}
		tabbedPaneView = new TabAnzeigeView(mainControl);

		mainControl.setTabAnzeigeView(tabbedPaneView);
		turnier = turnierListe.get(index);
		mainControl.setTurnier(turnier);
		mainControl.setGruppenTableControl(new GruppenTableControl(mainControl));
		mainControl.getGruppenTableControl().getGruppe();
		mainControl.setSpielerTableControl(new SpielerTableControl(mainControl));
		mainControl.getSpielerTableControl().getSpieler();

		mainControl.setPartienTableControl(new PartienTableControl(mainControl));
		for (int z = 0; z < mainControl.getTurnier().getAnzahlGruppen(); z++) {
			mainControl.getPartienTableControl().getPartien(z);
		}
		tabbedPaneView2 = new TabAnzeigeView[turnier.getAnzahlGruppen()];

		mainControl.setTabAnzeigeView2(tabbedPaneView2);
		TurnierTabelleControl turnierTabelleControl = new TurnierTabelleControl(mainControl);
		TerminTabelleControl terminTabelleControl = new TerminTabelleControl(mainControl);
		RundenEingabeFormularControl rundenEingabeFormularControl = new RundenEingabeFormularControl(mainControl);

		mainControl.setTurnierTabelleControl(turnierTabelleControl);
		mainControl.setTerminTabelleControl(terminTabelleControl);
		mainControl.setRundenEingabeFormularControl(rundenEingabeFormularControl);

		for (int z = 0; z < turnier.getAnzahlGruppen(); z++) {
			tabbedPaneView2[z] = new TabAnzeigeView(mainControl);
			tabbedPaneView.insertTab(turnier.getGruppe()[z].getGruppenName(), gruppenIcon, tabbedPaneView2[z], null, z);
			mainControl.getTurnierTabelleControl().makeSimpleTableView(z);
			mainControl.getTerminTabelleControl().makeSimpleTableView(z);
			// mainControl.getRundenEingabeFormularControl().makeTerminTabelle(z);
			mainControl.getRundenEingabeFormularControl().makeRundenEditView(z);
			mainControl.getTurnierTabelleControl().okAction(z);
		}

		mainControl.getNaviView().setTabellenname("Turnier: " + mainControl.getTurnier().getTurnierName());

		hauptPanel.addTab(turnier.getTurnierName(), turnierIcon, tabbedPaneView);
		int selectIndex = hauptPanel.getTabCount() - 1;
		hauptPanel.setSelectedIndex(selectIndex);
	}

	public void loadTurnierListe() {
		Turnier temp = null;
		String turnierName = "";
		String startDatum = "";
		String endDatum = "";
		if (turnierTableControl != null) {
			turnierListe = turnierTableControl.loadTurnierListe();
		} else {
			mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
			turnierTableControl = mainControl.getTurnierTableControl();
			turnierListe = turnierTableControl.loadTurnierListe();

		}
		anzahlTurniere = turnierListe.size();
		if (this.turnierListeLadenView == null) {
			this.turnierListeLadenView = new TurnierListeLadenView(anzahlTurniere);
			hauptPanel.addTab("Turnierliste", turnierListeIcon, turnierListeLadenView);

		} else {
			this.turnierListeLadenView.removeAll();
			this.turnierListeLadenView.makePanel(anzahlTurniere);
		}

		ListIterator<Turnier> li = turnierListe.listIterator();
		while (li.hasNext()) {
			temp = li.next();
			turnierName = temp.getTurnierName();
			startDatum = temp.getStartDatum();
			endDatum = temp.getEndDatum();
			turnierListeLadenView.makeTurnierZeile(turnierName, startDatum, endDatum);
		}
		for (int i = 0; i < anzahlTurniere; i++) {
			turnierListeLadenView.getTurnierLadeButton()[i].addActionListener(this);
			turnierListeLadenView.getTurnierBearbeitenButton()[i].addActionListener(this);
			turnierListeLadenView.getTurnierLoeschenButton()[i].addActionListener(this);

		}
		this.turnierListeLadenView.updateUI();
	}

}
