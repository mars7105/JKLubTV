package de.turnierverwaltung.control.tournamentlist;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.TabbedPaneViewControl;
import de.turnierverwaltung.control.sqlite.SQLGamesControl;
import de.turnierverwaltung.control.sqlite.SQLGroupsControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.control.sqlite.SQLTournamentControl;
import de.turnierverwaltung.control.sqlite.SaveTournamentControl;
import de.turnierverwaltung.control.tournamenttable.CrossTableControl;
import de.turnierverwaltung.control.tournamenttable.MeetingTableControl;
import de.turnierverwaltung.control.tournamenttable.PairingsControl;
import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.ProgressBarView;
import de.turnierverwaltung.view.TabbedPaneView;
import de.turnierverwaltung.view.tournamentlist.EditTournamentView;
import de.turnierverwaltung.view.tournamentlist.TournamentListView;
import de.turnierverwaltung.view.tournamenttable.ButtonTabComponent;

public class ActionListenerTournamentItemsControl implements ActionListener {
	private final MainControl mainControl;
	private TournamentListView turnierListeLadenView;
	private SQLTournamentControl turnierTableControl;
	private EditTournamentView turnierEditierenView;
	private final JTabbedPane hauptPanel;
	private int anzahlTurniere;
	private Tournament turnier;
	private TabbedPaneView tabbedPaneView;
	private TabbedPaneView[] tabbedPaneView2;
	private ArrayList<Tournament> turnierListe;
	private int loadedTurnierID;

	private final ImageIcon turnierListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/games-highscores.png"))); //$NON-NLS-1$
	private final ImageIcon turnierIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png"))); //$NON-NLS-1$
	private final ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png"))); //$NON-NLS-1$
	private Tournament turnierEdit;
	private boolean selectTurnierTab;
	private ButtonTabComponent buttonTabComponent;

