package de.turnierverwaltung.control;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;
import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import de.turnierverwaltung.model.CSVVereine;
import de.turnierverwaltung.model.Player;

/**
 * 
 * @author mars
 *
 */
public class DSBDWZActionListenerControl implements ListSelectionListener, ActionListener {
	private MainControl mainControl;
	private DSBDWZControl dewisDialogControl;
	private ImageIcon insertIcon3 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user.png")));
	private ImageIcon insertIcon1 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user-offline.png")));
	private ImageIcon insertIcon2 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-ok-3.png")));
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
			ArrayList<Player> spieler = dewisDialogControl.getPlayers();
			if (index > -1) {

				Player neuerSpieler = spieler.get(index);
				Boolean savedPlayer = playerExist(neuerSpieler);
				ListIterator<Integer> lit = indices.listIterator();
				int counter = 0;
				Boolean notfound = false;
				int nf = 0;
				if (savedPlayer == false) {
					while (lit.hasNext()) {
						int temp = lit.next();

						if (temp == index) {

							notfound = true;
							nf = counter;
							break;

						}
						counter++;
					}
					if (notfound == true) {
						indices.remove(nf);

						dewisDialogControl.getSpielerDewisView().getList().getSelectedValue().setIcon(insertIcon1);
					} else {
						indices.add(dewisDialogControl.getSpielerDewisView().getList().getSelectedIndex());

						dewisDialogControl.getSpielerDewisView().getList().getSelectedValue().setIcon(insertIcon2);
					}
					if (indices.size() > 0) {
						dewisDialogControl.getDialog().getOkButton().setEnabled(true);

					} else {
						dewisDialogControl.getDialog().getOkButton().setEnabled(false);
					}
				}

				dewisDialogControl.getSpielerDewisView().getList().clearSelection();
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(dewisDialogControl.getDialog().getVereinsAuswahlOkButton())) {
			if (dewisDialogControl.getDialog().getVereinsAuswahl().getItemCount() > 0) {
				ArrayList<CSVVereine> items = dewisDialogControl.getZpsItems();
				int index = dewisDialogControl.getDialog().getVereinsAuswahl().getSelectedIndex();
				String zps = items.get(index).getCsvZPS();

				dewisDialogControl.makeDWZListe(zps);

			}

		}

		if (arg0.getSource().equals(dewisDialogControl.getDialog().getVereinsSucheButton())) {
			String zps = dewisDialogControl.getDialog().getVereinsSuche().getText();

			dewisDialogControl.makeDWZListe(zps);

		}
		if (arg0.getSource().equals(dewisDialogControl.getDialog().getCancelButton())) {
			dewisDialogControl.getDialog().closeWindow();
		}
		if (arg0.getSource().equals(dewisDialogControl.getDialog().getOkButton())) {
			try {
				ArrayList<Player> spieler = dewisDialogControl.getPlayers();
				if (spieler != null) {

					ListIterator<Integer> lit = indices.listIterator();

					while (lit.hasNext()) {
						int temp = lit.next();
						Player neuerSpieler = spieler.get(temp);
						if (playerExist(neuerSpieler) == false) {
							SQLPlayerControl stc = new SQLPlayerControl(mainControl);
							neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
							mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
							dewisDialogControl.getSpielerDewisView().getListModel().getElementAt(temp)
									.setIcon(insertIcon3);

						}
						dewisDialogControl.getSpielerDewisView().updateUI();

					}

				}

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				mainControl.fileSQLError();
			}
		}

	}

	private boolean playerExist(Player neuerSpieler) {
		SQLPlayerControl spielerTableControl = new SQLPlayerControl(this.mainControl);
		Boolean playerExist = false;
		try {
			playerExist = spielerTableControl.playerExist(neuerSpieler);

		} catch (SQLException e) {
			mainControl.fileSQLError();
		}

		return playerExist;
	}

	public void makeVereinsListe() throws ArrayIndexOutOfBoundsException, IOException {
		if (dewisDialogControl.getDialog().getVereinsName().isEnabled()) {
			String zps = mainControl.getPropertiesControl().getZPS();
			dewisDialogControl.makeVereinsListe(zps);
		} else if (dewisDialogControl.getDialog().getVereinsSuche().getText().length() > 0) {
			String zps = dewisDialogControl.getDialog().getVereinsSuche().getText();
			dewisDialogControl.makeDWZListe(zps);
		}
	}
}
