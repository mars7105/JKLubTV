package de.turnierverwaltung.model;

public class JSONObject {
	private String category;
	private String tournament;
	private String group;
	private String tableType;
	private String crossTableText;
	private String[][] crossTable;
	private String meetingTableText;
	private String[][] meetingTable;
	private String startDate;
	private String endDate;
	private String regularities;

	public JSONObject() {

	}

	public JSONObject(String category, String tournament, String group, String tableType, String tableText,
			String[][] crossTable, String meetingTableText, String[][] meetingTable, String startDate, String endDate,
			String regularities) {
		super();
		this.category = category;
		this.tournament = tournament;
		this.group = group;
		this.tableType = tableType;
		this.crossTableText = tableText;
		this.crossTable = crossTable;
		this.meetingTableText = meetingTableText;
		this.meetingTable = meetingTable;
		this.startDate = startDate;
		this.endDate = endDate;
		this.regularities = regularities;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTournament() {
		return tournament;
	}

	public void setTournament(String tournament) {
		this.tournament = tournament;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String[][] getCrossTable() {
		return crossTable;
	}

	public void setCrossTable(String[][] crossTable) {
		this.crossTable = crossTable;
	}

	public String getCrossTableText() {
		return crossTableText;
	}

	public void setCrossTableText(String crossTableText) {
		this.crossTableText = crossTableText;
	}

	public String getMeetingTableText() {
		return meetingTableText;
	}

	public void setMeetingTableText(String meetingTableText) {
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

	public String getRegularities() {
		return regularities;
	}

	public void setRegularities(String regularities) {
		this.regularities = regularities;
	}

}
