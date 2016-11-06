package de.turnierverwaltung.control;

import java.io.IOException;

import javax.swing.JOptionPane;
import de.turnierverwaltung.model.CrossTable;
import de.turnierverwaltung.model.JSON;
import de.turnierverwaltung.model.MeetingTable;

public class JSONSaveControl {
	private MainControl mainControl;
	private String url;
	private String jsonCrossName;
	private String jsonMeetingName;
	private JSON jsonCross;
	private JSON jsonMeeting;

	public JSONSaveControl(MainControl mainControl) {

		this.mainControl = mainControl;
	}

	public void uploadJSONFile() throws IOException {

		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();

		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();

			for (int i = 0; i < anzahlGruppen; i++) {
				if (this.mainControl.getTurnierTabelle()[i] == null) {
					this.mainControl.getTurnierTabelleControl().makeSimpleTableView(i);

					this.mainControl.getTerminTabelleControl().makeSimpleTableView(i);

				}

				CrossTable turnierTabelle = mainControl.getTurnierTabelle()[i];
				MeetingTable meetingTable = mainControl.getTerminTabelle()[i];
				int spalte = this.mainControl.getSimpleTableView()[i].getTable().getModel().getColumnCount();
				int zeile = this.mainControl.getSimpleTableView()[i].getTable().getModel().getRowCount();
				for (int x = 0; x < spalte; x++) {
					for (int y = 0; y < zeile; y++) {

						turnierTabelle.getTabellenMatrix()[x][y + 1] = (String) this.mainControl.getSimpleTableView()[i]
								.getTable().getValueAt(y, x);
					}
				}

				jsonCross = new JSON();
				jsonCrossName = "crosstable" + new Integer(i);
				jsonMeeting = new JSON();
				jsonMeetingName = "meetingtable" + new Integer(i);

				url = "http://olaf-trint.mamuck.de/test/receiveJSON.php";

				jsonCross.postRequest(url, turnierTabelle.getTabellenMatrix(), jsonCrossName);

				jsonMeeting.postRequest(url, meetingTable.getTabellenMatrix(), jsonMeetingName);

			}

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("PDFSaveControler.19") + Messages.getString("PDFSaveControler.20")); //$NON-NLS-1$ //$NON-NLS-2$

		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsonCrossName() {
		return jsonCrossName;
	}

	public void setJsonCrossName(String jsonCrossName) {
		this.jsonCrossName = jsonCrossName;
	}

	public String getJsonMeetingName() {
		return jsonMeetingName;
	}

	public void setJsonMeetingName(String jsonMeetingName) {
		this.jsonMeetingName = jsonMeetingName;
	}

	public JSON getJsonCross() {
		return jsonCross;
	}

	public void setJsonCross(JSON jsonCross) {
		this.jsonCross = jsonCross;
	}

	public JSON getJsonMeeting() {
		return jsonMeeting;
	}

	public void setJsonMeeting(JSON jsonMeeting) {
		this.jsonMeeting = jsonMeeting;
	}

}
