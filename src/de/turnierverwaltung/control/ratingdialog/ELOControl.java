package de.turnierverwaltung.control.ratingdialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;

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

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.Messages;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.rating.CSVVereine;
import de.turnierverwaltung.model.rating.ELOData;
import de.turnierverwaltung.model.rating.ELOPlayer;
import de.turnierverwaltung.model.rating.ELOPlayerList;
import de.turnierverwaltung.model.rating.SQLitePlayerELOList;
import de.turnierverwaltung.view.ratingdialog.ELODialogView;
import de.turnierverwaltung.view.ratingdialog.ELOPlayerView;

public class ELOControl {
	private final MainControl mainControl;
	private ELODialogView dialog;
	private ELOPlayerView spielerDewisView;
	private ArrayList<ELOPlayer> players;
	private ArrayList<CSVVereine> zpsItems;
	private ELOActionListenerControl eloDialogActionListenerControl;
	private Boolean eloFile;
	private JTextField spielerSearchTextField;
	private ELOPlayerView spielerSearchPanelList;
	private ArrayList<Player> searchplayerlist;
	private ArrayList<ELOPlayer> playerlist;
	private ELOPlayerList csvplayerlist;
	private Boolean runOnce;
	private SQLitePlayerELOList sqlitePlayerlist;
	private String playerELOList;
	private Boolean dbChecked;

	/**
	 *
	 * @param mainControl
	 * @throws IOException
	 */
	public ELOControl(final MainControl mainControl) throws IOException {
		super();
		runOnce = false;
		this.mainControl = mainControl;
		eloFile = mainControl.getPropertiesControl().checkPathToELOXML();
		dbChecked = false;
		if (eloFile == true) {
			playerELOList = mainControl.getPropertiesControl().getPathToPlayersELO();
			final int positionEXT = playerELOList.lastIndexOf('.');
			String extender = ""; //$NON-NLS-1$
			if (positionEXT > 0) {
				extender = playerELOList.substring(positionEXT);
				if (extender.equals(".sqlite")) {

					sqlitePlayerlist = new SQLitePlayerELOList();
					dbChecked = sqlitePlayerlist.checkDatabase(playerELOList);

				} else {
					csvplayerlist = new ELOPlayerList();

					csvplayerlist.readEloList(mainControl.getPropertiesControl().getPathToPlayersELO());
					playerlist = csvplayerlist.getPlayerList();
				}
			}

		} else {
			errorHandler();
		}

	}

	private void errorHandler() {
		if (runOnce == false) {
			eloFile = false;
			mainControl.getPropertiesControl().setPathToPlayersELO("");

			JOptionPane.showMessageDialog(null, Messages.getString("DewisDialogControl.9"),
					Messages.getString("DewisDialogControl.8"), JOptionPane.INFORMATION_MESSAGE);
		}
		runOnce = true;
	}

	public ELODialogView getDialog() {
		return dialog;
	}

	public ArrayList<ELOPlayer> getPlayers() {
		return players;
	}

	public ArrayList<Player> getSearchplayerlist() {
		return searchplayerlist;
	}

	public ELOPlayerView getSpielerDewisView() {
		return spielerDewisView;
	}

	public ELOPlayerView getSpielerSearchPanelList() {
		return spielerSearchPanelList;
	}

	public ArrayList<CSVVereine> getZpsItems() {
		return zpsItems;
	}

	/**
	*
	*/
	public void makeDialog() {
		if (playerlist != null || sqlitePlayerlist != null) {
			if (dialog == null) {

				dialog = new ELODialogView();

			} else {
				dialog.dispose();

				dialog = new ELODialogView();

			}

			dialog.getPlayerSearchView().getOkButton().setEnabled(false);
//			dialog.setLocationRelativeTo(null);
//			dialog.pack();
			dialog.setBounds(mainControl.getPropertiesControl().getELODialogX(),
					mainControl.getPropertiesControl().getELODialogY(),
					mainControl.getPropertiesControl().getELODialogWidth(),
					mainControl.getPropertiesControl().getELODialogHeight());

//			dialog.setEnabled(true);
//			dialog.setVisible(true);
			
		} else {
			errorHandler();
		}

	}

