package de.turnierverwaltung.view.navigation;

//JKlubTV - Ein Programm zum verwalten von Schach Turnieren
//Copyright (C) 2015  Martin Schmuck m_schmuck@gmx.net
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import de.turnierverwaltung.control.Version;
import de.turnierverwaltung.view.Messages;

public class NaviView extends JToolBar {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tabellenname;
	private final JLabel tabellenLabel;
	private String gruppenname;
	private final JLabel gruppenLabel;

	private final ImageIcon dbNewIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/db_add.png"))); //$NON-NLS-1$
	private final ImageIcon dbLoadIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-open-4.png"))); //$NON-NLS-1$
	private final ImageIcon tabelleSpeichernIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-save-5.png"))); //$NON-NLS-1$
	private final ImageIcon tabelleAktualisierenIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-refresh-6.png"))); //$NON-NLS-1$
	private final ImageIcon tabelleHTMLAusgabeIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/homepage.png"))); //$NON-NLS-1$
	private final ImageIcon turnierNew = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-new.png"))); //$NON-NLS-1$
	private JButton turnierAddButton;
	private final ImageIcon userNew = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-new-3.png"))); //$NON-NLS-1$
	private final ImageIcon userImport = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-open-4.png"))); //$NON-NLS-1$
	private final ImageIcon userExport = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-export.png"))); //$NON-NLS-1$
	private final ImageIcon DEWISSearch = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/db.png"))); //$NON-NLS-1$
	private final ImageIcon iCalendarIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/accessories-date.png"))); //$NON-NLS-1$
	private final ImageIcon exitIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/application-exit-5.png")));
	private final ImageIcon pairingsCancelIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/dialog-cancel-3.png")));
	private final ImageIcon launcherIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/launcher.png")));
	private final ImageIcon logoImg = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/jklubtv-logo.png")));
	private JButton spielerImport;
	private JButton spielerExport;
	private JButton spielerDEWISSearchButton;
	private JButton spielerAddButton;
	private JButton wizardButton;
	private JButton newDatabseButton;
	private JButton loadDatabaseButton;
	private JButton propertiesButton;
	private JButton infoButton;
	private JButton exitButton;
	private JLabel pathToDatabase;
	private final ImageIcon propertiesIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/configure-2.png"))); //$NON-NLS-1$
	private final ImageIcon infoIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png"))); //$NON-NLS-1$
	private final ImageIcon pdfIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/acroread-2.png"))); //$NON-NLS-1$
	private final ImageIcon excelIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/excel-1.png"))); //$NON-NLS-1$
	private JButton tabelleSpeichernButton;
	private JPanel tabellenPanel;
	private JPanel dateiPanel;
	private JButton tabelleAktualisierenButton;
	private JButton tabelleHTMLAusgabeButton;
	private JButton pdfSpeichernButton;
	private JButton iCalendarSpeichernButton;

	private JPanel turnierListePanel;
	private JPanel spielerListePanel;
	private JButton excelSpeichernButton;
	private JButton pairingsLoadButton;
	private JButton pairingsSaveButton;
	private JButton pairingsCancelButton;
	private final ImageIcon pairingsLoadIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/media-playlist-shuffle-3.png")));
	private final ImageIcon pairingsSaveIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-save-5.png")));
	private final ImageIcon updateButtonIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/view-refresh-6.png")));
	private JPanel pairingsPanel;
	private final JLabel pairingsLabel;
	private String pairingsname;
	private JButton updateButton;
	private JButton spielerELOSearchButton;

