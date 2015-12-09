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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import de.turnierverwaltung.ZahlGroesserAlsN;
import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.model.Gruppe;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.view.SpielerAnzahlView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class SpielerAnzahlControl implements ActionListener {
	private static int pruefeObZahlKleinerDreiIst(int zahl) throws ZahlKleinerAlsN, ZahlGroesserAlsN {
		if (zahl < 3) {
			throw new ZahlKleinerAlsN();
		}
		if (zahl > 20) {
			throw new ZahlGroesserAlsN();
		}
		return zahl;
	}

	private int gruppenAnzahl;
	private int spielerAnzahl[];
	private String[] title;
	private JButton[] okButton;
	private SpielerEingabeControl spielerEingabeControl;
	private MainControl mainControl;
	private SpielerAnzahlView[] spielerAnzahlView;
	private JTextField[] spielerAnzahlTextfield;
	private JTabbedPane hauptPanel;
	private TabAnzeigeView tabbedPaneView;
	private Turnier turnier;
	private Gruppe[] gruppe;
	private int selectIndex;
	private Spieler[][] spieler;
	private ImageIcon gruppenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-calendar-month.png")));

	public SpielerAnzahlControl(MainControl mainControl, int selectIndex) {
		this.mainControl = mainControl;
		this.selectIndex = selectIndex;
		turnier = this.mainControl.getTurnier();
		gruppe = turnier.getGruppe();

		this.mainControl.setTabAnzeigeControl(new TabAnzeigeControl(this.mainControl));
		this.mainControl.setTabAnzeigeView(new TabAnzeigeView(mainControl));
		tabbedPaneView = this.mainControl.getTabAnzeigeView();

		hauptPanel = this.mainControl.getHauptPanel();
		gruppenAnzahl = this.mainControl.getTurnier().getAnzahlGruppen();
		this.spielerAnzahl = new int[gruppenAnzahl];
		title = mainControl.getGruppenView().getGruppenName();
		spielerAnzahlView = new SpielerAnzahlView[gruppenAnzahl];
		spielerAnzahlTextfield = new JTextField[gruppenAnzahl];
		okButton = new JButton[gruppenAnzahl];
		spieler = new Spieler[gruppenAnzahl][];
		for (int i = 0; i < gruppenAnzahl; i++) {
			spieler[i] = new Spieler[spielerAnzahl[i]];
			spielerAnzahlView[i] = new SpielerAnzahlView(title[i]);
			spielerAnzahlTextfield[i] = spielerAnzahlView[i].getAnzahlSpielerTextField();
			okButton[i] = spielerAnzahlView[i].getOkButton();
			okButton[i].addActionListener(this);
			tabbedPaneView.addTab(title[i], spielerAnzahlView[i]);
			gruppe[i].setSpieler(spieler[i]);
		}
		tabbedPaneView.updateUI();
		this.mainControl.getNaviController().makeNaviPanel();
		hauptPanel.remove(this.selectIndex);
		hauptPanel.add(this.tabbedPaneView, this.selectIndex);
		hauptPanel.setTitleAt(selectIndex, "Gruppen");
		hauptPanel.setIconAt(selectIndex, gruppenIcon);
		hauptPanel.setSelectedIndex(selectIndex);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (mainControl.getSpielerEingabeControl() == null) {
			spielerEingabeControl = new SpielerEingabeControl(mainControl,this.selectIndex);
			mainControl.setSpielerEingabeControl(spielerEingabeControl);
		}
		for (int i = 0; i < gruppenAnzahl; i++) {
			if (arg0.getSource() == okButton[i]) {
				spielerAnzahl[i] = getSpielerAnzahl(i);
				spielerEingabeControl.makeTabbedPane(i);
				gruppe[i].setSpielerAnzahl(spielerAnzahl[i]);

			}
		}
	}

	public int getSpielerAnzahl(int indexI) {

		try {
			if (spielerAnzahlTextfield[indexI].getText().length() > 0) {
				try {
					spielerAnzahl[indexI] = pruefeObZahlKleinerDreiIst(
							Integer.parseInt(spielerAnzahlTextfield[indexI].getText()));
				} catch (NumberFormatException e) {

					JOptionPane.showMessageDialog(null, "Zahl ist fehlerhaft!");
					spielerAnzahlTextfield[indexI].setText("");
					spielerAnzahlTextfield[indexI].grabFocus();
				} catch (ZahlKleinerAlsN e) {
					JOptionPane.showMessageDialog(null, "Zahl darf nicht kleiner als 3 sein!");
					spielerAnzahlTextfield[indexI].setText("");
					spielerAnzahlTextfield[indexI].grabFocus();
				} catch (ZahlGroesserAlsN e) {
					JOptionPane.showMessageDialog(null, "Zahl darf nicht größer als 20 sein!");
					spielerAnzahlTextfield[indexI].setText("");
					spielerAnzahlTextfield[indexI].grabFocus();
				}
			}
		} catch (NumberFormatException e) {

		}

		return spielerAnzahl[indexI];
	}

	public SpielerAnzahlView[] getSpielerAnzahlView() {
		return spielerAnzahlView;
	}

	public void makeNewTab(int indexI) {
		spieler[indexI] = new Spieler[spielerAnzahl[indexI]];
		spielerAnzahlView[indexI] = new SpielerAnzahlView(title[indexI]);
		spielerAnzahlTextfield[indexI] = spielerAnzahlView[indexI].getAnzahlSpielerTextField();
		okButton[indexI] = spielerAnzahlView[indexI].getOkButton();
		okButton[indexI].addActionListener(this);
		tabbedPaneView.setComponentAt(indexI, spielerAnzahlView[indexI]);
	}

	public void setSpielerAnzahl(int[] spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}

	public void setSpielerAnzahlView(SpielerAnzahlView[] spielerAnzahlView) {
		this.spielerAnzahlView = spielerAnzahlView;
	}
}
