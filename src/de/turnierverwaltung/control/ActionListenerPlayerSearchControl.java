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
public class ActionListenerPlayerSearchControl implements ListSelectionListener, ActionListener {
	private MainControl mainControl;
	private DSBDWZControl dewisDialogControl;
	private ImageIcon insertIcon3 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user.png")));
	private ImageIcon insertIcon1 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user-offline.png")));
	private ImageIcon insertIcon2 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-ok-3.png")));
	private ArrayList<Integer> indices;
	private ELOControl eloControl;

	/**
	 * 
	 * @param mainControl
	 * @param dewisDialogControl
	 */
	public ActionListenerPlayerSearchControl(MainControl mainControl, DSBDWZControl dewisDialogControl) {
		super();
		this.mainControl = mainControl;
		this.dewisDialogControl = dewisDialogControl;
		indices = new ArrayList<Integer>();
	}

	public ActionListenerPlayerSearchControl(MainControl mainControl2, ELOControl eloControl) {
		super();
		this.mainControl = mainControl2;
		this.eloControl = eloControl;
		indices = new ArrayList<Integer>();
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			if (eloControl == null) {
				int index = dewisDialogControl.getSpielerSearchPanelList().getList().getSelectedIndex();
				ArrayList<Player> spieler = dewisDialogControl.getSearchplayerlist();
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

						dewisDialogControl.getSpielerSearchPanelList().getList().getSelectedValue()
								.setIcon(insertIcon1);
					}
					if (notfound == false && savedPlayer == false) {
						indices.add(dewisDialogControl.getSpielerSearchPanelList().getList().getSelectedIndex());

						dewisDialogControl.getSpielerSearchPanelList().getList().getSelectedValue()
								.setIcon(insertIcon2);
					}
					dewisDialogControl.getDialog().getOkButton().setEnabled(true);
					dewisDialogControl.getSpielerSearchPanelList().getList().clearSelection();

				}

			} else {
				int index = eloControl.getSpielerSearchPanelList().getList().getSelectedIndex();
				ArrayList<Player> spieler = eloControl.getSearchplayerlist();
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

						eloControl.getSpielerSearchPanelList().getList().getSelectedValue().setIcon(insertIcon1);
					}
					if (notfound == false && savedPlayer == false) {
						indices.add(eloControl.getSpielerSearchPanelList().getList().getSelectedIndex());

						eloControl.getSpielerSearchPanelList().getList().getSelectedValue().setIcon(insertIcon2);
					}
					eloControl.getDialog().getPlayerSearchView().getOkButton().setEnabled(true);
					eloControl.getSpielerSearchPanelList().getList().clearSelection();

				}

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (eloControl == null) {
			if (arg0.getSource().equals(dewisDialogControl.getDialog().getPlayerSearchView().getCancelButton())) {
				dewisDialogControl.getDialog().closeWindow();
			}
			if (arg0.getSource().equals(dewisDialogControl.getDialog().getPlayerSearchView().getOkButton())) {
				try {
					ArrayList<Player> spieler = dewisDialogControl.getSearchplayerlist();
					if (spieler != null) {

						ListIterator<Integer> lit = indices.listIterator();

						while (lit.hasNext()) {
							int temp = lit.next();
							Player neuerSpieler = spieler.get(temp);
							if (playerExist(neuerSpieler) == false) {
								SQLPlayerControl stc = new SQLPlayerControl(mainControl);
								neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
								mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
								dewisDialogControl.getSpielerSearchPanelList().getListModel().getElementAt(temp)
										.setIcon(insertIcon3);

							}
							dewisDialogControl.getSpielerSearchPanelList().getList().setSelectedIndex(temp);

						}

					}

					mainControl.getSpielerLadenControl().updateSpielerListe();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}
			}
		} else {
			if (arg0.getSource().equals(eloControl.getDialog().getPlayerSearchView().getCancelButton())) {
				eloControl.getDialog().closeWindow();
			}
			if (arg0.getSource().equals(eloControl.getDialog().getPlayerSearchView().getOkButton())) {
				try {
					ArrayList<Player> spieler = eloControl.getSearchplayerlist();
					if (spieler != null) {

						ListIterator<Integer> lit = indices.listIterator();

						while (lit.hasNext()) {
							int temp = lit.next();
							Player neuerSpieler = spieler.get(temp);
							if (playerExist(neuerSpieler) == false) {
								SQLPlayerControl stc = new SQLPlayerControl(mainControl);
								neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
								mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
								eloControl.getSpielerSearchPanelList().getListModel().getElementAt(temp)
										.setIcon(insertIcon3);

							}
							eloControl.getSpielerSearchPanelList().getList().setSelectedIndex(temp);

						}

					}

					mainControl.getSpielerLadenControl().updateSpielerListe();
				} catch (SQLException e) {
					mainControl.fileSQLError();
				}
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

}
