package de.turnierverwaltung.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
import java.util.ListIterator;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private ELOActionListenerControl dewisDialogActionListenerControl;
	private ArrayList<Player> spielerListe;
	private Boolean csvFiles;
	private JTextField spielerSearchTextField;
	private ELOPlayerView spielerSearchPanelList;
	private ArrayList<Player> searchplayerlist;
	private ArrayList<ELOPlayer> playerlist;

	/**
	 * 
	 * @param mainControl
	 */
	public ELOControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		csvFiles = mainControl.getPropertiesControl().checkPathToVereineCSV()
				&& mainControl.getPropertiesControl().checkPathToSpielerCSV();
		dewisDialogActionListenerControl = new ELOActionListenerControl(this.mainControl, this);

	}

	/**
	 * 
	 * @param zps
	 *            = ZPS number of the association
	 */
	public void makeDWZListe(String zps) {

		players = null;
		if (csvFiles == true) {
			try {
				ELOPlayerList csvplayerlist = new ELOPlayerList();
				csvplayerlist.readEloList(mainControl.getPropertiesControl().getPathToPlayersELO());
				players = csvplayerlist.getPlayerList();

				SQLPlayerControl sqlpc = new SQLPlayerControl(mainControl);

				try {
					spielerListe = sqlpc.getAllSpieler();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (players != null) {

					spielerDewisView = new ELOPlayerView();

					ListIterator<ELOPlayer> list = players.listIterator();
					while (list.hasNext()) {

						Player player = list.next().getPlayer();
						ListIterator<Player> li = spielerListe.listIterator();
						Boolean foundPlayer = false;
						while (li.hasNext()) {
							Player tmp = li.next();
							try {
								int tmpzps = Integer.parseInt(tmp.getDsbZPSNumber());
								int tmpmgl = Integer.parseInt(tmp.getDsbMGLNumber());
								int playerzps = Integer.parseInt(player.getDsbZPSNumber());
								int playermgl = Integer.parseInt(player.getDsbMGLNumber());
								if (tmpzps == playerzps && tmpmgl == playermgl) {
									spielerDewisView.makeSpielerZeile(player, 2);
									foundPlayer = true;

								}
							} catch (NumberFormatException e) {

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
				} else {
					// dialog.getUpdateButton().setEnabled(false);
					JLabel noItemLabel = new JLabel(Messages.getString("DewisDialogControl.0")); //$NON-NLS-1$
					JPanel noItemPanel = new JPanel();
					noItemPanel.add(noItemLabel);
					dialog.setDsbPanel(noItemPanel);

				}
				dialog.refresh();
			} catch (ArrayIndexOutOfBoundsException e2) {

			}
		}
	}

	/**
	* 
	*/
	public void makeDialog() {
		// Boolean csvFiles = vereinsSuche.checkifSpielerFileExist();
		if (dialog == null) {
			try {
				dialog = new ELODialogView(csvFiles);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			dialog.dispose();
			try {
				dialog = new ELODialogView(csvFiles);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		dialog.getOkButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getCancelButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getOkButton().setEnabled(false);

	}

	public void makePlayerSearchList() {
		if (csvFiles == true) {
			ELOPlayerList csvplayerlist = new ELOPlayerList();
			try {
				csvplayerlist.readEloList(mainControl.getPropertiesControl().getPathToPlayersELO());
			} catch (ArrayIndexOutOfBoundsException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

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
					spielerSearchPanelList = new ELOPlayerView();
					dialog.getPlayerSearchView().setDsbPanel(spielerSearchPanelList);
					searchplayerlist = new ArrayList<Player>();

					playerlist = csvplayerlist.getPlayerList();
					String eingabe = spielerSearchTextField.getText().toUpperCase();
					ListIterator<ELOPlayer> li = playerlist.listIterator();
					int counter = 0;
					while (li.hasNext() && counter < 20) {
						ELOPlayer tmp = li.next();
						String surname = "";
						String forename = "";
						String name = "";
						if (tmp.getPlayer().getSurname().length() >= eingabe.length()) {
							surname = tmp.getPlayer().getSurname().substring(0, eingabe.length()).toUpperCase();
						}
						if (tmp.getPlayer().getForename().length() >= eingabe.length()) {
							forename = tmp.getPlayer().getForename().substring(0, eingabe.length()).toUpperCase();
						}
						if (tmp.getPlayer().getName().length() >= eingabe.length()) {
							name = tmp.getPlayer().getName().substring(0, eingabe.length()).toUpperCase();
						}
						if (eingabe.equals(surname) || eingabe.equals(forename) || eingabe.equals(name)) {

							ListIterator<Player> list = spielerListe.listIterator();
							Boolean foundPlayer = false;
							while (list.hasNext()) {
								Player temp = list.next();
								try {
									int tmpzps = Integer.parseInt(temp.getDsbZPSNumber());
									int tmpmgl = Integer.parseInt(temp.getDsbMGLNumber());
									int playerzps = Integer.parseInt(tmp.getPlayer().getDsbZPSNumber());
									int playermgl = Integer.parseInt(tmp.getPlayer().getDsbMGLNumber());
									if (tmpzps == playerzps && tmpmgl == playermgl) {
										spielerSearchPanelList.makeSpielerZeile(tmp.getPlayer(), 2);
										searchplayerlist.add(tmp.getPlayer());
										foundPlayer = true;

									}
								} catch (NumberFormatException e2) {

								}
							}
							if (foundPlayer == false) {
								spielerSearchPanelList.makeSpielerZeile(tmp.getPlayer(), 0);
								searchplayerlist.add(tmp.getPlayer());
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
		}
	}

	// private void errorHandler() {
	// csvFiles = false;
	// mainControl.getPropertiesControl().setPathToVereineCSV("");
	// mainControl.getPropertiesControl().setPathToPlayersCSV("");
	// dialog.dispose();
	// JOptionPane.showMessageDialog(null,
	// Messages.getString("DewisDialogControl.7"),
	// Messages.getString("DewisDialogControl.8"), JOptionPane.INFORMATION_MESSAGE);
	// }

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

}