	public NaviView() {
		final Color titleColor = new Color((SystemColor.control).getRGB());
		final Color titleTextColor = new Color((SystemColor.controlText).getRGB());
		setBackground(titleColor);
		setForeground(titleTextColor);
		final JLabel logoLabel = new JLabel(logoImg);
		final JLabel logoText = new JLabel("Version: " + Version.getString("version.1"));
		setLayout(new BorderLayout());
		setRollover(true);
		wizardButton = new JButton(Messages.getString("NaviView.35"), launcherIcon);
		wizardButton.setPreferredSize(new Dimension(200, 40));
		wizardButton.setHorizontalAlignment(SwingConstants.LEFT);
		newDatabseButton = new JButton(Messages.getString("NaviView.11"), dbNewIcon); //$NON-NLS-1$
		newDatabseButton.setPreferredSize(new Dimension(200, 40));
		newDatabseButton.setHorizontalAlignment(SwingConstants.LEFT);
		loadDatabaseButton = new JButton(Messages.getString("NaviView.12"), dbLoadIcon); //$NON-NLS-1$
		loadDatabaseButton.setPreferredSize(new Dimension(200, 40));
		loadDatabaseButton.setHorizontalAlignment(SwingConstants.LEFT);

		propertiesButton = new JButton(Messages.getString("NaviView.29"), propertiesIcon); //$NON-NLS-1$
		propertiesButton.setPreferredSize(new Dimension(200, 40));
		propertiesButton.setHorizontalAlignment(SwingConstants.LEFT);
		infoButton = new JButton(Messages.getString("NaviView.30"), infoIcon); //$NON-NLS-1$
		infoButton.setPreferredSize(new Dimension(200, 40));
		infoButton.setHorizontalAlignment(SwingConstants.LEFT);
		exitButton = new JButton(Messages.getString("NaviView.32"), exitIcon); //$NON-NLS-1$
		exitButton.setPreferredSize(new Dimension(200, 40));
		exitButton.setHorizontalAlignment(SwingConstants.LEFT);
		tabelleSpeichernButton = new JButton(Messages.getString("NaviView.13"), tabelleSpeichernIcon); //$NON-NLS-1$
		tabelleSpeichernButton.setPreferredSize(new Dimension(200, 40));
		tabelleSpeichernButton.setHorizontalAlignment(SwingConstants.LEFT);
		tabelleAktualisierenButton = new JButton(Messages.getString("NaviView.14"), tabelleAktualisierenIcon); //$NON-NLS-1$
		tabelleAktualisierenButton.setPreferredSize(new Dimension(200, 40));
		tabelleAktualisierenButton.setHorizontalAlignment(SwingConstants.LEFT);
		pdfSpeichernButton = new JButton(Messages.getString("NaviView.15"), //$NON-NLS-1$
				pdfIcon);
		pdfSpeichernButton.setPreferredSize(new Dimension(200, 40));
		pdfSpeichernButton.setHorizontalAlignment(SwingConstants.LEFT);

		iCalendarSpeichernButton = new JButton(Messages.getString("NaviView.31"), //$NON-NLS-1$
				iCalendarIcon);
		iCalendarSpeichernButton.setPreferredSize(new Dimension(200, 40));
		iCalendarSpeichernButton.setHorizontalAlignment(SwingConstants.LEFT);

		excelSpeichernButton = new JButton(Messages.getString("NaviView.2"), excelIcon); //$NON-NLS-1$
		excelSpeichernButton.setPreferredSize(new Dimension(200, 40));
		excelSpeichernButton.setHorizontalAlignment(SwingConstants.LEFT);
		tabelleHTMLAusgabeButton = new JButton(Messages.getString("NaviView.16"), tabelleHTMLAusgabeIcon); //$NON-NLS-1$
		tabelleHTMLAusgabeButton.setPreferredSize(new Dimension(200, 40));
		tabelleHTMLAusgabeButton.setHorizontalAlignment(SwingConstants.LEFT);

		pairingsLoadButton = new JButton(Messages.getString("NaviView.27"), pairingsLoadIcon);
		pairingsLoadButton.setPreferredSize(new Dimension(200, 40));
		pairingsLoadButton.setHorizontalAlignment(SwingConstants.LEFT);

		pairingsSaveButton = new JButton(Messages.getString("NaviView.28"), pairingsSaveIcon);
		pairingsSaveButton.setPreferredSize(new Dimension(200, 40));
		pairingsSaveButton.setHorizontalAlignment(SwingConstants.LEFT);

		pairingsCancelButton = new JButton(Messages.getString("NaviView.33"), pairingsCancelIcon);
		pairingsCancelButton.setPreferredSize(new Dimension(200, 40));
		pairingsCancelButton.setHorizontalAlignment(SwingConstants.LEFT);

		dateiPanel = new JPanel();
		// dateiPanel.setBackground(Color.LIGHT_GRAY);
		final BoxLayout dateiPanelLayout = new BoxLayout(dateiPanel, BoxLayout.PAGE_AXIS);
		dateiPanel.setLayout(dateiPanelLayout);
		// Turnierliste
		turnierAddButton = new JButton(Messages.getString("NaviView.17"), turnierNew); //$NON-NLS-1$
		turnierAddButton.setPreferredSize(new Dimension(200, 40));
		turnierAddButton.setHorizontalAlignment(SwingConstants.LEFT);
		turnierListePanel = new JPanel();
		// turnierListePanel.setBackground(Color.LIGHT_GRAY);
		final BoxLayout turnierListePanelLayout = new BoxLayout(turnierListePanel, BoxLayout.PAGE_AXIS);
		turnierListePanel.setLayout(turnierListePanelLayout);
		NaviPanelElementView panel3 = new NaviPanelElementView();

		panel3.add(turnierAddButton);
		NaviTitleLabelView titleView = new NaviTitleLabelView(Messages.getString("NaviView.18"));

		turnierListePanel.add(titleView);
		turnierListePanel.add(panel3);
		turnierListePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// neuer Spieler
		spielerAddButton = new JButton(Messages.getString("NaviView.19"), userNew); //$NON-NLS-1$
		spielerAddButton.setPreferredSize(new Dimension(200, 40));
		spielerAddButton.setHorizontalAlignment(SwingConstants.LEFT);
		spielerListePanel = new JPanel();
		// spielerListePanel.setBackground(Color.LIGHT_GRAY);
		final BoxLayout spielerListePanelLayout = new BoxLayout(spielerListePanel, BoxLayout.PAGE_AXIS);
		spielerListePanel.setLayout(spielerListePanelLayout);
		panel3 = new NaviPanelElementView();

		panel3.add(spielerAddButton);
		titleView = new NaviTitleLabelView(Messages.getString("NaviView.20"));

		spielerListePanel.add(titleView);
		spielerListePanel.add(panel3);
		spielerListePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		// DWZ Abfrage
		spielerDEWISSearchButton = new JButton(Messages.getString("NaviView.23"), DEWISSearch); //$NON-NLS-1$
		spielerDEWISSearchButton.setPreferredSize(new Dimension(200, 40));
		spielerDEWISSearchButton.setHorizontalAlignment(SwingConstants.LEFT);

		panel3 = new NaviPanelElementView();

		panel3.add(spielerDEWISSearchButton);

		spielerListePanel.add(panel3);
		// ELO Abfrage
		spielerELOSearchButton = new JButton(Messages.getString("NaviView.34"), DEWISSearch); //$NON-NLS-1$
		spielerELOSearchButton.setPreferredSize(new Dimension(200, 40));
		spielerELOSearchButton.setHorizontalAlignment(SwingConstants.LEFT);

		panel3 = new NaviPanelElementView();

		panel3.add(spielerELOSearchButton);

		spielerListePanel.add(panel3);
		// update dwz
		updateButton = new JButton(Messages.getString("DEWISDialogView.5"), updateButtonIcon); //$NON-NLS-1$
		updateButton.setPreferredSize(new Dimension(200, 40));
		updateButton.setHorizontalAlignment(SwingConstants.LEFT);

		panel3 = new NaviPanelElementView();

		panel3.add(updateButton);

		spielerListePanel.add(panel3);
		// Spieler Import
		spielerImport = new JButton(Messages.getString("NaviView.21"), userImport); //$NON-NLS-1$
		spielerImport.setPreferredSize(new Dimension(200, 40));
		spielerImport.setHorizontalAlignment(SwingConstants.LEFT);

		panel3 = new NaviPanelElementView();

		panel3.add(spielerImport);

		spielerListePanel.add(panel3);

		// Spieler Export
		spielerExport = new JButton(Messages.getString("NaviView.22"), userExport); //$NON-NLS-1$
		spielerExport.setPreferredSize(new Dimension(200, 40));
		spielerExport.setHorizontalAlignment(SwingConstants.LEFT);

		panel3 = new NaviPanelElementView();

		panel3.add(spielerExport);

		spielerListePanel.add(panel3);

		tabellenPanel = new NaviPanelElementView();
		final BoxLayout tabellenPanelLayout = new BoxLayout(tabellenPanel, BoxLayout.PAGE_AXIS);
		tabellenPanel.setLayout(tabellenPanelLayout);
		final NaviPanelElementView panel = new NaviPanelElementView();

		panel.add(newDatabseButton);
		final NaviPanelElementView panel2 = new NaviPanelElementView();
		panel2.add(loadDatabaseButton);
		final NaviPanelElementView panel2e = new NaviPanelElementView();
		panel2e.add(wizardButton);

		final NaviPanelElementView panel2a = new NaviPanelElementView();
		// panel2a.getFlowLayout().setVgap(1);

		panel2a.add(propertiesButton);
		final NaviPanelElementView panel2b = new NaviPanelElementView();
		// panel2b.getFlowLayout().setVgap(1);

		panel2b.add(infoButton);
		final NaviPanelElementView panel2c = new NaviPanelElementView();
		panel2c.add(exitButton);
		final NaviPanelElementView panelLabel = new NaviPanelElementView();
		// panelLabel.getFlowLayout().setVgap(1);
		pathToDatabase = new JLabel(" "); //$NON-NLS-1$
		panelLabel.add(pathToDatabase);

		final NaviPanelElementView panel4c = new NaviPanelElementView();
		panel4c.add(tabelleAktualisierenButton);
		final NaviPanelElementView panel4d = new NaviPanelElementView();
		panel4d.add(tabelleSpeichernButton);
		final NaviPanelElementView panel4e = new NaviPanelElementView();
		panel4e.add(tabelleHTMLAusgabeButton);
		final NaviPanelElementView panel4f = new NaviPanelElementView();
		panel4f.add(pdfSpeichernButton);

		final NaviPanelElementView panel4g = new NaviPanelElementView();
		panel4g.add(excelSpeichernButton);
		final NaviPanelElementView panel4i = new NaviPanelElementView();
		panel4i.add(iCalendarSpeichernButton);
		final NaviPanelElementView panel4h = new NaviPanelElementView();
		// panel4h.setBackground(Color.LIGHT_GRAY);
		panel4h.add(pairingsLoadButton);

		final NaviPanelElementView panel4k = new NaviPanelElementView();
		panel4k.add(pairingsSaveButton);
		final NaviPanelElementView panel4l = new NaviPanelElementView();
		panel4l.add(pairingsCancelButton);

		titleView = new NaviTitleLabelView(Messages.getString("NaviView.25"));

		dateiPanel.add(titleView);
		dateiPanel.add(panel);
		dateiPanel.add(panel2);
		dateiPanel.add(panel2e);
		dateiPanel.add(panel2a);
		dateiPanel.add(panel2b);
		dateiPanel.add(panel2c);

		dateiPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		final NaviPanelElementView datenbankPanelLabel = new NaviPanelElementView();

		final JLabel datenbankLabel = new JLabel(Messages.getString("NaviView.26")); //$NON-NLS-1$
		// datenbankLabel.setBackground(Color.LIGHT_GRAY);
		datenbankPanelLabel.add(datenbankLabel);

		titleView = new NaviTitleLabelView(tabellenname);
		tabellenLabel = titleView.getTitleLabel();

		final NaviPanelElementView gruppenPanelLabel = new NaviPanelElementView();
		gruppenLabel = new JLabel(gruppenname);
		gruppenPanelLabel.add(gruppenLabel);

		tabellenPanel.add(titleView);

		tabellenPanel.add(panel4c);
		tabellenPanel.add(panel4d);
		JLabel minusminus = new JLabel(" ");
		NaviPanelElementView minusminusPanel = new NaviPanelElementView();

		minusminusPanel.add(minusminus);
		tabellenPanel.add(minusminusPanel);
		tabellenPanel.add(panel4e);
		tabellenPanel.add(panel4f);
		tabellenPanel.add(panel4g);
		tabellenPanel.add(panel4i);
		minusminus = new JLabel(" ");
		minusminusPanel = new NaviPanelElementView();

		minusminusPanel.add(minusminus);
		tabellenPanel.add(minusminusPanel);
		tabellenPanel.add(panel4h);

		pairingsPanel = new NaviPanelElementView();
		final BoxLayout pairingsPanelLayout = new BoxLayout(pairingsPanel, BoxLayout.PAGE_AXIS);
		pairingsPanel.setLayout(pairingsPanelLayout);
		titleView = new NaviTitleLabelView(pairingsname);

		pairingsLabel = titleView.getTitleLabel();

		pairingsPanel.add(titleView);
		pairingsPanel.add(panel4k);
		pairingsPanel.add(panel4l);
		tabellenPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		pairingsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dateiPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		final NaviPanelElementView panel5 = new NaviPanelElementView();
		final BoxLayout boxLayout = new BoxLayout(panel5, BoxLayout.Y_AXIS);
		panel5.setLayout(boxLayout);

		panel5.add(dateiPanel);
		panel5.add(tabellenPanel);
		panel5.add(pairingsPanel);
		panel5.add(turnierListePanel);
		panel5.add(spielerListePanel);

		this.add(panel5, BorderLayout.NORTH);
		final JPanel logoPanel1 = new JPanel();
		logoPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
		final JPanel logoPanel2 = new JPanel();
		logoPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
		logoPanel1.add(logoLabel);
		logoText.setForeground(Color.WHITE);
		logoText.setBackground(Color.BLACK);
		logoPanel2.add(logoText);

		logoPanel1.setBackground(Color.BLACK);
		logoPanel1.setForeground(Color.WHITE);
		logoPanel2.setBackground(Color.BLACK);
		logoPanel2.setForeground(Color.WHITE);
		final JPanel logoCenterPanel = new JPanel();
		logoCenterPanel.setLayout(new BorderLayout());

		logoCenterPanel.setBackground(titleColor);
		logoCenterPanel.setForeground(titleTextColor);
		logoCenterPanel.add(logoPanel1, BorderLayout.CENTER);
		logoCenterPanel.add(logoPanel2, BorderLayout.SOUTH);
		this.add(logoCenterPanel, BorderLayout.SOUTH);

	}