	public ActionListenerTournamentItemsControl(final MainControl mainControl) {
		anzahlTurniere = 0;
		loadedTurnierID = -1;
		this.mainControl = mainControl;

		turnierTableControl = mainControl.getSqlTournamentControl();

		this.mainControl.setTabbedPaneViewControl(new TabbedPaneViewControl(this.mainControl, "X"));

		hauptPanel = this.mainControl.getHauptPanel();
		selectTurnierTab = false;
		mainControl.setButtonTabComponent(new ButtonTabComponent(hauptPanel, mainControl, turnierIcon, true));

		buttonTabComponent = mainControl.getButtonTabComponent();
	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

		if (turnierEditierenView != null) {

			if (arg0.getSource().equals(turnierEditierenView.getOkButton())) {
				final String turnierName = turnierEditierenView.getTextFieldTurnierName().getText();
				final DateFormat formatter1 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
				final DateFormat formatter2 = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
				String startDatum = "";
				String endDatum = "";
				try {

					startDatum = formatter1.format(turnierEditierenView.getStartDatumTextField().getDate());
					endDatum = formatter2.format(turnierEditierenView.getEndDatumTextField().getDate());

				} catch (final NullPointerException e2) {
					startDatum = "";
					endDatum = "";
				}

				turnierEdit.setTurnierName(turnierName);
				turnierEdit.setStartDatum(startDatum);
				turnierEdit.setEndDatum(endDatum);

				for (int i = 0; i < turnierEdit.getAnzahlGruppen(); i++) {

					final String gEV = turnierEditierenView.getTextFieldGruppenName()[i].getText();

					turnierEdit.getGruppe()[i].setGruppenName(gEV);
				}

				try {
					turnierTableControl.updateTurnier(turnierEdit);

					final SQLGroupsControl gtC = new SQLGroupsControl(mainControl);
					gtC.updateGruppen(turnierEdit);

					turnierEditierenView.dispose();
					// mainControl.setEnabled(true);
					loadTurnierListe();
					if (turnierEdit.getTurnierId() == loadedTurnierID) {
						reloadTurnier();
					}
				} catch (final SQLException e) {
					turnierEditierenView.dispose();
					// mainControl.setEnabled(true);
					final ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}

			}
			if (arg0.getSource().equals(turnierEditierenView.getCancelButton())) {

				turnierEditierenView.dispose();
				// mainControl.setEnabled(true);
			}
		}

		for (int i = 0; i < anzahlTurniere; i++) {

			if (arg0.getSource().equals(turnierListeLadenView.getTournamentListItems().get(i).getTurnierLadeButton())) {
				final Tournament turnier = mainControl.getTournament();

				if (turnier == null) {
					selectTurnierTab = true;
					try {
						loadTurnier(i);
					} catch (final SQLException e) {
						final ExceptionHandler eh = new ExceptionHandler(mainControl);
						eh.fileSQLError(e.getMessage());
					}
				} else {
					if (turnier.getTurnierId() == -1) {
						// Custom button text
						final Object[] options = { Messages.getString("TurnierListeLadenControl.16"), //$NON-NLS-1$
								Messages.getString("TurnierListeLadenControl.17") }; //$NON-NLS-1$
						final int abfrage = JOptionPane.showOptionDialog(mainControl,
								Messages.getString("TurnierListeLadenControl.18") //$NON-NLS-1$
										+ Messages.getString("TurnierListeLadenControl.19"), //$NON-NLS-1$
								Messages.getString("TurnierListeLadenControl.20"), //$NON-NLS-1$
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
								options[1]);
						if (abfrage == 0) {

							selectTurnierTab = true;
							try {
								loadTurnier(i);
							} catch (final SQLException e) {
								final ExceptionHandler eh = new ExceptionHandler(mainControl);
								eh.fileSQLError(e.getMessage());
							}
						}
					} else {
						final ArrayList<Game> changedPartien = mainControl.getChangedGames();
						if (changedPartien != null) {
							if (changedPartien.size() > 0) {
								// Custom button text
								final Object[] options = { Messages.getString("TurnierListeLadenControl.10"), //$NON-NLS-1$
										Messages.getString("TurnierListeLadenControl.11") }; //$NON-NLS-1$
								final int abfrage = JOptionPane.showOptionDialog(mainControl,
										Messages.getString("TurnierListeLadenControl.12") //$NON-NLS-1$
												+ Messages.getString("TurnierListeLadenControl.13"), //$NON-NLS-1$
										Messages.getString("TurnierListeLadenControl.14"), //$NON-NLS-1$
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
										options[1]);
								if (abfrage == 0) {
									final SaveTournamentControl saveGames = new SaveTournamentControl(mainControl);
									try {
										final Boolean saved = saveGames.saveChangedPartien();
										if (saved == false) {
											changedPartien.clear();
										}

									} catch (final SQLException e) {
										changedPartien.clear();
										final ExceptionHandler eh = new ExceptionHandler(mainControl);
										eh.fileSQLError(e.getMessage());
									}
									selectTurnierTab = true;
									try {
										loadTurnier(i);
									} catch (final SQLException e) {
										final ExceptionHandler eh = new ExceptionHandler(mainControl);
										eh.fileSQLError(e.getMessage());
									}
								}
							} else if (changedPartien.size() == 0) {
								selectTurnierTab = true;
								try {
									loadTurnier(i);
								} catch (final SQLException e) {
									final ExceptionHandler eh = new ExceptionHandler(mainControl);
									eh.fileSQLError(e.getMessage());
								}
							}

						} else {
							selectTurnierTab = true;
							try {
								loadTurnier(i);
							} catch (final SQLException e) {
								final ExceptionHandler eh = new ExceptionHandler(mainControl);
								eh.fileSQLError(e.getMessage());
							}
						}
					}
				}
			}

			if (arg0.getSource()
					.equals(turnierListeLadenView.getTournamentListItems().get(i).getTurnierBearbeitenButton())) {
				turnierEdit = turnierListe.get(i);
				final SQLGroupsControl gTC = new SQLGroupsControl(mainControl);
				try {
					// mainControl.setEnabled(false);
					turnierEdit = gTC.getGruppe(turnierEdit);

					turnierEditierenView = new EditTournamentView(turnierEdit);
					turnierEditierenView.getOkButton().addActionListener(this);
					turnierEditierenView.getCancelButton().addActionListener(this);
					// final ActionListenerPlayerGroupAddControl playerGroupAdd = new
					// ActionListenerPlayerGroupAddControl(
					// mainControl, turnierEdit);
					// playerGroupAdd.init();
					// final ActionListenerPlayerGroupDeleteControl playerGroupDelete = new
					// ActionListenerPlayerGroupDeleteControl(
					// mainControl, turnierEdit);
					// playerGroupDelete.init();
					final ActionListenerPlayerTournamentEditControl playerTournamentEdit = new ActionListenerPlayerTournamentEditControl(
							mainControl, turnierEdit);
					playerTournamentEdit.init();
					turnierEditierenView.showDialog();
				} catch (final SQLException e) {
					turnierEditierenView.dispose();
					final ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
					// mainControl.setEnabled(true);
				}
			}

			// Wichtig:
			// Diese Abfrage muss an letzter Stelle stehen,
			// da ansonsten eine ArraOutOfBounds Exception auftritt!
			if (arg0.getSource()
					.equals(turnierListeLadenView.getTournamentListItems().get(i).getTurnierLoeschenButton())) {
				if (mainControl.getTournament() != null) {
					if (mainControl.getTournament().getTurnierId() == turnierListe.get(i).getTurnierId()) {
						JOptionPane.showMessageDialog(mainControl, Messages.getString("TurnierListeLadenControl.4")); //$NON-NLS-1$

					} else {
						try {
							deleteTurnier(i);
						} catch (final SQLException e) {
							final ExceptionHandler eh = new ExceptionHandler(mainControl);
							eh.fileSQLError(e.getMessage());
						}
					}
				} else {
					try {
						deleteTurnier(i);
					} catch (final SQLException e) {
						final ExceptionHandler eh = new ExceptionHandler(mainControl);
						eh.fileSQLError(e.getMessage());
					}
				}
			}

		}

	}

