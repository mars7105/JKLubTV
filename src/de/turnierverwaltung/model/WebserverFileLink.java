package de.turnierverwaltung.model;

public class WebserverFileLink {
	private String webserverPath;
	private String filename;
	private String pathToFile;
	private Boolean showLink;
	private String icsfilename;

	public WebserverFileLink(String webServerPath2, String filename2, Boolean showLink2) {
		super();
		this.webserverPath = webServerPath2;
		this.filename = filename2.replaceAll(" ", "");
		this.showLink = showLink2;

		pathToFile = "";
	}

	public WebserverFileLink(String webserverPath, String filename, String icsfilename, Boolean showLink) {
		super();
		this.webserverPath = webserverPath;
		this.filename = filename.replaceAll(" ", "");
		this.showLink = showLink;
		this.icsfilename = icsfilename;

		pathToFile = "";

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

	public String getPathToICS() {
		if (showLink) {
			pathToFile = "<a href='" + this.webserverPath + icsfilename + "' target='_blank'><img src='"
					+ this.webserverPath + "accessories-date.png' alt='" + Messages.getString("WebserverFileLink.2")
					+ "' />&nbsp;" + Messages.getString("WebserverFileLink.2") + "</a>";
		}
		return pathToFile;
	}

	public String getPathToPDF() {
		if (showLink) {
			pathToFile = "<a href='" + this.webserverPath + this.filename + "' target='_blank'><img src='"
					+ this.webserverPath + "acroread-2.png' alt='" + Messages.getString("WebserverFileLink.0")
					+ "' />&nbsp;" + Messages.getString("WebserverFileLink.0") + "</a>";
		}
		return pathToFile;
	}

	public String getPathToUserImageBlack() {
		if (showLink) {
			pathToFile = "<img src='" + this.webserverPath + "im-user-black.png' alt='black' />&nbsp;";

		}
		return pathToFile;
	}

	public String getPathToUserImageBlackWhite() {
		if (showLink) {
			pathToFile = "style='background-image: url(" + this.webserverPath
					+ "im-user-black-small.png); background-repeat: no-repeat;background-position: right bottom;'";

		}
		return pathToFile;
	}

	public String getPathToUserImageWhite() {
		if (showLink) {
			pathToFile = "<img src='" + this.webserverPath + "im-user-white.png' alt='white' />&nbsp;";

		}
		return pathToFile;
	}

	public String getPathToUserImageWhiteBlack() {
		if (showLink) {
			pathToFile = "style='background-image: url(" + this.webserverPath
					+ "im-user-white-small.png); background-repeat: no-repeat;background-position: right bottom;'";

		}
		return pathToFile;
	}
}
