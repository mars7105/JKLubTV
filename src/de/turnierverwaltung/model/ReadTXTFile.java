package de.turnierverwaltung.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class ReadTXTFile {

	public ReadTXTFile() {

	}

	public ArrayList<ELOPlayer> readFile(String filename) {
		ArrayList<ELOPlayer> playerList = null;
		try {
			ELOPlayer eloPlayer = null;
			playerList = new ArrayList<ELOPlayer>();
			String[] row = new String[13];

			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			for (int i = 0; (line = reader.readLine()) != null; i++) {
				row[0] = line.substring(0, 14).trim();
				row[1] = line.substring(15, 75).trim();
				row[2] = line.substring(76, 78).trim();
				row[3] = line.substring(80, 81).trim();
				row[4] = line.substring(84, 86).trim();
				row[5] = line.substring(89, 91).trim();
				row[6] = line.substring(94, 97).trim();
				row[7] = line.substring(109, 111).trim();
				row[8] = line.substring(113, 117).trim();
				row[9] = line.substring(119, 121).trim();
				row[10] = line.substring(123, 125).trim();
				row[11] = line.substring(126, 130).trim();
				row[12] = line.substring(132, 134).trim();

				if (i > 0) {
					int fideid = -1;
					try {
						fideid = Integer.parseInt(row[0]);
					} catch (NumberFormatException e) {
						fideid = -1;
					}

					String name = "";
					String[] nameString = row[1].split("\\s");
					if (nameString.length > 1) {
						name = (nameString[1] + " " + nameString[0]).replaceAll(",", "").trim();
					}

					String country = row[2];
					String sex = row[3];
					String title = row[4];
					String w_title = row[5];
					String o_title = row[6];
					String foa_title = row[7];
					int rating = -1;
					try {
						rating = Integer.parseInt(row[8]);
					} catch (NumberFormatException e) {
						rating = -1;
					}
					int games = -1;
					try {
						games = Integer.parseInt(row[9]);
					} catch (NumberFormatException e) {
						games = -1;
					}
					int k = -1;
					try {
						k = Integer.parseInt(row[10]);
					} catch (NumberFormatException e) {
						k = -1;
					}
					int birthday = -1;
					try {
						birthday = Integer.parseInt(row[11]);
					} catch (NumberFormatException e) {
						birthday = -1;
					}

					String flag = row[12];

					eloPlayer = new ELOPlayer(fideid, name, country, sex, title, w_title, o_title, foa_title, rating,
							games, k, birthday, flag);

					playerList.add(eloPlayer);
				}

			}
			reader.close();
		} catch (StringIndexOutOfBoundsException e1) {
			playerList = null;
		} catch (IOException e2) {
			playerList = null;
		}

		return playerList;
	}
}
