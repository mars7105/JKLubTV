package de.turnierverwaltung.model;

public class JSONObject {
	private String tournamentName;
	private String groupName;
	private String menuName;
	private String[] crossTableText;
	private String[][] crossTable;
	private String[] meetingTableText;
	private String[][] meetingTable;
	private String startDate;
	private String endDate;
	private String[] sidePanels;
	private long timeStamp;
	private String jsonCrossTitle;
	private String jsonMeetingtitle;
	private String siteName;

	public JSONObject(String tournamentName, String groupName, String menuName, String[] crossTableText2,
			String[][] crossTable, String[] meetingTableText2, String[][] meetingTable, String startDate,
			String endDate, String sidePanels[], String jsonCrossTitle, String jsonMeetingtitle, String siteName) {
		super();

		this.tournamentName = tournamentName;
		this.groupName = groupName;
		this.menuName = menuName;
		this.crossTableText = crossTableText2;
		this.crossTable = crossTable;
		this.meetingTableText = meetingTableText2;
		this.meetingTable = meetingTable;
		this.startDate = startDate;
		this.endDate = endDate;
		this.jsonCrossTitle = jsonCrossTitle;
		this.jsonMeetingtitle = jsonMeetingtitle;
		this.timeStamp = System.currentTimeMillis() / 1000;
		this.sidePanels = sidePanels;
		this.siteName = siteName;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String[][] getCrossTable() {
		return crossTable;
	}

	public void setCrossTable(String[][] crossTable) {
		this.crossTable = crossTable;
	}

	public String[] getCrossTableText() {
		return crossTableText;
	}

	public void setCrossTableText(String[] crossTableText) {
		this.crossTableText = crossTableText;
	}

	public String[] getMeetingTableText() {
		return meetingTableText;
	}

	public void setMeetingTableText(String[] meetingTableText) {
		this.meetingTableText = meetingTableText;
	}

	public String[][] getMeetingTable() {
		return meetingTable;
	}

	public void setMeetingTable(String[][] meetingTable) {
		this.meetingTable = meetingTable;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String[] getRegularities() {
		return sidePanels;
	}

	public void setRegularities(String[] sidePanels) {
		this.sidePanels = sidePanels;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getJsonCrossTitle() {
		return jsonCrossTitle;
	}

	public void setJsonCrossTitle(String jsonCrossTitle) {
		this.jsonCrossTitle = jsonCrossTitle;
	}

	public String getJsonMeetingtitle() {
		return jsonMeetingtitle;
	}

	public void setJsonMeetingtitle(String jsonMeetingtitle) {
		this.jsonMeetingtitle = jsonMeetingtitle;
	}

	public String[] getSidePanels() {
		return sidePanels;
	}

	public void setSidePanels(String[] sidePanels) {
		this.sidePanels = sidePanels;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}
