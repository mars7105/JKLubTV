package de.turnierverwaltung.model;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostRequest {
	private String url;
	private String username;
	private String password;
	private String output;
	// private HttpURLConnection connection;

	public PostRequest(String url, String username, String password) throws IOException {
		this.url = url + "/";
		this.username = username;
		this.password = password;
		output = "";

	}

	public void login() throws IOException {
		URL urlPath = new URL(url + "checklogin.php");
		String param = "login=" + URLEncoder.encode("true", "UTF-8") + "&username="
				+ URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		connection.setInstanceFollowRedirects(false);
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

		output = "";
		for (String line; (line = reader.readLine()) != null;) {
			output = line;
			System.out.println(line);
			// if (!line.equals("Login")) {
			//
			// throw new IOException();
			// }
		}

		reader.close();
		connection.disconnect();
	}

	public void logout() throws IOException {
		URL urlPath = new URL(url + "logout.php");
		String param = "Logout=" + URLEncoder.encode("true", "UTF-8");
		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		connection.setInstanceFollowRedirects(false);
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
		output = "";
		for (String line; (line = reader.readLine()) != null;) {
			output = line;
			System.out.println(line);
			// if (!line.equals("Logout")) {
			//
			// throw new IOException();
			// }
		}

		os.close();
		reader.close();
		connection.disconnect();
	}

	public void sendJSONStringToServer(String jsonString, String jsonFileName, String configFlag) throws IOException {

		// String body = URLEncoder.encode(jsonString, "UTF-8");
		String param = "jsonFileName=" + URLEncoder.encode(jsonFileName, "UTF-8") + "&json="
				+ URLEncoder.encode(jsonString, "UTF-8") + "&configFlag=" + URLEncoder.encode(configFlag, "UTF-8");
		URL urlPath = new URL(url + "receiveJSON.php");
		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		connection.setInstanceFollowRedirects(false);
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
		output = "";
		for (String line; (line = reader.readLine()) != null;) {
			output = line;
			System.out.println(line);
			// if (!line.equals("Ok")) {
			//
			// throw new IOException();
			// }
		}

		os.close();
		reader.close();
		connection.disconnect();
	}

	public void sendFilenames(String filenames) throws IOException {
		String param = "jsonFiles=" + URLEncoder.encode(filenames, "UTF-8");
		URL urlPath = new URL(url + "receiveFileJSON.php");
		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		connection.setInstanceFollowRedirects(false);
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
		output = "";
		for (String line; (line = reader.readLine()) != null;) {
			output = line;
			System.out.println(line);
			// if (!line.equals("Ok")) {
			//
			// throw new IOException();
			// }
		}

		os.close();
		reader.close();
		connection.disconnect();

	}

	public Boolean testConnection() throws IOException, FileNotFoundException {
		Boolean testConnection = false;
		URL urlPath = new URL(url + "testconnection.php");
		String param = "test=" + URLEncoder.encode("true", "UTF-8");

		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		connection.setInstanceFollowRedirects(false);
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
		output = "";
		for (String line; (line = reader.readLine()) != null;) {
			output = line;
			System.out.println(line);
			// if (!line.equals("Ok") && !line.equals("Wrong username or
			// password")) {
			//
			// throw new IOException();
			// }

		}
		if (output.equals("Ok")) {
			testConnection = true;
		} else {
			testConnection = false;
		}
		os.close();
		reader.close();
		connection.disconnect();
		return testConnection;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
