package de.turnierverwaltung.model;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostRequest {

	public PostRequest() {
		super();
	}

	public void sendJSONStringToServer(String urlString, String jsonString, String name, Boolean configFlag,
			String[] title) throws IOException {
		String cFlag = "";
		if (configFlag == true) {
			cFlag = "true";
		} else {
			cFlag = "false";
		}
		// String body = URLEncoder.encode(jsonString, "UTF-8");
		String param = "name=" + URLEncoder.encode(name, "UTF-8") + "&json=" + URLEncoder.encode(jsonString, "UTF-8")
				+ "&configFlag=" + cFlag + "&title=" + title[0] + "&groupname=" + title[1] + "&tabletype=" + title[2];
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setReadTimeout(10000 /* milliseconds */ );
		connection.setConnectTimeout(15000 /* milliseconds */ );
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		connection.setFixedLengthStreamingMode(param.getBytes().length);

		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", String.valueOf(param.length()));

		// open
		connection.connect();

		// setup send
		OutputStream os = new BufferedOutputStream(connection.getOutputStream());
		os.write(param.getBytes());
		// System.out.println(param);
		// clean up
		os.flush();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		for (String line; (line = reader.readLine()) != null;) {
			if (line.equals("ERROR!")) {
				throw new IOException();
			}
		}

		os.close();
		reader.close();
		connection.disconnect();
	}
}
