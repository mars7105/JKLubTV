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
			int index = eloDialogControl.getSpielerSearchPanelList().getList().getSelectedIndex();
			ArrayList<Player> spieler = eloDialogControl.getSearchplayerlist();
			if (index > -1) {

				Player neuerSpieler = spieler.get(index);
				Boolean savedPlayer = playerExist(neuerSpieler);
				if (savedPlayer == false) {
					ListIterator<Integer> lit = indices.listIterator();
					int counter = 0;
					Boolean notfound = false;
					int nf = 0;

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

						eloDialogControl.getSpielerSearchPanelList().getList().getSelectedValue().setIcon(insertIcon1);
					} else {
						indices.add(eloDialogControl.getSpielerSearchPanelList().getList().getSelectedIndex());

						eloDialogControl.getSpielerSearchPanelList().getList().getSelectedValue().setIcon(insertIcon2);
					}
					if (indices.size() > 0) {
						eloDialogControl.getDialog().getPlayerSearchView().getOkButton().setEnabled(true);

					} else {
						eloDialogControl.getDialog().getPlayerSearchView().getOkButton().setEnabled(false);
					}
				}

				eloDialogControl.getSpielerSearchPanelList().getList().clearSelection();
			}

		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource().equals(eloDialogControl.getDialog().getPlayerSearchView().getCancelButton())) {
			eloDialogControl.getDialog().closeWindow();
		}
		if (arg0.getSource().equals(eloDialogControl.getDialog().getPlayerSearchView().getOkButton())) {
			try {
				ArrayList<Player> spieler = eloDialogControl.getSearchplayerlist();
				if (spieler != null) {

					ListIterator<Integer> lit = indices.listIterator();

					while (lit.hasNext()) {
						int temp = lit.next();
						Player neuerSpieler = spieler.get(temp);
						neuerSpieler.copyELODataToPlayer();
						if (playerExist(neuerSpieler) == false) {
							SQLPlayerControl stc = new SQLPlayerControl(mainControl);
							neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
							mainControl.getSpielerLadenControl().getSpieler().add(neuerSpieler);
							eloDialogControl.getSpielerSearchPanelList().getListModel().getElementAt(temp)
									.setIcon(insertIcon3);

						}
						eloDialogControl.getSpielerSearchPanelList().getList().updateUI();

					}

				}

				mainControl.getSpielerLadenControl().updateSpielerListe();
			} catch (SQLException e) {
				ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			}
		}

	}

	private boolean playerExist(Player neuerSpieler) {
		SQLPlayerControl spielerTableControl = new SQLPlayerControl(this.mainControl);
		Boolean playerExist = false;

		playerExist = spielerTableControl.playerFideExist(neuerSpieler.getEloData().getFideid());
		if (playerExist == false) {
			playerExist = spielerTableControl.playerFideExist(neuerSpieler.getDwzData().getCsvFIDE_ID());
		}

		return playerExist;
	}

}
