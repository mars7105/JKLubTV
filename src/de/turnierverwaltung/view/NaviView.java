package de.turnierverwaltung.view;

import java.awt.BorderLayout;
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
	private ImageIcon turnierNew = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-new.png")));
	private JButton turnierAddButton;
	private ImageIcon userNew = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-new-3.png")));
	private ImageIcon userImport = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-open-4.png")));
	private ImageIcon userExport = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-export.png")));
	private ImageIcon DEWISSearch = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/db.png")));
	private JButton spielerImport;
	private JButton spielerExport;
	private JButton spielerDEWISSearchButton;
	private JButton spielerAddButton;

	private JButton newDatabseButton;
	private JButton loadDatabaseButton;
	private JLabel pathToDatabase;

	private ImageIcon pdfIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/acroread-2.png")));
	private JButton tabelleSpeichernButton;
	private JPanel tabellenPanel;
	private JPanel dateiPanel;
	private JButton tabelleAktualisierenButton;
	private JButton tabelleHTMLAusgabeButton;
	private JButton pdfSpeichernButton;
	private JPanel turnierListePanel;
	private JPanel spielerListePanel;

	public NaviView() {
		this.setBackground(Color.LIGHT_GRAY);

		this.setLayout(new BorderLayout());
		newDatabseButton = new JButton("Neue Datenbank", dbNewIcon);
		newDatabseButton.setPreferredSize(new Dimension(200, 40));
		newDatabseButton.setHorizontalAlignment(SwingConstants.LEFT);
		loadDatabaseButton = new JButton("Datenbank laden", dbLoadIcon);
		loadDatabaseButton.setPreferredSize(new Dimension(200, 40));
		loadDatabaseButton.setHorizontalAlignment(SwingConstants.LEFT);

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
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		// Turnierliste
		turnierAddButton = new JButton("Neues Turnier", turnierNew);
		turnierAddButton.setPreferredSize(new Dimension(200, 40));
		turnierAddButton.setHorizontalAlignment(SwingConstants.LEFT);
		turnierListePanel = new JPanel();
		turnierListePanel.setBackground(Color.LIGHT_GRAY);
		BoxLayout turnierListePanelLayout = new BoxLayout(turnierListePanel, BoxLayout.PAGE_AXIS);
		turnierListePanel.setLayout(turnierListePanelLayout);
		JPanel panel3 = new JPanel();
		panel3.setLayout(flowLayout);
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.add(turnierAddButton);
		JLabel turnierListeLabel = new JLabel("Turnierliste");
		turnierListeLabel.setBackground(Color.LIGHT_GRAY);
		JPanel turnierListePanelLabel = new JPanel();
		turnierListePanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		turnierListePanelLabel.setBackground(Color.LIGHT_GRAY);
		turnierListePanelLabel.add(turnierListeLabel);
		turnierListePanel.add(turnierListePanelLabel);
		turnierListePanel.add(panel3);
		turnierListePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// neuer Spieler
		spielerAddButton = new JButton("Neuer Spieler", userNew);
		spielerAddButton.setPreferredSize(new Dimension(200, 40));
		spielerAddButton.setHorizontalAlignment(SwingConstants.LEFT);
		spielerListePanel = new JPanel();
		spielerListePanel.setBackground(Color.LIGHT_GRAY);
		BoxLayout spielerListePanelLayout = new BoxLayout(spielerListePanel, BoxLayout.PAGE_AXIS);
		spielerListePanel.setLayout(spielerListePanelLayout);
		panel3 = new JPanel();
		panel3.setLayout(flowLayout);
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.add(spielerAddButton);
		JLabel spielerListeLabel = new JLabel("Spielerliste");
		spielerListeLabel.setBackground(Color.LIGHT_GRAY);
		JPanel spielerListePanelLabel = new JPanel();
		spielerListePanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		spielerListePanelLabel.setBackground(Color.LIGHT_GRAY);
		spielerListePanelLabel.add(spielerListeLabel);
		spielerListePanel.add(spielerListePanelLabel);
		spielerListePanel.add(panel3);
		spielerListePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Spieler Import
		spielerImport = new JButton("Import Spielerliste", userImport);
		spielerImport.setPreferredSize(new Dimension(200, 40));
		spielerImport.setHorizontalAlignment(SwingConstants.LEFT);

		panel3 = new JPanel();
		panel3.setLayout(flowLayout);
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.add(spielerImport);

		spielerListePanel.add(panel3);

		// Spieler Export
		spielerExport = new JButton("Export Spielerliste", userExport);
		spielerExport.setPreferredSize(new Dimension(200, 40));
		spielerExport.setHorizontalAlignment(SwingConstants.LEFT);

		panel3 = new JPanel();
		panel3.setLayout(flowLayout);
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.add(spielerExport);

		spielerListePanel.add(panel3);

		// DWZ Abfrage
		spielerDEWISSearchButton = new JButton("DSB Datenbank", DEWISSearch);
		spielerDEWISSearchButton.setPreferredSize(new Dimension(200, 40));
		spielerDEWISSearchButton.setHorizontalAlignment(SwingConstants.LEFT);

		panel3 = new JPanel();
		panel3.setLayout(flowLayout);
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.add(spielerDEWISSearchButton);

		spielerListePanel.add(panel3);

		//

		tabellenPanel = new JPanel();
		tabellenPanel.setBackground(Color.LIGHT_GRAY);
		BoxLayout tabellenPanelLayout = new BoxLayout(tabellenPanel, BoxLayout.PAGE_AXIS);
		tabellenPanel.setLayout(tabellenPanelLayout);
		JPanel panel = new JPanel();
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
		dateiPanelLabel.setPreferredSize(new Dimension(200, 30));

		JLabel dateiLabel = new JLabel("Datei");
		dateiLabel.setBackground(Color.LIGHT_GRAY);
		dateiPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		dateiPanelLabel.add(dateiLabel);
		dateiPanel.add(dateiPanelLabel);
		dateiPanel.add(panel);
		dateiPanel.add(panel2);
		// dateiPanel.add(panel4b);
		dateiPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel datenbankPanelLabel = new JPanel();
		datenbankPanelLabel.setBackground(Color.LIGHT_GRAY);
		datenbankPanelLabel.setPreferredSize(new Dimension(200, 30));
		JLabel datenbankLabel = new JLabel("Datenbank");
		datenbankLabel.setBackground(Color.LIGHT_GRAY);
		datenbankPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		datenbankPanelLabel.add(datenbankLabel);
		// datenbankPanel.add(datenbankPanelLabel);
		// datenbankPanel.add(panel3);
		// datenbankPanel.add(panel4);
		// datenbankPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel tabellenPanelLabel = new JPanel();
		tabellenPanelLabel.setPreferredSize(new Dimension(200, 30));
		tabellenPanelLabel.setBackground(Color.LIGHT_GRAY);
		tabellenLabel = new JLabel(tabellenname);

		tabellenPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabellenPanelLabel.add(tabellenLabel);

		JPanel gruppenPanelLabel = new JPanel();
		gruppenPanelLabel.setBackground(Color.LIGHT_GRAY);
		gruppenLabel = new JLabel(gruppenname);
		gruppenPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		gruppenPanelLabel.add(gruppenLabel);

		tabellenPanel.add(tabellenPanelLabel);

		tabellenPanel.add(panel4c);
		tabellenPanel.add(panel4d);

		tabellenPanel.add(panel4e);
		tabellenPanel.add(panel4f);
		tabellenPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel5 = new JPanel();
		BoxLayout boxLayout = new BoxLayout(panel5, BoxLayout.Y_AXIS);
		panel5.setLayout(boxLayout);
		panel5.setBackground(Color.LIGHT_GRAY);
		panel5.add(dateiPanel);
		// panel5.add(datenbankPanel);
		panel5.add(tabellenPanel);
		panel5.add(turnierListePanel);
		panel5.add(spielerListePanel);

		this.add(panel5, BorderLayout.NORTH);

	}

	public JButton getTurnierAddButton() {
		return turnierAddButton;
	}

	public void setTurnierAddButton(JButton turnierAddButton) {
		this.turnierAddButton = turnierAddButton;
	}

	public JPanel getTurnierListePanel() {
		return turnierListePanel;
	}

	public void setTurnierListePanel(JPanel turnierListePanel) {
		this.turnierListePanel = turnierListePanel;
	}

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

	// public JButton getTurnierListeButton() {
	// return turnierListeButton;
	// }
	//
	// public void setTurnierListeButton(JButton turnierListeButton) {
	// this.turnierListeButton = turnierListeButton;
	// }
	//
	// public JButton getSpielerListeButton() {
	// return spielerListeButton;
	// }
	//
	// public void setSpielerListeButton(JButton spielerListeButton) {
	// this.spielerListeButton = spielerListeButton;
	// }

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

	// public JButton getInfoButton() {
	// return infoButton;
	// }
	//
	// public void setInfoButton(JButton infoButton) {
	// this.infoButton = infoButton;
	// }

	public JPanel getTabellenPanel() {
		return tabellenPanel;
	}

	public void setTabellenPanel(JPanel tabellenPanel) {
		this.tabellenPanel = tabellenPanel;
	}

	// public JPanel getDatenbankPanel() {
	// return datenbankPanel;
	// }
	//
	// public void setDatenbankPanel(JPanel datenbankPanel) {
	// this.datenbankPanel = datenbankPanel;
	// }

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

	public JButton getSpielerImport() {
		return spielerImport;
	}

	public void setSpielerImport(JButton spielerImport) {
		this.spielerImport = spielerImport;
	}

	public JButton getSpielerExport() {
		return spielerExport;
	}

	public void setSpielerExport(JButton spielerExport) {
		this.spielerExport = spielerExport;
	}

	public JButton getSpielerDEWISSearchButton() {
		return spielerDEWISSearchButton;
	}

	public void setSpielerDEWISSearchButton(JButton spielerDEWISSearchButton) {
		this.spielerDEWISSearchButton = spielerDEWISSearchButton;
	}

	public JButton getSpielerAddButton() {
		return spielerAddButton;
	}

	public void setSpielerAddButton(JButton spielerAddButton) {
		this.spielerAddButton = spielerAddButton;
	}

	public JPanel getSpielerListePanel() {
		return spielerListePanel;
	}

	public void setSpielerListePanel(JPanel spielerListePanel) {
		this.spielerListePanel = spielerListePanel;
	}

}
