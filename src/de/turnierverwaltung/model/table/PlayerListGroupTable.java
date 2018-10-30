package de.turnierverwaltung.model.table;

import de.turnierverwaltung.model.Formeln;
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

		int cols = 0;

		int spielerAnzahl = this.group.getSpielerAnzahl();
		Formeln formeln = new Formeln();
		if (spielerAnzahl <= 0 && partienAnzahl > 0) {
			spielerAnzahl = formeln.getSpielerAnzahl(partienAnzahl);

		}
		if (rundenAnzahl <= 0 && spielerAnzahl > 0) {
			rundenAnzahl = formeln.getRundenAnzahl(spielerAnzahl);

		}
		if (partienAnzahl <= 0) {

			partienAnzahl = formeln.getPartienanzahl(spielerAnzahl);
		}
		this.group.setSpielerAnzahl(spielerAnzahl);
		this.group.setRundenAnzahl(rundenAnzahl);
		this.group.setPartienAnzahl(partienAnzahl);

		cols = partienAnzahl * 2 / rundenAnzahl;

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
				playerMatrix[index][0] = "<" + TournamentConstants.SPIELFREI + ">";

				playerMatrix[index][1] = "";

				playerMatrix[index][2] = "";

				playerMatrix[index][3] = "Neuer Spieler";

			}

			index++;
		}
		for (int i = index; i < cols; i++) {
			playerMatrix[i][0] = "<" + TournamentConstants.SPIELFREI + ">";

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
