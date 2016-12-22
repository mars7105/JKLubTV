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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.NewTournamentGroupsView;

public class NewTournamentGroupsControl implements ActionListener {
	private MainControl mainControl;
	private NewTournamentGroupsView gruppenView;
	private JButton gruppenOKButton;
	private JButton gruppenCancelButton;
	private JTabbedPane hauptPanel;
	private int gruppenAnzahl;
	private Tournament turnier;
	private ArrayList<Group> gruppe;
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png")));

	public NewTournamentGroupsControl(MainControl mainControl) {

		this.mainControl = mainControl;
		turnier = this.mainControl.getTurnier();
		hauptPanel = this.mainControl.getHauptPanel();
		gruppenAnzahl = turnier.getAnzahlGruppen();
		this.mainControl.setGruppenView(new NewTournamentGroupsView());
		this.gruppenView = this.mainControl.getGruppenView();
		gruppenView.runView(gruppenAnzahl);
		gruppenOKButton = this.gruppenView.getOkButton();
		gruppenOKButton.addActionListener(this);
		gruppenCancelButton = this.gruppenView.getCancelButton();
		gruppenCancelButton.addActionListener(this);
		gruppenView.getGruppenNameTextField()[0].grabFocus();
		hauptPanel.remove(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.add(this.gruppenView, TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		hauptPanel.setTitleAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, turnier.getTurnierName());
		hauptPanel.setIconAt(TournamentConstants.TAB_ACTIVE_TOURNAMENT, gruppenIcon);
		hauptPanel.setSelectedIndex(TournamentConstants.TAB_ACTIVE_TOURNAMENT);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == gruppenOKButton) {
			makeGruppe();

			mainControl.setSpielerAnzahlControl(new NewTournamentPlayerCountControl(this.mainControl));
		}
		if (arg0.getSource() == gruppenCancelButton) {
			this.mainControl.setTurnierControl(new NewTournamentControl(this.mainControl));
		}

	}

	public int getGruppenAnzahl() {
		return gruppenAnzahl;
	}

	private void makeGruppe() {
		gruppe = new ArrayList<Group>();

		for (int i = 0; i < gruppenAnzahl; i++) {
			gruppe.add(new Group());
			gruppe.get(i).setGruppenName(gruppenView.getGruppenNameTextField()[i].getText());

		}
		turnier.setGruppe(gruppe);

	}

	public void setGruppenAnzahl(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

}
