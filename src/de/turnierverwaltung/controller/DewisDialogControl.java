package de.turnierverwaltung.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.turnierverwaltung.model.DewisClub;
import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.view.DEWISDialogView;
import de.turnierverwaltung.view.SpielerDewisView;

public class DewisDialogControl implements ListSelectionListener, ActionListener {
	private MainControl mainControl;
	private DEWISDialogView dialog;
	private SpielerDewisView spielerDewisView;
	private ArrayList<Spieler> players;

	public DewisDialogControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
	}

	public void makeDWZLste() {
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
			mainControl.getPropertiesControl().setProperties("zps", zps);
			mainControl.getPropertiesControl().writeProperties();
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

	public void makeDialog() {
		try {
			mainControl.setEnabled(false);

			dialog = new DEWISDialogView();
			dialog.getVereinsSucheButton().addActionListener(this);
			dialog.getOkButton().addActionListener(this);
			dialog.getCancelButton().addActionListener(this);
			dialog.getOkButton().setEnabled(false);
			String zps = mainControl.getPropertiesControl().getProperties("zps");
			if (zps.length() > 0) {
				dialog.getVereinsSuche().setText(zps);
				makeDWZLste();
			}
		} catch (Exception e) {
			mainControl.setEnabled(true);
		}
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == dialog.getVereinsSucheButton()) {
			makeDWZLste();

		}
		if (arg0.getSource() == dialog.getCancelButton()) {
			mainControl.setEnabled(true);
			dialog.closeWindow();
			mainControl.getSpielerLadenControl().makePanel();
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
					mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
				}
			}
			mainControl.getSpielerLadenControl().makePanel();
		}

	}
}
