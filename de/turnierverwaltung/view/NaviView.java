package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class NaviView extends JPanel {
	private JButton turnierListeButton;
	private ImageIcon spielerListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/arrow-right-3.png")));
	private ImageIcon turnierListeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/arrow-right-3.png")));
	private JButton spielerListeButton;
	private ImageIcon dbNewIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-new.png")));
	private ImageIcon dbLoadIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-open-4.png")));
	private JButton newDatabseButton;
	private JButton loadDatabaseButton;
	private JLabel pathToDatabase;

	public NaviView() {
		BorderLayout borderLayout = new BorderLayout();
		// this.setLayout(borderLayout);
		this.setBackground(Color.LIGHT_GRAY);
		EmptyBorder eBorder = new EmptyBorder(5,5,5,5);
		this.setBorder(eBorder);
		newDatabseButton = new JButton("Neue Datenbank", dbNewIcon);
		newDatabseButton.setPreferredSize(new Dimension(200, 40));
		loadDatabaseButton = new JButton("Datenbank laden", dbLoadIcon);
		loadDatabaseButton.setPreferredSize(new Dimension(200, 40));
		turnierListeButton = new JButton("Turnierliste", turnierListeIcon);
		turnierListeButton.setPreferredSize(new Dimension(200, 40));
		spielerListeButton = new JButton("Spielerliste", spielerListeIcon);
		spielerListeButton.setPreferredSize(new Dimension(200, 40));
		JPanel panel = new JPanel();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setVgap(1);
		panel.setLayout(flowLayout);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.add(newDatabseButton);
		JPanel panel2 = new JPanel();
		panel2.setLayout(flowLayout);
		panel2.setBackground(Color.LIGHT_GRAY);
		panel2.add(loadDatabaseButton);
		JPanel panelLabel = new JPanel();
		panelLabel.setLayout(flowLayout);
		panelLabel.setBackground(Color.LIGHT_GRAY);
		pathToDatabase = new JLabel(" ");
		panelLabel.add(pathToDatabase);
		JPanel panel3 = new JPanel();
		panel3.setLayout(flowLayout);
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.add(turnierListeButton);
		JPanel panel4 = new JPanel();
		panel4.setLayout(flowLayout);
		panel4.setBackground(Color.LIGHT_GRAY);
		panel4.add(spielerListeButton);
		JPanel panel5 = new JPanel();
		BoxLayout boxLayout = new BoxLayout(panel5, BoxLayout.PAGE_AXIS);
		panel5.setLayout(boxLayout);
		panel5.setBackground(Color.LIGHT_GRAY);
		panel5.add(panel);
		panel5.add(panel2);
		panel5.add(panelLabel);
		panel5.add(panel3);
		panel5.add(panel4);
		this.add(panel5);

	}

	public JLabel getPathToDatabase() {
		return pathToDatabase;
	}

	public void setPathToDatabase(JLabel pathToDatabase) {
		this.pathToDatabase = pathToDatabase;
		this.pathToDatabase.updateUI();
	}

	public JButton getTurnierListeButton() {
		return turnierListeButton;
	}

	public void setTurnierListeButton(JButton turnierListeButton) {
		this.turnierListeButton = turnierListeButton;
	}

	public JButton getSpielerListeButton() {
		return spielerListeButton;
	}

	public void setSpielerListeButton(JButton spielerListeButton) {
		this.spielerListeButton = spielerListeButton;
	}

	public JButton getNewDatabseButton() {
		return newDatabseButton;
	}

	public void setNewDatabseButton(JButton newDatabseButton) {
		this.newDatabseButton = newDatabseButton;
	}

	public JButton getLoadDatabaseButton() {
		return loadDatabaseButton;
	}

	public void setLoadDatabaseButton(JButton loadDatabaseButton) {
		this.loadDatabaseButton = loadDatabaseButton;
	}

}
