package de.turnierverwaltung.model;

import java.io.IOException;
import com.google.gson.Gson;

public class JSON {

	public JSON() {

	}

	public void postRequest(String url, String tournamentName, String groupName, String startDate, String endDate,
			String menuName, String[] crossTableText, String[] meetingTableText, String[] sidePanels, String jsonFileName,
			String[][] crossTableMatrix, String jsonCrossTitle, String[][] meetingTableMatrix, String jsonMeetingtitle,
			String siteName, Boolean configFlag) throws IOException {
		Gson gson = new Gson();

		String[][] crossTable = mirrorArray(crossTableMatrix);
		String[][] meetingTable = mirrorArray(meetingTableMatrix);

		JSONObject jsonObject = new JSONObject(tournamentName, groupName, menuName, crossTableText, crossTable,
				meetingTableText, meetingTable, startDate, endDate, sidePanels, jsonCrossTitle, jsonMeetingtitle,
				siteName);
		PostRequest postRequest = new PostRequest();
		String cflag = "";
		if (configFlag) {
			cflag = "true";
		} else {
			cflag = "false";
		}
		postRequest.sendJSONStringToServer(url, gson.toJson(jsonObject), jsonFileName, cflag, menuName);
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
