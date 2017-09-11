package de.turnierverwaltung.model;

public class WebserverFileLink {
	private String webserverPath;
	private String filename;
	private String pathToFile;

	public WebserverFileLink(String webserverPath, String filename) {
		super();
		this.webserverPath = webserverPath;
		this.filename = filename.replaceAll(" ", "");

	}

	public String getPathToICS() {
		pathToFile = "&nbsp;&nbsp;&nbsp;<a href='" + this.webserverPath + this.filename + "' target='_blank'><img src='"
				+ this.webserverPath + "accessories-date.png' alt='" + Messages.getString("WebserverFileLink.2")
				+ "' />&nbsp;" + Messages.getString("WebserverFileLink.2") + "</a>";
		return pathToFile;
	}

	public String getPathToPDF() {
		pathToFile = "&nbsp;&nbsp;&nbsp;<a href='" + this.webserverPath + this.filename + "' target='_blank'><img src='"
				+ this.webserverPath + "acroread-2.png' alt='" + Messages.getString("WebserverFileLink.0")
				+ "' />&nbsp;" + Messages.getString("WebserverFileLink.0") + "</a>";
		return pathToFile;
	}

	public String getPathToEXCEL() {
		pathToFile = "&nbsp;&nbsp;&nbsp;<a href='" + this.webserverPath + this.filename + "' target='_blank'><img src='"
				+ this.webserverPath + "excel-1.png' alt='" + Messages.getString("WebserverFileLink.1") + "' />&nbsp;"
				+ Messages.getString("WebserverFileLink.1") + "</a>";
		return pathToFile;
	}
}
