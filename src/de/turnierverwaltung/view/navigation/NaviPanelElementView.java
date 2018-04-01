package de.turnierverwaltung.view.navigation;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.JPanel;

public class NaviPanelElementView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FlowLayout flowLayout;

	/**
	 * Create the panel.
	 */
	public NaviPanelElementView() {
		Color titleColor = new Color((SystemColor.control).getRGB());
		Color titleTextColor = new Color((SystemColor.controlText).getRGB());
		flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setVgap(1);
		setLayout(flowLayout);
		setBackground(titleColor);
		setForeground(titleTextColor);
	}

	// public FlowLayout getFlowLayout() {
	// return flowLayout;
	// }
	//
	// public void setFlowLayout(FlowLayout flowLayout) {
	// this.flowLayout = flowLayout;
	// }

}
