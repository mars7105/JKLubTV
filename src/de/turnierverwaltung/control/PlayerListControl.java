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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.ButtonTabComponent;
import de.turnierverwaltung.view.EditPlayerView;
import de.turnierverwaltung.view.PlayerListView;

public class PlayerListControl implements ActionListener {
	private MainControl mainControl;
	// private TabAnzeigeView tabbedPaneView;
	private JTabbedPane hauptPanel;
	private int spielerAnzahl;
	private PlayerListView spielerLadenView;
	private ArrayList<Player> spieler;
	private SQLPlayerControl spielerTableControl;
	private EditPlayerView spielerEditierenView;
	private int spielerIndex;

	private ImageIcon spielerListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-group.png"))); //$NON-NLS-1$

	public PlayerListControl(MainControl mainControl) {
		this.mainControl = mainControl;
		hauptPanel = this.mainControl.getHauptPanel();
		this.mainControl.getNaviView().getDateiPanel().setVisible(true);
		this.mainControl.getNaviView().getSpielerListePanel().setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (spielerEditierenView != null) {
			if (arg0.getSource() == spielerEditierenView.getOkButton()) {
				String foreName = spielerEditierenView.getTextFieldForename().getText();
				String surName = spielerEditierenView.getTextFieldSurname().getText();
				if (!surName.equals("Spielfrei")) {
					try {
						String kuerzel = spielerEditierenView.getTextFieldKuerzel().getText();
						String dwz = spielerEditierenView.getTextFieldDwz().getText();
						String zps = spielerEditierenView.getTextFieldZPS().getText();
						String mgl = spielerEditierenView.getTextFieldMGL().getText();
						int age = spielerEditierenView.getTextComboBoxAge().getSelectedIndex();

						spieler.get(spielerIndex).setForename(foreName);
						spieler.get(spielerIndex).setSurname(surName);
						spieler.get(spielerIndex).setKuerzel(kuerzel);
						spieler.get(spielerIndex).setDwz(dwz);
						spieler.get(spielerIndex).setDsbZPSNumber(zps);
						spieler.get(spielerIndex).setDsbMGLNumber(mgl);
						spieler.get(spielerIndex).setAge(age);
						spieler.get(spielerIndex).extractForenameAndSurenameToName();
						SQLPlayerControl stc = new SQLPlayerControl(mainControl);

						stc.updateOneSpieler(spieler.get(spielerIndex));

						mainControl.setEnabled(true);

						updateSpielerListe();

						if (mainControl.getTurnier() != null) {
							mainControl.getTurnierListeLadenControl().reloadTurnier();
						}
						spielerEditierenView.closeWindow();
					} catch (SQLException e1) {
						spielerEditierenView.closeWindow();
						mainControl.fileSQLError();

					}
				} else {
					mainControl.setEnabled(true);
					try {
						updateSpielerListe();
					} catch (SQLException e) {
						spielerEditierenView.closeWindow();
						mainControl.fileSQLError();
					}
					spielerEditierenView.closeWindow();
				}

			}

			if (arg0.getSource() == spielerEditierenView.getCancelButton()) {
				mainControl.setEnabled(true);
				spielerEditierenView.closeWindow();

			}

		}

		if (spielerLadenView != null) {

			for (int i = 0; i < spielerAnzahl; i++) {
				if (arg0.getSource() == spielerLadenView.getSpielerBearbeitenButton()[i]) {
					if (mainControl.getNeuesTurnier() == false) {
						spielerIndex = i;
						spielerEditierenView = new EditPlayerView(spieler.get(i));
						spielerEditierenView.getOkButton().addActionListener(this);
						spielerEditierenView.getCancelButton().addActionListener(this);
						mainControl.setEnabled(false);
					} else {
						JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerLadenControl.2"));
					}
				}
			}

			for (int i = 0; i < spielerAnzahl; i++) {
				if (arg0.getSource() == spielerLadenView.getSpielerLoeschenButton()[i]) {
					if (mainControl.getNeuesTurnier() == false) {
						try {
							SQLPlayerControl stC = new SQLPlayerControl(mainControl);

							stC.loescheSpieler(spieler.get(i));

							updateSpielerListe();
						} catch (SQLException e) {
							mainControl.fileSQLError();
						}
					} else {
						JOptionPane.showMessageDialog(mainControl, Messages.getString("SpielerLadenControl.2"));
					}
				}
			}

		}

	}

	public void updateSpielerListe() throws SQLException {

		spielerTableControl = new SQLPlayerControl(this.mainControl);
		spieler = new ArrayList<Player>();
		spieler = spielerTableControl.getAllSpieler();
		testPlayerListForDoubles();
		spielerAnzahl = spieler.size();
		int selectedTab = 0;
		if (spielerLadenView == null) {
			spielerLadenView = new PlayerListView(spielerAnzahl, mainControl.getPropertiesControl().getSpielerProTab());
			hauptPanel.addTab(Messages.getString("SpielerLadenControl.1"), spielerListeIcon, spielerLadenView);
			ButtonTabComponent buttonComp = new ButtonTabComponent(hauptPanel, mainControl, spielerListeIcon, false);
			hauptPanel.setTabComponentAt(TournamentConstants.TAB_PLAYER_LIST, buttonComp);

		} else {
			selectedTab = spielerLadenView.getSpielerListe().getSelectedIndex();
			spielerLadenView.removeAll();
			spielerLadenView.init(spieler.size());

		}
		spielerLadenView.getTitleView().setFlowLayoutLeft();

		int index = 0;
		// Collections.sort(spieler, new SortName());
		for (Player player : spieler) {

			if (!player.getName().equals("") && player.getSurname().equals("")) {
				player.extractNameToForenameAndSurename();
				spielerTableControl.updateOneSpieler(player);
			}
			if (player.getName().equals("") && !player.getSurname().equals("")) {
				player.extractForenameAndSurenameToName();
				spielerTableControl.updateOneSpieler(player);
			}

			spielerLadenView.makeSpielerZeile(player, index);
			spielerLadenView.getSpielerBearbeitenButton()[index].addActionListener(this);
			spielerLadenView.getSpielerLoeschenButton()[index].addActionListener(this);
			index++;
		}
		if (selectedTab > 0 && spielerLadenView.getSpielerListe().getComponentCount() > selectedTab) {
			spielerLadenView.getSpielerListe().setSelectedIndex(selectedTab);
		}
		spielerLadenView.updateUI();

	}

	private void testPlayerListForDoubles() throws SQLException {
		Boolean loop = false;
		SQLPlayerControl stc = new SQLPlayerControl(mainControl);
		do {
			loop = false;
			for (int i = 0; i < spieler.size(); i++) {
				int zName = 0;
				for (int y = 0; y < spieler.size(); y++) {
					if (i != y) {
						if (spieler.get(i).getName().equals(spieler.get(y).getName())) {
							zName++;
							spieler.get(y)
									.setSurname(spieler.get(y).getSurname() + "_" + new Integer(zName).toString());

							spieler.get(y).extractForenameAndSurenameToName();
							stc.updateOneSpieler(spieler.get(y));
							loop = true;
						}
					}

				}
			}
		} while (loop == true);
	}

	public ArrayList<Player> getSpieler() {
		return spieler;
	}

	public void setSpieler(ArrayList<Player> spieler) {
		this.spieler = spieler;
	}

	public PlayerListView getSpielerLadenView() {
		return spielerLadenView;
	}

	public void setSpielerLadenView(PlayerListView spielerLadenView) {
		this.spielerLadenView = spielerLadenView;
	}

}
