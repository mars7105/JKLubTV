package de.turnierverwaltung.controller;

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
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;
import de.turnierverwaltung.model.DewisClub;
import de.turnierverwaltung.model.SortName;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.view.DEWISDialogView;
import de.turnierverwaltung.view.SpielerDewisView;

public class DewisDialogControl {
	private MainControl mainControl;
	private DEWISDialogView dialog;
	private SpielerDewisView spielerDewisView;
	private ArrayList<Spieler> players;
	private DewisDialogVereinsSucheControl vereinsSuche;
	private ArrayList<String[]> zpsItems;
	private DewisDialogActionListenerControl dewisDialogActionListenerControl;

	public DewisDialogControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		dewisDialogActionListenerControl = new DewisDialogActionListenerControl(
				this.mainControl, this);
	}

	/**
	 * 
	 * @param zps
	 *            = ZPS number of the association
	 */
	public void makeDWZListe(String zps) {
		DewisClub verein = new DewisClub(zps);
		players = new ArrayList<Spieler>();
		players = verein.getSpieler();
		if (players != null) {
			Collections.sort(players, new SortName());

			spielerDewisView = new SpielerDewisView();
			for (Spieler player : players) {
				spielerDewisView.makeSpielerZeile(player);
			}
			spielerDewisView.makeList();
			spielerDewisView.updateUI();
			spielerDewisView.getList().addListSelectionListener(
					dewisDialogActionListenerControl);
			dialog.setDsbPanel(spielerDewisView);
			mainControl.getPropertiesControl().setZPS(zps);
			mainControl.getPropertiesControl().writeProperties();
			dialog.getUpdateButton().setEnabled(true);
		} else {
			dialog.getUpdateButton().setEnabled(false);
			JLabel noItemLabel = new JLabel(
					Messages.getString("DewisDialogControl.0")); //$NON-NLS-1$
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
		try {
			mainControl.setEnabled(false);

			dialog = new DEWISDialogView();
			dialog.getVereinsSucheButton().addActionListener(
					dewisDialogActionListenerControl);
			dialog.getVereinsAuswahlOkButton().addActionListener(
					dewisDialogActionListenerControl);
			dialog.getUpdateButton().addActionListener(
					dewisDialogActionListenerControl);
			dialog.getUpdateButton().setEnabled(false);
			dialog.getOkButton().addActionListener(
					dewisDialogActionListenerControl);
			dialog.getCancelButton().addActionListener(
					dewisDialogActionListenerControl);
			dialog.getOkButton().setEnabled(false);
			String zps = mainControl.getPropertiesControl().getZPS();
			if (zps.length() > 0) {
				dialog.getVereinsSuche().setText(zps);

				makeDWZListe(zps);
			}
			vereinsSuche = new DewisDialogVereinsSucheControl(mainControl);
			if (vereinsSuche.checkifFileExist() == false) {
				dialog.getVereinsAuswahl().setEnabled(false);
				dialog.getVereinsAuswahlOkButton().setEnabled(false);
				dialog.getVereinsName().setEnabled(false);
				dialog.getVereinsName().setBackground(Color.LIGHT_GRAY);
			}
		} catch (Exception e) {
			mainControl.setEnabled(true);
		}
	}

	public void makeVereinsListe() {
		ArrayList<String[]> vereine = vereinsSuche.searchForVerein(dialog
				.getVereinsName().getText());
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
	 */
	public Boolean searchSpieler(Spieler neuerSpieler, Boolean updateDWZ) {
		ArrayList<Spieler> spieler = mainControl.getSpielerLadenControl()
				.getSpieler();

		for (Spieler player : spieler) {
			if (player.getName().equals(neuerSpieler.getName()) == true) {
				if (player.getDWZ() != neuerSpieler.getDWZ()
						&& updateDWZ == true) {

					SpielerTableControl stc = new SpielerTableControl(
							mainControl);
					player.setDwz(neuerSpieler.getDWZ());
					stc.updateOneSpieler(player);

				}

				return true;
			}
		}
		return false;

	}

	public DEWISDialogView getDialog() {
		return dialog;
	}

	public void setDialog(DEWISDialogView dialog) {
		this.dialog = dialog;
	}

	public SpielerDewisView getSpielerDewisView() {
		return spielerDewisView;
	}

	public void setSpielerDewisView(SpielerDewisView spielerDewisView) {
		this.spielerDewisView = spielerDewisView;
	}

	public ArrayList<Spieler> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Spieler> players) {
		this.players = players;
	}

	public DewisDialogVereinsSucheControl getVereinsSuche() {
		return vereinsSuche;
	}

	public void setVereinsSuche(DewisDialogVereinsSucheControl vereinsSuche) {
		this.vereinsSuche = vereinsSuche;
	}

	public ArrayList<String[]> getZpsItems() {
		return zpsItems;
	}

	public void setZpsItems(ArrayList<String[]> zpsItems) {
		this.zpsItems = zpsItems;
	}

}
