package de.turnierverwaltung.model;

public class WebserverFileLink {
	private String webserverPath;
	private String filename;
	private String pathToFile;
	private Boolean showLink;

	public WebserverFileLink(String webserverPath, String filename, Boolean showLink) {
		super();
		this.webserverPath = webserverPath;
		this.filename = filename.replaceAll(" ", "");
		this.showLink = showLink;
		pathToFile = "";

	}

	public String getPathToICS() {
		if (showLink) {
			pathToFile = "&nbsp;&nbsp;&nbsp;<a href='" + this.webserverPath + this.filename
					+ "' target='_blank'><img src='" + this.webserverPath + "accessories-date.png' alt='"
					+ Messages.getString("WebserverFileLink.2") + "' />&nbsp;"
					+ Messages.getString("WebserverFileLink.2") + "</a>";
		}
		return pathToFile;
	}

	public String getPathToPDF() {
		if (showLink) {
			pathToFile = "&nbsp;&nbsp;&nbsp;<a href='" + this.webserverPath + this.filename
					+ "' target='_blank'><img src='" + this.webserverPath + "acroread-2.png' alt='"
					+ Messages.getString("WebserverFileLink.0") + "' />&nbsp;"
					+ Messages.getString("WebserverFileLink.0") + "</a>";
		}
		return pathToFile;
	}

	public String getPathToEXCEL() {
		if (showLink) {
			pathToFile = "&nbsp;&nbsp;&nbsp;<a href='" + this.webserverPath + this.filename
					+ "' target='_blank'><img src='" + this.webserverPath + "excel-1.png' alt='"
					+ Messages.getString("WebserverFileLink.1") + "' />&nbsp;"
					+ Messages.getString("WebserverFileLink.1") + "</a>";
		}
		return pathToFile;
	}
}
