package de.turnierverwaltung.model;

public class WebserverFileLink {
	private final String webserverPath;
	private final String filename;
	private String pathToFile;
	private final Boolean showLink;
	private String icsfilename;

	public WebserverFileLink(final String webServerPath2, final String filename2, final Boolean showLink2) {
		super();
		webserverPath = webServerPath2;
		filename = filename2.replaceAll(" ", "");
		showLink = showLink2;

		pathToFile = "";
	}

	public WebserverFileLink(final String webserverPath, final String filename, final String icsfilename,
			final Boolean showLink) {
		super();
		this.webserverPath = webserverPath;
		this.filename = filename.replaceAll(" ", "");
		this.showLink = showLink;
		this.icsfilename = icsfilename;

		pathToFile = "";

	}

	public String getPathToEXCEL() {
		if (showLink) {
			pathToFile = "&nbsp;&nbsp;&nbsp;<a href='" + webserverPath + filename + "' target='_blank'><img src='"
					+ webserverPath + "excel-1.png' alt='" + Messages.getString("WebserverFileLink.1") + "' />&nbsp;"
					+ Messages.getString("WebserverFileLink.1") + "</a>";
		}
		return pathToFile;
	}

	public String getPathToICS() {
		if (showLink) {
			pathToFile = "<a href='" + webserverPath + icsfilename + "' target='_blank'><img src='" + webserverPath
					+ "accessories-date.png' alt='" + Messages.getString("WebserverFileLink.2") + "' />&nbsp;"
					+ Messages.getString("WebserverFileLink.2") + "</a>";
		}
		return pathToFile;
	}

	public String getPathToPDF() {
		if (showLink) {
			pathToFile = "<a href='" + webserverPath + filename + "' target='_blank'><img src='" + webserverPath
					+ "acroread-2.png' alt='" + Messages.getString("WebserverFileLink.0") + "' />&nbsp;"
					+ Messages.getString("WebserverFileLink.0") + "</a>";
		}
		return pathToFile;
	}

	public String getPathToUserImageBlack() {
		if (showLink) {
			pathToFile = "<img src='" + webserverPath + "im-user-black.png' alt='black' />&nbsp;";

		}
		return pathToFile;
	}

	public String getPathToUserImageBlackWhite() {
		if (showLink) {
			pathToFile = "style='background-image: url(" + webserverPath
					+ "im-user-black-small.png); background-repeat: no-repeat;background-position: right bottom;'";

		}
		return pathToFile;
	}

	public String getPathToUserImageWhite() {
		if (showLink) {
			pathToFile = "<img src='" + webserverPath + "im-user-white.png' alt='white' />&nbsp;";

		}
		return pathToFile;
	}

	public String getPathToUserImageWhiteBlack() {
		if (showLink) {
			pathToFile = "style='background-image: url(" + webserverPath
					+ "im-user-white-small.png); background-repeat: no-repeat;background-position: right bottom;'";

		}
		return pathToFile;
	}

	public String getPathsForMeetingtable() {
		if (showLink) {
			pathToFile = "<br />" + getPathToPDF() + "&nbsp;&nbsp;&nbsp;" + getPathToICS();
		}
		return pathToFile;
	}

	public String getPathToWhitePoint() {
//		if (showLink) {
			pathToFile = "style='background-image: url(" + webserverPath
					+ "whitepoint.png); background-repeat: no-repeat;background-position: right center;'";

//		}
		return pathToFile;
	}

	public String getPathToBlackPoint() {
//		if (showLink) {
			pathToFile = "style='background-image: url(" + webserverPath
					+ "blackpoint.png); background-repeat: no-repeat;background-position: right center;'";

//		}
		return pathToFile;
	}
}
