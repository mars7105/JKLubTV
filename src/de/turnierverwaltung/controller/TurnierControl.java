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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.ZahlGroesserAlsN;
import de.turnierverwaltung.ZahlKleinerAlsN;
import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.view.TurnierView;

public class TurnierControl implements ActionListener {
	private static int pruefeObZahlKleinerEinsIst(int zahl) throws ZahlKleinerAlsN, ZahlGroesserAlsN {
		if (zahl <= 0) {
			throw new ZahlKleinerAlsN();
		}
		if (zahl > 15) {
			throw new ZahlGroesserAlsN();
		}
		return zahl;
	}

	private int selectIndex;
	private MainControl mainControl;
	private TurnierView turnierView;
	private JButton turnierOkButton;
	private JTabbedPane hauptPanel;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private int gruppenAnzahl;
	private ImageIcon turnierIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-remove-3.png")));

	private Turnier turnier;

	public TurnierControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.mainControl.setGruppenControl(null);
		this.mainControl.setSpielerAnzahlControl(null);
		this.mainControl.setSpielerEingabeControl(null);
		this.mainControl.setRundenEingabeFormularControl(null);
		this.mainControl.setTurnierTabelleControl(null);
		hauptPanel = this.mainControl.getHauptPanel();
		this.mainControl.setTurnierView(new TurnierView());
		this.turnierView = this.mainControl.getTurnierView();
		this.turnierView.setVisible(true);
		turnierOkButton = this.mainControl.getTurnierView().getOkButton();
		turnierOkButton.addActionListener(this);
		this.mainControl.setNeuesTurnier(true);
		if (mainControl.getTurnier() != null) {
			int selectedIndex = hauptPanel.getTabCount() - 1;

			if (hauptPanel.getTitleAt(selectedIndex).equals(mainControl.getTurnier().getTurnierName())) {
				hauptPanel.remove(selectedIndex);
			}
		}
		hauptPanel.addTab("Neues Turnier", turnierIcon, this.turnierView);
		selectIndex = hauptPanel.getTabCount() - 1;
		hauptPanel.setSelectedIndex(selectIndex);

//		mainControl.getMenueControl().setWarnHinweis(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == turnierOkButton) {
			turnierOkButton();

		}

	}

	public String getEndDatum() {
		return endDatum;
	}

	public int getGruppenAnzahl() {
		return gruppenAnzahl;
	}

	public String getStartDatum() {
		return startDatum;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public String getTurnierName() {
		return turnierName;
	}

	public JButton getTurnierOkButton() {
		return turnierOkButton;
	}

	private void makeTurnier() {
		turnier = new Turnier();
		turnier.setTurnierName(turnierName);
		turnier.setStartDatum(startDatum);
		turnier.setEndDatum(endDatum);
		turnier.setAnzahlGruppen(gruppenAnzahl);
		this.mainControl.setTurnier(turnier);
		hauptPanel.setTitleAt(selectIndex, turnier.getTurnierName());
	}

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public void setGruppenAnzahl(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public void setTurnier(Turnier turnier) {
		this.turnier = turnier;
	}

	public void setTurnierName(String turnierName) {
		this.turnierName = turnierName;
	}

	public void setTurnierOkButton(JButton turnierOkButton) {
		this.turnierOkButton = turnierOkButton;
	}

	private void turnierOkButton() {
		gruppenAnzahl = 0;
		turnierName = turnierView.getTurnierNameTextField().getText();
		startDatum = turnierView.getStartDatumTextField();
		endDatum = turnierView.getEndDatumTextField();

		try {

			gruppenAnzahl = pruefeObZahlKleinerEinsIst(new Integer(turnierView.getGruppenAnzahlTextField().getText()));

			if (turnierName.length() > 0 && startDatum.length() > 0 && endDatum.length() > 0 && gruppenAnzahl > 0) {
				makeTurnier();

				this.mainControl.setGruppenControl(new GruppenControl(this.mainControl, selectIndex));

			}

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Gruppenanzahl ist fehlerhaft!");
			turnierView.getGruppenAnzahlTextField().setText("");
			turnierView.getGruppenAnzahlTextField().grabFocus();
		} catch (ZahlKleinerAlsN e) {
			JOptionPane.showMessageDialog(null, "Zahl darf nicht kleiner als 1 sein!");
			turnierView.getGruppenAnzahlTextField().setText("");
			turnierView.getGruppenAnzahlTextField().grabFocus();
		} catch (ZahlGroesserAlsN e) {
			JOptionPane.showMessageDialog(null, "Zahl darf nicht größer als 15 sein!");
			turnierView.getGruppenAnzahlTextField().setText("");
			turnierView.getGruppenAnzahlTextField().grabFocus();
		}

	}
}
