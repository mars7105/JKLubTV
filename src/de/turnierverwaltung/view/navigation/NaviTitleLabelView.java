package de.turnierverwaltung.view.navigation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;

import de.turnierverwaltung.view.TitleLabelView;

public class NaviTitleLabelView extends TitleLabelView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NaviTitleLabelView(String title) {
		
		super(title);
		Color titleColor = new Color((SystemColor.control).getRGB());
		Color titleTextColor = new Color((SystemColor.controlText).getRGB());
		setBackground(titleColor);
		setForeground(titleTextColor);
		setFlowLayoutLeft();
		getTitlePanel().setPreferredSize(new Dimension(200, 30));
		setPreferredSize(new Dimension(200, 40));
		
		updateUI();
	}

}
