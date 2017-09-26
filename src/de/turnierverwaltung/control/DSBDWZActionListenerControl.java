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
				// dewisDialogControl.getDialog().getOkButton().setEnabled(false);

			} else {
				ArrayList<Player> spieler = dewisDialogControl.getPlayers();
				Player neuerSpieler = spieler.get(index);
				Boolean savedPlayer = playerExist(neuerSpieler);
				ListIterator<Integer> lit = indices.listIterator();
				int counter = 0;
				Boolean notfound = false;
				int nf = 0;
				ImageIcon insertIcon1 = new ImageIcon(
						Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user-offline.png")));
				ImageIcon insertIcon2 = new ImageIcon(
						Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-ok-3.png")));
				while (lit.hasNext()) {
					int temp = lit.next();

					if (temp == index && savedPlayer == false) {

						notfound = true;
						nf = counter;
						// break;

					}
					counter++;
				}
				if (notfound == true) {
					indices.remove(nf);

					dewisDialogControl.getSpielerDewisView().getList().getSelectedValue().setIcon(insertIcon1);
				}
				if (notfound == false && savedPlayer == false) {
					indices.add(dewisDialogControl.getSpielerDewisView().getList().getSelectedIndex());

					dewisDialogControl.getSpielerDewisView().getList().getSelectedValue().setIcon(insertIcon2);
				}
				dewisDialogControl.getDialog().getOkButton().setEnabled(true);
				dewisDialogControl.getSpielerDewisView().getList().clearSelection();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == dewisDialogControl.getDialog().getVereinsAuswahlOkButton()) {
			if (dewisDialogControl.getDialog().getVereinsAuswahl().getItemCount() > 0) {
				ArrayList<CSVVereine> items = dewisDialogControl.getZpsItems();
				int index = dewisDialogControl.getDialog().getVereinsAuswahl().getSelectedIndex();
				// String items = dewisDialogControl.getZpsItems().get(index);
				String zps = items.get(index).getCsvZPS();
				dewisDialogControl.makeDWZListe(zps);
			}

		}
		if (arg0.getSource() == dewisDialogControl.getDialog().getVereinsSucheButton()) {
			if (dewisDialogControl.getDialog().getVereinsName().isEnabled()) {
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

						}
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
		ArrayList<Player> spieler;
		try {
			spieler = spielerTableControl.getAllSpielerOrderByZPS();

			for (Player player : spieler) {
				try {
					int tmpzps = Integer.parseInt(player.getDsbZPSNumber());
					int tmpmgl = Integer.parseInt(player.getDsbMGLNumber());
					int playerzps = Integer.parseInt(neuerSpieler.getDsbZPSNumber());
					int playermgl = Integer.parseInt(neuerSpieler.getDsbMGLNumber());
					if (tmpzps == playerzps && tmpmgl == playermgl) {
						return true;

					}

				} catch (NumberFormatException e) {

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void makeVereinsListe() {
		if (dewisDialogControl.getDialog().getVereinsName().isEnabled()) {
			dewisDialogControl.makeVereinsListe();
		} else if (dewisDialogControl.getDialog().getVereinsSuche().getText().length() > 0) {
			String zps = dewisDialogControl.getDialog().getVereinsSuche().getText();
			dewisDialogControl.makeDWZListe(zps);
		}
	}
}
