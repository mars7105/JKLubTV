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

public class DSBDWZControl {
	private MainControl mainControl;
	private DSBDWZDialogView dialog;
	private DSBDWZPlayerView spielerDewisView;
	private ArrayList<Player> players;
	private ArrayList<CSVVereine> zpsItems;
	private DSBDWZActionListenerControl dewisDialogActionListenerControl;
	private ArrayList<Player> spielerListe;
	private Boolean csvFiles;
	private JTextField spielerSearchTextField;
	private DSBDWZPlayerView spielerSearchPanelList;
	private ArrayList<CSVPlayer> playerlist;
	private ArrayList<Player> searchplayerlist;
	private ArrayList<DWZData> dwzDataArray;
	private SQLitePlayerDWZList sqlitePlayerlist;

	/**
	 * 
	 * @param mainControl
	 */
	public DSBDWZControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		csvFiles = mainControl.getPropertiesControl().checkPathToVereineCSV()
				&& mainControl.getPropertiesControl().checkPathToSpielerCSV();
		dewisDialogActionListenerControl = new DSBDWZActionListenerControl(this.mainControl, this);

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
			ListIterator<DWZData> list = dwzDataArray.listIterator();
			while (list.hasNext()) {
				DWZData dwzData = list.next();
				Player player = new Player();
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
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			dialog.dispose();
			try {
				dialog = new DSBDWZDialogView(csvFiles);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String zps = mainControl.getPropertiesControl().getZPS();
		if (csvFiles == true) {
			dialog.getVereinsAuswahlOkButton().addActionListener(dewisDialogActionListenerControl);
			makeVereinsListe(zps);
		} else {
			dialog.getVereinsSucheButton().addActionListener(dewisDialogActionListenerControl);
		}

		dialog.getOkButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getCancelButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getOkButton().setEnabled(false);

		if (zps.length() > 0) {
			dialog.getVereinsSuche().setText(zps);

			makeDWZListe(zps);
		}

	}

	/**
	 * 
	 * @param zps
	 *            = ZPS number of the association
	 * @throws IOException
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public void makeDWZListe(String zps) {

		try {
			spielerDewisView = new DSBDWZPlayerView();
			DSBDWZClub verein = null;
			players = null;
			dwzDataArray = null;
			if (csvFiles == true) {
				String filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
				int positionEXT = filename.lastIndexOf('.');
				String extender = ""; //$NON-NLS-1$
				if (positionEXT > 0) {
					extender = filename.substring(positionEXT);
					if (extender.equals(".sqlite")) {
						sqlitePlayerlist = new SQLitePlayerDWZList();
						dwzDataArray = sqlitePlayerlist.getPlayerOfVerein(filename, zps);
					} else {
						CSVPlayerList csvplayerlist = new CSVPlayerList();
						csvplayerlist.loadPlayerCSVList(filename);
						players = csvplayerlist.getPlayerOfVerein(zps);
					}
				}

			} else {
				verein = new DSBDWZClub(zps);
				players = verein.getSpieler();
			}
			SQLPlayerControl sqlpc = new SQLPlayerControl(mainControl);

			try {
				spielerListe = sqlpc.getAllSpielerOrderByZPS();
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			}
			if (players != null) {
				Collections.sort(players, new SortSurname());

				ListIterator<Player> list = players.listIterator();
				while (list.hasNext()) {

					Player player = list.next();
					ListIterator<Player> li = spielerListe.listIterator();
					Boolean foundPlayer = false;
					while (li.hasNext()) {
						Player tmp = li.next();

						String tmpzps = tmp.getDwzData().getCsvZPS();
						String tmpmgl = tmp.getDwzData().getCsvMgl_Nr();
						String playerzps = player.getDwzData().getCsvZPS();
						String playermgl = player.getDwzData().getCsvMgl_Nr();
						if (tmpzps.equals(playerzps) && tmpmgl.equals(playermgl)) {
							spielerDewisView.makeSpielerZeile(player, 2);
							foundPlayer = true;

						}

					}
					if (foundPlayer == false) {
						spielerDewisView.makeSpielerZeile(player, 0);
					}

				}
				spielerDewisView.makeList();
				spielerDewisView.updateUI();
				spielerDewisView.getList().addListSelectionListener(dewisDialogActionListenerControl);
				dialog.setDsbPanel(spielerDewisView);
				mainControl.getPropertiesControl().setZPS(zps);
				mainControl.getPropertiesControl().writeProperties();
				// dialog.getUpdateButton().setEnabled(true);
			}
			if (dwzDataArray != null) {
				// Collections.sort(players, new SortSurname());

				ListIterator<DWZData> list = dwzDataArray.listIterator();
				while (list.hasNext()) {

					DWZData dwzData = list.next();
					// System.out.println( "-" + dwzData.getCsvZPS());
					ListIterator<Player> li = spielerListe.listIterator();
					Boolean foundPlayer = false;
					while (li.hasNext()) {
						Player tmp = li.next();

						String tmpzps = tmp.getDwzData().getCsvZPS();
						String tmpmgl = tmp.getDwzData().getCsvMgl_Nr();
						String playerzps = dwzData.getCsvZPS();
						String playermgl = dwzData.getCsvMgl_Nr();
						if (tmpzps.equals(playerzps) && tmpmgl.equals(playermgl)) {
							spielerDewisView.makeSpielerZeile(dwzData, 2);
							foundPlayer = true;

						}

					}
					if (foundPlayer == false) {
						spielerDewisView.makeSpielerZeile(dwzData, 0);
					}

				}
				spielerDewisView.makeList();
				spielerDewisView.updateUI();
				spielerDewisView.getList().addListSelectionListener(dewisDialogActionListenerControl);
				dialog.setDsbPanel(spielerDewisView);
				mainControl.getPropertiesControl().setZPS(zps);
				mainControl.getPropertiesControl().writeProperties();
				// dialog.getUpdateButton().setEnabled(true);
			}
			if (players == null && dwzDataArray == null) {
				// dialog.getUpdateButton().setEnabled(false);
				JLabel noItemLabel = new JLabel(Messages.getString("DewisDialogControl.0")); //$NON-NLS-1$
				JPanel noItemPanel = new JPanel();
				noItemPanel.add(noItemLabel);
				dialog.setDsbPanel(noItemPanel);
			}

			dialog.refresh();
		} catch (ArrayIndexOutOfBoundsException e) {
			errorHandler();
		} catch (IOException e) {
			errorHandler();
		}
	}

	public void makePlayerSearchList() {
		try {
			String filename = mainControl.getPropertiesControl().getPathToPlayersCSV();
			int positionEXT = filename.lastIndexOf('.');
			String extender = ""; //$NON-NLS-1$
			if (positionEXT > 0) {
				extender = filename.substring(positionEXT);

			}

			if (csvFiles == true) {
				if (extender.equals(".sqlite") == false) {
					CSVPlayerArrayList csvplayerlist = new CSVPlayerArrayList();

					csvplayerlist.loadPlayerCSVList(mainControl.getPropertiesControl().getPathToPlayersCSV());

					playerlist = csvplayerlist.getAllPlayer();
					ActionListenerPlayerSearchControl psc = new ActionListenerPlayerSearchControl(mainControl, this);
					spielerSearchTextField = dialog.getPlayerSearchView().getSearchField();
					dialog.getPlayerSearchView().getOkButton().addActionListener(psc);
					dialog.getPlayerSearchView().getCancelButton().addActionListener(psc);
					spielerSearchTextField.addKeyListener(new KeyListener() {

						@Override
						public void keyPressed(KeyEvent e) {

						}

						@Override
						public void keyReleased(KeyEvent e) {

							spielerSearchPanelList = new DSBDWZPlayerView();
							dialog.getPlayerSearchView().setDsbPanel(spielerSearchPanelList);
							searchplayerlist = new ArrayList<Player>();
							String eingabe = spielerSearchTextField.getText().toUpperCase();
							ListIterator<CSVPlayer> li = playerlist.listIterator();
							int counter = 0;
							while (li.hasNext() && counter < 20) {
								Player csvPlayer = li.next().getPlayer();
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

									ListIterator<Player> list = spielerListe.listIterator();
									Boolean foundPlayer = false;
									while (list.hasNext()) {
										Player temp = list.next();
										try {
											String tmpzps = temp.getDwzData().getCsvZPS();
											String tmpmgl = temp.getDwzData().getCsvMgl_Nr();
											String playerzps = csvPlayer.getDwzData().getCsvZPS();
											String playermgl = csvPlayer.getDwzData().getCsvMgl_Nr();
											if (tmpzps.equals(playerzps) && tmpmgl.equals(playermgl)) {
												spielerSearchPanelList.makeSpielerZeile(csvPlayer, 2);
												searchplayerlist.add(csvPlayer);
												foundPlayer = true;

											}
										} catch (NumberFormatException e2) {

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
							spielerSearchPanelList.updateUI();
							spielerSearchPanelList.getList().addListSelectionListener(psc);

						}

						@Override
						public void keyTyped(KeyEvent e) {

						}
					});
				} else {
					ActionListenerPlayerSearchControl psc = new ActionListenerPlayerSearchControl(mainControl, this);
					spielerSearchTextField = dialog.getPlayerSearchView().getSearchField();
					dialog.getPlayerSearchView().getOkButton().addActionListener(psc);
					dialog.getPlayerSearchView().getCancelButton().addActionListener(psc);
					spielerSearchTextField.addKeyListener(new KeyListener() {

						@Override
						public void keyPressed(KeyEvent e) {

						}

						@Override
						public void keyReleased(KeyEvent e) {
							SQLPlayerControl sqlpc = new SQLPlayerControl(mainControl);
							spielerSearchPanelList = new DSBDWZPlayerView();
							dialog.getPlayerSearchView().setDsbPanel(spielerSearchPanelList);
							searchplayerlist = new ArrayList<Player>();
							String eingabe = spielerSearchTextField.getText().toUpperCase();
							ArrayList<DWZData> dwzPlayer = sqlitePlayerlist.getPlayersByName(
									mainControl.getPropertiesControl().getPathToPlayersCSV(), eingabe);
							ListIterator<DWZData> li = dwzPlayer.listIterator();
							while (li.hasNext()) {
								DWZData tmp = li.next();

								if (sqlpc.playerExist(tmp)) {
									spielerSearchPanelList.makeSpielerZeile(tmp, 2);
								} else {
									spielerSearchPanelList.makeSpielerZeile(tmp, 0);
								}
								Player player = new Player();
								player.setDwzData(tmp);
								player.copyDWZDataToPlayer();
								searchplayerlist.add(player);

							}
							spielerSearchPanelList.makeList();
							spielerSearchPanelList.updateUI();
							spielerSearchPanelList.getList().addListSelectionListener(psc);

						}

						@Override
						public void keyTyped(KeyEvent e) {

						}
					});
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			errorHandler();
		} catch (IOException e) {
			errorHandler();
		}
	}

	/**
	 * 
	 */
	public void makeVereinsListe(String zps) {

		try {
			CSVVereineList vereine = new CSVVereineList();

			vereine.loadVereine(mainControl.getPropertiesControl().getPathToVereineCSV());

			zpsItems = vereine.getCsvvereine();
			Collections.sort(zpsItems, new SortCSVVereine());

			dialog.getVereinsAuswahl().removeAllItems();
			Iterator<CSVVereine> it = vereine.getCsvvereine().iterator();
			int counter = 0;
			int selectIndex = 0;
			while (it.hasNext()) {
				CSVVereine temp = it.next();
				// System.out.println(temp.getCsvZPS() + " " + zps);
				if (temp.getCsvZPS().equals(zps)) {
					selectIndex = counter;
				}

				dialog.getVereinsAuswahl().addItem(temp.getCsvVereinname());
				counter++;
			}
			dialog.getVereinsAuswahl().setSelectedIndex(selectIndex);

		} catch (IOException e) {
			errorHandler();
		} catch (ArrayIndexOutOfBoundsException e2) {
			errorHandler();

		} catch (NoClassDefFoundError e3) {
			errorHandler();
		}
	}

	public void setDialog(DSBDWZDialogView dialog) {
		this.dialog = dialog;
	}

	public void setPlayerlist(ArrayList<CSVPlayer> playerlist) {
		this.playerlist = playerlist;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public void setSearchplayerlist(ArrayList<Player> searchplayerlist) {
		this.searchplayerlist = searchplayerlist;
	}

	public void setSpielerDewisView(DSBDWZPlayerView spielerDewisView) {
		this.spielerDewisView = spielerDewisView;
	}

	public void setSpielerSearchPanelList(DSBDWZPlayerView spielerSearchPanelList) {
		this.spielerSearchPanelList = spielerSearchPanelList;
	}

	public void setZpsItems(ArrayList<CSVVereine> zpsItems) {
		this.zpsItems = zpsItems;
	}

}