	public void makePlayerSearchList() {
		if (playerlist != null || sqlitePlayerlist != null) {
			eloDialogActionListenerControl = new ELOActionListenerControl(mainControl, this);
			dialog.getPlayerSearchView().getOkButton().addActionListener(eloDialogActionListenerControl);
			dialog.getPlayerSearchView().getCancelButton().addActionListener(eloDialogActionListenerControl);

			spielerSearchTextField = dialog.getPlayerSearchView().getSearchField();

			spielerSearchTextField.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(final KeyEvent e) {

				}

				@Override
				public void keyReleased(final KeyEvent e) {
					spielerSearchPanelList = new ELOPlayerView(Messages.getString("DewisDialogControl.12"));
					dialog.getPlayerSearchView().setDsbPanel(spielerSearchPanelList);
					searchplayerlist = new ArrayList<Player>();
					final String eingabe = spielerSearchTextField.getText().toUpperCase();
					if (playerlist != null) {
						final ListIterator<ELOPlayer> li = playerlist.listIterator();

						int counter = 0;
						while (li.hasNext() && counter < 20) {
							final Player tmp = li.next().getPlayer();
							tmp.extractNameToForenameAndSurename();
							String surname = "";
							String forename = "";
							String name = "";
							if (tmp.getSurname().length() >= eingabe.length()) {
								surname = tmp.getSurname().substring(0, eingabe.length()).toUpperCase();
							}
							if (tmp.getForename().length() >= eingabe.length()) {
								forename = tmp.getForename().substring(0, eingabe.length()).toUpperCase();
							}
							if (tmp.getName().length() >= eingabe.length()) {
								name = tmp.getName().substring(0, eingabe.length()).toUpperCase();
							}
							if (eingabe.equals(surname) || eingabe.equals(forename) || eingabe.equals(name)) {
								if (playerExist(tmp)) {
									spielerSearchPanelList.makeSpielerZeile(tmp, 2);
								} else {
									spielerSearchPanelList.makeSpielerZeile(tmp, 0);
								}

								searchplayerlist.add(tmp);

								counter++;
							}

						}
					}
					// if (sqlitePlayerlist != null) {
					if (dbChecked == true) {

						final ArrayList<ELOData> eloPlayer = sqlitePlayerlist.getPlayersByName(playerELOList, eingabe);
						final ListIterator<ELOData> li = eloPlayer.listIterator();
						while (li.hasNext()) {
							final ELOData tmp = li.next();

							if (playerExist(tmp)) {
								spielerSearchPanelList.makeSpielerZeile(tmp, 2);
							} else {
								spielerSearchPanelList.makeSpielerZeile(tmp, 0);
							}
							final Player player = new Player();
							player.setEloData(tmp);
							searchplayerlist.add(player);

						}
					}
					spielerSearchPanelList.makeList();
					spielerSearchPanelList.updateUI();
					spielerSearchPanelList.getList().addListSelectionListener(eloDialogActionListenerControl);

				}

				@Override
				public void keyTyped(final KeyEvent e) {

				}
			});
			dialog.showDialog();
		} else {
			errorHandler();
		}
	}

	private boolean playerExist(final ELOData tmp) {
		final SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);

		Boolean playerExist = false;
		try {
			playerExist = spielerTableControl.playerFideExist(tmp.getFideid());
		} catch (final SQLException e) {
			playerExist = false;
		}
		return playerExist;
	}

	private boolean playerExist(final Player neuerSpieler) {
		final SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);
		Boolean playerExist = false;

		neuerSpieler.getDwzData().setCsvFIDE_ID(neuerSpieler.getEloData().getFideid());
		try {
			playerExist = spielerTableControl.playerFideExist(neuerSpieler.getEloData().getFideid());
		} catch (final SQLException e) {
			playerExist = false;
		}

		return playerExist;
	}

	public void setDialog(final ELODialogView dialog) {
		this.dialog = dialog;
	}

	public void setPlayers(final ArrayList<ELOPlayer> players) {
		this.players = players;
	}

	public void setSearchplayerlist(final ArrayList<Player> searchplayerlist) {
		this.searchplayerlist = searchplayerlist;
	}

	public void setSpielerDewisView(final ELOPlayerView spielerDewisView) {
		this.spielerDewisView = spielerDewisView;
	}

	public void setSpielerSearchPanelList(final ELOPlayerView spielerSearchPanelList) {
		this.spielerSearchPanelList = spielerSearchPanelList;
	}

	public void setZpsItems(final ArrayList<CSVVereine> zpsItems) {
		this.zpsItems = zpsItems;
	}

}
