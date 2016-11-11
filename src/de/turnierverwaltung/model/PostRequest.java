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
	URL url;

	public PostRequest(URL url) {
		this.url = url;
	}

	public void sendJSONStringToServer(String jsonString, String jsonFileName, String configFlag) throws IOException {

		// String body = URLEncoder.encode(jsonString, "UTF-8");
		String param = "jsonFileName=" + URLEncoder.encode(jsonFileName, "UTF-8") + "&json="
				+ URLEncoder.encode(jsonString, "UTF-8") + "&configFlag=" + URLEncoder.encode(configFlag, "UTF-8");

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

	public void sendSessionRequest(Boolean request) throws IOException {
		String param = "";
		if (request == true) {
			param = "sessionStart=" + URLEncoder.encode("true", "UTF-8");
		} else {
			param = "sessionEnd=" + URLEncoder.encode("true", "UTF-8");
		}

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

	public void sendFilenames(String filenames) throws IOException {
		String param = "jsonFiles=" + URLEncoder.encode(filenames, "UTF-8");

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
