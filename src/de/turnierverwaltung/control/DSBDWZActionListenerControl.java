package de.turnierverwaltung.control;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.ImageIcon;
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

	private ArrayList<Integer> indices;

	/**
	 * 
	 * @param mainControl
	 * @param dewisDialogControl
	 */
	public DSBDWZActionListenerControl(MainControl mainControl, DSBDWZControl dewisDialogControl) {
		super();
		this.mainControl = mainControl;
		this.dewisDialogControl = dewisDialogControl;
		indices = new ArrayList<Integer>();
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			int index = dewisDialogControl.getSpielerDewisView().getList().getSelectedIndex();
			if (index == -1) {
				// No selection, disable fire button.
				dewisDialogControl.getDialog().getOkButton().setEnabled(false);

			} else {
				ListIterator<Integer> lit = indices.listIterator();
				int counter = 0;
				Boolean notfound = false;
				while (lit.hasNext()) {
					int temp = lit.next();
					if (temp == index) {
						indices.remove(counter);
						ImageIcon insertIcon = new ImageIcon(
								Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-add-2.png")));
						dewisDialogControl.getSpielerDewisView().getList().getSelectedValue().setIcon(insertIcon);
						notfound = true;
						break;
					}
					counter++;
				}
				if (notfound == false) {
					indices.add(dewisDialogControl.getSpielerDewisView().getList().getSelectedIndex());
					ImageIcon insertIcon = new ImageIcon(
							Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-ok-3.png")));
					dewisDialogControl.getSpielerDewisView().getList().getSelectedValue().setIcon(insertIcon);
				}
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

				if (dewisDialogControl.getPlayers() != null) {

					ListIterator<Integer> lit = indices.listIterator();

					while (lit.hasNext()) {
						int temp = lit.next();
						Player neuerSpieler = new Player();
						neuerSpieler = dewisDialogControl.getPlayers().get(temp);
//						dewisDialogControl.getSpielerDewisView().getList().setSelectedIndex(temp);
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
