package de.turnierverwaltung.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.view.SpielerEditierenView;
import de.turnierverwaltung.view.SpielerHinzufuegenView;
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
	private SpielerHinzufuegenView spielerHinzufuegenView;
	private DewisDialogControl dewisDialogControl;
	private int spielerIndex;

	public SpielerLadenControl(MainControl mainControl) {
		this.mainControl = mainControl;
		dewisDialogControl = new DewisDialogControl(this.mainControl);
		hauptPanel = this.mainControl.getHauptPanel();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
				updateSpielerListe();

				spielerHinzufuegenView.closeWindow();
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
				updateSpielerListe();
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
		

			if (arg0.getSource() == spielerLadenView.getSpielerImport()) {
				SpielerTableImportController spielerImport = new SpielerTableImportController();
				spielerImport.importSpielerTable();
				updateSpielerListe();
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
		}
		
	}


	public void neuerSpieler() {
		updateSpielerListe();
		spielerHinzufuegenView = new SpielerHinzufuegenView();

		spielerHinzufuegenView.getOkButton().addActionListener(this);
		spielerHinzufuegenView.getCancelButton().addActionListener(this);
		mainControl.setEnabled(false);
	}

	public void updateSpielerListe() {
		spielerTableControl = new SpielerTableControl(this.mainControl);
		spieler = new ArrayList<Spieler>();
		spieler = spielerTableControl.getAllSpieler();
		spielerAnzahl = spieler.size();
		if (spielerLadenView == null) {
			spielerLadenView = new SpielerLadenView(spielerAnzahl);
			hauptPanel.addTab("Spieler", spielerLadenView);
		} else {
			spielerLadenView.removeAll();
			spielerLadenView.init(spielerAnzahl);
		}

		int index = 0;
		for (Spieler player:spieler) {

			spielerLadenView.makeSpielerZeile(player, index);
			spielerLadenView.getSpielerBearbeitenButton()[index].addActionListener(this);
			spielerLadenView.getSpielerLoeschenButton()[index].addActionListener(this);
			index++;
		}
		spielerLadenView.getSpielerAddButton().addActionListener(this);
		spielerLadenView.getSpielerExport().addActionListener(this);
		spielerLadenView.getSpielerImport().addActionListener(this);
		spielerLadenView.getSpielerDEWISSearchButton().addActionListener(this);

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
