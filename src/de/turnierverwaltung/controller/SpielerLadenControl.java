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
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.JPanel;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.view.SpielerEditierenView;
import de.turnierverwaltung.view.SpielerHinzufuegenView;
import de.turnierverwaltung.view.SpielerLadenView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class SpielerLadenControl implements ActionListener {
	private MainControl mainControl;
	private TabAnzeigeView tabbedPaneView;
	private JPanel hauptPanel;
	private int spielerAnzahl;
	private SpielerLadenView spielerLadenView;
	private ArrayList<Spieler> spieler;
	private SpielerTableControl spielerTableControl;
	private SpielerEditierenView spielerEditierenView;
	private SpielerHinzufuegenView spielerHinzufuegenView;
	private DewisDialogControl dewisDialogControl;
	private int spielerIndex;

	public SpielerLadenControl(MainControl mainControl) {
		this.mainControl = mainControl;
		dewisDialogControl = new DewisDialogControl(this.mainControl);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == spielerLadenView.getSpielerImport()) {
			SpielerTableImportController spielerImport = new SpielerTableImportController();
			spielerImport.importSpielerTable();
			makePanel();
		}
		if (arg0.getSource() == spielerLadenView.getSpielerExport()) {
			SpielerTableExportController spielerExport = new SpielerTableExportController(this.mainControl);
			spielerExport.exportSpielerTable();
		}
		if (arg0.getSource() == spielerLadenView.getSpielerAddButton()) {
			spielerHinzufuegenView = new SpielerHinzufuegenView();

			spielerHinzufuegenView.getOkButton().addActionListener(this);
			spielerHinzufuegenView.getCancelButton().addActionListener(this);
			mainControl.setEnabled(false);
		}
		if (arg0.getSource() == spielerLadenView.getSpielerDEWISSearchButton()) {
			dewisDialogControl.makeDialog();
		}

		if (spielerHinzufuegenView != null) {
			if (arg0.getSource() == spielerHinzufuegenView.getOkButton()) {
				String name = spielerHinzufuegenView.getTextFieldName().getText();
				String kuerzel = spielerHinzufuegenView.getTextFieldKuerzel().getText();
				String dwz = spielerHinzufuegenView.getTextFieldDwz().getText();
				int age = spielerHinzufuegenView.getTextComboBoxAge().getSelectedIndex();
				Spieler neuerSpieler = new Spieler();
				neuerSpieler.setName(name);
				neuerSpieler.setKuerzel(kuerzel);
				neuerSpieler.setDwz(dwz);
				neuerSpieler.setAge(age);
				SpielerTableControl stc = new SpielerTableControl(mainControl);
				neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
				spieler.add(neuerSpieler);
				spielerHinzufuegenView.getTextFieldName().setEditable(false);
				spielerHinzufuegenView.getTextFieldKuerzel().setEditable(false);
				spielerHinzufuegenView.getTextFieldDwz().setEditable(false);
				spielerHinzufuegenView.getTextComboBoxAge().setEnabled(false);
				spielerHinzufuegenView.spielerPanel();

			}

			if (arg0.getSource() == spielerHinzufuegenView.getCancelButton()) {
				mainControl.setEnabled(true);
				spielerHinzufuegenView.closeWindow();
				makePanel();
			}
		}
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
				spielerEditierenView.closeWindow();
				makePanel();

			}
			if (arg0.getSource() == spielerEditierenView.getCancelButton()) {
				mainControl.setEnabled(true);
				spielerEditierenView.closeWindow();

			}
			if (arg0.getSource() == spielerEditierenView.getAddSpielerButton()) {

			}
		}
		for (int i = 0; i < spielerAnzahl; i++) {
			if (arg0.getSource() == spielerLadenView.getSpielerBearbeitenButton()[i]) {
				spielerIndex = i;
				spielerEditierenView = new SpielerEditierenView(spieler.get(i));
				spielerEditierenView.getOkButton().addActionListener(this);
				spielerEditierenView.getCancelButton().addActionListener(this);
				mainControl.setEnabled(false);
			}
			if (arg0.getSource() == spielerLadenView.getSpielerLoeschenButton()[i]) {
				SpielerTableControl stC = new SpielerTableControl(mainControl);
				stC.loescheSpieler(spieler.get(i));
				makePanel();
			}

		}

	}

	public void makePanel() {
		// this.mainControl.getTurnier();

		this.mainControl.setTabAnzeigeControl(new TabAnzeigeControl(this.mainControl));
		this.mainControl.setTabAnzeigeView(new TabAnzeigeView(mainControl));
		tabbedPaneView = this.mainControl.getTabAnzeigeView();
		int windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width - 275;
		int windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height - 275;

		tabbedPaneView.setMinimumSize(new Dimension(800, 600));
		tabbedPaneView.setMaximumSize(new Dimension(windowWidth, windowHeight));

		hauptPanel = this.mainControl.getHauptPanel();
		updateSpielerListe();
		hauptPanel.removeAll();
		mainControl.getNaviController().makeNaviPanel();
		hauptPanel.add(spielerLadenView);
		hauptPanel.updateUI();

	}

	public void neuerSpieler() {
		makePanel();
		spielerHinzufuegenView = new SpielerHinzufuegenView();

		spielerHinzufuegenView.getOkButton().addActionListener(this);
		spielerHinzufuegenView.getCancelButton().addActionListener(this);
		mainControl.setEnabled(false);
	}

	private void updateSpielerListe() {
		spielerTableControl = new SpielerTableControl(this.mainControl);
		spieler = spielerTableControl.getAllSpieler();
		spielerLadenView = new SpielerLadenView(spieler.size());
		spielerAnzahl = spieler.size();
		int index = 0;
		ListIterator<Spieler> li = spieler.listIterator();
		while (li.hasNext()) {

			spielerLadenView.makeSpielerZeile(li.next());
			spielerLadenView.getSpielerBearbeitenButton()[index].addActionListener(this);
			spielerLadenView.getSpielerLoeschenButton()[index].addActionListener(this);
			index++;
		}
		spielerLadenView.getSpielerAddButton().addActionListener(this);
		spielerLadenView.getSpielerExport().addActionListener(this);
		spielerLadenView.getSpielerImport().addActionListener(this);
		spielerLadenView.getSpielerDEWISSearchButton().addActionListener(this);

	}

	public ArrayList<Spieler> getSpieler() {
		return spieler;
	}

	public void setSpieler(ArrayList<Spieler> spieler) {
		this.spieler = spieler;
	}

}
