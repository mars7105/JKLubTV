package de.turnierverwaltung.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class StartpageItemView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StartpageItemView() {
		super();
		
		Color titleColor = new Color((SystemColor.text).getRGB());
		Color titleTextColor = new Color((SystemColor.textText).getRGB());
		setBackground(titleColor);
		setForeground(titleTextColor);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		Border blackline = BorderFactory.createLineBorder(Color.black);
		setBorder(blackline);
		setPreferredSize(new Dimension(800, 100));

	}

}
