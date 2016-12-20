package de.turnierverwaltung.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import de.turnierverwaltung.model.JSON;
import de.turnierverwaltung.model.JSONFileObject;
import de.turnierverwaltung.model.JSONReceiveObject;
import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.model.TableContent;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.model.roundrobin.CrossTable;
import de.turnierverwaltung.model.roundrobin.MeetingTable;

public class JSONSaveControl {
	private MainControl mainControl;
	private String jsonFileName;
	private String jsonMeetingName;
	private JSON jsonCross;
	private JSON jsonMeeting;

	public JSONSaveControl(MainControl mainControl) {

		this.mainControl = mainControl;
	}

	public void uploadJSONFile(String url, String username, String password, String menuName,
			ArrayList<Sidepanel> sidepanelItems, ArrayList<ArrayList<TableContent>> tableContentgroups)
			throws IOException {

		Boolean ready = mainControl.getRundenEingabeFormularControl().checkNewTurnier();
		// url = "http://projekte.mamuck.de/jklubtv/receiveJSON.php";
		String[] filenames = new String[1];

		if (ready) {
			int anzahlGruppen = this.mainControl.getTurnier().getAnzahlGruppen();

			jsonCross = new JSON(url, username, password);
			String groupName[] = new String[anzahlGruppen];
			CrossTable[] turnierTabelle = new CrossTable[anzahlGruppen];
			MeetingTable[] meetingTable = new MeetingTable[anzahlGruppen];
			String tournamentName = mainControl.getTurnier().getTurnierName();
			String jsonCrossTitle = Messages.getString("NaviController.34");
			String jsonMeetingtitle = Messages.getString("NaviController.35");
			String startDate = mainControl.getTurnier().getStartDatum();
			String endDate = mainControl.getTurnier().getEndDatum();

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
			jsonFileName = "json-" + mainControl.getTurnier().getMd5Sum() + ".json";
			filenames[0] = "../temp/" + jsonFileName;
			int index = 0;
			String[] header = new String[sidepanelItems.size()];
			String[] body = new String[sidepanelItems.size()];
			int[] color = new int[sidepanelItems.size()];
			for (Sidepanel item : sidepanelItems) {
				header[index] = item.getHeader();
				body[index] = item.getBody();
				color[index] = item.getColor();
				index++;
			}

			String[] crossHeader = new String[anzahlGruppen];
			String[] crossBody = new String[anzahlGruppen];
			int[] crossColor = new int[anzahlGruppen];
			String[] meetingHeader = new String[anzahlGruppen];
			String[] meetingBody = new String[anzahlGruppen];
			int[] meetingColor = new int[anzahlGruppen];

			int i = 0;
			for (Iterator<ArrayList<TableContent>> iterator = tableContentgroups.iterator(); iterator.hasNext();) {
				ArrayList<TableContent> tableContentList = iterator.next();
				for (TableContent tableContentItem : tableContentList) {
					if (tableContentItem.getTableType() == TournamentConstants.CROSSTABLETYPE) {
						crossHeader[i] = tableContentItem.getHeader();
						crossBody[i] = tableContentItem.getBody();
						crossColor[i] = tableContentItem.getColor();
					}
					if (tableContentItem.getTableType() == TournamentConstants.MEETINGTABLETYPE) {
						meetingHeader[i] = tableContentItem.getHeader();
						meetingBody[i] = tableContentItem.getBody();
						meetingColor[i] = tableContentItem.getColor();
					}

				}
				i++;
			}
			String md5Sum = mainControl.getTurnier().getMd5Sum();
			JSONReceiveObject status1 = jsonCross.postRequest(tournamentName, groupName, startDate, endDate, menuName,
					crossHeader, crossBody, crossColor, meetingHeader, meetingBody, meetingColor, header, body, color,
					jsonFileName, ctableMatrix, jsonCrossTitle, mtableMatrix, jsonMeetingtitle, siteName, configFlag,
					md5Sum);
			JSONReceiveObject status2 = null;
			JSONReceiveObject status3 = null;
			if (status1.isStatusOk()) {
				String htmlfiles[][] = new String[1][anzahlGruppen];
				for (int htmlindex = 0; htmlindex < anzahlGruppen; htmlindex++) {
					htmlfiles[0][htmlindex] = new String();
					htmlfiles[0][htmlindex] = "temp/htmlfile-" + md5Sum + "-" + htmlindex + ".html";
				}
				JSON jsonFileObjects = new JSON(url, username, password);
				JSONFileObject jsonFiles = new JSONFileObject(filenames, htmlfiles);
				status2 = jsonFileObjects.postFileNames(jsonFiles);
				if (status2.isStatusOk()) {
					status3 = jsonFileObjects.makeTables();
					if (status3.isStatusOk() == true) {
						JOptionPane.showMessageDialog(null, status3.getStatusCode());
					} else {
						JOptionPane.showMessageDialog(null, status3.getStatusCode());

					}
				} else {
					JOptionPane.showMessageDialog(null, status2.getStatusCode());
				}

			} else {
				JOptionPane.showMessageDialog(null, status1.getStatusCode());

			}
		} else

		{
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
