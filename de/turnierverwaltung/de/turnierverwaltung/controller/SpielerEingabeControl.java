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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;
import de.turnierverwaltung.view.SpielerAnzahlView;
import de.turnierverwaltung.view.SpielerEingabeView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class SpielerEingabeControl implements ActionListener, KeyListener {

	private MainControl mainControl;
	private SpielerEingabeView[] spielerEingabeView;
	private SpielerAnzahlView[] spielerAnzahlView;
	private JButton[] okButton;
	private JButton[] cancelButton;
	private int[] spielerAnzahl;
	private int gruppenAnzahl;
	private TabAnzeigeView tabAnzeigeView;
	private JPanel hauptPanel;
	private Turnier turnier;
	private Gruppe[] gruppe;
	private Spieler[] spieler;
	private RundenEingabeFormularControl rundenEingabeFormularControl;
	private ArrayList<Spieler> alleSpieler;

	public SpielerEingabeControl(MainControl mainControl) {
		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 25;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 75;
		this.mainControl = mainControl;
		SpielerTableControl spielerTableControl = new SpielerTableControl(
				mainControl);
		alleSpieler = spielerTableControl.getAllSpieler();
		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();
		hauptPanel = this.mainControl.getHauptPanel();
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();
		tabAnzeigeView
				.setPreferredSize(new Dimension(windowWidth, windowHeight));
		spielerAnzahlView = this.mainControl.getSpielerAnzahlControl()
				.getSpielerAnzahlView();
		gruppenAnzahl = this.mainControl.getTurnier().getAnzahlGruppen();
		spielerAnzahl = new int[gruppenAnzahl];

		spielerEingabeView = new SpielerEingabeView[gruppenAnzahl];

		this.mainControl
				.setSpielerEingabeView(new SpielerEingabeView[gruppenAnzahl]);
		okButton = new JButton[gruppenAnzahl];
		cancelButton = new JButton[gruppenAnzahl];
		gruppenAnzahl = this.mainControl.getTurnierControl().getGruppenAnzahl();
		spielerEingabeView = new SpielerEingabeView[gruppenAnzahl];
		this.mainControl.setSpielerEingabeView(spielerEingabeView);
		rundenEingabeFormularControl = new RundenEingabeFormularControl(
				this.mainControl);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String name = "";
		String kuerzel = "";
		String dwz = "";
		int spielerID = 0;
		int sAnzahl = 0;
		for (int i = 0; i < gruppenAnzahl; i++) {
			if (arg0.getSource() == okButton[i]) {
				sAnzahl = gruppe[i].getSpielerAnzahl();
				gruppe[i].setRundenAnzahl(sAnzahl + ((sAnzahl % 2) - 1));

				spieler = new Spieler[sAnzahl];
				for (int y = 0; y < sAnzahl; y++) {
					spieler[y] = new Spieler();
					name = spielerEingabeView[i].getSpielerTextfield()[y]
							.getText();
					kuerzel = spielerEingabeView[i].getKuerzelTextfield()[y]
							.getText();
					dwz = spielerEingabeView[i].getDwzTextfield()[y].getText();
					spielerID = spielerEingabeView[i].getSpielerID()[y];
					spieler[y].setName(name);
					spieler[y].setKuerzel(kuerzel);
					spieler[y].setDwz(dwz);
					if (spielerID >= 0) {
						spieler[y].setSpielerId(spielerID);
						SpielerTableControl stc = new SpielerTableControl(
								mainControl);
						stc.updateOneSpieler(spieler[y]);

					}

				}

				gruppe[i].setSpieler(spieler);
				Arrays.sort(spieler);
				rundenEingabeFormularControl.makeTerminTabelle(i);

			}
			if (arg0.getSource() == cancelButton[i]) {
				// Custom button text
				Object[] options = { "Ja", "Abbrechen" };
				int abfrage = JOptionPane
						.showOptionDialog(
								null,
								"Wollen Sie wirklich abbrechen? "
										+ "Alle eingegebenen Daten dieser Gruppe gehen verloren.",
								"Meldung",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.WARNING_MESSAGE, null, options,
								options[1]);
				if (abfrage == 0) {
					this.mainControl.getSpielerAnzahlControl().makeNewTab(i);
				}
			}
			for (int s = 0; s < spielerAnzahl[i]; s++) {
				if (arg0.getSource() == spielerEingabeView[i].getSpielerSuche()[s]) {
					JTextField field = spielerEingabeView[i]
							.getSpielerTextfield()[s];
					@SuppressWarnings("unchecked")
					JComboBox<String> box = spielerEingabeView[i]
							.getSpielerSuche()[s];

					field.setText((String) box.getSelectedItem());

					Spieler temp = null;

					ListIterator<Spieler> li = alleSpieler.listIterator();

					String textField = "";
					textField = field.getText();
					while (li.hasNext()) {
						temp = li.next();
						if (textField.regionMatches(true, 0, temp.getName(), 0,
								textField.length())) {
							dwz = temp.getDwz();
							kuerzel = temp.getKuerzel();
							spielerID = temp.getSpielerId();
							spielerEingabeView[i].getKuerzelTextfield()[s]
									.setText(kuerzel);
							spielerEingabeView[i].getDwzTextfield()[s]
									.setText(dwz);
							spielerEingabeView[i].getSpielerID()[s] = spielerID;
						}

					}
				}
			}
		}
		hauptPanel.updateUI();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < gruppenAnzahl; i++) {
			for (int s = 0; s < spielerAnzahl[i]; s++)
				if (e.getSource() == spielerEingabeView[i]
						.getSpielerTextfield()[s]) {
					spielerEingabeView[i].getSpielerSuche()[s]
							.removeActionListener(this);
					spielerEingabeView[i].getSpielerSuche()[s].removeAllItems();

					int anzahlZeichen = spielerEingabeView[i]
							.getSpielerTextfield()[s].getText().length();
					Spieler temp = null;

					ListIterator<Spieler> li = alleSpieler.listIterator();

					String textField = "";
					String labels = "";
					textField = spielerEingabeView[i].getSpielerTextfield()[s]
							.getText() + e.getKeyChar();
					while (li.hasNext()) {
						temp = li.next();

						if (textField.regionMatches(true, 0, temp.getName(), 0,
								textField.length())) {
							labels = temp.getName();
							spielerEingabeView[i].getSpielerSuche()[s]
									.addItem(labels);
						}

					}
					if (textField.length() == 0) {
						suchAnzeige(i);
					}
					spielerEingabeView[i].getSpielerSuche()[s]
							.addActionListener(this);

				}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void keyTyped(KeyEvent e) {

	}

	@SuppressWarnings("unchecked")
	public void makeTabbedPane(int index) {

		if (spielerAnzahlView[index].getAnzahlSpielerTextField().getText()
				.length() > 0) {
			spielerAnzahl[index] = this.mainControl.getSpielerAnzahlControl()
					.getSpielerAnzahl(index);
			spielerEingabeView[index] = new SpielerEingabeView(
					spielerAnzahl[index]);
			okButton[index] = spielerEingabeView[index].getOkButton();
			okButton[index].addActionListener(this);
			cancelButton[index] = spielerEingabeView[index].getCancelButton();
			cancelButton[index].addActionListener(this);
			tabAnzeigeView.setComponentAt(index, spielerEingabeView[index]);
			suchAnzeige(index);
			hauptPanel.updateUI();
		}
	}

	@SuppressWarnings("unchecked")
	private void suchAnzeige(int index) {
		for (int i = 0; i < spielerAnzahl[index]; i++) {

			Spieler temp = null;
			ListIterator<Spieler> li = alleSpieler.listIterator();
			String labels = "";
			while (li.hasNext()) {
				temp = li.next();
				labels = temp.getName();
				spielerEingabeView[index].getSpielerSuche()[i].addItem(labels);
			}
			spielerEingabeView[index].getSpielerSuche()[i]
					.addActionListener(this);
			spielerEingabeView[index].getSpielerTextfield()[i]
					.addKeyListener(this);
		}
	}
}
