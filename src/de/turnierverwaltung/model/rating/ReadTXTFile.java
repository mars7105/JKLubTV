package de.turnierverwaltung.model.rating;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import de.turnierverwaltung.control.sqlite.SQLInfoControl;
import de.turnierverwaltung.model.Info;
import de.turnierverwaltung.model.TournamentConstants;

public final class ReadTXTFile {
	private String date;

	public ReadTXTFile() {

	}

	public ArrayList<ELOPlayer> readFile(final String filename) {
		ArrayList<ELOPlayer> playerList = null;
		date = "";
		try {
			ELOPlayer eloPlayer = null;
			playerList = new ArrayList<ELOPlayer>();
			final String[] row = new String[13];

			final BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			for (int i = 0; (line = reader.readLine()) != null; i++) {
				row[0] = line.substring(0, 14).trim();
				row[1] = line.substring(15, 75).trim();
				row[2] = line.substring(76, 78).trim();
				row[3] = line.substring(80, 81).trim();
				row[4] = line.substring(84, 87).trim();
				row[5] = line.substring(89, 92).trim();
				row[6] = line.substring(94, 98).trim();
				row[7] = line.substring(109, 112).trim();
				row[8] = line.substring(113, 118).trim();
				row[9] = line.substring(119, 122).trim();
				row[10] = line.substring(123, 125).trim();
				row[11] = line.substring(126, 130).trim();
				row[12] = line.substring(132, 135).trim();
				if (i == 0) {
					date = row[8];
					final Info info = new Info("ELO", "1.0", "date", date, 0);
					final SQLInfoControl infCtrl = new SQLInfoControl();
					try {
						infCtrl.insertOneInfo(info);
					} catch (final SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (i > 0) {
					int fideid = -1;
					try {
						fideid = Integer.parseInt(row[0]);
					} catch (final NumberFormatException e) {
						fideid = -1;
					}

					final String name = row[1];
					// String[] nameString = row[1].split("\\s",2);
					// if (nameString.length >= 1) {
					// name = (nameString[1] + " " + nameString[0]).replaceAll(",", "").trim();
					// }

					final String country = row[2];
					final String sex = row[3];
					final String title = row[4];
					final String w_title = row[5];
					final String o_title = row[6];
					final String foa_title = row[7];
					int rating = -1;
					try {
						rating = Integer.parseInt(row[8]);
					} catch (final NumberFormatException e) {
						rating = -1;
					}
					int games = -1;
					try {
						games = Integer.parseInt(row[9]);
					} catch (final NumberFormatException e) {
						games = -1;
					}
					int k = -1;
					try {
						k = Integer.parseInt(row[10]);
					} catch (final NumberFormatException e) {
						k = -1;
					}
					int birthday = -1;
					try {
						birthday = Integer.parseInt(row[11]);
					} catch (final NumberFormatException e) {
						birthday = -1;
					}

					final String flag = row[12];

					eloPlayer = new ELOPlayer(fideid, name, country, sex, title, w_title, o_title, foa_title, rating,
							games, k, birthday, flag);

					playerList.add(eloPlayer);
				}

			}
			reader.close();
		} catch (final StringIndexOutOfBoundsException e1) {
			playerList = null;
		} catch (final IOException e2) {
			playerList = null;
		}
		TournamentConstants.STANDARD_RATING_LIST_DATE = date;
		return playerList;
	}
}
