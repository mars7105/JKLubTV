package de.turnierverwaltung.control.ratingdialog;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.sqlite.SQLPlayerControl;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.rating.CSVPlayer;
import de.turnierverwaltung.model.rating.CSVPlayerList;
import de.turnierverwaltung.model.rating.ELOPlayer;
import de.turnierverwaltung.model.rating.ReadTXTFile;
import de.turnierverwaltung.model.rating.SQLitePlayerDWZList;
import de.turnierverwaltung.model.rating.SQLitePlayerELOList;
import de.turnierverwaltung.view.ratingdialog.ProgressBarDWZUpdateView;

public class UpdateRatingsControl {
	private ProgressBarDWZUpdateView ladebalkenView;
	private final MainControl mainControl;

	public UpdateRatingsControl(final MainControl mainControl) {
		super();
		this.mainControl = mainControl;

	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private void createAndShowGUI(final int anzahl) {

		ladebalkenView = new ProgressBarDWZUpdateView(anzahl);

		ladebalkenView.pack();

		ladebalkenView.setVisible(true);
	}

	/**
	 *
	 */
	public void updateSpieler() {
		// final int abfrage = 1;
		final String elofilename = mainControl.getPropertiesControl().getPathToPlayersELO();

		Boolean dbCheckedELO = false;

		Boolean dbCheckedDWZ = false;
		int positionEXT = elofilename.lastIndexOf('.');
		String elofileextender = "";
		if (positionEXT > 0) {
			elofileextender = elofilename.substring(positionEXT);
		}
		final SQLitePlayerDWZList dwzlist = new SQLitePlayerDWZList();
		final String dwzfilename = mainControl.getPropertiesControl().getPathToPlayersCSV();
		positionEXT = dwzfilename.lastIndexOf('.');
		String dwzfileextender = "";
		if (positionEXT > 0) {
			dwzfileextender = dwzfilename.substring(positionEXT);
			if (dwzfileextender.equals(".sqlite")) {
				dbCheckedDWZ = dwzlist.checkDatabase(dwzfilename);
			}
		}

		// if (abfrage >= 0 && abfrage <= 1) {

		ArrayList<ELOPlayer> elospieler = null;
		// final DSBDWZClub verein = null;
		// final ArrayList<Player> spieler = null;
		final SQLPlayerControl spielerTableControl = new SQLPlayerControl(mainControl);
		ArrayList<Player> spielerliste = null;
		SQLitePlayerELOList elolist = null;
		CSVPlayerList csvPlayerlist = null;

		if (elofileextender.equals(".sqlite")) {
			elolist = new SQLitePlayerELOList();
			dbCheckedELO = elolist.checkDatabase(elofilename);

		} else {
			final ReadTXTFile rtf = new ReadTXTFile();

			elospieler = rtf.readFile(elofilename);

		}

		try {
			spielerliste = spielerTableControl.getAllSpielerOrderByZPS();
		} catch (final SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// Sorting
		Collections.sort(spielerliste, new Comparator<Player>() {
			@Override
			public int compare(final Player player2, final Player player1) {

				return player1.getDwzData().getCsvZPS().compareTo(player2.getDwzData().getCsvZPS());
			}
		});
		createAndShowGUI(spielerliste.size());

		for (final Player player : spielerliste) {
			try {
				// Elo Update - standard_rating_list.txt
				if (elospieler != null) {
					for (final ELOPlayer eloplayer : elospieler) {
						if (player.getDwzData() != null) {
							if (eloplayer.getEloData().getFideid() == player.getDwzData().getCsvFIDE_ID()) {
								player.setEloData(eloplayer.getEloData());
								player.getEloData().setSpielerId(player.getSpielerId());
								spielerTableControl.updateOneSpieler(player);

							}
						}
						if (player.getEloData() != null) {
							if (eloplayer.getEloData().getFideid() == player.getEloData().getFideid()) {
								player.setEloData(eloplayer.getEloData());
								player.getEloData().setSpielerId(player.getSpielerId());
								spielerTableControl.updateOneSpieler(player);

							}
						}

					}

				}

				// Elo Update -standard_rating_list.sqlite
				if (dbCheckedELO == true && player.getEloData().getFideid() > 0) {
					final int dwzfideid = player.getDwzData().getCsvFIDE_ID();
					final int elofideid = player.getEloData().getFideid();
					final Player temp = new Player();
					if (dwzfideid > 0) {
						temp.setEloData(elolist.getPlayer(elofilename, dwzfideid));
					}
					if (elofideid > 0) {
						temp.setEloData(elolist.getPlayer(elofilename, elofideid));
					}
					player.setEloData(temp.getEloData());

					player.copyELODataToPlayer();
					player.getEloData().setSpielerId(player.getSpielerId());
					spielerTableControl.updateOneSpieler(player);

				}
				// Online DWZ Update
				// if (abfrage == 0) {
				// final String zps = player.getDwzData().getCsvZPS();
				// if (verein != null) {
				// if (verein.getZps().equals(zps) == false) {
				// verein = new DSBDWZClub(zps);
				// spieler = verein.getSpieler();
				// }
				// } else {
				// verein = new DSBDWZClub(zps);
				// spieler = verein.getSpieler();
				// }
				// if (spieler != null) {
				// for (final Player temp : spieler) {
				// try {
				// final String tempMGL = temp.getDwzData().getCsvMgl_Nr();
				// final String playerMGL = player.getDwzData().getCsvMgl_Nr();
				// if (tempMGL.equals(playerMGL)) {
				// if (player.getDWZ() != temp.getDWZ() || player.getDwzData()
				// .getCsvIndex() != temp.getDwzData().getCsvIndex()) {
				// player.setDwz(temp.getDWZ());
				// player.getDwzData().setCsvIndex(temp.getDwzData().getCsvIndex());
				//
				// spielerTableControl.updateOneSpieler(player);
				//
				// }
				// }
				// } catch (final NumberFormatException e) {
				//
				// }
				//
				// }
				// }
				// }
				// Offline DWZ Update
				// if (abfrage == 1) {
				if (player.getDwzData().getCsvZPS().length() > 0) {
					CSVPlayer csvPlayer;
					Player sqlitePlayer;
					if (dbCheckedDWZ == true) {
						// dwzlist = new SQLitePlayerDWZList();
						sqlitePlayer = dwzlist.getPlayer(dwzfilename, player.getDwzData().getCsvZPS(),
								player.getDwzData().getCsvMgl_Nr());
						if (sqlitePlayer.getDwzData().getCsvDWZ() != player.getDwzData().getCsvDWZ()) {
							player.setDwzData(sqlitePlayer.getDwzData());

							spielerTableControl.updateOneSpieler(player);

						}

					} else {
						// DWZ Update -.sqlite
						csvPlayerlist = new CSVPlayerList();

						try {
							csvPlayerlist.loadPlayerCSVList(mainControl.getPropertiesControl().getPathToPlayersCSV());
						} catch (ArrayIndexOutOfBoundsException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						csvPlayer = csvPlayerlist.getPlayer(player.getDwzData().getCsvZPS(),
								player.getDwzData().getCsvMgl_Nr());
						// DWZ Update - .csv

						if (csvPlayer.getPlayer().getDwzData().getCsvDWZ() != player.getDwzData().getCsvDWZ()) {
							player.setDwzData(csvPlayer.getPlayer().getDwzData());

							spielerTableControl.updateOneSpieler(player);

						}
					}

				}
				ladebalkenView.iterate();
			} catch (final NullPointerException e) {
				System.out.println(e.getMessage());

			} catch (final SQLException e1) {
				System.out.println(e1.getMessage());
			} finally {
				// SQLiteDAOFactory.setDB_PATH(oldPath);
			}
		}
		// }
	}
}
