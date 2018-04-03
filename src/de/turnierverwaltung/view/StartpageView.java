package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class StartpageView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton wizardButton;

	public StartpageView() {
		super();
		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
		mainPanel.setLayout(new GridLayout(0, 2, 20, 20));
		wizardButton = new JButton("Wizard");
		wizardButton.setPreferredSize(new Dimension(200, 40));
		wizardButton.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(wizardButton);
		mainPanel.add(panel);
		JLabel l2 = new JLabel("2");
		JLabel l3 = new JLabel("3");
		JLabel l4 = new JLabel("4");
		JLabel l5 = new JLabel("5");
		JLabel l6 = new JLabel("6");
		JLabel l7 = new JLabel("7");
		mainPanel.add(l2);
		mainPanel.add(l3);
		mainPanel.add(l4);
		mainPanel.add(l5);
		mainPanel.add(l6);
		mainPanel.add(l7);
		add(mainPanel, BorderLayout.CENTER);
		// ButtonPanelView buttonPanel = new ButtonPanelView();
		// buttonPanel.makeAllButtons();
		// add(buttonPanel, BorderLayout.SOUTH);

	}

}
