package de.turnierverwaltung.model;

public class JSONFileObject {
	private String[] filename;
	private String[][] htmlfiles;

	public JSONFileObject(String[] filenames, String[][] htmlfiles) {

		this.filename = filenames;
		this.htmlfiles = htmlfiles;
	}

	public String[] getFilename() {
		return filename;
	}

	public void setFilename(String[] filename) {
		this.filename = filename;
	}

	public String[][] getHtmlfiles() {
		return htmlfiles;
	}

	public void setHtmlfiles(String[][] htmlfiles) {
		this.htmlfiles = htmlfiles;
	}

}
