package de.turnierverwaltung.model;

import java.io.IOException;
import com.google.gson.Gson;

public class JSON {

	public JSON() {

	}

	public void postRequest(String url, String[][] tabellenMatrix, String name, Boolean configFlag, String[] title)
			throws IOException {
		Gson gson = new Gson();

		String[][] mirrorMatrix = mirrorArray(tabellenMatrix);

		PostRequest postRequest = new PostRequest();

		postRequest.sendJSONStringToServer(url, gson.toJson(mirrorMatrix), name, configFlag, title);

	}

	private String[][] mirrorArray(String[][] array) {
		String[][] newArray = new String[array[0].length][array.length];
		for (int x = 0; x < array.length; x++) {
			for (int y = 0; y < array[0].length; y++) {
				newArray[y][x] = array[x][y];
			}
		}
		return newArray;

	}
}
