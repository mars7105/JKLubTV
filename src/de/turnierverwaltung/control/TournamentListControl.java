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

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.ButtonTabComponent;
import de.turnierverwaltung.view.EditTournamentView;
import de.turnierverwaltung.view.TournamentListView;

public class TournamentListControl implements ActionListener {
	private MainControl mainControl;
	private TournamentListView turnierListeLadenView;
	private SQLTournamentControl turnierTableControl;
	private EditTournamentView turnierEditierenView;
	private JTabbedPane hauptPanel;
	private int anzahlTurniere;
	private Tournament turnier;
	private TabbedPaneView tabbedPaneView;
	private TabbedPaneView[] tabbedPaneView2;
	private ArrayList<Tournament> turnierListe;
	private int loadedTurnierID;

	private ImageIcon turnierListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/games-highscores.png"))); //$NON-NLS-1$
	private ImageIcon turnierIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png"))); //$NON-NLS-1$
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png"))); //$NON-NLS-1$
	private Tournament turnierEdit;
	private boolean selectTurnierTab;
	private ButtonTabComponent buttonTabComponent;

	public TournamentListControl(MainControl mainControl) {
		anzahlTurniere = 0;
		loadedTurnierID = -1;
		this.mainControl = mainControl;

		this.mainControl.setTurnierListeLadenView(turnierListeLadenView);
		turnierTableControl = mainControl.getTurnierTableControl();

		this.mainControl.setTabAnzeigeControl(new TabbedPaneViewControl(this.mainControl, "X"));

		hauptPanel = this.mainControl.getHauptPanel();
		selectTurnierTab = false;
		mainControl.setButtonTabComponent(new ButtonTabComponent(hauptPanel, mainControl, turnierIcon, true));

		buttonTabComponent = mainControl.getButtonTabComponent();
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

				try {
					turnierTableControl.updateTurnier(turnierEdit);

					SQLGroupsControl gtC = new SQLGroupsControl(mainControl);
					gtC.updateGruppen(turnierEdit);

					mainControl.setEnabled(true);
					turnierEditierenView.dispose();

					loadTurnierListe();
					if (turnierEdit.getTurnierId() == loadedTurnierID) {
						reloadTurnier();
					}
				} catch (SQLException e) {
					turnierEditierenView.dispose();
					mainControl.fileSQLError();
				}

			}
			if (arg0.getSource() == turnierEditierenView.getCancelButton()) {
				mainControl.setEnabled(true);

				turnierEditierenView.dispose();
			}
		}

		for (int i = 0; i < anzahlTurniere; i++) {

			if (arg0.getSource() == turnierListeLadenView.getTurnierLadeButton()[i]) {
				Tournament turnier = this.mainControl.getTurnier();

				if (turnier == null) {
					selectTurnierTab = true;
					try {
						loadTurnier(i);
					} catch (SQLException e) {
						mainControl.fileSQLError();
					}
				} else {
					if (turnier.getTurnierId() == -1) {
						// Custom button text
						Object[] options = { Messages.getString("TurnierListeLadenControl.16"), //$NON-NLS-1$
								Messages.getString("TurnierListeLadenControl.17") }; //$NON-NLS-1$
						int abfrage = JOptionPane.showOptionDialog(mainControl,
								Messages.getString("TurnierListeLadenControl.18") //$NON-NLS-1$
										+ Messages.getString("TurnierListeLadenControl.19"), //$NON-NLS-1$
								Messages.getString("TurnierListeLadenControl.20"), //$NON-NLS-1$
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
								options[1]);
						if (abfrage == 0) {

							selectTurnierTab = true;
							try {
								loadTurnier(i);
							} catch (SQLException e) {
								mainControl.fileSQLError();
							}
						}
					} else {
						ArrayList<Game> changedPartien = this.mainControl.getChangedPartien();
						if (changedPartien != null) {
							if (changedPartien.size() > 0) {
								// Custom button text
								Object[] options = { Messages.getString("TurnierListeLadenControl.10"), //$NON-NLS-1$
										Messages.getString("TurnierListeLadenControl.11") }; //$NON-NLS-1$
								int abfrage = JOptionPane.showOptionDialog(mainControl,
										Messages.getString("TurnierListeLadenControl.12") //$NON-NLS-1$
												+ Messages.getString("TurnierListeLadenControl.13"), //$NON-NLS-1$
										Messages.getString("TurnierListeLadenControl.14"), //$NON-NLS-1$
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
										options[1]);
								if (abfrage == 0) {
									SaveTournamentControl saveGames = new SaveTournamentControl(mainControl);
									try {
										Boolean saved = saveGames.saveChangedPartien();
										if (saved == false) {
											changedPartien.clear();
										}

									} catch (SQLException e) {
										changedPartien.clear();
									}
									selectTurnierTab = true;
									try {
										loadTurnier(i);
									} catch (SQLException e) {
										mainControl.fileSQLError();
									}
								}
							} else if (changedPartien.size() == 0) {
								selectTurnierTab = true;
								try {
									loadTurnier(i);
								} catch (SQLException e) {
									mainControl.fileSQLError();
								}
							}

						} else {
							selectTurnierTab = true;
							try {
								loadTurnier(i);
							} catch (SQLException e) {
								mainControl.fileSQLError();
							}
						}
					}
				}
			}

			if (arg0.getSource() == turnierListeLadenView.getTurnierBearbeitenButton()[i]) {
				turnierEdit = turnierListe.get(i);
				SQLGroupsControl gTC = new SQLGroupsControl(mainControl);
				try {
					turnierEdit = gTC.getGruppe(turnierEdit);

					turnierEditierenView = new EditTournamentView(turnierEdit);
					turnierEditierenView.getOkButton().addActionListener(this);
					turnierEditierenView.getCancelButton().addActionListener(this);
					mainControl.setEnabled(false);
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}
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
		SQLTournamentControl ttC = new SQLTournamentControl(mainControl);
		ttC.loescheTurnier(turnierListe.get(turnierId));
		loadTurnierListe();
	}

	public void reloadTurnier() throws SQLException {
		turnier = mainControl.getTurnier();
		if (turnier != null && loadedTurnierID >= 0) {
			for (int i = 0; i < anzahlTurniere; i++) {
				if (turnierListe.get(i).getTurnierId() == loadedTurnierID) {

					loadTurnier(i);
				}
			}
		}
	}

	public void loadTurnier(int index) throws SQLException {
		int selectedTab = hauptPanel.getSelectedIndex();

		mainControl.setSpielerEingabeControl(null);
		if (mainControl.getTurnier() != null) {
			if (hauptPanel.getTabCount() - 1 == TournamentConstants.TAB_ACTIVE_TOURNAMENT) {
				hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);

			}

		}
		
		turnier = turnierListe.get(index);
		mainControl.setTurnier(turnier);
		mainControl.setGruppenTableControl(new SQLGroupsControl(mainControl));
		mainControl.getGruppenTableControl().getGruppe();
		mainControl.setSpielerTableControl(new SQLPlayerControl(mainControl));
		mainControl.getSpielerTableControl().getSpieler();
		tabbedPaneView = new TabbedPaneView(mainControl, Messages.getString("TurnierListeLadenControl.15"));
		tabbedPaneView.getTitleView().setFlowLayoutLeft();
		mainControl.setTabAnzeigeView(tabbedPaneView);
		mainControl.setPartienTableControl(new SQLGamesControl(mainControl));
		for (int z = 0; z < mainControl.getTurnier().getAnzahlGruppen(); z++) {
			mainControl.getPartienTableControl().getPartien(z);
		}
		tabbedPaneView2 = new TabbedPaneView[turnier.getAnzahlGruppen()];

		mainControl.setTabAnzeigeView2(tabbedPaneView2);
		CrossTableControl turnierTabelleControl = new CrossTableControl(mainControl);
		MeetingTableControl terminTabelleControl = new MeetingTableControl(mainControl);

		mainControl.setTurnierTabelleControl(turnierTabelleControl);
		mainControl.setTerminTabelleControl(terminTabelleControl);

		for (int z = 0; z < turnier.getAnzahlGruppen(); z++) {
			tabbedPaneView2[z] = new TabbedPaneView(mainControl, "Gruppen");
			tabbedPaneView.getTabbedPane().insertTab(turnier.getGruppe()[z].getGruppenName(), gruppenIcon,
					tabbedPaneView2[z], null, z);
			mainControl.getTurnierTabelleControl().makeSimpleTableView(z);

			mainControl.getTerminTabelleControl().makeSimpleTableView(z);
			mainControl.getTurnierTabelleControl().okAction(z);
		}
		PairingsControl rundenEingabeFormularControl = new PairingsControl(mainControl);
		mainControl.setRundenEingabeFormularControl(rundenEingabeFormularControl);
		mainControl.getTurnier().setNoDWZCalc(mainControl.getPropertiesControl().getNoDWZ());
		mainControl.getTurnier().setNoFolgeDWZCalc(mainControl.getPropertiesControl().getNoFolgeDWZ());
		mainControl.getNaviView().setTabellenname(
				Messages.getString("TurnierListeLadenControl.5") + mainControl.getTurnier().getTurnierName()); //$NON-NLS-1$
		mainControl.getNaviController().setPairingIsActive(false);
		this.mainControl.setNeuesTurnier(false);

		hauptPanel.addTab(turnier.getTurnierName(), turnierIcon, tabbedPaneView);
		buttonTabComponent = new ButtonTabComponent(hauptPanel, mainControl, turnierIcon, true);

		hauptPanel.setTabComponentAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, buttonTabComponent);
		if (selectTurnierTab == true) {
			selectTurnierTab = false;
			selectedTab = hauptPanel.getTabCount() - 1;
		} else {
			hauptPanel.setSelectedIndex(hauptPanel.getTabCount() - 1);
		}
		hauptPanel.setSelectedIndex(selectedTab);
		loadedTurnierID = turnier.getTurnierId();

	}

	public void loadTurnierListe() throws SQLException {
		Tournament temp = null;
		String turnierName = ""; //$NON-NLS-1$
		String startDatum = ""; //$NON-NLS-1$
		String endDatum = ""; //$NON-NLS-1$
		if (turnierTableControl != null) {
			turnierListe = turnierTableControl.loadTurnierListe();
		} else {
			mainControl.setTurnierTableControl(new SQLTournamentControl(mainControl));
			turnierTableControl = mainControl.getTurnierTableControl();
			turnierListe = turnierTableControl.loadTurnierListe();

		}
		if (turnierListe != null) {
			anzahlTurniere = turnierListe.size();
			if (this.turnierListeLadenView == null) {
				this.turnierListeLadenView = new TournamentListView(anzahlTurniere,
						mainControl.getPropertiesControl().getTurniereProTab());
				hauptPanel.addTab(Messages.getString("TurnierListeLadenControl.9"), turnierListeIcon, //$NON-NLS-1$
						turnierListeLadenView);
				ButtonTabComponent buttonComp = new ButtonTabComponent(hauptPanel, mainControl, turnierListeIcon,
						false);
				hauptPanel.setTabComponentAt(TournamentConstants.TAB_TOURNAMENTS_LIST, buttonComp);

			} else {
				this.turnierListeLadenView.removeAll();
				this.turnierListeLadenView.makePanel(anzahlTurniere);
			}
			turnierListeLadenView.getTitleView().setFlowLayoutLeft();
			// Collections.sort(turnierListe, new SortTournamentList());
			ListIterator<Tournament> li = turnierListe.listIterator();
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

	public void loadPairingsView() {
		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();
		if (ready) {
			mainControl.getNaviView().getTabellenPanel().setVisible(false);
			PairingsControl pairingsControl = mainControl.getRundenEingabeFormularControl();
			pairingsControl.init();
			int gruppenAnzahl = mainControl.getTurnier().getAnzahlGruppen();
			TabbedPaneView[] tabAnzeigeView2 = this.mainControl.getTabAnzeigeView2();

			for (int i = 0; i < gruppenAnzahl; i++) {
				tabAnzeigeView2[i].getTabbedPane().setEnabledAt(0, false);
				tabAnzeigeView2[i].getTabbedPane().setEnabledAt(1, false);
				pairingsControl.makeRundenEditView(i);
				tabAnzeigeView2[i].getTabbedPane().setSelectedIndex(2);

			}
			this.mainControl.getNaviView().getPairingsPanel().setVisible(true);
			this.mainControl.getNaviController().setPairingIsActive(true);

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}

	}

}
