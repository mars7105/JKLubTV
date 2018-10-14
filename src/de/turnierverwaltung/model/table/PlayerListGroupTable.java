package de.turnierverwaltung.model.table;

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;

public class PlayerListGroupTable {
	public Group group;
	private final Player[] players;
	private final Game[] games;
	private final String columnNames[];
	private final Object playerMatrix[][];
	private int partienAnzahl;
	private int rundenAnzahl;

	public PlayerListGroupTable(final Group group) {
		super();
		this.group = group;
		players = this.group.getSpieler();
		games = this.group.getPartien();
		partienAnzahl = this.group.getPartienAnzahl();
		rundenAnzahl = this.group.getRundenAnzahl();
		int spielerAnzahl = this.group.getSpielerAnzahl();
		int cols = 0;
		if (rundenAnzahl == 0) {

			if (spielerAnzahl % 2 == 0) {
				partienAnzahl = (spielerAnzahl / 2) * (spielerAnzahl - 1);
				rundenAnzahl = spielerAnzahl - 1;
			} else {
				partienAnzahl = (spielerAnzahl - 1) / 2;
				rundenAnzahl = spielerAnzahl;
			}
		} else {
			cols = partienAnzahl * 2 / rundenAnzahl;
		}
//		System.out.println(partienAnzahl + " " + rundenAnzahl + " " + this.group.getSpielerAnzahl());
		// cols = this.group.getSpielerAnzahl();
		playerMatrix = new String[cols][4];
		columnNames = new String[4];
		columnNames[0] = "Name";
		columnNames[1] = "DWZ";
		columnNames[2] = "ELO";
		columnNames[3] = "Löschen";
		int index = 0;
		for (final Player player : players) {

			if (player.getSpielerId() > TournamentConstants.SPIELFREI_ID) {

				playerMatrix[index][0] = player.getSurname() + ", " + player.getForename();

				playerMatrix[index][1] = player.getDwz();
				playerMatrix[index][2] = "";
				if (player.getEloData() != null) {
					if (player.getEloData().getRating() > 0) {
						playerMatrix[index][2] = String.valueOf(player.getEloData().getRating());
					}
				}
				if (player.getDwzData() != null) {
					if (player.getDwzData().getCsvFIDE_Elo() > 0) {
						playerMatrix[index][2] = String.valueOf(player.getDwzData().getCsvFIDE_Elo());
					}
				}
				playerMatrix[index][3] = "Löschen";
			} else {
				playerMatrix[index][0] = "<Spielfrei>";

				playerMatrix[index][1] = "";

				playerMatrix[index][2] = "";

				playerMatrix[index][3] = "Neuer Spieler";

			}

			index++;
		}
		for (int i = index; i < cols; i++) {
			playerMatrix[i][0] = "<Spielfrei>";

			playerMatrix[i][1] = "";

			playerMatrix[i][2] = "";

			playerMatrix[i][3] = "Neuer Spieler";
		}

	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(final Group group) {
		this.group = group;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Game[] getGames() {
		return games;
	}

	public Object[][] getPlayerMatrix() {
		return playerMatrix;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

}
