package de.turnierverwaltung.model;

public class TableContent {
	private String header;
	private String body;
	private int idTableContent;
	private int tableType;
	private int idGroup;

	public TableContent(String header, String body, int idTableContent, int tableType, int idGroup) {

		this.header = header;
		this.body = body;
		this.idTableContent = idTableContent;
		this.idGroup = idGroup;
		this.tableType = tableType;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getIdTableContent() {
		return idTableContent;
	}

	public void setIdTableContent(int idTableContent) {
		this.idTableContent = idTableContent;
	}

	public int getTableType() {
		return tableType;
	}

	public void setTableType(int tableType) {
		this.tableType = tableType;
	}

	public int getIdGroup() {
		return idGroup;
	}

	public void setIdGroup(int idGroup) {
		this.idGroup = idGroup;
	}

}
