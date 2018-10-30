package de.turnierverwaltung.control.ratingdialog;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.turnierverwaltung.control.ExceptionHandler;
import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.model.Player;

/**
 *
 * @author mars
 *
 */
public class ActionListenerPlayerSearchControl implements ListSelectionListener, ActionListener {
	private final MainControl mainControl;
	private final DSBDWZControl dewisDialogControl;
	private final ImageIcon insertIcon3 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user.png")));
	private final ImageIcon insertIcon1 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user-offline.png")));
	private final ImageIcon insertIcon2 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-ok-3.png")));
	private final ArrayList<Integer> indices;
	private ELOControl eloControl;

	/**
	 *
	 * @param mainControl
	 * @param dewisDialogControl
	 */
	public ActionListenerPlayerSearchControl(final MainControl mainControl, final DSBDWZControl dewisDialogControl) {
		super();
		this.mainControl = mainControl;
		this.dewisDialogControl = dewisDialogControl;

		indices = new ArrayList<Integer>();
	}

	// public ActionListenerPlayerSearchControl(final MainControl mainControl2,
	// final ELOControl eloControl) {
	// super();
	// mainControl = mainControl2;
	// this.eloControl = eloControl;
	// indices = new ArrayList<Integer>();
	// }

	@Override
	public void actionPerformed(final ActionEvent arg0) {
		if (eloControl == null) {
			if (arg0.getSource().equals(dewisDialogControl.getDialog().getPlayerSearchView().getCancelButton())) {
				dewisDialogControl.getDialog().closeWindow();
			}
			if (arg0.getSource().equals(dewisDialogControl.getDialog().getPlayerSearchView().getOkButton())) {
				try {
					final ArrayList<Player> spieler = dewisDialogControl.getSearchplayerlist();
					if (spieler != null) {

						final ListIterator<Integer> lit = indices.listIterator();

						while (lit.hasNext()) {
							final int temp = lit.next();
							final Player neuerSpieler = spieler.get(temp);
							if (playerExist(neuerSpieler) == false) {
								final SQLPlayerControl stc = new SQLPlayerControl(mainControl);
								neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
								mainControl.getPlayerListControl().getSpieler().add(neuerSpieler);
								dewisDialogControl.getSpielerSearchPanelList().getListModel().getElementAt(temp)
										.setIcon(insertIcon3);

							}
							dewisDialogControl.getSpielerSearchPanelList().getList().setSelectedIndex(temp);

						}

					}

					mainControl.getPlayerListControl().updateSpielerListe();
					dewisDialogControl.makePlayerSearchSelectedList();
				} catch (final SQLException e) {
					final ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}
			}
		} else {
			if (arg0.getSource().equals(eloControl.getDialog().getPlayerSearchView().getCancelButton())) {
				eloControl.getDialog().closeWindow();
			}
			if (arg0.getSource().equals(eloControl.getDialog().getPlayerSearchView().getOkButton())) {
				try {
					final ArrayList<Player> spieler = eloControl.getSearchplayerlist();
					if (spieler != null) {

						final ListIterator<Integer> lit = indices.listIterator();

						while (lit.hasNext()) {
							final int temp = lit.next();
							final Player neuerSpieler = spieler.get(temp);
							if (playerExist(neuerSpieler) == false) {
								final SQLPlayerControl stc = new SQLPlayerControl(mainControl);
								neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
								mainControl.getPlayerListControl().getSpieler().add(neuerSpieler);
								eloControl.getSpielerSearchPanelList().getListModel().getElementAt(temp)
										.setIcon(insertIcon3);

							}
							eloControl.getSpielerSearchPanelList().getList().setSelectedIndex(temp);

						}

					}

					mainControl.getPlayerListControl().updateSpielerListe();
				} catch (final SQLException e) {
					final ExceptionHandler eh = new ExceptionHandler(mainControl);
					eh.fileSQLError(e.getMessage());
				}
			}
		}
	}

	private boolean playerExist(final Player neuerSpieler) {
		final SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);
		Boolean playerExist = false;
		try {
			playerExist = spielerTableControl.playerExist(neuerSpieler);

		} catch (final SQLException e) {
			final ExceptionHandler eh = new ExceptionHandler(mainControl);
			eh.fileSQLError(e.getMessage());
		}

		return playerExist;
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			if (eloControl == null) {
				final int index = dewisDialogControl.getSpielerSearchPanelList().getList().getSelectedIndex();
				final ArrayList<Player> spieler = dewisDialogControl.getSearchplayerlist();
				if (index == -1) {

				} else {

					final Player neuerSpieler = spieler.get(index);
					final Boolean savedPlayer = playerExist(neuerSpieler);
					final ListIterator<Integer> lit = indices.listIterator();
					int counter = 0;
					Boolean notfound = false;
					int nf = 0;

					while (lit.hasNext()) {
						final int temp = lit.next();

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
				final int index = eloControl.getSpielerSearchPanelList().getList().getSelectedIndex();
				final ArrayList<Player> spieler = eloControl.getSearchplayerlist();
				if (index == -1) {

				} else {

					final Player neuerSpieler = spieler.get(index);
					final Boolean savedPlayer = playerExist(neuerSpieler);
					final ListIterator<Integer> lit = indices.listIterator();
					int counter = 0;
					Boolean notfound = false;
					int nf = 0;

					while (lit.hasNext()) {
						final int temp = lit.next();

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

}
