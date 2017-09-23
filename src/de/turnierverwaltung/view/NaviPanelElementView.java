package de.turnierverwaltung.view;

import java.awt.Color;
import java.awt.FlowLayout;

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
		flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setVgap(1);
		setLayout(flowLayout);
		setBackground(Color.LIGHT_GRAY);
	}

//	public FlowLayout getFlowLayout() {
//		return flowLayout;
//	}
//
//	public void setFlowLayout(FlowLayout flowLayout) {
//		this.flowLayout = flowLayout;
//	}

}
