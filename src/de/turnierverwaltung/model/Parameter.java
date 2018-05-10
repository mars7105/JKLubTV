package de.turnierverwaltung.model;

import java.io.File;

public class Parameter {
	private String args[];

	public Parameter(String[] args) {
		super();
		this.args = args;

	}

	private String search(String searchString) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(searchString)) {
				if (i + 1 < args.length) {
					return args[i + 1];

				} else {
					return "nf";
				}

			}
		}
		return "";
	}

	public String getTournamentPath() {
		String path = this.search("-f");
		if (path.equals("nf")) {
			return "nf";
		} else {
			if (path.length() > 0) {
				if (checkTournamentPath(path) == true) {
					return path;
				} else {
					return "nf";
				}
			} else {
				return "";
			}

		}
	}

	private Boolean searchOption(String searchString) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals(searchString)) {
				return true;
			}
		}
		return false;
	}

	public Boolean resetProperties() {
		String searchString = "--reset";
		return searchOption(searchString);
	}

	public Boolean getHelp() {
		String searchString = "--help";
		return searchOption(searchString);
	}

	private Boolean checkTournamentPath(String filename) {
		if (this.args.length > 0) {
			if (filename.length() > 0) {
				File file = new File(filename);
				if (file.exists()) {
					return true;
				}
			}

		}
		return false;
	}
}
