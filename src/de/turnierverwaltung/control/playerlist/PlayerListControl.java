package de.turnierverwaltung.control.playerlist;

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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.PropertiesControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.table.PlayerListTable;
import de.turnierverwaltung.model.table.PlayerListTableModel;
import de.turnierverwaltung.view.playerlist.EditPlayerView;
import de.turnierverwaltung.view.playerlist.PlayerListView;
import de.turnierverwaltung.view.tournamenttable.ButtonTabComponent;

public class PlayerListControl {
	private final MainControl mainControl;
	// private TabAnzeigeView tabbedPaneView;
	private final JTabbedPane hauptPanel;
	// private int spielerAnzahl;
	private PlayerListView playerList2View;
	private ArrayList<Player> spieler;
	private SQLPlayerControl spielerTableControl;
	private EditPlayerView spielerEditierenView;
	private int spielerIndex;

	private final ImageIcon spielerListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-group.png"))); //$NON-NLS-1$
	private Action deleteAction;
	private Action editAction;

	public PlayerListControl(final MainControl mainControl) {
		this.mainControl = mainControl;
		hauptPanel = this.mainControl.getHauptPanel();
		this.mainControl.getNaviView().getDateiPanel().setVisible(true);
		this.mainControl.getNaviView().getSpielerListePanel().setVisible(true);

	}

	public ArrayList<Player> getSpieler() {
		return spieler;
	}

	public PlayerListView getSpielerLadenView() {
		return playerList2View;
	}

	public void setSpieler(final ArrayList<Player> spieler) {
		this.spieler = spieler;
	}

	public void setSpielerLadenView(final PlayerListView spielerLadenView) {
		playerList2View = spielerLadenView;
	}

	public EditPlayerView getSpielerEditierenView() {
		return spielerEditierenView;
	}

	public void setSpielerEditierenView(final EditPlayerView spielerEditierenView) {
		this.spielerEditierenView = spielerEditierenView;
	}

	public void updateSpielerListe() throws SQLException {
		try {
			final PropertiesControl prop = mainControl.getPropertiesControl();
			final int cutForename = Integer.parseInt(prop.getCutForename());
			final int cutSurname = Integer.parseInt(prop.getCutSurname());
			Player.cutFname = cutForename;
			Player.cutSname = cutSurname;
			spielerTableControl = new SQLPlayerControl(mainControl);
			spieler = new ArrayList<Player>();
			spieler = spielerTableControl.getAllSpieler();
			testPlayerListForDoubles();
			// spielerAnzahl = spieler.size();
			// final int selectedTab = 0;
			final PlayerListTable playerListTable = new PlayerListTable(spieler);
			final PlayerListTableModel playerListTableModel = new PlayerListTableModel(
					playerListTable.getPlayerMatrix(), playerListTable.getColumnNames());
			actionListener();
			playerList2View = new PlayerListView(playerListTableModel, editAction, deleteAction);
			if (hauptPanel.getTabCount() == 0) {
				hauptPanel.addTab(Messages.getString("SpielerLadenControl.1"), spielerListeIcon, playerList2View);

			} else {
				final int selectedTab = hauptPanel.getSelectedIndex();
				hauptPanel.removeTabAt(TournamentConstants.TAB_PLAYER_LIST);
				hauptPanel.insertTab(Messages.getString("SpielerLadenControl.1"), spielerListeIcon, playerList2View,
						"Playerlist", TournamentConstants.TAB_PLAYER_LIST);
				hauptPanel.setSelectedIndex(selectedTab);
			}
			final ButtonTabComponent buttonComp = new ButtonTabComponent(hauptPanel, mainControl, spielerListeIcon,
					false);
			hauptPanel.setTabComponentAt(TournamentConstants.TAB_PLAYER_LIST, buttonComp);

			playerList2View.updateUI();

		} catch (final NullPointerException e) {

		}
	}

	public void testPlayerListForDoubles() {
		for (Player player : spieler) {
			if (player.getName().equals(TournamentConstants.SPIELFREI)) {
				TournamentConstants.setSpielfrei(TournamentConstants.SPIELFREI + "*");
				mainControl.getPropertiesControl().setSpielfrei(TournamentConstants.SPIELFREI);
				mainControl.getPropertiesControl().writeProperties();
			}
		}

	}