	public JPanel getDateiPanel() {
		return dateiPanel;
	}

	public JButton getExcelSpeichernButton() {
		return excelSpeichernButton;
	}

	public JButton getExitButton() {
		return exitButton;
	}

	public String getGruppenname() {
		return gruppenname;
	}

	public JButton getiCalendarSpeichernButton() {
		return iCalendarSpeichernButton;
	}

	public JButton getInfoButton() {
		return infoButton;
	}

	public JButton getLoadDatabaseButton() {
		return loadDatabaseButton;
	}

	public JButton getNewDatabseButton() {
		return newDatabseButton;
	}

	public JButton getPairingsCancelButton() {
		return pairingsCancelButton;
	}

	public JButton getPairingsLoadButton() {
		return pairingsLoadButton;
	}

	public JPanel getPairingsPanel() {
		return pairingsPanel;
	}

	public JButton getPairingsSaveButton() {
		return pairingsSaveButton;
	}

	public JLabel getPathToDatabase() {
		return pathToDatabase;
	}

	public JButton getPdfSpeichernButton() {
		return pdfSpeichernButton;
	}

	public JButton getPropertiesButton() {
		return propertiesButton;
	}

	public JButton getSpielerAddButton() {
		return spielerAddButton;
	}

	public JButton getSpielerDEWISSearchButton() {
		return spielerDEWISSearchButton;
	}

