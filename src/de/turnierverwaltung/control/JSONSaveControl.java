package de.turnierverwaltung.control;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import de.turnierverwaltung.model.CrossTable;
import de.turnierverwaltung.model.JSON;
import de.turnierverwaltung.model.JSONFileObject;
import de.turnierverwaltung.model.MeetingTable;
import de.turnierverwaltung.model.Sidepanel;

public class JSONSaveControl {
	private MainControl mainControl;
	private String jsonFileName;
	private String jsonMeetingName;
	private JSON jsonCross;
	private JSON jsonMeeting;

	public JSONSaveControl(MainControl mainControl) {

		this.mainControl = mainControl;
	}

	public void uploadJSONFile(String url, String menuName, ArrayList<Sidepanel> sidepanelItems) throws IOException {

		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();
		// url = "http://projekte.mamuck.de/jklubtv/receiveJSON.php";
		String filenames = "";
		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();

			jsonCross = new JSON(url + "/receiveJSON.php");
			jsonCross.postStart();
			String groupName[] = new String[anzahlGruppen];
			CrossTable[] turnierTabelle = new CrossTable[anzahlGruppen];
			MeetingTable[] meetingTable = new MeetingTable[anzahlGruppen];
			String tournamentName = mainControl.getTurnier().getTurnierName();
			String jsonCrossTitle = Messages.getString("NaviController.34");
			String jsonMeetingtitle = Messages.getString("NaviController.35");
			String startDate = mainControl.getTurnier().getStartDatum();
			String endDate = mainControl.getTurnier().getEndDatum();

			// sidePanels[0] = "Regularien";
			// sidePanels[1] = "Wertung:" + "<br />" + "1.) Punkte" + "<br />" +
			// "2.) Sonneborn-Berger-Wertung" + "<br />"
			// + "3.) direkter Vergleich" + "<br />" + "4.) um Abstieg oder
			// Preisrang: Stichkampf * -" + "<br />"
			// + "sonst gleiche Plazierung" + "<br />" + " " + "<br />"
			// + "* Stichkampf: eine Schnellpartie (30 min pro Spieler) mit
			// gegenüber Hauptturnier vertauschten Farben;"
			// + "<br />"
			// + "bei Gleichstand danach je eine Blitzpartie (mit stets
			// vertauschten Farben) bis zur Entscheidung. ";
			// sidePanels[2] = "Test";
			// sidePanels[3] = "Lorem ipsum dolor sit amet, consetetur
			// sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut
			// labore et dolore magna aliquyam erat, sed diam voluptua. At vero
			// eos et accusam et justo duo dolores et ea rebum. Stet clita kasd
			// gubergren, no sea takimata sanctus est Lorem ipsum dolor sit
			// amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr,
			// sed diam nonumy eirmod tempor invidunt ut labore et dolore magna
			// aliquyam erat, sed diam voluptua. At vero eos et accusam et justo
			// duo dolores et ea rebum. Stet clita kasd gubergren, no sea
			// takimata sanctus est Lorem ipsum dolor sit amet.";
			String[] crossTableText = new String[anzahlGruppen];
			String[] meetingTableText = new String[anzahlGruppen];
			for (int u = 0; u < anzahlGruppen; u++) {
				crossTableText[u] = "crossTableText" + new Integer(u);
				meetingTableText[u] = "meetingTableText" + new Integer(u);
			}
			String siteName = "JKlubTV Demosite";
			Boolean configFlag = true;

			String[][][] ctableMatrix = new String[anzahlGruppen][][];
			String[][][] mtableMatrix = new String[anzahlGruppen][][];
			for (int i = 0; i < anzahlGruppen; i++) {
				if (i == 0) {
					configFlag = true;
				} else {
					configFlag = false;
				}
				if (this.mainControl.getTurnierTabelle()[i] == null) {
					this.mainControl.getTurnierTabelleControl().makeSimpleTableView(i);

					this.mainControl.getTerminTabelleControl().makeSimpleTableView(i);

				}

				turnierTabelle[i] = mainControl.getTurnierTabelle()[i];
				meetingTable[i] = mainControl.getTerminTabelle()[i];
				int spalte = this.mainControl.getSimpleTableView()[i].getTable().getModel().getColumnCount();
				int zeile = this.mainControl.getSimpleTableView()[i].getTable().getModel().getRowCount();
				for (int x = 0; x < spalte; x++) {
					for (int y = 0; y < zeile; y++) {

						turnierTabelle[i].getTabellenMatrix()[x][y
								+ 1] = (String) this.mainControl.getSimpleTableView()[i].getTable().getValueAt(y, x);
					}
				}

				groupName[i] = mainControl.getTurnier().getGruppe()[i].getGruppenName();

				ctableMatrix[i] = turnierTabelle[i].getTabellenMatrix();
				mtableMatrix[i] = meetingTable[i].getTabellenMatrix();

			}
			jsonFileName = "json_file" + ".json";
			filenames = "jsonFiles/" + jsonFileName;
			int index = 0;
			String[] header = new String[sidepanelItems.size()];
			String[] body = new String[sidepanelItems.size()];
			for (Sidepanel item : sidepanelItems) {
				header[index] = item.getHeader();
				body[index] = item.getBody();
				index++;
			}
			jsonCross.postRequest(tournamentName, groupName, startDate, endDate, menuName, crossTableText,
					meetingTableText, header, body, jsonFileName, ctableMatrix, jsonCrossTitle, mtableMatrix,
					jsonMeetingtitle, siteName, configFlag);
			jsonCross.postEnd();
			JSON jsonFileObjects = new JSON(url + "/receiveFileJSON.php");
			JSONFileObject jsonFiles = new JSONFileObject(filenames);
			jsonFileObjects.postFileNames(jsonFiles);

		} else {
			JOptionPane.showMessageDialog(null,
					Messages.getString("PDFSaveControler.19") + Messages.getString("PDFSaveControler.20")); //$NON-NLS-1$ //$NON-NLS-2$

		}

	}

	public String getJsonCrossName() {
		return jsonFileName;
	}

	public void setJsonCrossName(String jsonCrossName) {
		this.jsonFileName = jsonCrossName;
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
