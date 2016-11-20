package de.turnierverwaltung.view;

import java.awt.FlowLayout;

import javax.swing.JComboBox;

public class ColorSelectorView extends JComboBox<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -290306089748409530L;

	/**
	 * 
	 */

	public ColorSelectorView() {
		super();

		this.addItem(Messages.getString("ColorSelectorView.0"));
		this.addItem(Messages.getString("ColorSelectorView.1"));
		this.addItem(Messages.getString("ColorSelectorView.2"));
		this.addItem(Messages.getString("ColorSelectorView.3"));
		this.addItem(Messages.getString("ColorSelectorView.4"));
		this.addItem(Messages.getString("ColorSelectorView.5"));

	}

}
