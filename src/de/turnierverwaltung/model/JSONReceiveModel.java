package de.turnierverwaltung.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JSONReceiveModel {
	String output;

	public JSONReceiveModel() {

	}

	public JSONReceiveObject receiveJSON(HttpURLConnection connection) throws IOException, FileNotFoundException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		output = "";
		for (String line; (line = reader.readLine()) != null;) {
			output += line;
		}
		System.out.println(output);

		reader.close();
		Gson gson = new Gson();
		JSONReceiveObject jsonString = null;
		if (output == "") {
			jsonString = null;
		} else {
			try {
				jsonString = gson.fromJson(output, JSONReceiveObject.class);
			} catch (JsonSyntaxException e) {
				jsonString = null;
			} catch (NullPointerException e2) {
				jsonString = null;
			}
		}

		if (jsonString == null) {
			jsonString = new JSONReceiveObject();
			String[] md5sum = new String[1];
			md5sum[0] = "";
			jsonString.setMd5sum(md5sum);
			jsonString.setStatusCode("Wrong URL?");
			jsonString.setPhpModul("");
			jsonString.setVersion("");
		}
		return jsonString;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
