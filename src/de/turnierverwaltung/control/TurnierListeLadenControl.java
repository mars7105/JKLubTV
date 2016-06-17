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
import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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
	private int loadedTurnierID;

	private ImageIcon turnierListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/games-highscores.png"))); //$NON-NLS-1$
	private ImageIcon turnierIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png"))); //$NON-NLS-1$
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png"))); //$NON-NLS-1$
	private Turnier turnierEdit;

	public TurnierListeLadenControl(MainControl mainControl) {
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

				turnierEdit.setTurnierName(turnierName);
				turnierEdit.setStartDatum(startDatum);
				turnierEdit.setEndDatum(endDatum);

				for (int i = 0; i < turnierEdit.getAnzahlGruppen(); i++) {

					String gEV = turnierEditierenView.getTextFieldGruppenName()[i].getText();

					turnierEdit.getGruppe()[i].setGruppenName(gEV);
				}

				turnierTableControl.updateTurnier(turnierEdit);

				GruppenTableControl gtC = new GruppenTableControl(mainControl);
				gtC.updateGruppen(turnierEdit);

				mainControl.setEnabled(true);
				turnierEditierenView.dispose();
				try {
					loadTurnierListe();
					if (turnierEdit.getTurnierId() == loadedTurnierID) {
						reloadTurnier();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (arg0.getSource() == turnierEditierenView.getCancelButton()) {
				mainControl.setEnabled(true);

				turnierEditierenView.dispose();
			}
		}

		for (int i = 0; i < anzahlTurniere; i++) {

			if (arg0.getSource() == turnierListeLadenView.getTurnierLadeButton()[i]) {
				Turnier turnier = this.mainControl.getTurnier();
				if (turnier == null) {
					loadTurnier(i);
				} else {
					// Custom button text
					Object[] options = { Messages.getString("TurnierListeLadenControl.10"), //$NON-NLS-1$
							Messages.getString("TurnierListeLadenControl.11") }; //$NON-NLS-1$
					int abfrage = JOptionPane.showOptionDialog(mainControl,
							Messages.getString("TurnierListeLadenControl.12") //$NON-NLS-1$
									+ Messages.getString("TurnierListeLadenControl.13"), //$NON-NLS-1$
							Messages.getString("TurnierListeLadenControl.14"), JOptionPane.YES_NO_CANCEL_OPTION, //$NON-NLS-1$
							JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (abfrage == 0) {
						loadTurnier(i);
					}
				}

			}

			if (arg0.getSource() == turnierListeLadenView.getTurnierBearbeitenButton()[i]) {
				turnierEdit = turnierListe.get(i);
				GruppenTableControl gTC = new GruppenTableControl(mainControl);
				turnierEdit = gTC.getGruppe(turnierEdit);

				turnierEditierenView = new TurnierEditierenView(turnierEdit);
				turnierEditierenView.getOkButton().addActionListener(this);
				turnierEditierenView.getCancelButton().addActionListener(this);
				mainControl.setEnabled(false);

			}

			// Wichtig:
			// Diese Abfrage muss an letzter Stelle stehen,
			// da ansonsten eine ArraOutOfBounds Exception auftritt!
			if (arg0.getSource() == turnierListeLadenView.getTurnierLoeschenButton()[i]) {
				if (mainControl.getTurnier() != null) {
					if (mainControl.getTurnier().getTurnierId() == turnierListe.get(i).getTurnierId()) {
						JOptionPane.showMessageDialog(mainControl, Messages.getString("TurnierListeLadenControl.4")); //$NON-NLS-1$

					} else {
						try {
							deleteTurnier(i);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					try {
						deleteTurnier(i);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}

	private void deleteTurnier(int turnierId) throws SQLException {
		TurnierTableControl ttC = new TurnierTableControl(mainControl);
		ttC.loescheTurnier(turnierListe.get(turnierId));
		loadTurnierListe();
	}

	public void reloadTurnier() {
		turnier = mainControl.getTurnier();
		if (turnier != null && loadedTurnierID >= 0) {
			for (int i = 0; i < anzahlTurniere; i++) {
				if (turnierListe.get(i).getTurnierId() == loadedTurnierID) {

					loadTurnier(i);
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

		mainControl.setTurnierTabelleControl(turnierTabelleControl);
		mainControl.setTerminTabelleControl(terminTabelleControl);

		for (int z = 0; z < turnier.getAnzahlGruppen(); z++) {
			tabbedPaneView2[z] = new TabAnzeigeView(mainControl);
			tabbedPaneView.insertTab(turnier.getGruppe()[z].getGruppenName(), gruppenIcon, tabbedPaneView2[z], null, z);
			mainControl.getTurnierTabelleControl().makeSimpleTableView(z);

			mainControl.getTerminTabelleControl().makeSimpleTableView(z);
			mainControl.getTurnierTabelleControl().okAction(z);
		}
		RundenEingabeFormularControl rundenEingabeFormularControl = new RundenEingabeFormularControl(mainControl);
		mainControl.setRundenEingabeFormularControl(rundenEingabeFormularControl);
		mainControl.getTurnier().setNoDWZCalc(mainControl.getPropertiesControl().getNoDWZ());
		mainControl.getTurnier().setNoFolgeDWZCalc(mainControl.getPropertiesControl().getNoFolgeDWZ());
		mainControl.getNaviView().setTabellenname(
				Messages.getString("TurnierListeLadenControl.5") + mainControl.getTurnier().getTurnierName()); //$NON-NLS-1$
		mainControl.getNaviController().setPairingIsActive(false);
		this.mainControl.setNeuesTurnier(false);
		hauptPanel.addTab(turnier.getTurnierName(), turnierIcon, tabbedPaneView);
		int selectIndex = hauptPanel.getTabCount() - 1;
		hauptPanel.setSelectedIndex(selectIndex);
		this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
		loadedTurnierID = turnier.getTurnierId();
		mainControl.getEigenschaftenControl().setColumnWidhtToZero();

	}

	public void loadTurnierListe() throws SQLException {
		Turnier temp = null;
		String turnierName = ""; //$NON-NLS-1$
		String startDatum = ""; //$NON-NLS-1$
		String endDatum = ""; //$NON-NLS-1$
		if (turnierTableControl != null) {
			turnierListe = turnierTableControl.loadTurnierListe();
		} else {
			mainControl.setTurnierTableControl(new TurnierTableControl(mainControl));
			turnierTableControl = mainControl.getTurnierTableControl();
			turnierListe = turnierTableControl.loadTurnierListe();

		}
		if (turnierListe != null) {
			anzahlTurniere = turnierListe.size();
			if (this.turnierListeLadenView == null) {
				this.turnierListeLadenView = new TurnierListeLadenView(anzahlTurniere,
						mainControl.getPropertiesControl().getTurniereProTab());
				hauptPanel.addTab(Messages.getString("TurnierListeLadenControl.9"), turnierListeIcon, //$NON-NLS-1$
						turnierListeLadenView);

			} else {
				this.turnierListeLadenView.removeAll();
				this.turnierListeLadenView.makePanel(anzahlTurniere);
			}

			// Collections.sort(turnierListe, new SortTournamentList());
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
		} else {
			JOptionPane.showMessageDialog(mainControl, "Falsche Datei gewählt. "); //$NON-NLS-1$
		}
	}

	public int getLoadedTurnierID() {
		return loadedTurnierID;
	}

	public void setLoadedTurnierID(int loadedTurnierID) {
		this.loadedTurnierID = loadedTurnierID;
	}

}
