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
import de.turnierverwaltung.view.playerlist.EditPlayerView;
import de.turnierverwaltung.view.playerlist.PlayerListView;
import de.turnierverwaltung.view.tournamenttable.ButtonTabComponent;

public class PlayerListControl implements ActionListener {
	private final MainControl mainControl;
	// private TabAnzeigeView tabbedPaneView;
	private final JTabbedPane hauptPanel;
	private int spielerAnzahl;
	private PlayerListView spielerLadenView;
	private ArrayList<Player> spieler;
	private SQLPlayerControl spielerTableControl;
	private EditPlayerView spielerEditierenView;
	private int spielerIndex;

	private final ImageIcon spielerListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-group.png"))); //$NON-NLS-1$

	public PlayerListControl(final MainControl mainControl) {
		this.mainControl = mainControl;
		hauptPanel = this.mainControl.getHauptPanel();
		this.mainControl.getNaviView().getDateiPanel().setVisible(true);
		this.mainControl.getNaviView().getSpielerListePanel().setVisible(true);

	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

		if (spielerEditierenView != null) {
			if (arg0.getSource().equals(spielerEditierenView.getOkButton())) {
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
					} catch (final NumberFormatException e) {
						dwzindex = -1;
					}
					int dwzInt = 0;
					try {
						dwzInt = Integer.parseInt(dwz);
					} catch (final NumberFormatException e) {
						dwzInt = 0;
					}
					int fideId = 0;
					try {
						fideId = Integer.parseInt(fideid);
					} catch (final NumberFormatException e) {
						fideId = 0;
					}
					int rating = 0;
					try {
						rating = Integer.parseInt(elo);
					} catch (final NumberFormatException e) {
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

			if (arg0.getSource().equals(spielerEditierenView.getCancelButton())) {
				// mainControl.setEnabled(true);
				spielerEditierenView.closeWindow();

			}

		}

		if (spielerLadenView != null) {

			for (int i = 0; i < spielerAnzahl; i++) {
				if (arg0.getSource()
						.equals(spielerLadenView.getPlayerListItems().get(i).getSpielerBearbeitenButton())) {
					// mainControl.setEnabled(false);
					if (mainControl.getNewTournament() == false) {
						spielerIndex = i;
						if (spieler.get(i).getDwzData().getCsvSpielername().length() > 0) {
							spieler.get(i).setName(spieler.get(i).getDwzData().getCsvSpielername());
							spieler.get(i).extractNameToForenameAndSurename();
						}
						spielerEditierenView = new EditPlayerView(spieler.get(i));
						spielerEditierenView.getOkButton().addActionListener(this);
						spielerEditierenView.getCancelButton().addActionListener(this);
						spielerEditierenView.showDialog();
					} else {
						JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerLadenControl.2"));
					}
				}
			}

			for (int i = 0; i < spielerAnzahl; i++) {
				if (arg0.getSource().equals(spielerLadenView.getPlayerListItems().get(i).getSpielerLoeschenButton())) {
					if (mainControl.getNewTournament() == false) {
						try {
							final SQLPlayerControl stC = new SQLPlayerControl(mainControl);

							stC.loescheSpieler(spieler.get(i));

							updateSpielerListe();
						} catch (final SQLException e) {
							final ExceptionHandler eh = new ExceptionHandler(mainControl);
							eh.fileSQLError(e.getMessage());
						}
					} else {
						JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerLadenControl.2"));
					}
				}
			}

		}

	}

	public ArrayList<Player> getSpieler() {
		return spieler;
	}

	public PlayerListView getSpielerLadenView() {
		return spielerLadenView;
	}

	public void setSpieler(final ArrayList<Player> spieler) {
		this.spieler = spieler;
	}

	public void setSpielerLadenView(final PlayerListView spielerLadenView) {
		this.spielerLadenView = spielerLadenView;
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
			// testPlayerListForDoubles();
			spielerAnzahl = spieler.size();
			int selectedTab = 0;
			if (spielerLadenView == null) {
				spielerLadenView = new PlayerListView(spielerAnzahl,
						mainControl.getPropertiesControl().getSpielerProTab());
				hauptPanel.addTab(Messages.getString("SpielerLadenControl.1"), spielerListeIcon, spielerLadenView);
				final ButtonTabComponent buttonComp = new ButtonTabComponent(hauptPanel, mainControl, spielerListeIcon,
						false);
				hauptPanel.setTabComponentAt(TournamentConstants.TAB_PLAYER_LIST, buttonComp);

			} else {
				selectedTab = spielerLadenView.getSpielerListe().getSelectedIndex();
				spielerLadenView.removeAll();
				spielerLadenView.init(spieler.size());

			}
			spielerLadenView.getTitleView().setFlowLayoutLeft();

			int index = 0;
			for (final Player player : spieler) {

				spielerLadenView.makeSpielerZeile(player, index);
				spielerLadenView.getPlayerListItems().get(index).getSpielerBearbeitenButton().addActionListener(this);
				spielerLadenView.getPlayerListItems().get(index).getSpielerLoeschenButton().addActionListener(this);
				index++;
			}
			if (selectedTab > 0 && spielerLadenView.getSpielerListe().getComponentCount() > selectedTab) {
				spielerLadenView.getSpielerListe().setSelectedIndex(selectedTab);
			}
			spielerLadenView.updateUI();
		} catch (final NullPointerException e) {

		}
	}

}
