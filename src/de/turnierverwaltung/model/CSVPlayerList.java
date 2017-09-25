package de.turnierverwaltung.model;

import java.util.ArrayList;
import java.util.HashMap;

public class CSVPlayerList {
	private HashMap<Integer, CSVPlayer> csvPlayer;

	public CSVPlayerList() {

		csvPlayer = new HashMap<Integer, CSVPlayer>();
	}

	public HashMap<Integer, CSVPlayer> getCsvPlayer() {
		return csvPlayer;
	}

	public void setCsvPlayer(HashMap<Integer, CSVPlayer> csvPlayer) {
		this.csvPlayer = csvPlayer;
	}

	public void addPlayer(String zps, String mgl, CSVPlayer csv_Player) {
		int key = decodeKey(zps, mgl);
		if (key >= 0) {
			csvPlayer.put(key, csv_Player);
		}
	}

	public ArrayList<Player> getPlayerOfVerein(String zps) {
		ArrayList<Player> temp = new ArrayList<Player>();
		int zpsi = 0;
		int key = 0;
		try {
			zpsi = Integer.parseInt(zps);
			key = zpsi * 10000;
			for (int i = 0; i < 9999; i++) {

				if (csvPlayer.containsKey(key + i)) {
					temp.add(csvPlayer.get(key + i).getPlayer());
				}
			}
		} catch (NumberFormatException e) {
			temp = null;
		}

		return temp;

	}

	public CSVPlayer getPlayer(String zps, String mgl) {
		int key = decodeKey(zps, mgl);
		if (key >= 0) {
			return csvPlayer.get(key);
		} else {
			return null;
		}
	}

	private int decodeKey(String zps, String mgl) {
		try {
			int zpsi = Integer.parseInt(zps);
			int mgli = Integer.parseInt(mgl);
			int key = (zpsi * 10000) + mgli;
			return key;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
