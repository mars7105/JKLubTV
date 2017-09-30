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
public class ELOActionListenerControl implements ListSelectionListener, ActionListener {
	private MainControl mainControl;
	private ELOControl eloDialogControl;
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
	public ELOActionListenerControl(MainControl mainControl, ELOControl dewisDialogControl) {
		super();
		this.mainControl = mainControl;
		this.eloDialogControl = dewisDialogControl;
		indices = new ArrayList<Integer>();
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			int index = eloDialogControl.getSpielerDewisView().getList().getSelectedIndex();
			ArrayList<Player> spieler = eloDialogControl.getPlayers();
			if (index == -1) {

			} else {

				Player neuerSpieler = spieler.get(index);
				Boolean savedPlayer = playerExist(neuerSpieler);
				ListIterator<Integer> lit = indices.listIterator();
				int counter = 0;
				Boolean notfound = false;
				int nf = 0;

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

					eloDialogControl.getSpielerDewisView().getList().getSelectedValue().setIcon(insertIcon1);
				}
				if (notfound == false && savedPlayer == false) {
					indices.add(eloDialogControl.getSpielerDewisView().getList().getSelectedIndex());

					eloDialogControl.getSpielerDewisView().getList().getSelectedValue().setIcon(insertIcon2);
				}
				eloDialogControl.getDialog().getOkButton().setEnabled(true);
				eloDialogControl.getSpielerDewisView().getList().clearSelection();

			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(eloDialogControl.getDialog().getVereinsAuswahlOkButton())) {
			if (eloDialogControl.getDialog().getVereinsAuswahl().getItemCount() > 0) {
				ArrayList<CSVVereine> items = eloDialogControl.getZpsItems();
				int index = eloDialogControl.getDialog().getVereinsAuswahl().getSelectedIndex();
				String zps = items.get(index).getCsvZPS();
				eloDialogControl.makeDWZListe(zps);
			}

		}

		if (arg0.getSource().equals(eloDialogControl.getDialog().getVereinsSucheButton())) {
			String zps = eloDialogControl.getDialog().getVereinsSuche().getText();
			eloDialogControl.makeDWZListe(zps);
		}
		if (arg0.getSource().equals(eloDialogControl.getDialog().getCancelButton())) {
			eloDialogControl.getDialog().closeWindow();
		}
		if (arg0.getSource().equals(eloDialogControl.getDialog().getOkButton())) {
			try {
				ArrayList<Player> spieler = eloDialogControl.getPlayers();
				if (spieler != null) {

					ListIterator<Integer> lit = indices.listIterator();

					while (lit.hasNext()) {
						int temp = lit.next();
						Player neuerSpieler = spieler.get(temp);
						if (playerExist(neuerSpieler) == false) {
							SQLPlayerControl stc = new SQLPlayerControl(mainControl);
							neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
							mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
							eloDialogControl.getSpielerDewisView().getListModel().getElementAt(temp)
									.setIcon(insertIcon3);

						}
						eloDialogControl.getSpielerDewisView().getList().setSelectedIndex(temp);

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

	public void makeVereinsListe() {
		if (eloDialogControl.getDialog().getVereinsName().isEnabled()) {
			String zps = mainControl.getPropertiesControl().getZPS();
			eloDialogControl.makeVereinsListe(zps);
		} else if (eloDialogControl.getDialog().getVereinsSuche().getText().length() > 0) {
			String zps = eloDialogControl.getDialog().getVereinsSuche().getText();
			eloDialogControl.makeDWZListe(zps);
		}
	}
}
