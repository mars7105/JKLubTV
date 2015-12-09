package de.turnierverwaltung.controller;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

				turnierListe.get(turnierIndex).setTurnierName(turnierName);
				turnierListe.get(turnierIndex).setStartDatum(startDatum);
				turnierListe.get(turnierIndex).setEndDatum(endDatum);

				turnierTableControl.updateTurnier(turnierListe.get(turnierIndex));

				GruppenTableControl gtC = new GruppenTableControl(mainControl);
				gtC.getGruppe();

				for (int i = 0; i < turnierListe.get(turnierIndex).getAnzahlGruppen(); i++) {
					String gEV = turnierEditierenView.getTextFieldGruppenName()[i].getText();
					turnierListe.get(turnierIndex).getGruppe()[i].setGruppenName(gEV);
					gtC.updateGruppe(i);
				}

				mainControl.setEnabled(true);
				turnierEditierenView.dispose();
				loadTurnier();

			}
			if (arg0.getSource() == turnierEditierenView.getCancelButton()) {
				mainControl.setEnabled(true);
				turnierEditierenView.dispose();
			}
		}
		if (arg0.getSource() == turnierListeLadenView.getTurnierAddButton()) {
			Turnier turnier = this.mainControl.getTurnier();
			if (turnier == null) {
				mainControl.setTurnierControl(new TurnierControl(mainControl));
			} else {
				// Custom button text
				Object[] options = { "Ja", "Abbrechen" };
				int abfrage = JOptionPane.showOptionDialog(null,
						"Wollen Sie wirklich ein neues Turnier erstellen? " + "Alle eingegebenen Daten gehen verloren.",
						"A Silly Question", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
						options, options[1]);
				if (abfrage == 0) {
					// mainControl.resetApp();
					mainControl.setTurnierControl(new TurnierControl(mainControl));
				}
			}
		}

		for (int i = 0; i < anzahlTurniere; i++) {

			if (arg0.getSource() == turnierListeLadenView.getTurnierLadeButton()[i]) {
				if (turnier != turnierListe.get(i)) {
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
						mainControl.getPartienTableControl().getPartien(z);
					}
					tabbedPaneView2 = new TabAnzeigeView[turnier.getAnzahlGruppen()];

					mainControl.setTabAnzeigeView2(tabbedPaneView2);
					TurnierTabelleControl turnierTabelleControl = new TurnierTabelleControl(mainControl);
					TerminTabelleControl terminTabelleControl = new TerminTabelleControl(mainControl);
					RundenEingabeFormularControl rundenEingabeFormularControl = new RundenEingabeFormularControl(
							mainControl);

					mainControl.setTurnierTabelleControl(turnierTabelleControl);
					mainControl.setTerminTabelleControl(terminTabelleControl);
					mainControl.setRundenEingabeFormularControl(rundenEingabeFormularControl);

					for (int z = 0; z < turnier.getAnzahlGruppen(); z++) {
						tabbedPaneView2[z] = new TabAnzeigeView(mainControl);
						tabbedPaneView.insertTab(turnier.getGruppe()[z].getGruppenName(), gruppenIcon,
								tabbedPaneView2[z], null, z);
						mainControl.getTurnierTabelleControl().makeSimpleTableView(z);
						mainControl.getTerminTabelleControl().makeSimpleTableView(z);
						// mainControl.getRundenEingabeFormularControl().makeTerminTabelle(z);
						mainControl.getRundenEingabeFormularControl().makeRundenEditView(z);
						mainControl.getTurnierTabelleControl().okAction(z);
					}

					// hauptPanel.removeAll();
					// mainControl.getNaviController().makeNaviPanel();
					mainControl.getNaviView()
							.setTabellenname("Turnier: " + mainControl.getTurnier().getTurnierName());
					hauptPanel.addTab(turnier.getTurnierName(), turnierIcon, tabbedPaneView);
					int selectIndex = hauptPanel.getTabCount() - 1;
					hauptPanel.setSelectedIndex(selectIndex);
					hauptPanel.addChangeListener(new TurnierAnsicht(mainControl, turnier));
//					hauptPanel.updateUI();

				}
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
				TurnierTableControl ttC = new TurnierTableControl(mainControl);
				ttC.loescheTurnier(turnierListe.get(i));
				loadTurnier();
			}

		}
	}

	public void loadTurnier() {
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
		this.turnierListeLadenView = new TurnierListeLadenView(anzahlTurniere);
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
		turnierListeLadenView.getTurnierAddButton().addActionListener(this);
		// hauptPanel.removeAll();
		// mainControl.getNaviController().makeNaviPanel();
		hauptPanel.addTab("Turnierliste", turnierListeIcon, turnierListeLadenView);
		hauptPanel.updateUI();
	}

	class TurnierAnsicht implements ChangeListener {

		private MainControl mainControl;
		private Turnier turnier;

		public TurnierAnsicht(MainControl mainControl, Turnier turnier) {
			super();
			this.mainControl = mainControl;
			this.turnier = turnier;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			if (e.getSource() instanceof JTabbedPane) {
				JTabbedPane pane = (JTabbedPane) e.getSource();
				int selectedIndex = pane.getSelectedIndex();
				if (pane.getTitleAt(selectedIndex).compareTo(turnier.getTurnierName()) == 0) {
					this.mainControl.getNaviView().getTabellenPanel().setVisible(true);

				} else {
					this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
				}
			}
		}

	}
}
