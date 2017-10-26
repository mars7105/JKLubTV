package de.turnierverwaltung.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
		try {
			elospieler = rtf.readFile(mainControl.getPropertiesControl().getPathToPlayersELO());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		;
		DSBDWZClub verein = null;
		ArrayList<Player> spieler = null;
		SQLPlayerControl spielerTableControl = new SQLPlayerControl(this.mainControl);
		ArrayList<Player> spielerliste = null;
		try {
			spielerliste = spielerTableControl.getAllSpielerOrderByZPS();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		createAndShowGUI(spielerliste.size());
		for (Player player : spielerliste) {
			if (verein != null) {
				if (verein.getZps().equals(player.getDwzData().getCsvZPS()) == false) {
					verein = new DSBDWZClub(player.getDwzData().getCsvZPS());
					spieler = verein.getSpieler();
				}
			} else {
				verein = new DSBDWZClub(player.getDwzData().getCsvZPS());
				spieler = verein.getSpieler();
			}
			if (elospieler != null) {
				for (ELOPlayer eloplayer : elospieler) {

					if (eloplayer.getEloData().getFideid() == player.getDwzData().getCsvFIDE_ID()) {
						player.setEloData(eloplayer.getEloData());
						try {
							spielerTableControl.updateOneSpieler(player);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
								player.setDwz(temp.getDWZ());
								player.getDwzData().setCsvIndex(temp.getDwzData().getCsvIndex());

								try {
									spielerTableControl.updateOneSpieler(player);
									// System.out.println(player.getName());
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					} catch (NumberFormatException e) {

					}

				}
			}

			ladebalkenView.iterate();
		}
	}
}
