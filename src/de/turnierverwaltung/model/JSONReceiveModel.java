package de.turnierverwaltung.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.google.gson.Gson;

public class JSONReceiveModel {

	public JSONReceiveModel() {

	}

	public JSONReceiveObject receiveJSON(HttpURLConnection connection) throws IOException, FileNotFoundException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String output = "";
		for (String line; (line = reader.readLine()) != null;) {
			output += line;
		}
//		 System.out.println(output);

		reader.close();
		Gson gson = new Gson();
		JSONReceiveObject jsonString = null;
		if (output == "") {
			jsonString = new JSONReceiveObject();
			jsonString.setMd5sum("");
			jsonString.setStatusCode("Wrong URL?");
		} else {
			jsonString = gson.fromJson(output, JSONReceiveObject.class);
		}

		return jsonString;
	}
}
