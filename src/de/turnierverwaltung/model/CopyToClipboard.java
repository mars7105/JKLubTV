package de.turnierverwaltung.model;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class CopyToClipboard {

	public CopyToClipboard() {
		super();

	}

	public void copy(String content) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(content), null);
	}
}
