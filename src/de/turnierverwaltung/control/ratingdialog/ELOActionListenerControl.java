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
import de.turnierverwaltung.model.rating.DWZData;
import de.turnierverwaltung.model.rating.SQLitePlayerDWZList;
import de.turnierverwaltung.view.ratingdialog.ELODialogView;

/**
 *
 * @author mars
 *
 */
public class ELOActionListenerControl implements ListSelectionListener, ActionListener {
	private final MainControl mainControl;
	private final ELOControl eloDialogControl;
	private final ImageIcon insertIcon3 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user.png")));
	private final ImageIcon insertIcon1 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/im-user-offline.png")));
	private final ImageIcon insertIcon2 = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-ok-3.png")));
	private final ArrayList<Integer> indices;
	private SQLitePlayerDWZList spdwzlist;
	private Boolean dbChecked;

	/**
	 *
	 * @param mainControl
	 * @param dewisDialogControl
	 */
	public ELOActionListenerControl(final MainControl mainControl, final ELOControl dewisDialogControl) {
		super();
		this.mainControl = mainControl;
		eloDialogControl = dewisDialogControl;
		indices = new ArrayList<Integer>();
		spdwzlist = new SQLitePlayerDWZList();
		final String pathToPlayersCSV = mainControl.getPropertiesControl().getPathToPlayersCSV();
		dbChecked = spdwzlist.checkDatabase(pathToPlayersCSV);

	}

	@Override
	public void actionPerformed(final ActionEvent arg0) {

		if (arg0.getSource().equals(eloDialogControl.getDialog().getPlayerSearchView().getCancelButton())) {
			ELODialogView dialog = eloDialogControl.getDialog();
			mainControl.getPropertiesControl().writeELODialogProperties(dialog.getBounds().x, dialog.getBounds().y,
					dialog.getBounds().width, dialog.getBounds().height);

			dialog.closeWindow();
//			if (mainControl.getNewTournamentPlayerIncludeControl() != null) {
//				HashMap<Integer, NewTournamentPlayerIncludeView> spielerEingabeView = mainControl
//						.getNewTournamentPlayerIncludeControl().getSpielerEingabeView();
//				int index = mainControl.getNewTournamentPlayerIncludeControl().getPlayerListView().getIndex();
//				int lineIndex=mainControl.getNewTournamentPlayerIncludeControl().getPlayerListView().getLineIndex();
//				
//				
//				mainControl.getNewTournamentPlayerIncludeControl().fillPlayerList(index);
//				for (PlayerLineView playerLineView : spielerEingabeView.get(index).getPlayerLineViews()) {
//					for (ActionListener act : playerLineView.getPlayerAddButton().getActionListeners()) {
//						if (((PlayerListActionPopup) act).getPlayerLineView().getLineIndex() == lineIndex) {
//							mainControl.getNewTournamentPlayerIncludeControl().getPlayerListView().dispose();
//							((PlayerListActionPopup) act).newPlayer();
//						}
//					}
//
//				}
//
//			}
		}
		if (arg0.getSource().equals(eloDialogControl.getDialog().getPlayerSearchView().getOkButton())) {
			try {
				final ArrayList<Player> spieler = eloDialogControl.getSearchplayerlist();
				if (spieler != null) {

					final ListIterator<Integer> lit = indices.listIterator();

					while (lit.hasNext()) {
						final int temp = lit.next();
						final Player neuerSpieler = spieler.get(temp);
						neuerSpieler.copyELODataToPlayer();
						if (playerExist(neuerSpieler) == false) {

							final String pathToPlayersCSV = mainControl.getPropertiesControl().getPathToPlayersCSV();
							if (dbChecked == true) {
								final ArrayList<DWZData> dwzDataList = spdwzlist.getPlayersByFideId(pathToPlayersCSV,
										neuerSpieler.getEloData().getFideid());
								if (dwzDataList != null) {
									if (dwzDataList.size() == 1) {
										neuerSpieler.setDwzData(dwzDataList.get(0));
									}
								}
							}
							final SQLPlayerControl stc = new SQLPlayerControl(mainControl);

							neuerSpieler.setSpielerId(stc.insertOneSpieler(neuerSpieler));
							mainControl.getPlayerListControl().getSpieler().add(neuerSpieler);
							eloDialogControl.getSpielerSearchPanelList().getListModel().getElementAt(temp)
									.setIcon(insertIcon3);

						}
						eloDialogControl.getSpielerSearchPanelList().getList().updateUI();

					}

				}

				mainControl.getPlayerListControl().updateSpielerListe();
			} catch (final SQLException e) {
				final ExceptionHandler eh = new ExceptionHandler(mainControl);
				eh.fileSQLError(e.getMessage());
			}
		}

	}

	private boolean playerExist(final Player neuerSpieler) {
		final SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);
		Boolean playerExist = false;

		try {
			playerExist = spielerTableControl.playerFideExist(neuerSpieler.getEloData().getFideid());

			if (playerExist == false) {
				playerExist = spielerTableControl.playerFideExist(neuerSpieler.getDwzData().getCsvFIDE_ID());
			}
		} catch (final SQLException e) {
			try {
				playerExist = spielerTableControl.playerFideExist(neuerSpieler.getDwzData().getCsvFIDE_ID());
			} catch (final SQLException e1) {
				playerExist = false;
			}
		}
		return playerExist;
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			final int index = eloDialogControl.getSpielerSearchPanelList().getList().getSelectedIndex();
			final ArrayList<Player> spieler = eloDialogControl.getSearchplayerlist();
			if (index > -1) {

				final Player neuerSpieler = spieler.get(index);
				final Boolean savedPlayer = playerExist(neuerSpieler);
				if (savedPlayer == false) {
					final ListIterator<Integer> lit = indices.listIterator();
					int counter = 0;
					Boolean notfound = false;
					int nf = 0;

					while (lit.hasNext()) {
						final int temp = lit.next();

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

}
