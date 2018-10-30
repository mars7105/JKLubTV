package de.turnierverwaltung.model.table;

import java.util.ArrayList;

import de.turnierverwaltung.model.Player;

public class PlayerListTable {
	private final ArrayList<Player> playerList;
	private String[][] playerMatrix;
	private final int cols;
	private String[] columnNames;

	public PlayerListTable(final ArrayList<Player> playerList) {
		super();
		this.playerList = playerList;
		cols = playerList.size();
		playerMatrix = new String[cols][5];
		columnNames = new String[5];
		columnNames[0] = "Name";
		columnNames[1] = "DWZ";
		columnNames[2] = "ELO";
		columnNames[3] = "Edit";
		columnNames[4] = "Delete";
		int index = 0;
		for (final Player player : this.playerList) {
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
			playerMatrix[index][3] = "Edit";
			playerMatrix[index][4] = "Delete";
			index++;
		}
	}

	public String[][] getPlayerMatrix() {
		return playerMatrix;
	}

	public void setPlayerMatrix(final String[][] playerMatrix) {
		this.playerMatrix = playerMatrix;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(final String[] columnNames) {
		this.columnNames = columnNames;
	}

}
