package de.turnierverwaltung.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class NaviView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tabellenname;
	private JLabel tabellenLabel;
	private String gruppenname;
	private JLabel gruppenLabel;	
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
	private ImageIcon tabelleAktualisierenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-refresh-6.png")));
	private ImageIcon tabelleHTMLAusgabeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/homepage.png")));
	private JButton newDatabseButton;
	private JButton loadDatabaseButton;
	private JLabel pathToDatabase;
	private JButton infoButton;
	private ImageIcon infoIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png")));
	private ImageIcon pdfIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/acroread-2.png")));
	private JButton tabelleSpeichernButton;
	private JPanel tabellenPanel;
	private JPanel datenbankPanel;
	private JPanel dateiPanel;
	private JButton tabelleAktualisierenButton;
	private JButton tabelleHTMLAusgabeButton;
	private JButton pdfSpeichernButton;


	public NaviView() {
		this.setBackground(Color.LIGHT_GRAY);
		EmptyBorder eBorder = new EmptyBorder(5, 5, 5, 5);
		this.setBorder(eBorder);
		newDatabseButton = new JButton("Neue Datenbank", dbNewIcon);
		newDatabseButton.setPreferredSize(new Dimension(200, 40));
		newDatabseButton.setHorizontalAlignment(SwingConstants.LEFT);
		loadDatabaseButton = new JButton("Datenbank laden", dbLoadIcon);
		loadDatabaseButton.setPreferredSize(new Dimension(200, 40));
		loadDatabaseButton.setHorizontalAlignment(SwingConstants.LEFT);
		turnierListeButton = new JButton("Turnierliste", turnierListeIcon);
		turnierListeButton.setPreferredSize(new Dimension(200, 40));
		turnierListeButton.setHorizontalAlignment(SwingConstants.LEFT);
		spielerListeButton = new JButton("Spielerliste", spielerListeIcon);
		spielerListeButton.setPreferredSize(new Dimension(200, 40));
		spielerListeButton.setHorizontalAlignment(SwingConstants.LEFT);
		infoButton = new JButton("Info", infoIcon);
		infoButton.setPreferredSize(new Dimension(200, 40));
		infoButton.setHorizontalAlignment(SwingConstants.LEFT);
		tabelleSpeichernButton = new JButton("Speichern", tabelleSpeichernIcon);
		tabelleSpeichernButton.setPreferredSize(new Dimension(200, 40));
		tabelleSpeichernButton.setHorizontalAlignment(SwingConstants.LEFT);
		tabelleAktualisierenButton = new JButton("Aktualisieren", tabelleAktualisierenIcon);
		tabelleAktualisierenButton.setPreferredSize(new Dimension(200, 40));
		tabelleAktualisierenButton.setHorizontalAlignment(SwingConstants.LEFT);
		pdfSpeichernButton = new JButton("PDF Ausgabe", pdfIcon);
		pdfSpeichernButton.setPreferredSize(new Dimension(200, 40));
		pdfSpeichernButton.setHorizontalAlignment(SwingConstants.LEFT);
		tabelleHTMLAusgabeButton = new JButton("HTML Ausgabe", tabelleHTMLAusgabeIcon);
		tabelleHTMLAusgabeButton.setPreferredSize(new Dimension(200, 40));
		tabelleHTMLAusgabeButton.setHorizontalAlignment(SwingConstants.LEFT);
		
		
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
		panel4c.add(tabelleAktualisierenButton);
		JPanel panel4d = new JPanel();
		panel4d.setLayout(flowLayout);
		panel4d.setBackground(Color.LIGHT_GRAY);
		panel4d.add(tabelleSpeichernButton);
		JPanel panel4e = new JPanel();
		panel4e.setLayout(flowLayout);
		panel4e.setBackground(Color.LIGHT_GRAY);
		panel4e.add(tabelleHTMLAusgabeButton);
		JPanel panel4f = new JPanel();
		panel4f.setLayout(flowLayout);
		panel4f.setBackground(Color.LIGHT_GRAY);
		panel4f.add(pdfSpeichernButton);
		
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
		tabellenLabel = new JLabel(tabellenname);
		tabellenPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabellenPanelLabel.add(tabellenLabel);

		JPanel gruppenPanelLabel = new JPanel();
		gruppenPanelLabel.setBackground(Color.LIGHT_GRAY);
		gruppenLabel = new JLabel(gruppenname);
		gruppenPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		gruppenPanelLabel.add(gruppenLabel);

		JPanel turnierTabellenPanel = new JPanel();
		turnierTabellenPanel.setBackground(Color.LIGHT_GRAY);
		JLabel turnierTabellenLabel = new JLabel("Tabellen");
		turnierTabellenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		turnierTabellenPanel.add(turnierTabellenLabel);
		
		tabellenPanel.add(turnierTabellenPanel);
		
		tabellenPanel.add(panel4c);
		tabellenPanel.add(panel4d);
//		tabellenPanel.add(gruppenPanelLabel);
//		tabellenPanel.add(tabellenPanelLabel);
		tabellenPanel.add(panel4e);
		tabellenPanel.add(panel4f);
		tabellenPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		
//		paarungsPanel = new JPanel();
//		paarungsPanel.setBackground(Color.LIGHT_GRAY);
//		BoxLayout paarungsPanelLayout = new BoxLayout(paarungsPanel, BoxLayout.PAGE_AXIS);
//		paarungsPanel.setLayout(paarungsPanelLayout);

//		JPanel paarungsPanelLabel = new JPanel();
//		paarungsPanelLabel.setBackground(Color.LIGHT_GRAY);
//		paarungsLabel = new JLabel(paarungsname);
//		paarungsPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
//		paarungsPanelLabel.add(paarungsLabel);
//		paarungsPanel.add(paarungsPanelLabel);	
//		paarungsPanel.add(panel4g);
//		paarungsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel panel5 = new JPanel();
		BoxLayout boxLayout = new BoxLayout(panel5, BoxLayout.Y_AXIS);
		panel5.setLayout(boxLayout);
		panel5.setBackground(Color.LIGHT_GRAY);
		panel5.add(dateiPanel);
		panel5.add(datenbankPanel);
		panel5.add(tabellenPanel);
//		panel5.add(paarungsPanel);
		
		this.add(panel5);

	}

