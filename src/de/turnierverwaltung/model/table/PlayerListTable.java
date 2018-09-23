package de.turnierverwaltung.model.table;

import de.turnierverwaltung.model.Game;
import de.turnierverwaltung.model.Group;
import de.turnierverwaltung.model.Player;
import de.turnierverwaltung.model.TournamentConstants;

public class PlayerListTable {
	public Group group;
	private final Player[] players;
	private final Game[] games;
	private final int playerCount;
	private final String columnNames[];
	private final Object playerMatrix[][];

	public PlayerListTable(final Group group) {
		super();
		this.group = group;
		players = this.group.getSpieler();
		games = this.group.getPartien();
		playerCount = this.group.getSpielerAnzahl();
		// System.out.println(playerCount);

		int cols = 0;
		for (final Player player : players) {
			if (player.getSpielerId() == TournamentConstants.SPIELFREI_ID) {
				cols = playerCount - 1;
			} else {
				cols = playerCount;
			}
		}
		playerMatrix = new String[cols][4];
		columnNames = new String[4];
		columnNames[0] = "Name";
		columnNames[1] = "DWZ";
		columnNames[2] = "ELO";
		columnNames[3] = "Löschen";
		int index = 0;
		for (final Player player : players) {
			if (player.getSpielerId() != TournamentConstants.SPIELFREI_ID) {

				playerMatrix[index][0] = player.getSurname() + ", " + player.getForename();
				playerMatrix[index][1] = player.getDwz();
				if (player.getEloData() != null) {
					if (player.getEloData().getRating() > 0) {
						playerMatrix[index][2] = String.valueOf(player.getEloData().getRating());
					} else {
						playerMatrix[index][2] = "";
					}
				} else {
					playerMatrix[index][2] = "";
				}
				playerMatrix[index][3] = "Löschen";
				index++;
			}
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
