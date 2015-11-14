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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.view.GruppenView;

public class GruppenControl implements ActionListener {
	private MainControl mainControl;
	private GruppenView gruppenView;
	private JButton gruppenOKButton;
	private JButton gruppenCancelButton;
	private JPanel hauptPanel;
	private int gruppenAnzahl;
	private Turnier turnier;
	private Gruppe[] gruppe;

	public GruppenControl(MainControl mainControl) {

		this.mainControl = mainControl;
		turnier = this.mainControl.getTurnier();
		hauptPanel = this.mainControl.getHauptPanel();
		gruppenAnzahl = turnier.getAnzahlGruppen();
		this.mainControl.setGruppenView(new GruppenView());
		this.gruppenView = this.mainControl.getGruppenView();
		gruppenView.runView(gruppenAnzahl);
		gruppenOKButton = this.gruppenView.getOkButton();
		gruppenOKButton.addActionListener(this);
		gruppenCancelButton = this.gruppenView.getCancelButton();
		gruppenCancelButton.addActionListener(this);
		gruppenView.getGruppenNameTextField()[0].grabFocus();
		hauptPanel.removeAll();
		this.mainControl.getNaviController().makeNaviPanel();
		hauptPanel.add(gruppenView);
		hauptPanel.updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == gruppenOKButton) {
			gruppenAnzahl = turnier.getAnzahlGruppen();
			makeGruppe();

			this.mainControl.setSpielerAnzahlControl(new SpielerAnzahlControl(this.mainControl));

		}
		if (arg0.getSource() == gruppenCancelButton) {
			this.mainControl.setTurnierControl(new TurnierControl(this.mainControl));
		}

	}

	public int getGruppenAnzahl() {
		return gruppenAnzahl;
	}

	private void makeGruppe() {
		gruppe = new Gruppe[gruppenAnzahl];

		for (int i = 0; i < gruppenAnzahl; i++) {
			gruppe[i] = new Gruppe();
			gruppe[i].setGruppenName(gruppenView.getGruppenNameTextField()[i].getText());

		}
		turnier.setGruppe(gruppe);

	}

	public void setGruppenAnzahl(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

}
