package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.turnierverwaltung.model.Player;

/**
 * 
 * @author mars
 *
 */
public class DSBDWZActionListenerControl implements ListSelectionListener, ActionListener {
	private MainControl mainControl;
	private DSBDWZControl dewisDialogControl;

	/**
	 * 
	 * @param mainControl
	 * @param dewisDialogControl
	 */
	public DSBDWZActionListenerControl(MainControl mainControl, DSBDWZControl dewisDialogControl) {
		super();
		this.mainControl = mainControl;
		this.dewisDialogControl = dewisDialogControl;

	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			int index = dewisDialogControl.getSpielerDewisView().getList().getSelectedIndex();
			if (index == -1) {
				// No selection, disable fire button.
				dewisDialogControl.getDialog().getOkButton().setEnabled(false);

			} else {
				// dewisDialogControl.getSpielerDewisView().getList().get
				// Selection, enable the fire button.

				dewisDialogControl.getDialog().getOkButton().setEnabled(true);

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == dewisDialogControl.getDialog().getVereinsAuswahlOkButton()) {
			if (dewisDialogControl.getDialog().getVereinsAuswahl().getItemCount() > 0) {

				int index = dewisDialogControl.getDialog().getVereinsAuswahl().getSelectedIndex();
				String items[] = dewisDialogControl.getZpsItems().get(index);
				String zps = items[0];
				dewisDialogControl.makeDWZListe(zps);
			}

		}
		if (arg0.getSource() == dewisDialogControl.getDialog().getVereinsSucheButton()) {
			if (dewisDialogControl.getDialog().getVereinsName().getText().length() > 0) {
				dewisDialogControl.makeVereinsListe();
			} else if (dewisDialogControl.getDialog().getVereinsSuche().getText().length() > 0) {
				String zps = dewisDialogControl.getDialog().getVereinsSuche().getText();
				dewisDialogControl.makeDWZListe(zps);
			}

		}
		if (arg0.getSource() == dewisDialogControl.getDialog().getCancelButton()) {
			dewisDialogControl.getDialog().closeWindow();
		}
		if (arg0.getSource() == dewisDialogControl.getDialog().getOkButton()) {
			try {
				int[] indices = dewisDialogControl.getSpielerDewisView().getList().getSelectedIndices();

				if (dewisDialogControl.getPlayers() != null) {
					for (int i = 0; i < indices.length; i++) {
						Player neuerSpieler = new Player();
						neuerSpieler = dewisDialogControl.getPlayers().get(indices[i]);
						dewisDialogControl.getSpielerDewisView().getList().setSelectedIndex(indices[i]);
						Boolean findPlayer = dewisDialogControl.searchSpieler(neuerSpieler, false);
						if (findPlayer == false) {
							SQLPlayerControl stc = new SQLPlayerControl(mainControl);
							neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
							mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
						}
					}
				}

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
		if (arg0.getSource() == dewisDialogControl.getDialog().getUpdateButton()) {
			try {
				@SuppressWarnings("unused")
				Boolean findPlayer = true;
				for (Player player : dewisDialogControl.getPlayers()) {
					findPlayer = dewisDialogControl.searchSpieler(player, true);

				}

				dewisDialogControl.getDialog().closeWindow();
				// mainControl.setEnabled(true);

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}
	}

}
