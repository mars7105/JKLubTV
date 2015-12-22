package de.turnierverwaltung.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private int frage;

	public DewisDialogControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
		frage = -1;

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
			dialog.getUpdateButton().setEnabled(true);
		} else {
			dialog.getUpdateButton().setEnabled(false);
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
			dialog.getUpdateButton().addActionListener(this);
			dialog.getUpdateButton().setEnabled(false);
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
//			mainControl.getHauptPanel().remove(mainControl.getSpielerLadenControl().getSpielerLadenView());
//			mainControl.getSpielerLadenControl().makePanel();
		}
		if (arg0.getSource() == dialog.getOkButton()) {
			int[] indices = spielerDewisView.getList().getSelectedIndices();
			mainControl.setEnabled(true);

			if (players != null) {
				for (int i = 0; i < indices.length; i++) {
					Spieler neuerSpieler = new Spieler();
					neuerSpieler = players.get(indices[i]);
					Boolean findPlayer = searchSpieler(neuerSpieler);
					if (findPlayer == false) {
						SpielerTableControl stc = new SpielerTableControl(mainControl);
						neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
						mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
					}
				}
			}
			dialog.closeWindow();
			mainControl.getSpielerLadenControl().updateSpielerListe();
		}
		if (arg0.getSource() == dialog.getUpdateButton()) {
			frage = 0;
			for (Spieler player : players) {
				searchSpieler(player);

			}
			frage = -1;
			dialog.closeWindow();
			mainControl.setEnabled(true);
			mainControl.getSpielerLadenControl().updateSpielerListe();
		}
	}

	private Boolean searchSpieler(Spieler neuerSpieler) {
		ArrayList<Spieler> spieler = mainControl.getSpielerLadenControl().getSpieler();

		for (Spieler player : spieler) {
			if (player.getName().compareTo(neuerSpieler.getName()) == 0) {
				if (player.getDWZ() != neuerSpieler.getDWZ()) {
					if (frage != 0) {
						frage = abfrage(neuerSpieler);
					}
					if (frage == 0) {
						SpielerTableControl stc = new SpielerTableControl(mainControl);
						neuerSpieler.setSpielerId(player.getSpielerId());
						stc.updateOneSpieler(neuerSpieler);
					}
				} else {
					if (frage != 0) {
						JOptionPane.showMessageDialog(null,
								"Spieler " + neuerSpieler.getName() + " ist schon vorhanden.");
					}
				}
				return true;
			}
		}
		return false;

	}

	private int abfrage(Spieler spieler) {
		int abfrage = 0;
		String hinweisText = spieler.getName() + " ist bereits vorhanden. Möchten Sie die neue DWZ übernehmen?";

		// Custom button text
		Object[] options = { "Ja, für alle Spieler", "Nein" };
		abfrage = JOptionPane.showOptionDialog(null, hinweisText, "Meldung", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE, null, options, options[1]);

		return abfrage;
	}
}
