package de.turnierverwaltung.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.gson.Gson;

public class JSON {
	private String url;
	private PostRequest postRequest;
	private String username;
	private String password;

	public JSON(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;

	}

	public Boolean postRequest(String tournamentName, String[] groupName, String startDate, String endDate,
			String menuName, String[] crossHeader, String[] crossTableText, int[] crossTableColor,
			String[] meetingHeader, String[] meetingTableText, int[] meetingTableColor, String[] header, String[] body,
			int[] color, String jsonFileName, String[][][] crossTableMatrix, String jsonCrossTitle,
			String[][][] meetingTableMatrix, String jsonMeetingtitle, String siteName, Boolean configFlag)
			throws IOException, FileNotFoundException {
		Gson gson = new Gson();
		String[][][] crossTable = new String[crossTableMatrix.length][][];
		String[][][] meetingTable = new String[meetingTableMatrix.length][][];

		for (int i = 0; i < crossTableMatrix.length; i++) {

			crossTable[i] = mirrorArray(crossTableMatrix[i]);
			meetingTable[i] = mirrorArray(meetingTableMatrix[i]);
		}
		JSONObject jsonObject = new JSONObject(tournamentName, groupName, menuName, crossHeader, crossTableText,
				crossTable, crossTableColor, meetingHeader, meetingTableText, meetingTable, meetingTableColor,
				startDate, endDate, header, body, color, jsonCrossTitle, jsonMeetingtitle, siteName);
		postRequest = new PostRequest(url, username, password);
		Boolean testConnection = false;

		testConnection = postRequest.sendJSONStringToServer(gson.toJson(jsonObject), jsonFileName);
		// postRequest.logout();
		// postRequest.setOutput("JSON File uploladed.");

		return testConnection;
	}

	public Boolean postFileNames(JSONFileObject filenames) throws IOException {
		Gson gson = new Gson();
		Boolean testConnection = false;
		postRequest = new PostRequest(url, username, password);

		testConnection = postRequest.sendFilenames(gson.toJson(filenames));
		// postRequest.logout();
		// postRequest.setOutput("Filenames uploladed.");

		return testConnection;
	}

	public Boolean makeTables() throws IOException {
		Boolean testConnection = false;
		postRequest = new PostRequest(url, username, password);

		testConnection = postRequest.makeHTMLTables();
		return testConnection;
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

		postRequest = new PostRequest(url, username, password);

		testConnection = postRequest.testConnection();
		// postRequest.logout();

		return testConnection;
	}

	public PostRequest getPostRequest() {
		return postRequest;
	}

	public void setPostRequest(PostRequest postRequest) {
		this.postRequest = postRequest;
	}

}
