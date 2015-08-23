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
	private ImageIcon tabelleSpeichernIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-save-5.png")));

	private JButton newDatabseButton;
	private JButton loadDatabaseButton;
	private JLabel pathToDatabase;
	private JButton infoButton;
	private ImageIcon infoIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png")));
	private JButton tabelleSpeichernButton;
	private JPanel tabellenPanel;
	private JPanel datenbankPanel;
	private JPanel dateiPanel;

	public NaviView() {
		BorderLayout borderLayout = new BorderLayout();
		// this.setLayout(borderLayout);
		this.setBackground(Color.LIGHT_GRAY);
		EmptyBorder eBorder = new EmptyBorder(5, 5, 5, 5);
		this.setBorder(eBorder);
		newDatabseButton = new JButton("Neue Datenbank", dbNewIcon);
		newDatabseButton.setPreferredSize(new Dimension(200, 40));
		loadDatabaseButton = new JButton("Datenbank laden", dbLoadIcon);
		loadDatabaseButton.setPreferredSize(new Dimension(200, 40));
		turnierListeButton = new JButton("Turnierliste", turnierListeIcon);
		turnierListeButton.setPreferredSize(new Dimension(200, 40));
		spielerListeButton = new JButton("Spielerliste", spielerListeIcon);
		spielerListeButton.setPreferredSize(new Dimension(200, 40));
		infoButton = new JButton("Info", infoIcon);
		infoButton.setPreferredSize(new Dimension(200, 40));
		tabelleSpeichernButton = new JButton("Speichern", tabelleSpeichernIcon);
		tabelleSpeichernButton.setPreferredSize(new Dimension(200, 40));


		dateiPanel = new JPanel();
		dateiPanel.setBackground(Color.LIGHT_GRAY);
		BoxLayout dateiPanelLayout = new BoxLayout(dateiPanel, BoxLayout.PAGE_AXIS);
		dateiPanel.setLayout(dateiPanelLayout);
		datenbankPanel = new JPanel();
		datenbankPanel.setBackground(Color.LIGHT_GRAY);
		BoxLayout datenbankPanelLayout = new BoxLayout(datenbankPanel, BoxLayout.PAGE_AXIS);
		datenbankPanel.setLayout(datenbankPanelLayout);
		tabellenPanel = new JPanel();
		tabellenPanel.setBackground(Color.LIGHT_GRAY);
		BoxLayout tabellenPanelLayout = new BoxLayout(tabellenPanel, BoxLayout.PAGE_AXIS);
		tabellenPanel.setLayout(tabellenPanelLayout);
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
		JPanel panel4b = new JPanel();
		panel4b.setLayout(flowLayout);
		panel4b.setBackground(Color.LIGHT_GRAY);
		panel4b.add(infoButton);
		JPanel panel4c = new JPanel();
		panel4c.setLayout(flowLayout);
		panel4c.setBackground(Color.LIGHT_GRAY);
		panel4c.add(tabelleSpeichernButton);
		JPanel dateiPanelLabel = new JPanel();
		dateiPanelLabel.setBackground(Color.LIGHT_GRAY);
		JLabel dateiLabel = new JLabel("Datei");
		dateiLabel.setBackground(Color.LIGHT_GRAY);
		dateiPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		dateiPanelLabel.add(dateiLabel);
		dateiPanel.add(dateiPanelLabel);
		dateiPanel.add(panel);
		dateiPanel.add(panel2);
		dateiPanel.add(panel4b);
		dateiPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel datenbankPanelLabel = new JPanel();
		datenbankPanelLabel.setBackground(Color.LIGHT_GRAY);
		JLabel datenbankLabel = new JLabel("Datenbank");
		datenbankLabel.setBackground(Color.LIGHT_GRAY);
		datenbankPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		datenbankPanelLabel.add(datenbankLabel);
		datenbankPanel.add(datenbankPanelLabel);
		datenbankPanel.add(panel3);
		datenbankPanel.add(panel4);
		datenbankPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel tabellenPanelLabel = new JPanel();
		tabellenPanelLabel.setBackground(Color.LIGHT_GRAY);
		JLabel tabellenLabel = new JLabel("Tabellen");
		tabellenPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabellenPanelLabel.add(tabellenLabel);
		tabellenPanel.add(tabellenPanelLabel);
		tabellenPanel.add(panel4c);
		tabellenPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel panel5 = new JPanel();
		BoxLayout boxLayout = new BoxLayout(panel5, BoxLayout.Y_AXIS);
		panel5.setLayout(boxLayout);
		panel5.setBackground(Color.LIGHT_GRAY);
		panel5.add(dateiPanel);
		panel5.add(datenbankPanel);
		//panel5.add(tabellenPanel);
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

	public JButton getInfoButton() {
		return infoButton;
	}

	public void setInfoButton(JButton infoButton) {
		this.infoButton = infoButton;
	}

	public JPanel getTabellenPanel() {
		return tabellenPanel;
	}

	public void setTabellenPanel(JPanel tabellenPanel) {
		this.tabellenPanel = tabellenPanel;
	}

	public JPanel getDatenbankPanel() {
		return datenbankPanel;
	}

	public void setDatenbankPanel(JPanel datenbankPanel) {
		this.datenbankPanel = datenbankPanel;
	}

	public JPanel getDateiPanel() {
		return dateiPanel;
	}

	public void setDateiPanel(JPanel dateiPanel) {
		this.dateiPanel = dateiPanel;
	}

}