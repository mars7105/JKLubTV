package de.turnierverwaltung.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import com.google.gson.Gson;

public class JSON {
	private String url;
	private PostRequest postRequest;

	public JSON(String url) {
		this.url = url;
	}

	public void postRequest(String tournamentName, String groupName, String startDate, String endDate, String menuName,
			String[] crossTableText, String[] meetingTableText, String[] sidePanels, String jsonFileName,
			String[][] crossTableMatrix, String jsonCrossTitle, String[][] meetingTableMatrix, String jsonMeetingtitle,
			String siteName, Boolean configFlag) throws IOException, FileNotFoundException {
		Gson gson = new Gson();

		String[][] crossTable = mirrorArray(crossTableMatrix);
		String[][] meetingTable = mirrorArray(meetingTableMatrix);

		JSONObject jsonObject = new JSONObject(tournamentName, groupName, menuName, crossTableText, crossTable,
				meetingTableText, meetingTable, startDate, endDate, sidePanels, jsonCrossTitle, jsonMeetingtitle,
				siteName);
		postRequest = new PostRequest(new URL(url));
		String cflag = "";
		if (configFlag) {
			cflag = "true";
		} else {
			cflag = "false";
		}
		postRequest.sendJSONStringToServer(gson.toJson(jsonObject), jsonFileName, cflag);
	}

	public void postFileNames(JSONFileObject filenames) throws IOException {
		Gson gson = new Gson();
		postRequest = new PostRequest(new URL(url));
		postRequest.sendFilenames(gson.toJson(filenames));
	}

	public void postStart() throws IOException, FileNotFoundException {

		PostRequest postRequest = new PostRequest(new URL(url));
		postRequest.sendSessionRequest(true);

	}

	public void postEnd() throws IOException, FileNotFoundException {
		PostRequest postRequest = new PostRequest(new URL(url));
		postRequest.sendSessionRequest(false);
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

	public Boolean testConnection() throws IOException, FileNotFoundException {
		Boolean testConnection = false;

		postRequest = new PostRequest(new URL(url));
		testConnection = postRequest.testConnection();

		return testConnection;
	}
}