	public JButton getSpielerELOSearchButton() {
		return spielerELOSearchButton;
	}

	public JButton getSpielerExport() {
		return spielerExport;
	}

	public JButton getSpielerImport() {
		return spielerImport;
	}

	public JPanel getSpielerListePanel() {
		return spielerListePanel;
	}

	public JButton getTabelleAktualisierenButton() {
		return tabelleAktualisierenButton;
	}

	public JButton getTabelleHTMLAusgabeButton() {
		return tabelleHTMLAusgabeButton;
	}

	public String getTabellenname() {
		return tabellenname;
	}

	public JPanel getTabellenPanel() {
		return tabellenPanel;
	}

	public JButton getTabelleSpeichernButton() {
		return tabelleSpeichernButton;
	}

	public JButton getTurnierAddButton() {
		return turnierAddButton;
	}

	public JPanel getTurnierListePanel() {
		return turnierListePanel;
	}

	public JButton getUpdateButton() {
		return updateButton;
	}

	public void setDateiPanel(final JPanel dateiPanel) {
		this.dateiPanel = dateiPanel;
	}

	public void setExcelSpeichernButton(final JButton excelSpeichernButton) {
		this.excelSpeichernButton = excelSpeichernButton;
	}

	public void setExitButton(final JButton exitButton) {
		this.exitButton = exitButton;
	}

