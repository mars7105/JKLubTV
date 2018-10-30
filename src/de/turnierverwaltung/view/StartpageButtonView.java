package de.turnierverwaltung.view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class StartpageButtonView extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StartpageButtonView(String text) {
		super();
		Dimension buttonDim = new Dimension(300, 50);

		this.setText(text);
		setPreferredSize(buttonDim);
		setHorizontalAlignment(SwingConstants.LEFT);
	}

}
