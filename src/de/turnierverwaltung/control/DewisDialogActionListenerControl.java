package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.turnierverwaltung.model.Spieler;

public class DewisDialogActionListenerControl implements ListSelectionListener,
		ActionListener {
	private MainControl mainControl;
	private DewisDialogControl dewisDialogControl;

	public DewisDialogActionListenerControl(MainControl mainControl,
			DewisDialogControl dewisDialogControl) {
		super();
		this.mainControl = mainControl;
		this.dewisDialogControl = dewisDialogControl;

	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {

			if (dewisDialogControl.getSpielerDewisView().getList()
					.getSelectedIndex() == -1) {
				// No selection, disable fire button.
				dewisDialogControl.getDialog().getOkButton().setEnabled(false);

			} else {
				// Selection, enable the fire button.
				dewisDialogControl.getDialog().getOkButton().setEnabled(true);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == dewisDialogControl.getDialog()
				.getVereinsAuswahlOkButton()) {
			if (dewisDialogControl.getDialog().getVereinsAuswahl()
					.getItemCount() > 0) {

				int index = dewisDialogControl.getDialog().getVereinsAuswahl()
						.getSelectedIndex();
				String items[] = dewisDialogControl.getZpsItems().get(index);
				String zps = items[0];
				dewisDialogControl.makeDWZListe(zps);
			}

		}
		if (arg0.getSource() == dewisDialogControl.getDialog()
				.getVereinsSucheButton()) {
			if (dewisDialogControl.getDialog().getVereinsName().getText()
					.length() > 0) {
				dewisDialogControl.makeVereinsListe();
			} else if (dewisDialogControl.getDialog().getVereinsSuche()
					.getText().length() > 0) {
				String zps = dewisDialogControl.getDialog().getVereinsSuche()
						.getText();
				dewisDialogControl.makeDWZListe(zps);
			}

		}
		if (arg0.getSource() == dewisDialogControl.getDialog()
				.getCancelButton()) {
			dewisDialogControl.getDialog().closeWindow();
		}
		if (arg0.getSource() == dewisDialogControl.getDialog().getOkButton()) {
			int[] indices = dewisDialogControl.getSpielerDewisView().getList()
					.getSelectedIndices();

			if (dewisDialogControl.getPlayers() != null) {
				for (int i = 0; i < indices.length; i++) {
					Spieler neuerSpieler = new Spieler();
					neuerSpieler = dewisDialogControl.getPlayers().get(
							indices[i]);
					Boolean findPlayer = dewisDialogControl.searchSpieler(
							neuerSpieler, false);
					if (findPlayer == false) {
						SpielerTableControl stc = new SpielerTableControl(
								mainControl);
						neuerSpieler.setSpielerId(stc
								.insertOneSpieler(neuerSpieler));
						mainControl.getSpielerLadenControl().getSpieler()
								.add(neuerSpieler);
					}
				}
			}
			try {
				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
		if (arg0.getSource() == dewisDialogControl.getDialog()
				.getUpdateButton()) {

			@SuppressWarnings("unused")
			Boolean findPlayer = true;
			for (Spieler player : dewisDialogControl.getPlayers()) {
				findPlayer = dewisDialogControl.searchSpieler(player, true);

			}

			dewisDialogControl.getDialog().closeWindow();
//			mainControl.setEnabled(true);
			try {
				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
	}

}