	public void setGruppenname(final String gruppenname) {
		this.gruppenname = gruppenname;
		gruppenLabel.setText(gruppenname);
		gruppenLabel.updateUI();
	}

	public void setiCalendarSpeichernButton(final JButton iCalendarSpeichernButton) {
		this.iCalendarSpeichernButton = iCalendarSpeichernButton;
	}

	public void setInfoButton(final JButton infoButton) {
		this.infoButton = infoButton;
	}

	public void setLoadDatabaseButton(final JButton loadDatabaseButton) {
		this.loadDatabaseButton = loadDatabaseButton;
	}

	public void setNewDatabseButton(final JButton newDatabseButton) {
		this.newDatabseButton = newDatabseButton;
	}

	public void setPairingsCancelButton(final JButton pairingsCancelButton) {
		this.pairingsCancelButton = pairingsCancelButton;
	}

	public void setPairingsLoadButton(final JButton pairingsLoadButton) {
		this.pairingsLoadButton = pairingsLoadButton;
	}

	public void setPairingsPanel(final JPanel pairingsPanel) {
		this.pairingsPanel = pairingsPanel;
	}

	public void setPairingsSaveButton(final JButton pairingsSaveButton) {
		this.pairingsSaveButton = pairingsSaveButton;
	}

	public void setPathToDatabase(final JLabel pathToDatabase) {
		this.pathToDatabase = pathToDatabase;
		this.pathToDatabase.updateUI();
	}

