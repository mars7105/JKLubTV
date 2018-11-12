package de.turnierverwaltung.control.ratingdialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

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

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.SortSurname;
import de.turnierverwaltung.model.rating.CSVPlayer;
import de.turnierverwaltung.model.rating.CSVPlayerArrayList;
import de.turnierverwaltung.model.rating.CSVPlayerList;
import de.turnierverwaltung.model.rating.CSVVereine;
import de.turnierverwaltung.model.rating.CSVVereineList;
import de.turnierverwaltung.model.rating.DSBDWZClub;
import de.turnierverwaltung.model.rating.DWZData;
import de.turnierverwaltung.model.rating.SQLitePlayerDWZList;
import de.turnierverwaltung.model.rating.SortCSVVereine;
import de.turnierverwaltung.view.ratingdialog.DSBDWZDialogView;
import de.turnierverwaltung.view.ratingdialog.DSBDWZPlayerView;
import de.turnierverwaltung.view.tournamentlist.PlayerTournamentEditView;

public class DSBDWZControl {
	private final MainControl mainControl;
	private DSBDWZDialogView dialog;
	private DSBDWZPlayerView spielerDewisView;
	private ArrayList<Player> players;
	private ArrayList<CSVVereine> zpsItems;
	private final DSBDWZActionListenerControl dewisDialogActionListenerControl;
	private ArrayList<Player> spielerListe;
	private Boolean csvFiles;
	private JTextField spielerSearchTextField;
	private DSBDWZPlayerView spielerSearchPanelList;
	private ArrayList<CSVPlayer> playerlist;
	private ArrayList<Player> searchplayerlist;
	private ArrayList<DWZData> dwzDataArray;
	private SQLitePlayerDWZList sqlitePlayerlist;
	private String standardZPS;
	private Boolean dbChecked;
	private PlayerTournamentEditView playerListView;

	/**
	 *
	 * @param mainControl
	 */
	public DSBDWZControl(final MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		sqlitePlayerlist = new SQLitePlayerDWZList();
		final String filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
		dbChecked = sqlitePlayerlist.checkDatabase(filename);

		standardZPS = "";
		csvFiles = mainControl.getPropertiesControl().checkPathToVereineCSV()
				&& mainControl.getPropertiesControl().checkPathToSpielerCSV();
		dewisDialogActionListenerControl = new DSBDWZActionListenerControl(this.mainControl, this);
		spielerSearchPanelList = new DSBDWZPlayerView(Messages.getString("DewisDialogControl.11"));
		makePlayerSearchSelectedList();

	}

	private void errorHandler() {
		csvFiles = false;
		mainControl.getPropertiesControl().setPathToVereineCSV("");
		mainControl.getPropertiesControl().setPathToPlayersCSV("");
		dialog.dispose();
		JOptionPane.showMessageDialog(null, Messages.getString("DewisDialogControl.7"),
				Messages.getString("DewisDialogControl.8"), JOptionPane.INFORMATION_MESSAGE);
	}

	public DSBDWZDialogView getDialog() {
		return dialog;
	}

	public ArrayList<CSVPlayer> getPlayerlist() {
		return playerlist;
	}

	public ArrayList<Player> getPlayers() {
		if (players == null) {
			players = new ArrayList<Player>();
			final ListIterator<DWZData> list = dwzDataArray.listIterator();
			while (list.hasNext()) {
				final DWZData dwzData = list.next();
				final Player player = new Player();
				player.setDwzData(dwzData);
				player.setName(dwzData.getCsvSpielername());

				players.add(player);
			}
		}
		return players;
	}

	public ArrayList<Player> getSearchplayerlist() {
		return searchplayerlist;
	}

	public DSBDWZPlayerView getSpielerDewisView() {
		return spielerDewisView;
	}

	public DSBDWZPlayerView getSpielerSearchPanelList() {
		return spielerSearchPanelList;
	}

	public ArrayList<CSVVereine> getZpsItems() {
		return zpsItems;
	}

