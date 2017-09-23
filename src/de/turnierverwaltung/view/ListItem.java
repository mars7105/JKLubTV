package de.turnierverwaltung.view;

//ListItem.java

/* Eventuell hier erst das Paket definieren, falls erforderlich! */

import javax.swing.*;

public class ListItem {
	// Das anzuzeigende Icon
	private ImageIcon icon = null;

	// Der Text
	private String text;

	public ListItem(ImageIcon iinserIcon, String text) {
		// Erzeugung des ImageIcons durch Angabe des Bild-Quellpfads
		icon = iinserIcon;

		// Zuweisung des Textes
		this.text = text;
	}

	// Liefert das Icon
	public ImageIcon getIcon() {
		return icon;
	}

	// Liefert den Text
	public String getText() {
		return text;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public void setText(String text) {
		this.text = text;
	}

}