	public void setPdfSpeichernButton(final JButton pdfSpeichernButton) {
		this.pdfSpeichernButton = pdfSpeichernButton;
	}

	public void setPropertiesButton(final JButton propertiesButton) {
		this.propertiesButton = propertiesButton;
	}

	public void setSpielerAddButton(final JButton spielerAddButton) {
		this.spielerAddButton = spielerAddButton;
	}

	public void setSpielerDEWISSearchButton(final JButton spielerDEWISSearchButton) {
		this.spielerDEWISSearchButton = spielerDEWISSearchButton;
	}

	public void setSpielerELOSearchButton(final JButton spielerELOSearchButton) {
		this.spielerELOSearchButton = spielerELOSearchButton;
	}

	public void setSpielerExport(final JButton spielerExport) {
		this.spielerExport = spielerExport;
	}

	public void setSpielerImport(final JButton spielerImport) {
		this.spielerImport = spielerImport;
	}

	public void setSpielerListePanel(final JPanel spielerListePanel) {
		this.spielerListePanel = spielerListePanel;
	}

	public void setTabelleAktualisierenButton(final JButton tabelleAktualisierenButton) {
		this.tabelleAktualisierenButton = tabelleAktualisierenButton;
	}

	public void setTabelleHTMLAusgabeButton(final JButton tabelleHTMLAusgabeButton) {
		this.tabelleHTMLAusgabeButton = tabelleHTMLAusgabeButton;
	}

	public void setTabellenname(final String tabellenname) {
		this.tabellenname = tabellenname;
		tabellenLabel.setText(tabellenname);
		tabellenLabel.updateUI();
		pairingsname = tabellenname;
		pairingsLabel.setText(tabellenname);
		pairingsLabel.updateUI();
	}

	public void setTabellenPanel(final JPanel tabellenPanel) {
		this.tabellenPanel = tabellenPanel;
	}

	public void setTabelleSpeichernButton(final JButton tabelleSpeichernButton) {
		this.tabelleSpeichernButton = tabelleSpeichernButton;
	}

	public void setTurnierAddButton(final JButton turnierAddButton) {
		this.turnierAddButton = turnierAddButton;
	}

	public void setTurnierListePanel(final JPanel turnierListePanel) {
		this.turnierListePanel = turnierListePanel;
	}

	public void setUpdateButton(final JButton updateButton) {
		this.updateButton = updateButton;
	}

	public JButton getWizardButton() {
		return wizardButton;
	}

	public void setWizardButton(final JButton wizardButton) {
		this.wizardButton = wizardButton;
	}

}
