package de.turnierverwaltung.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.turnierverwaltung.model.DSBDWZClub;
import de.turnierverwaltung.model.ELOPlayer;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.ReadTXTFile;
import de.turnierverwaltung.view.ProgressBarDWZUpdateView;

public class UpdateRatingsControl {
	private ProgressBarDWZUpdateView ladebalkenView;
	private MainControl mainControl;

	public UpdateRatingsControl(MainControl mainControl) {
		super();
		this.mainControl = mainControl;
	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private void createAndShowGUI(int anzahl) {

		ladebalkenView = new ProgressBarDWZUpdateView(anzahl);

		ladebalkenView.pack();

		ladebalkenView.setVisible(true);
	}

	public void updateSpieler() {
		ReadTXTFile rtf = new ReadTXTFile();
		ArrayList<ELOPlayer> elospieler = null;

		elospieler = rtf.readFile(mainControl.getPropertiesControl().getPathToPlayersELO());

		DSBDWZClub verein = null;
		ArrayList<Player> spieler = null;
		SQLPlayerControl spielerTableControl = new SQLPlayerControl(this.mainControl);
		ArrayList<Player> spielerliste = null;
		try {
			spielerliste = spielerTableControl.getAllSpielerOrderByZPS();
			// Sorting
			Collections.sort(spielerliste, new Comparator<Player>() {
				@Override
				public int compare(Player player2, Player player1) {

					return player1.getDwzData().getCsvZPS().compareTo(player2.getDwzData().getCsvZPS());
				}
			});
			createAndShowGUI(spielerliste.size());
			for (Player player : spielerliste) {
				String zps = player.getDwzData().getCsvZPS();
				if (verein != null) {
					if (verein.getZps().equals(zps) == false) {
						verein = new DSBDWZClub(zps);
						spieler = verein.getSpieler();
					}
				} else {
					verein = new DSBDWZClub(zps);
					spieler = verein.getSpieler();
				}
				if (elospieler != null) {
					for (ELOPlayer eloplayer : elospieler) {

						if (eloplayer.getEloData().getFideid() == player.getDwzData().getCsvFIDE_ID()) {
							player.setEloData(eloplayer.getEloData());

							spielerTableControl.updateOneSpieler(player);

						}
					}
				}
				if (spieler != null) {
					for (Player temp : spieler) {
						try {
							String tempMGL = temp.getDwzData().getCsvMgl_Nr();
							String playerMGL = player.getDwzData().getCsvMgl_Nr();
							if (tempMGL.equals(playerMGL)) {
								if (player.getDWZ() != temp.getDWZ()
										|| player.getDwzData().getCsvIndex() != temp.getDwzData().getCsvIndex()) {
									// player.setDwzData(temp.getDwzData());
									player.setDwz(temp.getDWZ());
									player.getDwzData().setCsvIndex(temp.getDwzData().getCsvIndex());

									spielerTableControl.updateOneSpieler(player);
									// System.out.println(player.getName());

								}
							}
						} catch (NumberFormatException e) {

						}

					}
				}

				ladebalkenView.iterate();
			}
		} catch (SQLException e1) {
			spielerliste = null;
		}

	}
}
