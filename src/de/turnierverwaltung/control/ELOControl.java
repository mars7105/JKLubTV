package de.turnierverwaltung.control;

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
import de.turnierverwaltung.model.CSVVereine;
import de.turnierverwaltung.model.ELOPlayer;
import de.turnierverwaltung.model.ELOPlayerList;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.view.ELODialogView;
import de.turnierverwaltung.view.ELOPlayerView;

public class ELOControl {
	private MainControl mainControl;
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

	/**
	 * 
	 * @param mainControl
	 * @throws IOException
	 */
	public ELOControl(MainControl mainControl) throws IOException {
		super();
		runOnce = false;
		this.mainControl = mainControl;
		eloFile = mainControl.getPropertiesControl().checkPathToELOXML();
		if (eloFile == true) {
			csvplayerlist = new ELOPlayerList();

			csvplayerlist.readEloList(mainControl.getPropertiesControl().getPathToPlayersELO());
			playerlist = csvplayerlist.getPlayerList();
		} else {
			errorHandler();
		}

	}

	/**
	* 
	*/
	public void makeDialog() {
		if (playerlist != null) {
			if (dialog == null) {

				dialog = new ELODialogView();

			} else {
				dialog.dispose();

				dialog = new ELODialogView();

			}

			dialog.getPlayerSearchView().getOkButton().setEnabled(false);
		} else {
			errorHandler();
		}
	}

	public void makePlayerSearchList() {
		if (playerlist != null) {
			eloDialogActionListenerControl = new ELOActionListenerControl(this.mainControl, this);
			dialog.getPlayerSearchView().getOkButton().addActionListener(eloDialogActionListenerControl);
			dialog.getPlayerSearchView().getCancelButton().addActionListener(eloDialogActionListenerControl);
			// ELOActionListenerControl psc = new ELOActionListenerControl(mainControl,
			// this);
			spielerSearchTextField = dialog.getPlayerSearchView().getSearchField();

			spielerSearchTextField.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {
					spielerSearchPanelList = new ELOPlayerView();
					dialog.getPlayerSearchView().setDsbPanel(spielerSearchPanelList);
					searchplayerlist = new ArrayList<Player>();
					String eingabe = spielerSearchTextField.getText().toUpperCase();
					ListIterator<ELOPlayer> li = playerlist.listIterator();
					int counter = 0;
					while (li.hasNext() && counter < 20) {
						Player tmp = li.next().getPlayer();
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

					spielerSearchPanelList.makeList();
					spielerSearchPanelList.updateUI();
					spielerSearchPanelList.getList().addListSelectionListener(eloDialogActionListenerControl);

				}

				@Override
				public void keyTyped(KeyEvent e) {

				}
			});
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

	private boolean playerExist(Player neuerSpieler) {
		SQLPlayerControl spielerTableControl = new SQLPlayerControl(this.mainControl);
		Boolean playerExist = false;
		try {
			neuerSpieler.getDwzData().setCsvFIDE_ID(neuerSpieler.getEloData().getFideid());
			playerExist = spielerTableControl.playerFideExist(neuerSpieler);

		} catch (SQLException e) {
			mainControl.fileSQLError();
		}
		return playerExist;
	}

	public ELODialogView getDialog() {
		return dialog;
	}

	public void setDialog(ELODialogView dialog) {
		this.dialog = dialog;
	}

	public ELOPlayerView getSpielerDewisView() {
		return spielerDewisView;
	}

	public void setSpielerDewisView(ELOPlayerView spielerDewisView) {
		this.spielerDewisView = spielerDewisView;
	}

	public ArrayList<ELOPlayer> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<ELOPlayer> players) {
		this.players = players;
	}

	public ArrayList<CSVVereine> getZpsItems() {
		return zpsItems;
	}

	public void setZpsItems(ArrayList<CSVVereine> zpsItems) {
		this.zpsItems = zpsItems;
	}

	public ELOPlayerView getSpielerSearchPanelList() {
		return spielerSearchPanelList;
	}

	public void setSpielerSearchPanelList(ELOPlayerView spielerSearchPanelList) {
		this.spielerSearchPanelList = spielerSearchPanelList;
	}

	public ArrayList<Player> getSearchplayerlist() {
		return searchplayerlist;
	}

	public void setSearchplayerlist(ArrayList<Player> searchplayerlist) {
		this.searchplayerlist = searchplayerlist;
	}

}
