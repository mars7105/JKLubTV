package de.turnierverwaltung.model;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PostRequest {
	private String url;
	private String loginParam;
	private JSONReceiveObject json;

	public PostRequest(String url, String username, String password) throws IOException {
		this.url = url + "/lib/";
		loginParam = "login=" + URLEncoder.encode("true", "UTF-8") + "&username=" + URLEncoder.encode(username, "UTF-8")
				+ "&password=" + URLEncoder.encode(password, "UTF-8");

	}

	public JSONReceiveObject sendJSONStringToServer(String jsonString, String jsonFileName) throws IOException {
		String param = loginParam + "&jsonFileName=" + URLEncoder.encode(jsonFileName, "UTF-8") + "&json="
				+ URLEncoder.encode(jsonString, "UTF-8");
		URL urlPath = new URL(url + "receiveJSON.php");
		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		connection.setInstanceFollowRedirects(false);
		// connection.setReadTimeout(10000 /* milliseconds */ );
		// connection.setConnectTimeout(15000 /* milliseconds */ );
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
		os.close();
		JSONReceiveModel jsonReceive = new JSONReceiveModel();
		json = jsonReceive.receiveJSON(connection);

		connection.disconnect();
		return json;
	}

	public JSONReceiveObject sendFilenames(String filenames) throws IOException {
		String param = loginParam + "&jsonFiles=" + URLEncoder.encode(filenames, "UTF-8");
		URL urlPath = new URL(url + "receiveFileJSON.php");
		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		connection.setInstanceFollowRedirects(false);
		// connection.setReadTimeout(10000 /* milliseconds */ );
		// connection.setConnectTimeout(15000 /* milliseconds */ );
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
		os.close();
		JSONReceiveModel jsonReceive = new JSONReceiveModel();
		json = jsonReceive.receiveJSON(connection);

		connection.disconnect();
		return json;

	}

	public JSONReceiveObject makeHTMLTables() throws IOException {
		String param = loginParam + "&maketables=" + URLEncoder.encode("true", "UTF-8");
		URL urlPath = new URL(url + "jsontotable.php");
		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		// connection.setInstanceFollowRedirects(false);
		// connection.setReadTimeout(10000 /* milliseconds */ );
		// connection.setConnectTimeout(15000 /* milliseconds */ );
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
		os.close();
		JSONReceiveModel jsonReceive = new JSONReceiveModel();
		json = jsonReceive.receiveJSON(connection);

		connection.disconnect();
		return json;

	}

	public JSONReceiveObject testConnection() throws IOException, FileNotFoundException {
		URL urlPath = new URL(url + "testconnection.php");
		String param = loginParam + "&test=" + URLEncoder.encode("true", "UTF-8");

		HttpURLConnection connection = (HttpURLConnection) urlPath.openConnection();
		connection.setInstanceFollowRedirects(false);

		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		// connection.setRequestProperty("Set-Cookie", loginCookie);
		// connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length", String.valueOf(param.length()));

		// open
		connection.connect();

		// setup send
		OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
		writer.write(param);
		writer.flush();

		JSONReceiveModel jsonReceive = new JSONReceiveModel();
		json = jsonReceive.receiveJSON(connection);

		writer.close();

		connection.disconnect();
		return json;
	}

	public String getOutput() {
		if (json == null) {
			return "Wrong URL!";
		} else {
			return json.getStatusCode();
		}
	}

}