//	public JPanel getPaarungsPanel() {
//		return paarungsPanel;
//	}
//
//	public void setPaarungsPanel(JPanel paarungsPanel) {
//		this.paarungsPanel = paarungsPanel;
//		
//	}

//	public String getPaarungsname() {
//		return paarungsname;
//	}
//
//	public void setPaarungsname(String paarungsname) {
//		this.paarungsname = paarungsname;
//		paarungsLabel.setText(this.paarungsname);
//		paarungsLabel.updateUI();
//	}

	public String getGruppenname() {
		return gruppenname;
	}

	public void setGruppenname(String gruppenname) {
		this.gruppenname = gruppenname;
		gruppenLabel.setText(gruppenname);
		gruppenLabel.updateUI();
	}

	public String getTabellenname() {
		return tabellenname;
	}

	public void setTabellenname(String tabellenname) {
		this.tabellenname = tabellenname;
		tabellenLabel.setText(tabellenname);
		tabellenLabel.updateUI();
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

	public JButton getTabelleSpeichernButton() {
		return tabelleSpeichernButton;
	}

	public void setTabelleSpeichernButton(JButton tabelleSpeichernButton) {
		this.tabelleSpeichernButton = tabelleSpeichernButton;
	}

	public JButton getTabelleAktualisierenButton() {
		return tabelleAktualisierenButton;
	}

	public void setTabelleAktualisierenButton(JButton tabelleAktualisierenButton) {
		this.tabelleAktualisierenButton = tabelleAktualisierenButton;
	}

	public JButton getTabelleHTMLAusgabeButton() {
		return tabelleHTMLAusgabeButton;
	}

	public void setTabelleHTMLAusgabeButton(JButton tabelleHTMLAusgabeButton) {
		this.tabelleHTMLAusgabeButton = tabelleHTMLAusgabeButton;
	}

	public JButton getPdfSpeichernButton() {
		return pdfSpeichernButton;
	}

	public void setPdfSpeichernButton(JButton pdfSpeichernButton) {
		this.pdfSpeichernButton = pdfSpeichernButton;
	}

	
}
