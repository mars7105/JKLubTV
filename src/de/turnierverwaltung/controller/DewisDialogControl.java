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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.turnierverwaltung.model.DewisClub;
import de.turnierverwaltung.model.SortName;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.view.DEWISDialogView;
import de.turnierverwaltung.view.SpielerDewisView;

public class DewisDialogControl implements ListSelectionListener,
		ActionListener {
	private MainControl mainControl;
	private DEWISDialogView dialog;
	private SpielerDewisView spielerDewisView;
	private ArrayList<Spieler> players;
	private DewisDialogVereinsSucheController vereinsSuche;
	private ArrayList<String[]> zpsItems;

	public DewisDialogControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;

	}

	/**
	 * 
	 * @param zps = ZPS number of the association
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
			spielerDewisView.getList().addListSelectionListener(this);
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
			dialog.getVereinsSucheButton().addActionListener(this);
			dialog.getVereinsAuswahlOkButton().addActionListener(this);
			dialog.getUpdateButton().addActionListener(this);
			dialog.getUpdateButton().setEnabled(false);
			dialog.getOkButton().addActionListener(this);
			dialog.getCancelButton().addActionListener(this);
			dialog.getOkButton().setEnabled(false);
			String zps = mainControl.getPropertiesControl().getZPS();
			if (zps.length() > 0) {
				dialog.getVereinsSuche().setText(zps);

				makeDWZListe(zps);
			}
			vereinsSuche = new DewisDialogVereinsSucheController();
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

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (spielerDewisView.getList().getSelectedIndex() == -1) {
				// No selection, disable fire button.
				dialog.getOkButton().setEnabled(false);

			} else {
				// Selection, enable the fire button.
				dialog.getOkButton().setEnabled(true);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == dialog.getVereinsAuswahlOkButton()) {
			if (dialog.getVereinsAuswahl().getItemCount() > 0) {

				int index = dialog.getVereinsAuswahl().getSelectedIndex();
				String items[] = zpsItems.get(index);
				String zps = items[0];
				makeDWZListe(zps);
			}

		}
		if (arg0.getSource() == dialog.getVereinsSucheButton()) {
			if (dialog.getVereinsName().getText().length() > 0) {
				makeVereinsListe();
			} else if (dialog.getVereinsSuche().getText().length() > 0) {
				String zps = dialog.getVereinsSuche().getText();
				makeDWZListe(zps);
			}

		}
		if (arg0.getSource() == dialog.getCancelButton()) {
			dialog.closeWindow();
			mainControl.setEnabled(true);
		}
		if (arg0.getSource() == dialog.getOkButton()) {
			int[] indices = spielerDewisView.getList().getSelectedIndices();

			if (players != null) {
				for (int i = 0; i < indices.length; i++) {
					Spieler neuerSpieler = new Spieler();
					neuerSpieler = players.get(indices[i]);
					Boolean findPlayer = searchSpieler(neuerSpieler, false);
					if (findPlayer == false) {
						SpielerTableControl stc = new SpielerTableControl(
								mainControl);
						neuerSpieler.setSpielerId(stc
								.insertOneSpieler(neuerSpieler));
						mainControl.getSpielerLadenControl().getSpieler()
								.add(neuerSpieler);
					}
				}
			}
			mainControl.getSpielerLadenControl().updateSpielerListe();
		}
		if (arg0.getSource() == dialog.getUpdateButton()) {

			@SuppressWarnings("unused")
			Boolean findPlayer = true;
			for (Spieler player : players) {
				findPlayer = searchSpieler(player, true);

			}

			dialog.closeWindow();
			mainControl.setEnabled(true);
			mainControl.getSpielerLadenControl().updateSpielerListe();
		}
	}

	private void makeVereinsListe() {
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
	 * @param neuerSpieler = new player
	 * @param updateDWZ = update the dwz
	 * @return
	 */
	private Boolean searchSpieler(Spieler neuerSpieler, Boolean updateDWZ) {
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
}