	private void actionListener() {
		deleteAction = new AbstractAction() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final JTable table = (JTable) e.getSource();
				final int modelRow = Integer.valueOf(e.getActionCommand());

				if (mainControl.getNewTournament() == false) {
					try {
						final SQLPlayerControl stC = new SQLPlayerControl(mainControl);

						stC.loescheSpieler(spieler.get(modelRow));

						updateSpielerListe();
					} catch (final SQLException e2) {
						final ExceptionHandler eh = new ExceptionHandler(mainControl);
						eh.fileSQLError(e2.getMessage());
					}
				} else {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerLadenControl.2"));
				}
			}

		};
		editAction = new AbstractAction() {
			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final JTable table = (JTable) e.getSource();
				final int modelRow = Integer.valueOf(e.getActionCommand());

				// final String name = (String) table.getModel().getValueAt(modelRow, 0);
				// mainControl.setEnabled(false);
				if (mainControl.getNewTournament() == false) {
					spielerIndex = modelRow;
//					if (spieler.get(modelRow).getDwzData().getCsvSpielername().length() > 0) {
//						spieler.get(modelRow).setName(spieler.get(modelRow).getDwzData().getCsvSpielername());
//						spieler.get(modelRow).extractNameToForenameAndSurename();
//					}
					spielerEditierenView = new EditPlayerView(spieler.get(modelRow));
					spielerEditierenView.getOkButton().addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(final ActionEvent e) {
							final String foreName = spielerEditierenView.getTextFieldForename().getText();
							final String surName = spielerEditierenView.getTextFieldSurname().getText();
							final String name = surName + "," + foreName;
							try {
								final String kuerzel = spielerEditierenView.getTextFieldKuerzel().getText();
								final String dwz = spielerEditierenView.getTextFieldDwz().getText();
								final String dindex = spielerEditierenView.getTextFieldDwzIndex().getText();
								final String zps = spielerEditierenView.getTextFieldZPS().getText();
								final String mgl = spielerEditierenView.getTextFieldMGL().getText();
								final String fideid = spielerEditierenView.getTextFieldFideId().getText();
								final String elo = spielerEditierenView.getTextFieldELO().getText();
								int dwzindex = -1;
								try {
									dwzindex = Integer.parseInt(dindex);
								} catch (final NumberFormatException e1) {
									dwzindex = -1;
								}
								int dwzInt = 0;
								try {
									dwzInt = Integer.parseInt(dwz);
								} catch (final NumberFormatException e1) {
									dwzInt = 0;
								}
								int fideId = 0;
								try {
									fideId = Integer.parseInt(fideid);
								} catch (final NumberFormatException e1) {
									fideId = 0;
								}
								int rating = 0;
								try {
									rating = Integer.parseInt(elo);
								} catch (final NumberFormatException e1) {
									rating = 0;
								}
								final int age = spielerEditierenView.getTextComboBoxAge().getSelectedIndex();

								spieler.get(spielerIndex).setForename(foreName);
								spieler.get(spielerIndex).setSurname(surName);
								spieler.get(spielerIndex).setKuerzel(kuerzel);
								spieler.get(spielerIndex).setDwz(dwzInt);
								spieler.get(spielerIndex).getDwzData().setCsvDWZ(dwzInt);
								spieler.get(spielerIndex).getDwzData().setCsvZPS(zps);
								spieler.get(spielerIndex).getDwzData().setCsvMgl_Nr(mgl);
								spieler.get(spielerIndex).getDwzData().setCsvIndex(dwzindex);
								if (fideId > 0) {
									spieler.get(spielerIndex).getDwzData().setCsvFIDE_ID(fideId);
									spieler.get(spielerIndex).getEloData().setFideid(fideId);
								}
								if (rating > 0) {
									spieler.get(spielerIndex).getDwzData().setCsvFIDE_Elo(rating);
									spieler.get(spielerIndex).getEloData().setRating(rating);
								}
								spieler.get(spielerIndex).setAge(age);
								// spieler.get(spielerIndex).extractForenameAndSurenameToName();
								spieler.get(spielerIndex).setName(name);
								final SQLPlayerControl stc = new SQLPlayerControl(mainControl);

								stc.updateOneSpieler(spieler.get(spielerIndex));

								if (mainControl.getTournament() != null) {
									mainControl.getActionListenerTournamentItemsControl().reloadTurnier();
								}
								spielerEditierenView.closeWindow();
								// mainControl.setEnabled(true);
								updateSpielerListe();
							} catch (final SQLException e1) {
								spielerEditierenView.closeWindow();
								final ExceptionHandler eh = new ExceptionHandler(mainControl);
								eh.fileSQLError(e1.getMessage());

							}

						}

					});
					spielerEditierenView.getCancelButton().addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(final ActionEvent e) {
							spielerEditierenView.closeWindow();

						}

					});
					spielerEditierenView.showDialog();
				} else {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerLadenControl.2"));
				}
			}

		};
	}
}
