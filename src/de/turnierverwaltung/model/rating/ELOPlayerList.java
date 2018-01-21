package de.turnierverwaltung.model.rating;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ELOPlayerList {
	private ArrayList<ELOPlayer> playerList;

	public ELOPlayerList() {

		playerList = new ArrayList<ELOPlayer>();
	}

	public void addPlayer(String key, ELOPlayer csv_Player) {

		playerList.add(csv_Player);

	}

	/**
	 * 
	 * @return
	 */
	public Boolean checkifSpielerFileExist(String xmlFilenameSpieler) {
		if (xmlFilenameSpieler.equals("")) {
			return false;
		}
		File f = new File(xmlFilenameSpieler);
		if (f.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<ELOPlayer> getPlayerList() {
		return playerList;
	}

	public void readEloList(String filename) throws IOException {
		ReadTXTFile readTXTFile = new ReadTXTFile();
		playerList = readTXTFile.readFile(filename);

	}

	public void setCsvPlayer(ArrayList<ELOPlayer> csvPlayer) {
		this.playerList = csvPlayer;
	}

}
