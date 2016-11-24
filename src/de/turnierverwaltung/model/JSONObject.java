package de.turnierverwaltung.model;

public class JSONObject {
	private String tournamentName;
	private String[] groupName;
	private String menuName;
	private String[] crossHeader;
	private String[] crossTableText;
	private String[][][] crossTable;
	private String[] meetingHeader;
	private String[] meetingTableText;
	private String[][][] meetingTable;
	private String startDate;
	private String endDate;
	private String[] sidePanelsheader;
	private String[] sidePanelsbody;
	private long timeStamp;
	private String jsonCrossTitle;
	private String jsonMeetingtitle;
	private String siteName;
	private int[] crossTableColor;
	private int[] meetingTableColor;
	private int[] color;
	private String md5Sum;

	public JSONObject(String tournamentName, String[] groupName, String menuName, String[] crossHeader,
			String[] crossTableText2, String[][][] crossTable, int[] crossTableColor, String[] meetingHeader,
			String[] meetingTableText2, String[][][] meetingTable, int[] meetingTableColor, String startDate,
			String endDate, String[] header, String[] body, int[] color, String jsonCrossTitle, String jsonMeetingtitle,
			String siteName, String md5Sum) {
		super();
		this.crossHeader = crossHeader;
		this.meetingHeader = meetingHeader;
		this.tournamentName = tournamentName;
		this.groupName = groupName;
		this.menuName = menuName;
		this.crossTableText = crossTableText2;
		this.crossTable = crossTable;
		this.crossTableColor = crossTableColor;
		this.meetingTableText = meetingTableText2;
		this.meetingTable = meetingTable;
		this.meetingTableColor = meetingTableColor;
		this.startDate = startDate;
		this.endDate = endDate;
		this.jsonCrossTitle = jsonCrossTitle;
		this.jsonMeetingtitle = jsonMeetingtitle;
		this.timeStamp = System.currentTimeMillis() / 1000;
		this.sidePanelsheader = header;
		this.sidePanelsbody = body;
		this.siteName = siteName;
		this.color = color;
		this.md5Sum = md5Sum;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
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

	public String[] getSidePanelsheader() {
		return sidePanelsheader;
	}

	public void setSidePanelsheader(String[] sidePanelsheader) {
		this.sidePanelsheader = sidePanelsheader;
	}

	public String[] getSidePanelsbody() {
		return sidePanelsbody;
	}

	public void setSidePanelsbody(String[] sidePanelsbody) {
		this.sidePanelsbody = sidePanelsbody;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String[] getGroupName() {
		return groupName;
	}

	public void setGroupName(String[] groupName) {
		this.groupName = groupName;
	}

	public String[][][] getCrossTable() {
		return crossTable;
	}

	public void setCrossTable(String[][][] crossTable) {
		this.crossTable = crossTable;
	}

	public String[][][] getMeetingTable() {
		return meetingTable;
	}

	public void setMeetingTable(String[][][] meetingTable) {
		this.meetingTable = meetingTable;
	}

	public String[] getCrossHeader() {
		return crossHeader;
	}

	public void setCrossHeader(String[] crossHeader) {
		this.crossHeader = crossHeader;
	}

	public String[] getMeetingHeader() {
		return meetingHeader;
	}

	public void setMeetingHeader(String[] meetingHeader) {
		this.meetingHeader = meetingHeader;
	}

	public int[] getCrossTableColor() {
		return crossTableColor;
	}

	public void setCrossTableColor(int[] crossTableColor) {
		this.crossTableColor = crossTableColor;
	}

	public int[] getMeetingTableColor() {
		return meetingTableColor;
	}

	public void setMeetingTableColor(int[] meetingTableColor) {
		this.meetingTableColor = meetingTableColor;
	}

	public int[] getColor() {
		return color;
	}

	public void setColor(int[] color) {
		this.color = color;
	}

	public String getMd5Sum() {
		return md5Sum;
	}

	public void setMd5Sum(String md5Sum) {
		this.md5Sum = md5Sum;
	}

}
