package de.turnierverwaltung.view.ratingdialog;

//ListItem.java

/* Eventuell hier erst das Paket definieren, falls erforderlich! */
import javax.swing.ImageIcon;

public class ListItem {
	// Das anzuzeigende Icon
	private ImageIcon icon = null;

	// Der Text
	private String text;

	public ListItem(final ImageIcon iinserIcon, final String text) {
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

	public void setIcon(final ImageIcon icon) {
		this.icon = icon;
	}

	public void setText(final String text) {
		this.text = text;
	}

}