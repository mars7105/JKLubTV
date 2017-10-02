package de.turnierverwaltung.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class ReadTXTFile {

	public ReadTXTFile() {

	}

	public ArrayList<ELOPlayer> readFile(String filename) throws IOException {
		ELOPlayer eloPlayer = null;
		ArrayList<ELOPlayer> playerList = new ArrayList<ELOPlayer>();
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
			row[10] = line.substring(123, 124).trim();
			row[11] = line.substring(126, 129).trim();
			row[12] = line.substring(132, 134).trim();

			if (i > 0) {
				String fideid = row[0];
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
				String rating = row[8];
				String games = row[9];
				String k = row[10];
				String birthday = row[11];
				String flag = row[12];
				
				
				eloPlayer = new ELOPlayer(fideid, name, country, sex, title, w_title, o_title, foa_title, rating, games,
						k, birthday, flag);
				
				playerList.add(eloPlayer);
			}

		}
		reader.close();

		return playerList;
	}
}
