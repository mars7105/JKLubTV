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

import java.awt.Color;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.turnierverwaltung.model.DSBDWZClub;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.SortName;
import de.turnierverwaltung.view.DSBDWZDialogView;
import de.turnierverwaltung.view.DSBDWZPlayerView;

public class DSBDWZControl {
	private MainControl mainControl;
	private DSBDWZDialogView dialog;
	private DSBDWZPlayerView spielerDewisView;
	private ArrayList<Player> players;
	private DSBDWZAssociationSearchControl vereinsSuche;
	private ArrayList<String[]> zpsItems;
	private DSBDWZActionListenerControl dewisDialogActionListenerControl;
	private ArrayList<Player> spielerListe;

	/**
	 * 
	 * @param mainControl
	 */
	public DSBDWZControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		dewisDialogActionListenerControl = new DSBDWZActionListenerControl(this.mainControl, this);

	}

	/**
	 * 
	 * @param zps
	 *            = ZPS number of the association
	 */
	public void makeDWZListe(String zps) {
		DSBDWZClub verein = new DSBDWZClub(zps);
		players = new ArrayList<Player>();
		players = verein.getSpieler();
		SQLPlayerControl sqlpc = new SQLPlayerControl(mainControl);
		try {
			spielerListe = sqlpc.getAllSpielerOrderByZPS();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (players != null) {
			Collections.sort(players, new SortName());

			spielerDewisView = new DSBDWZPlayerView();

			ListIterator<Player> list = players.listIterator();
			while (list.hasNext()) {
				Player player = list.next();
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
							spielerDewisView.makeSpielerZeile(player, 1);
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

	}

	/**
	* 
	*/
	public void makeDialog() {

		if (dialog == null) {
			try {
				dialog = new DSBDWZDialogView();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			dialog.dispose();
			try {
				dialog = new DSBDWZDialogView();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dialog.getVereinsSucheButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getVereinsAuswahlOkButton().addActionListener(dewisDialogActionListenerControl);
		// dialog.getUpdateButton().addActionListener(dewisDialogActionListenerControl);
		// dialog.getUpdateButton().setEnabled(false);
		dialog.getOkButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getCancelButton().addActionListener(dewisDialogActionListenerControl);
		dialog.getOkButton().setEnabled(false);
		String zps = mainControl.getPropertiesControl().getZPS();
		if (zps.length() > 0) {
			dialog.getVereinsSuche().setText(zps);

			makeDWZListe(zps);
		}
		vereinsSuche = new DSBDWZAssociationSearchControl(mainControl);
		if (vereinsSuche.checkifFileExist() == false) {
			dialog.getVereinsAuswahl().setEnabled(false);
			dialog.getVereinsAuswahlOkButton().setEnabled(false);
			dialog.getVereinsName().setEnabled(false);
			dialog.getVereinsName().setBackground(Color.LIGHT_GRAY);
		}

	}

	/**
	 * 
	 */
	public void makeVereinsListe() {
		ArrayList<String[]> vereine = vereinsSuche.searchForVerein(dialog.getVereinsName().getText());
		zpsItems = new ArrayList<String[]>();
		dialog.getVereinsAuswahl().removeAllItems();

		for (String[] iterator : vereine) {

			String[] row = iterator;
			zpsItems.add(row);
			dialog.getVereinsAuswahl().addItem(row[3]);

		}

	}

	/**
	 * @param neuerSpieler
	 *            = new player
	 * @param updateDWZ
	 *            = update the dwz
	 * @return
	 * @throws SQLException
	 */
	public Boolean searchSpieler(Player neuerSpieler, Boolean updateDWZ) throws SQLException {

		for (Player player : players) {

			if (player.getDsbZPSNumber().equals(neuerSpieler.getDsbZPSNumber())
					&& player.getDsbMGLNumber().equals(neuerSpieler.getDsbMGLNumber())) {
				if (((player.getDWZ() < neuerSpieler.getDWZ() || player.getDWZ() > neuerSpieler.getDWZ())
						&& updateDWZ == true)) {

					SQLPlayerControl stc = new SQLPlayerControl(mainControl);
					neuerSpieler.setDwz(player.getDWZ());

					neuerSpieler.setDwzindex(player.getDwzindex());
					stc.updateOneSpieler(neuerSpieler);

				}

				return true;
			}
		}
		return false;

	}

	public DSBDWZDialogView getDialog() {
		return dialog;
	}

	public void setDialog(DSBDWZDialogView dialog) {
		this.dialog = dialog;
	}

	public DSBDWZPlayerView getSpielerDewisView() {
		return spielerDewisView;
	}

	public void setSpielerDewisView(DSBDWZPlayerView spielerDewisView) {
		this.spielerDewisView = spielerDewisView;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public DSBDWZAssociationSearchControl getVereinsSuche() {
		return vereinsSuche;
	}

	public void setVereinsSuche(DSBDWZAssociationSearchControl vereinsSuche) {
		this.vereinsSuche = vereinsSuche;
	}

	public ArrayList<String[]> getZpsItems() {
		return zpsItems;
	}

	public void setZpsItems(ArrayList<String[]> zpsItems) {
		this.zpsItems = zpsItems;
	}

}