	/**
	 * @throws IOException
	 * @throws ArrayIndexOutOfBoundsException
	 *
	 */
	public void makeDialog() throws ArrayIndexOutOfBoundsException, IOException {
		// Boolean csvFiles = vereinsSuche.checkifSpielerFileExist();
		if (dialog == null) {
			try {
				dialog = new DSBDWZDialogView(csvFiles);
			} catch (final URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			dialog.dispose();
			try {
				dialog = new DSBDWZDialogView(csvFiles);
			} catch (final URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		dialog.setLocationRelativeTo(null);
//		dialog.pack();
		dialog.setBounds(mainControl.getPropertiesControl().getDWZDialogX(),
				mainControl.getPropertiesControl().getDWZDialogY(),
				mainControl.getPropertiesControl().getDWZDialogWidth(),
				mainControl.getPropertiesControl().getDWZDialogHeight());

//		dialog.setEnabled(true);
//		dialog.setVisible(true);

		String zps = mainControl.getPropertiesControl().getZPS();

		if (csvFiles == true) {
			// dialog.getVereinsAuswahlOkButton().addActionListener(dewisDialogActionListenerControl);
			makeVereinsListe(zps);
		} else {
			dialog.getVereinsSucheButton().addActionListener(dewisDialogActionListenerControl);
		}

		dialog.getOkButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getCancelButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getOkButton().setEnabled(false);
		if (zps.equals("")) {
			zps = standardZPS;
		}
		// System.out.println(zps);
		if (zps.length() > 0) {
			dialog.getVereinsSuche().setText(zps);

			makeDWZListe(zps);
			dialog.getVereinsAuswahl().addItemListener(dewisDialogActionListenerControl);
		}
		dialog.showDialog();
	}

	/**
	 *
	 * @param zps = ZPS number of the association
	 * @throws IOException
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void makeDWZListe(final String zps) {

		try {
			spielerDewisView = new DSBDWZPlayerView(Messages.getString("DewisDialogControl.10"));
			DSBDWZClub verein = null;
			players = null;
			dwzDataArray = null;
			if (csvFiles == true) {
				final String filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
				final int positionEXT = filename.lastIndexOf('.');
				String extender = ""; //$NON-NLS-1$
				if (positionEXT > 0) {
					extender = filename.substring(positionEXT);
					if (extender.equals(".sqlite")) {
						// sqlitePlayerlist = new SQLitePlayerDWZList();
						if (dbChecked == true) {
							dwzDataArray = sqlitePlayerlist.getPlayerOfVerein(filename, zps);
						}
					} else {
						final CSVPlayerList csvplayerlist = new CSVPlayerList();
						csvplayerlist.loadPlayerCSVList(filename);
						players = csvplayerlist.getPlayerOfVerein(zps);
					}
				}

			} else {
				verein = new DSBDWZClub(zps);
				players = verein.getSpieler();
			}
			final SQLPlayerControl sqlpc = new SQLPlayerControl(mainControl);

			try {
				spielerListe = sqlpc.getAllSpielerOrderByZPS();
			} catch (final SQLException e) {
				final ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			}
			if (players != null) {
				Collections.sort(players, new SortSurname());

				final ListIterator<Player> list = players.listIterator();
				while (list.hasNext()) {

					final Player player = list.next();
					final ListIterator<Player> li = spielerListe.listIterator();
					Boolean foundPlayer = false;
					while (li.hasNext()) {
						final Player tmp = li.next();

						final String tmpzps = tmp.getDwzData().getCsvZPS();
						final String tmpmgl = tmp.getDwzData().getCsvMgl_Nr();
						final String playerzps = player.getDwzData().getCsvZPS();
						final String playermgl = player.getDwzData().getCsvMgl_Nr();
						if (tmpzps.equals(playerzps) && tmpmgl.equals(playermgl)) {
							spielerDewisView.makeSpielerZeile(player, 2);
							// spielerDewisView.makeSelectedSpielerZeile(player, 2);
							foundPlayer = true;

						}

					}
					if (foundPlayer == false) {
						spielerDewisView.makeSpielerZeile(player, 0);

					}

				}
				spielerDewisView.makeList();
				makeSelectedList();
				spielerDewisView.updateUI();
				spielerDewisView.getList().addListSelectionListener(dewisDialogActionListenerControl);
				dialog.setDsbPanel(spielerDewisView);
				mainControl.getPropertiesControl().setZPS(zps);
				mainControl.getPropertiesControl().writeProperties();
				// dialog.getUpdateButton().setEnabled(true);
			}
			if (dwzDataArray != null) {
				// Collections.sort(players, new SortSurname());

				final ListIterator<DWZData> list = dwzDataArray.listIterator();
				while (list.hasNext()) {

					final DWZData dwzData = list.next();
					// System.out.println( "-" + dwzData.getCsvZPS());
					final ListIterator<Player> li = spielerListe.listIterator();
					Boolean foundPlayer = false;
					while (li.hasNext()) {
						final Player tmp = li.next();

						final String tmpzps = tmp.getDwzData().getCsvZPS();
						final String tmpmgl = tmp.getDwzData().getCsvMgl_Nr();
						final String playerzps = dwzData.getCsvZPS();
						final String playermgl = dwzData.getCsvMgl_Nr();
						if (tmpzps.equals(playerzps) && tmpmgl.equals(playermgl)) {
							spielerDewisView.makeSpielerZeile(dwzData, 2);
							// spielerDewisView.makeSelectedSpielerZeile(dwzData, 2);
							foundPlayer = true;

						}

					}
					if (foundPlayer == false) {
						spielerDewisView.makeSpielerZeile(dwzData, 0);

					}

				}

				spielerDewisView.makeList();
				makeSelectedList();
				spielerDewisView.updateUI();
				spielerDewisView.getList().addListSelectionListener(dewisDialogActionListenerControl);
				dialog.setDsbPanel(spielerDewisView);
				mainControl.getPropertiesControl().setZPS(zps);
				mainControl.getPropertiesControl().writeProperties();
				// dialog.getUpdateButton().setEnabled(true);
			}
			if (players == null && dwzDataArray == null) {
				// dialog.getUpdateButton().setEnabled(false);
				final JLabel noItemLabel = new JLabel(Messages.getString("DewisDialogControl.0")); //$NON-NLS-1$
				final JPanel noItemPanel = new JPanel();
				noItemPanel.add(noItemLabel);
				dialog.setDsbPanel(noItemPanel);
			}

			dialog.refresh();
		} catch (final ArrayIndexOutOfBoundsException e) {
			errorHandler();
		} catch (final IOException e) {
			errorHandler();
		}
	}

	public void makePlayerSearchList() {
		try {
			final String filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
			final int positionEXT = filename.lastIndexOf('.');
			String extender = ""; //$NON-NLS-1$
			if (positionEXT > 0) {
				extender = filename.substring(positionEXT);

			}

			if (csvFiles == true) {
				if (extender.equals(".sqlite") == false) {
					final CSVPlayerArrayList csvplayerlist = new CSVPlayerArrayList();

					csvplayerlist.loadPlayerCSVList(mainControl.getPropertiesControl().getPathToPlayersCSV());

					playerlist = csvplayerlist.getAllPlayer();
					final ActionListenerPlayerSearchControl psc = new ActionListenerPlayerSearchControl(mainControl,
							this);
					spielerSearchTextField = dialog.getPlayerSearchView().getSearchField();
					dialog.getPlayerSearchView().getOkButton().addActionListener(psc);
					dialog.getPlayerSearchView().getCancelButton().addActionListener(psc);
					spielerSearchTextField.addKeyListener(new KeyListener() {

						@Override
						public void keyPressed(final KeyEvent e) {

						}

						@Override
						public void keyReleased(final KeyEvent e) {

							searchplayerlist = new ArrayList<Player>();
							final String eingabe = spielerSearchTextField.getText().toUpperCase();
							final ListIterator<CSVPlayer> li = playerlist.listIterator();
							int counter = 0;
							while (li.hasNext() && counter < 20) {
								final Player csvPlayer = li.next().getPlayer();
								String surname = "";
								String forename = "";
								String name = "";
								if (csvPlayer.getSurname().length() >= eingabe.length()) {
									surname = csvPlayer.getSurname().substring(0, eingabe.length()).toUpperCase();
								}
								if (csvPlayer.getForename().length() >= eingabe.length()) {
									forename = csvPlayer.getForename().substring(0, eingabe.length()).toUpperCase();
								}
								if (csvPlayer.getName().length() >= eingabe.length()) {
									name = csvPlayer.getName().substring(0, eingabe.length()).toUpperCase();
								}
								if (eingabe.equals(surname) || eingabe.equals(forename) || eingabe.equals(name)) {

									final ListIterator<Player> list = spielerListe.listIterator();
									Boolean foundPlayer = false;
									while (list.hasNext()) {
										final Player temp = list.next();
										try {
											final String tmpzps = temp.getDwzData().getCsvZPS();
											final String tmpmgl = temp.getDwzData().getCsvMgl_Nr();
											final String playerzps = csvPlayer.getDwzData().getCsvZPS();
											final String playermgl = csvPlayer.getDwzData().getCsvMgl_Nr();
											if (tmpzps.equals(playerzps) && tmpmgl.equals(playermgl)) {
												spielerSearchPanelList.makeSpielerZeile(csvPlayer, 2);
												// spielerSearchPanelList.makeSelectedSpielerZeile(csvPlayer, 2);
												searchplayerlist.add(csvPlayer);
												foundPlayer = true;

											}
										} catch (final NumberFormatException e2) {

										}
									}

									if (foundPlayer == false) {
										spielerSearchPanelList.makeSpielerZeile(csvPlayer, 0);

										searchplayerlist.add(csvPlayer);
									}

									counter++;
								}

							}
							spielerSearchPanelList.makeList();
							makePlayerSearchSelectedList();
							spielerSearchPanelList.updateUI();
							spielerSearchPanelList.getList().addListSelectionListener(psc);
							dialog.refresh();
						}

						@Override
						public void keyTyped(final KeyEvent e) {

						}
					});
				} else {
					final ActionListenerPlayerSearchControl psc = new ActionListenerPlayerSearchControl(mainControl,
							this);
					spielerSearchTextField = dialog.getPlayerSearchView().getSearchField();
					dialog.getPlayerSearchView().getOkButton().addActionListener(psc);
					dialog.getPlayerSearchView().getCancelButton().addActionListener(psc);
					spielerSearchTextField.addKeyListener(new KeyListener() {

						@Override
						public void keyPressed(final KeyEvent e) {

						}

						@Override
						public void keyReleased(final KeyEvent e) {
							final SQLPlayerControl sqlpc = new SQLPlayerControl(mainControl);
							spielerSearchPanelList = new DSBDWZPlayerView(Messages.getString("DewisDialogControl.11"));
							makePlayerSearchSelectedList();
							dialog.getPlayerSearchView().setDsbPanel(spielerSearchPanelList);
							searchplayerlist = new ArrayList<Player>();
							final String eingabe = spielerSearchTextField.getText().toUpperCase();
							if (dbChecked == true) {
								final ArrayList<DWZData> dwzPlayer = sqlitePlayerlist.getPlayersByName(
										mainControl.getPropertiesControl().getPathToPlayersCSV(), eingabe);
								final ListIterator<DWZData> li = dwzPlayer.listIterator();
								while (li.hasNext()) {
									final DWZData tmp = li.next();

									try {
										if (sqlpc.playerExist(tmp)) {
											spielerSearchPanelList.makeSpielerZeile(tmp, 2);

										} else {
											spielerSearchPanelList.makeSpielerZeile(tmp, 0);
										}
									} catch (final SQLException e1) {
										spielerSearchPanelList.makeSpielerZeile(tmp, 0);
									}
									final Player player = new Player();
									player.setDwzData(tmp);
									player.copyDWZDataToPlayer();
									searchplayerlist.add(player);

								}
							}
							spielerSearchPanelList.makeList();
							makePlayerSearchSelectedList();
							spielerSearchPanelList.updateUI();
							spielerSearchPanelList.getList().addListSelectionListener(psc);

						}

						@Override
						public void keyTyped(final KeyEvent e) {

						}
					});
				}
			}
		} catch (final ArrayIndexOutOfBoundsException e) {
			errorHandler();
		} catch (final IOException e) {
			errorHandler();
		}
	}

	/**
	 *
	 */
	public void makeSelectedList() {
		final SQLPlayerControl sqlpc = new SQLPlayerControl(mainControl);

		try {
			spielerListe = sqlpc.getAllSpielerOrderByZPS();
		} catch (final SQLException e) {
			final ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		}
		final ListIterator<Player> li = spielerListe.listIterator();
		spielerDewisView.makeSelectedList();
		while (li.hasNext()) {
			final Player tmp = li.next();

			spielerDewisView.makeSelectedSpielerZeile(tmp, 2);

		}
		// spielerDewisView.makeSelectedList();
	}

	/**
	 *
	 */
	public void makePlayerSearchSelectedList() {
		final SQLPlayerControl sqlpc = new SQLPlayerControl(mainControl);

		try {
			spielerListe = sqlpc.getAllSpielerOrderByZPS();
		} catch (final SQLException e) {
			final ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		}
		final ListIterator<Player> li = spielerListe.listIterator();
		spielerSearchPanelList.makeSelectedList();
		while (li.hasNext()) {
			final Player tmp = li.next();

			spielerSearchPanelList.makeSelectedSpielerZeile(tmp, 2);

		}
		// spielerDewisView.makeSelectedList();
	}

	/**
	 *
	 */
	public void makeVereinsListe(final String zps) {
		standardZPS = "";
		try {
			final CSVVereineList vereine = new CSVVereineList();

			vereine.loadVereine(mainControl.getPropertiesControl().getPathToVereineCSV());

			zpsItems = vereine.getCsvvereine();
			Collections.sort(zpsItems, new SortCSVVereine());

			dialog.getVereinsAuswahl().removeAllItems();
			final Iterator<CSVVereine> it = zpsItems.iterator();
			int counter = 0;
			int selectIndex = 0;
			while (it.hasNext()) {
				final CSVVereine temp = it.next();
				if (counter == 0) {
					standardZPS = temp.getCsvZPS();
				}
				// System.out.println(temp.getCsvZPS() + " " + zps);
				if (temp.getCsvZPS().equals(zps)) {
					selectIndex = counter;
				}

				dialog.getVereinsAuswahl().addItem(temp.getCsvVereinname());
				counter++;
			}

			dialog.getVereinsAuswahl().setSelectedIndex(selectIndex);

		} catch (final IOException e) {
			errorHandler();
		} catch (final ArrayIndexOutOfBoundsException e2) {
			errorHandler();

		} catch (final NoClassDefFoundError e3) {
			errorHandler();
		}
	}

	public void setDialog(final DSBDWZDialogView dialog) {
		this.dialog = dialog;
	}

	public void setPlayerlist(final ArrayList<CSVPlayer> playerlist) {
		this.playerlist = playerlist;
	}

	public void setPlayers(final ArrayList<Player> players) {
		this.players = players;
	}

	public void setSearchplayerlist(final ArrayList<Player> searchplayerlist) {
		this.searchplayerlist = searchplayerlist;
	}

	public void setSpielerDewisView(final DSBDWZPlayerView spielerDewisView) {
		this.spielerDewisView = spielerDewisView;
	}

	public void setSpielerSearchPanelList(final DSBDWZPlayerView spielerSearchPanelList) {
		this.spielerSearchPanelList = spielerSearchPanelList;
	}

	public void setZpsItems(final ArrayList<CSVVereine> zpsItems) {
		this.zpsItems = zpsItems;
	}

	public void setPlayerListView(PlayerTournamentEditView playerListView) {
		this.playerListView = playerListView;
	}

	public PlayerTournamentEditView getPlayerListView() {
		return playerListView;
	}

	
	
}
