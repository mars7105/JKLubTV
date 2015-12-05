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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.turnierverwaltung.model.DewisClub;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.view.DEWISDialogView;
import de.turnierverwaltung.view.SpielerDewisView;
import de.turnierverwaltung.view.SpielerEditierenView;
import de.turnierverwaltung.view.SpielerHinzufuegenView;
import de.turnierverwaltung.view.SpielerLadenView;
import de.turnierverwaltung.view.TabAnzeigeView;

public class SpielerLadenControl implements ActionListener, ListSelectionListener {
	private MainControl mainControl;
	private TabAnzeigeView tabbedPaneView;
	private JPanel hauptPanel;
	private int spielerAnzahl;
	private SpielerLadenView spielerLadenView;
	private ArrayList<Spieler> spieler;
	private ArrayList<Spieler> players;
	private SpielerTableControl spielerTableControl;
	private SpielerEditierenView spielerEditierenView;
	private SpielerHinzufuegenView spielerHinzufuegenView;
	private DEWISDialogView dialog;
	private int spielerIndex;
	private SpielerDewisView spielerDewisView;

	public SpielerLadenControl(MainControl mainControl) {
		this.mainControl = mainControl;

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
			try {
				mainControl.setEnabled(false);
				dialog = new DEWISDialogView();
				dialog.getVereinsSucheButton().addActionListener(this);
				dialog.getOkButton().addActionListener(this);
				dialog.getCancelButton().addActionListener(this);
				dialog.getOkButton().setEnabled(false);
			} catch (Exception e) {
				mainControl.setEnabled(true);
			}
		}
		if (dialog != null) {
			if (arg0.getSource() == dialog.getVereinsSucheButton()) {

				String zps = dialog.getVereinsSuche().getText();
				DewisClub verein = new DewisClub(zps);
				players = new ArrayList<Spieler>();
				players = verein.getSpieler();
				if (players != null) {
					spielerDewisView = new SpielerDewisView(players.size());
					for (Spieler player : players) {
						spielerDewisView.makeSpielerZeile(player);
					}
					spielerDewisView.makeList();
					spielerDewisView.updateUI();
					spielerDewisView.getList().addListSelectionListener(this);
					dialog.setDsbPanel(spielerDewisView);
				} else {
					JLabel noItemLabel = new JLabel("keine Spieler gefunden.");
					JPanel noItemPanel = new JPanel();
					noItemPanel.add(noItemLabel);
					dialog.setDsbPanel(noItemPanel);

				}
				dialog.pack();
				dialog.getButtonPanel().updateUI();
				dialog.getContentPanel().updateUI();

			}
			if (arg0.getSource() == dialog.getCancelButton()) {
				mainControl.setEnabled(true);
				dialog.closeWindow();
				makePanel();
			}
			if (arg0.getSource() == dialog.getOkButton()) {
				int[] indices = spielerDewisView.getList().getSelectedIndices();
				mainControl.setEnabled(true);
				dialog.closeWindow();
				if (players != null) {
					for (int i = 0; i < indices.length; i++) {
						Spieler neuerSpieler = new Spieler();
						neuerSpieler = players.get(indices[i]);
						SpielerTableControl stc = new SpielerTableControl(mainControl);
						neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
						spieler.add(neuerSpieler);
					}
				}
				makePanel();
			}
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

}
