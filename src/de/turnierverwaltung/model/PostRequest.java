package de.turnierverwaltung.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;

public class PostRequest {
	private String version;
	private String url;
	private String loginParam;
	private JSONReceiveObject json;

	public PostRequest(String url, String username, String password) throws IOException {

		this.url = url;
		loginParam = "login=" + URLEncoder.encode("true", "UTF-8") + "&username=" + URLEncoder.encode(username, "UTF-8")
				+ "&password=" + URLEncoder.encode(password, "UTF-8");
		checkWebsite();
	}

	private void checkWebsite() throws IOException {
		version = "";
		try {
			json = versioncheck();
			version = json.getVersion();
		} catch (NullPointerException e) {
			json = new JSONReceiveObject();
			json.setStatusCode("Wrong URL?");
			throw new NullPointerException();
		}

		if (version.equals("")) {
			throw new IOException();

		}
		if (version.equals("Frontend")) {
			String lib = createLibString();
			url += lib;

		}
		if (version.equals("typo3")) {

		}
	}

	public JSONReceiveObject sendJSONStringToServer(String jsonString, String jsonFileName) throws IOException {
		String param = loginParam + "&jsonFileName=" + URLEncoder.encode(jsonFileName, "UTF-8") + "&json="
				+ URLEncoder.encode(jsonString, "UTF-8");
		URL urlPath = new URL(url + "receiveJSON.php");
		json = sendPost(urlPath, param);
		return json;
	}

	public JSONReceiveObject sendFilenames(String filenames) throws IOException {
		String param = loginParam + "&jsonFiles=" + URLEncoder.encode(filenames, "UTF-8");
		URL urlPath = new URL(url + "receiveFileJSON.php");
		json = sendPost(urlPath, param);
		return json;

	}

	public JSONReceiveObject makeHTMLTables() throws IOException {
		String param = loginParam + "&maketables=" + URLEncoder.encode("true", "UTF-8");
		URL urlPath = new URL(url + "jsontotable.php");
		json = sendPost(urlPath, param);
		return json;

	}

	public JSONReceiveObject testConnection() throws IOException, FileNotFoundException {
		// URL urlPath = new URL( "http://chesscloud.eu/receivejson/");
		URL urlPath = new URL(url + "testconnection.php");
		String param = loginParam + "&test=" + URLEncoder.encode("true", "UTF-8");
		json = sendPost(urlPath, param);
		return json;
	}

	public JSONReceiveObject versioncheck() {
		URL urlPath = null;
		String param = null;
		// Test if Website is TYPO3
		try {

			urlPath = new URL(url + "receivejson");

			param = loginParam + "&version=" + URLEncoder.encode("true", "UTF-8");

			json = sendPost(urlPath, param);
		} catch (MalformedURLException e) {
			urlPath = null;
			param = null;
			json = null;
		} catch (IOException e) {
			urlPath = null;
			param = null;
			json = null;
		}
		// Test if Website is Frontend
		try {
			String lib = createLibString();
			if (json == null) {

				urlPath = new URL(url + lib + "versioncheck.php");

				param = loginParam + "&version=" + URLEncoder.encode("true", "UTF-8");
				json = sendPost(urlPath, param);
			}
		} catch (MalformedURLException e) {
			urlPath = null;
			param = null;
			json = null;
		} catch (UnsupportedEncodingException e) {
			urlPath = null;
			param = null;
			json = null;
		} catch (IOException e) {
			urlPath = null;
			param = null;
			json = null;
		}
		return json;

	}

	private String createLibString() {
		String lib = "";
		if ((url.substring(url.length() - 1)).equals("/")) {
			lib = "jklubtv/lib/";
		} else {
			lib = "/jklubtv/lib/";
		}
		return lib;
	}

	private JSONReceiveObject sendPost(URL urlPath, String param) throws IOException {

		HttpURLConnection connection = null;
		if (urlPath.getProtocol().toLowerCase().equals("https")) {
			trustAllHosts();
			HttpsURLConnection https = (HttpsURLConnection) urlPath.openConnection();
			connection = https;
		} else {
			connection = (HttpURLConnection) urlPath.openConnection();
		}
		connection.setInstanceFollowRedirects(false);

		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		// connection.setRequestProperty("Set-Cookie", loginCookie);
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
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
		// System.out.println(jsonReceive.getOutput());

		writer.close();

		connection.disconnect();
		return json;
	}

	/**
	 * Trust every server - dont check for any certificate
	 * 
	 * @throws NoSuchAlgorithmException
	 */
	private static void trustAllHosts() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509ExtendedTrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string, Socket socket)
					throws CertificateException {

			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string, Socket socket)
					throws CertificateException {

			}

			@Override
			public void checkClientTrusted(X509Certificate[] xcs, String string, SSLEngine ssle)
					throws CertificateException {

			}

			@Override
			public void checkServerTrusted(X509Certificate[] xcs, String string, SSLEngine ssle)
					throws CertificateException {

			}

		} };

		SSLContext sc;
		try {

			sc = SSLContext.getInstance("SSL");

			sc.init(null, trustAllCerts, new java.security.SecureRandom());

			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			/*
			 * end of the fix
			 */
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getOutput() {
		if (json == null) {
			return "Wrong URL!";
		} else {
			return json.getStatusCode();
		}
	}

}
