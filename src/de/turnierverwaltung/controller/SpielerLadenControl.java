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

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.view.SpielerEditierenView;
import de.turnierverwaltung.view.SpielerLadenView;

public class SpielerLadenControl implements ActionListener {
	private MainControl mainControl;
	// private TabAnzeigeView tabbedPaneView;
	private JTabbedPane hauptPanel;
	private int spielerAnzahl;
	private SpielerLadenView spielerLadenView;
	private ArrayList<Spieler> spieler;
	private SpielerTableControl spielerTableControl;
	private SpielerEditierenView spielerEditierenView;
	private int spielerIndex;

	private ImageIcon spielerListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-group.png"))); //$NON-NLS-1$

	public SpielerLadenControl(MainControl mainControl) {
		this.mainControl = mainControl;
		new DewisDialogControl(this.mainControl);
		hauptPanel = this.mainControl.getHauptPanel();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (spielerEditierenView != null) {
			if (arg0.getSource() == spielerEditierenView.getOkButton()) {
				String name = spielerEditierenView.getTextFieldName().getText();
				String kuerzel = spielerEditierenView.getTextFieldKuerzel().getText();
				String dwz = spielerEditierenView.getTextFieldDwz().getText();
				int age = spielerEditierenView.getTextComboBoxAge().getSelectedIndex();
				spieler.get(spielerIndex).setName(name);
				spieler.get(spielerIndex).setKuerzel(kuerzel);
				spieler.get(spielerIndex).setDwz(dwz);
				spieler.get(spielerIndex).setAge(age);
				SpielerTableControl stc = new SpielerTableControl(mainControl);
				stc.updateOneSpieler(spieler.get(spielerIndex));
				mainControl.setEnabled(true);
				updateSpielerListe();
				mainControl.getTurnierListeLadenControl().reloadTurnier();

				spielerEditierenView.closeWindow();

			}
		}
		if (spielerEditierenView != null) {
			if (arg0.getSource() == spielerEditierenView.getCancelButton()) {
				mainControl.setEnabled(true);
				spielerEditierenView.closeWindow();

			}

		}

		if (spielerLadenView != null) {

			for (int i = 0; i < spielerAnzahl; i++) {
				if (arg0.getSource() == spielerLadenView.getSpielerBearbeitenButton()[i]) {
					spielerIndex = i;
					spielerEditierenView = new SpielerEditierenView(spieler.get(i));
					spielerEditierenView.getOkButton().addActionListener(this);
					spielerEditierenView.getCancelButton().addActionListener(this);
					mainControl.setEnabled(false);
				}
			}

			for (int i = 0; i < spielerAnzahl; i++) {
				if (arg0.getSource() == spielerLadenView.getSpielerLoeschenButton()[i]) {
					SpielerTableControl stC = new SpielerTableControl(mainControl);
					stC.loescheSpieler(spieler.get(i));
					updateSpielerListe();
				}
			}

		}

	}

	public void updateSpielerListe() {
		spielerTableControl = new SpielerTableControl(this.mainControl);
		spieler = new ArrayList<Spieler>();
		spieler = spielerTableControl.getAllSpieler();
		spielerAnzahl = spieler.size();
		if (spielerLadenView == null) {
			spielerLadenView = new SpielerLadenView(spielerAnzahl);
			hauptPanel.addTab(Messages.getString("SpielerLadenControl.1"), spielerListeIcon, spielerLadenView); //$NON-NLS-1$
		} else {
			spielerLadenView.removeAll();
			spielerLadenView.init(spielerAnzahl);
		}

		int index = 0;
		for (Spieler player : spieler) {

			spielerLadenView.makeSpielerZeile(player, index);
			spielerLadenView.getSpielerBearbeitenButton()[index].addActionListener(this);
			spielerLadenView.getSpielerLoeschenButton()[index].addActionListener(this);
			index++;
		}

		spielerLadenView.updateUI();
	}

	public ArrayList<Spieler> getSpieler() {
		return spieler;
	}

	public void setSpieler(ArrayList<Spieler> spieler) {
		this.spieler = spieler;
	}

	public SpielerLadenView getSpielerLadenView() {
		return spielerLadenView;
	}

	public void setSpielerLadenView(SpielerLadenView spielerLadenView) {
		this.spielerLadenView = spielerLadenView;
	}

}
