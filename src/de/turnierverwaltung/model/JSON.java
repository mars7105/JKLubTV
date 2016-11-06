package de.turnierverwaltung.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import de.turnierverwaltung.control.Messages;

public class JSON {

	public JSON() {
		super();
		// TODO Automatisch generierter Konstruktorstub
	}

	public void createJSON(String pathName, String[][] tabellenMatrix, String name, int index) throws IOException {
		Gson gson = new Gson();

		int zeilen = tabellenMatrix.length;
		for (int i = 0; i < zeilen; i++) {
			String replacedStr = tabellenMatrix[i][0].replaceAll("<br />", "");
			tabellenMatrix[i][0] = replacedStr;
		}
		postRequest("http://olaf-trint.mamuck.de/test/index.php", gson.toJson(tabellenMatrix), name, index);
		saveFile(pathName, gson.toJson(tabellenMatrix));
	}

	public void saveFile(String pathname, String jsonString) throws IOException {
		int n = 0;
		File file = new File(pathname);
		if (file.exists()) {
			Object[] options = { Messages.getString("SaveDialog.2"), Messages.getString("SaveDialog.3") };
			n = JOptionPane.showOptionDialog(null,
					Messages.getString("SaveDialog.0") + file.getAbsolutePath() + Messages.getString("SaveDialog.1"),
					"Dateioperation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
					options[1]);
		}
		if (n == 0) {

			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF8"); //$NON-NLS-1$

			writer.write(jsonString);
			writer.flush();
			writer.close();

		}
	}

	public void postRequest(String url, String jsonString, String name, int index) {
		PostRequest postRequest = new PostRequest();
		try {
			postRequest.sendJSONStringToServer(url, jsonString, name, index);
		} catch (IOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
	}
}