	private void deleteTurnier(final int turnierId) throws SQLException {
		final SQLTournamentControl ttC = new SQLTournamentControl(mainControl);
		ttC.loescheTurnier(turnierListe.get(turnierId));
		loadTurnierListe();
	}

	public int getLoadedTurnierID() {
		return loadedTurnierID;
	}

	public void loadPairingsView() {
		final Boolean ready = mainControl.getPairingsControl().checkNewTurnier();
		if (ready) {
			final int gruppenAnzahl = mainControl.getTournament().getAnzahlGruppen();

			final ProgressBarView progressBar = new ProgressBarView(Messages.getString("NaviController.32"),
					Messages.getString("NaviController.31"), gruppenAnzahl);
			mainControl.getNaviView().getTabellenPanel().setVisible(false);
			progressBar.iterate(gruppenAnzahl);

			final PairingsControl pairingsControl = mainControl.getPairingsControl();
			pairingsControl.init();
			final TabbedPaneView[] tabAnzeigeView2 = mainControl.getTabbedPaneViewArray();

			for (int i = 0; i < gruppenAnzahl; i++) {
				progressBar.iterate();

				tabAnzeigeView2[i].getTabbedPane().setEnabledAt(0, false);
				tabAnzeigeView2[i].getTabbedPane().setEnabledAt(1, false);
				pairingsControl.makeRundenEditView(i);
				tabAnzeigeView2[i].getTabbedPane().setSelectedIndex(2);
				progressBar.iterate();

			}
			progressBar.iterate(gruppenAnzahl);

			mainControl.getNaviView().getPairingsPanel().setVisible(true);
			mainControl.getActionListenerPairingsMenuControl().setPairingIsActive(true);
			progressBar.iterate(gruppenAnzahl);

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("HTMLSaveControler.21") + Messages.getString("HTMLSaveControler.22")); //$NON-NLS-1$ //$NON-NLS-2$

		}

	}

	public void loadTurnier(final int index) throws SQLException {
		int selectedTab = hauptPanel.getSelectedIndex();

		mainControl.setNewTournamentPlayerIncludeControl(null);
		if (mainControl.getTournament() != null) {
			if (hauptPanel.getTabCount() - 1 == TournamentConstants.TAB_ACTIVE_TOURNAMENT) {
				hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);

			}

		}

		turnier = turnierListe.get(index);
		mainControl.setTournament(turnier);
		mainControl.setSqlGroupsControl(new SQLGroupsControl(mainControl));
		mainControl.getSqlGroupsControl().getGruppe();
		mainControl.setSqlPlayerControl(new SQLPlayerControl(mainControl));
		mainControl.getSqlPlayerControl().getSpieler();
		tabbedPaneView = new TabbedPaneView(mainControl, Messages.getString("TurnierListeLadenControl.15"));
		tabbedPaneView.getTitleView().setFlowLayoutLeft();
		mainControl.setTabbedPaneView(tabbedPaneView);
		mainControl.setSqlGamesControl(new SQLGamesControl(mainControl));
		for (int z = 0; z < mainControl.getTournament().getAnzahlGruppen(); z++) {
			mainControl.getSqlGamesControl().getPartien(z);
		}
		tabbedPaneView2 = new TabbedPaneView[turnier.getAnzahlGruppen()];

		mainControl.setTabbedPaneViewArray(tabbedPaneView2);
		final CrossTableControl turnierTabelleControl = new CrossTableControl(mainControl);
		final MeetingTableControl terminTabelleControl = new MeetingTableControl(mainControl);

		mainControl.setCrossTableControl(turnierTabelleControl);
		mainControl.setMeetingTableControl(terminTabelleControl);

		for (int z = 0; z < turnier.getAnzahlGruppen(); z++) {
			tabbedPaneView2[z] = new TabbedPaneView(mainControl, "Gruppen");
			tabbedPaneView.getTabbedPane().insertTab(turnier.getGruppe()[z].getGruppenName(), gruppenIcon,
					tabbedPaneView2[z], null, z);
			mainControl.getCrossTableControl().makeSimpleTableView(z);

			mainControl.getMeetingTableControl().makeSimpleTableView(z);
			mainControl.getCrossTableControl().okAction(z);

		}
		final PairingsControl rundenEingabeFormularControl = new PairingsControl(mainControl);
		mainControl.setPairingsControl(rundenEingabeFormularControl);
		mainControl.getTournament().setNoDWZCalc(mainControl.getPropertiesControl().getNoDWZ());
		mainControl.getTournament().setNoFolgeDWZCalc(mainControl.getPropertiesControl().getNoFolgeDWZ());
		mainControl.getNaviView().setTabellenname(
				Messages.getString("TurnierListeLadenControl.5") + mainControl.getTournament().getTurnierName()); //$NON-NLS-1$
		mainControl.getActionListenerPairingsMenuControl().setPairingIsActive(false);
		mainControl.setNewTournament(false);

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
		mainControl.getNaviView().getTabelleSpeichernButton().setEnabled(false);
	}

	public void loadTurnierListe() throws SQLException {
		Tournament temp = null;
		String turnierName = ""; //$NON-NLS-1$
		String startDatum = ""; //$NON-NLS-1$
		String endDatum = ""; //$NON-NLS-1$
		if (turnierTableControl != null) {
			turnierListe = turnierTableControl.loadTurnierListe();
		} else {
			mainControl.setSqlTournamentControl(new SQLTournamentControl(mainControl));
			turnierTableControl = mainControl.getSqlTournamentControl();
			turnierListe = turnierTableControl.loadTurnierListe();

		}
		if (turnierListe != null) {
			anzahlTurniere = turnierListe.size();
			if (turnierListeLadenView == null) {
				turnierListeLadenView = new TournamentListView(anzahlTurniere,
						mainControl.getPropertiesControl().getTurniereProTab());
				hauptPanel.addTab(Messages.getString("TurnierListeLadenControl.9"), turnierListeIcon, //$NON-NLS-1$
						turnierListeLadenView);
				final ButtonTabComponent buttonComp = new ButtonTabComponent(hauptPanel, mainControl, turnierListeIcon,
						false);
				hauptPanel.setTabComponentAt(TournamentConstants.TAB_TOURNAMENTS_LIST, buttonComp);

			} else {
				turnierListeLadenView.removeAll();
				turnierListeLadenView.makePanel(anzahlTurniere);
			}
			turnierListeLadenView.getTitleView().setFlowLayoutLeft();
			// Collections.sort(turnierListe, new SortTournamentList());
			final ListIterator<Tournament> li = turnierListe.listIterator();
			while (li.hasNext()) {
				temp = li.next();
				turnierName = temp.getTurnierName();
				startDatum = temp.getStartDatum();
				endDatum = temp.getEndDatum();
				turnierListeLadenView.makeTurnierZeile(turnierName, startDatum, endDatum);
			}
			for (int i = 0; i < anzahlTurniere; i++) {
				turnierListeLadenView.getTournamentListItems().get(i).getTurnierLadeButton().addActionListener(this);
				turnierListeLadenView.getTournamentListItems().get(i).getTurnierBearbeitenButton()
						.addActionListener(this);
				turnierListeLadenView.getTournamentListItems().get(i).getTurnierLoeschenButton()
						.addActionListener(this);

			}
			turnierListeLadenView.updateUI();
		} else {
			JOptionPane.showMessageDialog(mainControl, "Falsche Datei gewählt. "); //$NON-NLS-1$
		}
	}

	public void reloadTurnier() throws SQLException {
		turnier = mainControl.getTournament();
		if (turnier != null && loadedTurnierID >= 0) {
			for (int i = 0; i < anzahlTurniere; i++) {
				if (turnierListe.get(i).getTurnierId() == loadedTurnierID) {

					loadTurnier(i);
				}
			}
		}
	}

	public void setLoadedTurnierID(final int loadedTurnierID) {
		this.loadedTurnierID = loadedTurnierID;
	}

	public int getAnzahlTurniere() {
		return anzahlTurniere;
	}

	public void setAnzahlTurniere(final int anzahlTurniere) {
		this.anzahlTurniere = anzahlTurniere;
	}

	public EditTournamentView getTurnierEditierenView() {
		return turnierEditierenView;
	}

	public void setTurnierEditierenView(final EditTournamentView turnierEditierenView) {
		this.turnierEditierenView = turnierEditierenView;
	}

	public Tournament getTurnierEdit() {
		return turnierEdit;
	}

	public void setTurnierEdit(final Tournament turnierEdit) {
		this.turnierEdit = turnierEdit;
	}

}
