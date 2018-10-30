package de.turnierverwaltung.view.settingsdialog;

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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.SpinnerView;
import de.turnierverwaltung.view.TitleLabelView;
import say.swing.JFontChooser;

public class SettingsView extends JPanel {

	class OpenUrlAction implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			if (e.getSource().equals(dsbHomepageButton)) {
				open(dsbHomepage);
			}
			if (e.getSource().equals(fideHomepageButton)) {
				open(fideHomepage);
			}

		}

	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static void open(final URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (final IOException e) {
				/* TODO: error handling */}
		} else {
			/* TODO: error handling */}
	}

	private final JScrollPane scrollPane;
	private final JPanel centerPane;
	private JCheckBox checkBoxHeaderFooter;
	private JCheckBox checkBoxohneDWZ;
	private JCheckBox checkBoxohneFolgeDWZ;
	private JRadioButton germanLanguageCheckBox;
	private JRadioButton englishLanguageCheckBox;
	private JPanel htmlAll;
	private final ImageIcon germanFlag = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/de.png"))); //$NON-NLS-1$
	private final ImageIcon englishFlag = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/flag-gb.png"))); //$NON-NLS-1$
	private URI dsbHomepage;
	private JButton dsbHomepageButton;
	private JButton openVereineCSVButton;
	private JLabel openVereineCSVLabel;
	private JComboBox<String> spielerListeAuswahlBox;
	private JComboBox<String> turnierListeAuswahlBox;
	private JButton openDefaultPathButton;
	private JLabel openDefaultPathLabel;
	private JTextField playerTextField;
	private JTextField newDWZTextField;
	private JTextField oldDWZTextField;
	private JTextField pointsTextField;
	private JTextField sbbTextField;
	private JTextField rankingTextField;
	private JTextField roundTextField;
	private JTextField whiteTextField;
	private JTextField blackTextField;
	private JTextField resultTextField;
	private JTextField meetingTextField;
	private JButton okButton;
	private SpinnerView forenameLengthBox;
	private SpinnerView surnameLengthBox;
	private JTextField webserverPathTextField;
	private JCheckBox checkBoxPDFLinks;
	private JButton openPlayersCSVButton;
	private JLabel openPlayersCSVLabel;
	private URI fideHomepage;
	private JButton fideHomepageButton;
	private JButton openPlayersELOButton;
	private JLabel openPlayersELOLabel;
	private JCheckBox checkBoxhtmlToClipboard;
	private JCheckBox checkBoxohneELO;
	private JCheckBox checkBoxohneFolgeELO;
	private JTextField oldELOTextField;
	private JTextField newELOTextField;
	private JButton convertELOToSQLITEButton;
	private JLabel convertELOToSQLITELabel;
	private Font selectedFont;

	private JButton convertDWZToSQLITEButton;

	private JLabel convertDWZToSQLITELabel;
	private JButton fontChooserButton;
	private JFontChooser fontChooser;
	private JButton resetPropertiesButton;
	private JTextField tableCSSTextField;
	private JLabel createELODateLabel;
	private JLabel createDWZDateLabel;
	private JTextField spielfreiTextField;

	/**
	 * Create the panel.
	 */
	public SettingsView() {
		final TitleLabelView titleView = new TitleLabelView(Messages.getString("EigenschaftenView.0"));

		final JTabbedPane tabbedPane = new JTabbedPane();
		// contentPanel = new JPanel();
		// contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		setLayout(new BorderLayout());
		spielerListeAuswahlBox = new JComboBox<String>();
		spielerListeAuswahlBox.addItem(" 5"); //$NON-NLS-1$
		spielerListeAuswahlBox.addItem("10"); //$NON-NLS-1$
		spielerListeAuswahlBox.addItem("15"); //$NON-NLS-1$
		spielerListeAuswahlBox.addItem("20"); //$NON-NLS-1$

		turnierListeAuswahlBox = new JComboBox<String>();
		turnierListeAuswahlBox.addItem(" 5"); //$NON-NLS-1$
		turnierListeAuswahlBox.addItem("10"); //$NON-NLS-1$
		turnierListeAuswahlBox.addItem("15"); //$NON-NLS-1$
		turnierListeAuswahlBox.addItem("20"); //$NON-NLS-1$

		// JLabel titleLabel = new
		// JLabel(Messages.getString("EigenschaftenView.0")); //$NON-NLS-1$
		// JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		final JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		// titlepanel.add(titleLabel);

		northPanel.add(titleView);
		northPanel.add(topPanel);
		add(northPanel, BorderLayout.NORTH);
		centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 600));
		htmlAll = new JPanel();
		htmlAll.setLayout(new BoxLayout(htmlAll, BoxLayout.PAGE_AXIS));
		settingsPanel();
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
		wrapper.add(htmlAll, BorderLayout.NORTH);
		tabbedPane.addTab(Messages.getString("EigenschaftenView.69"), wrapper);
		htmlAll = new JPanel();
		htmlAll.setLayout(new BoxLayout(htmlAll, BoxLayout.PAGE_AXIS));
		makeHTMLEigenschaften();
		wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
		wrapper.add(htmlAll, BorderLayout.NORTH);
		tabbedPane.addTab(Messages.getString("EigenschaftenView.2"), wrapper);

		htmlAll = new JPanel();
		htmlAll.setLayout(new BoxLayout(htmlAll, BoxLayout.PAGE_AXIS));
		tableLabel();
		wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
		wrapper.add(htmlAll, BorderLayout.NORTH);
		tabbedPane.addTab(Messages.getString("EigenschaftenView.21"), wrapper);

		htmlAll = new JPanel();
		htmlAll.setLayout(new BoxLayout(htmlAll, BoxLayout.PAGE_AXIS));
		downloadDWZListe();
		downloadELOListe();
		wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout());
		wrapper.add(htmlAll, BorderLayout.NORTH);
		tabbedPane.addTab(Messages.getString("EigenschaftenView.5"), wrapper);

		centerPane.add(tabbedPane, BorderLayout.CENTER);
		final ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		okButton = buttonPane.getOkButton();

		// centerPane.add(buttonPane, BorderLayout.SOUTH);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(centerPane);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
	}

	private void downloadDWZListe() {
		// ohne Header und Footer
		try {
			dsbHomepage = new URI("http://www.schachbund.de/download.html"); //$NON-NLS-1$
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}

		final TitleLabelView titleView = new TitleLabelView(Messages.getString("EigenschaftenView.70"));
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);
		createDWZDateLabel = new JLabel();
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(createDWZDateLabel);
		htmlAll.add(htmlPanel);
		dsbHomepageButton = new JButton();
		dsbHomepageButton
				.setText("<HTML><FONT color=\"#000099\"><U>http://www.schachbund.de/download.html</U></FONT></HTML>"); //$NON-NLS-1$

		dsbHomepageButton.setOpaque(false);
		dsbHomepageButton.setToolTipText(dsbHomepage.toString());
		dsbHomepageButton.addActionListener(new OpenUrlAction());
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(dsbHomepageButton);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		final JLabel labelHeader1 = new JLabel(Messages.getString("EigenschaftenView.10") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.11")); //$NON-NLS-1$
		final JLabel labelHeader1b = new JLabel(Messages.getString("EigenschaftenView.12") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.13")); //$NON-NLS-1$
		final JLabel labelHeader2 = new JLabel(Messages.getString("EigenschaftenView.14") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.15")); //$NON-NLS-1$
		final JLabel labelHeader2b = new JLabel(Messages.getString("EigenschaftenView.16")); //$NON-NLS-1$
		htmlPanel.add(labelHeader1);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader1b);

		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader2);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader2b);
		htmlAll.add(htmlPanel);

		openVereineCSVButton = new JButton(Messages.getString("EigenschaftenView.18")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openVereineCSVButton);
		openVereineCSVLabel = new JLabel();
		htmlPanel.add(openVereineCSVLabel);
		htmlAll.add(htmlPanel);

		openPlayersCSVButton = new JButton(Messages.getString("EigenschaftenView.43")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openPlayersCSVButton);
		openPlayersCSVLabel = new JLabel();
		htmlPanel.add(openPlayersCSVLabel);
		htmlAll.add(htmlPanel);

		convertDWZToSQLITEButton = new JButton(Messages.getString("EigenschaftenView.65"));
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(convertDWZToSQLITEButton);
		convertDWZToSQLITELabel = new JLabel();
		htmlPanel.add(convertDWZToSQLITELabel);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());

	}

	private void downloadELOListe() {

		try {
			fideHomepage = new URI("https://ratings.fide.com/download.phtml"); //$NON-NLS-1$
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}

		final TitleLabelView titleView = new TitleLabelView(Messages.getString("EigenschaftenView.51"));
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);
		createELODateLabel = new JLabel();
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(createELODateLabel);
		htmlAll.add(htmlPanel);
		fideHomepageButton = new JButton();
		fideHomepageButton
				.setText("<HTML><FONT color=\"#000099\"><U>https://ratings.fide.com/download.phtml</U></FONT></HTML>"); //$NON-NLS-1$

		fideHomepageButton.setOpaque(false);
		fideHomepageButton.setToolTipText(fideHomepage.toString());
		fideHomepageButton.addActionListener(new OpenUrlAction());
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(fideHomepageButton);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		final JLabel labelHeader1 = new JLabel(Messages.getString("EigenschaftenView.53") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.54")); //$NON-NLS-1$
		final JLabel labelHeader1b = new JLabel(Messages.getString("EigenschaftenView.55") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.56")); //$NON-NLS-1$
		final JLabel labelHeader2 = new JLabel(Messages.getString("EigenschaftenView.57") //$NON-NLS-1$
				+ Messages.getString("EigenschaftenView.58")); //$NON-NLS-1$
		final JLabel labelHeader2b = new JLabel(Messages.getString("EigenschaftenView.59")); //$NON-NLS-1$
		htmlPanel.add(labelHeader1);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader1b);

		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader2);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(labelHeader2b);
		htmlAll.add(htmlPanel);

		htmlAll.add(htmlPanel);

		openPlayersELOButton = new JButton(Messages.getString("EigenschaftenView.52")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openPlayersELOButton);
		openPlayersELOLabel = new JLabel();
		htmlPanel.add(openPlayersELOLabel);
		htmlAll.add(htmlPanel);

		convertELOToSQLITEButton = new JButton(Messages.getString("EigenschaftenView.66"));
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(convertELOToSQLITEButton);
		convertELOToSQLITELabel = new JLabel();
		htmlPanel.add(convertELOToSQLITELabel);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
	}

	private void settingsPanel() {
		final ButtonGroup group = new ButtonGroup();
		// deutsch
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader = new JLabel("Deutsch (erst nach Neustart sichtbar)"); //$NON-NLS-1$
		final JLabel germanFlagLabel = new JLabel(germanFlag);
		TitleLabelView titleView = new TitleLabelView("Language / Sprache");
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);
		// JPanel title = new JPanel();
		// title.setLayout(new FlowLayout(FlowLayout.LEFT));
		// title.add(new JLabel("Sprache / Language")); //$NON-NLS-1$
		// htmlAll.add(title);
		germanLanguageCheckBox = new JRadioButton();
		germanLanguageCheckBox.setSelected(true);
		group.add(germanLanguageCheckBox);
		htmlPanel.add(germanLanguageCheckBox);
		htmlPanel.add(germanFlagLabel);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		// englisch
		labelHeader = new JLabel("English (visible after restart)"); //$NON-NLS-1$
		final JLabel englishFlagLabel = new JLabel(englishFlag);

		englishLanguageCheckBox = new JRadioButton();
		group.add(englishLanguageCheckBox);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(englishLanguageCheckBox);
		htmlPanel.add(englishFlagLabel);

		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
		fontChooser = new JFontChooser();

		fontChooserButton = new JButton(Messages.getString("EigenschaftenView.67"));
		titleView = new TitleLabelView(Messages.getString("EigenschaftenView.68"));
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(fontChooserButton);

		htmlAll.add(htmlPanel);
		htmlAll.add(new JSeparator());
		titleView = new TitleLabelView(Messages.getString("EigenschaftenView.20"));
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);

		openDefaultPathButton = new JButton(Messages.getString("EigenschaftenView.19")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openDefaultPathButton);
		openDefaultPathLabel = new JLabel();
		htmlPanel.add(openDefaultPathLabel);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
		titleView = new TitleLabelView(Messages.getString("EigenschaftenView.26"));
		titleView.setFlowLayoutLeft();

		// htmlAll.add(titleView);
		//
		// htmlPanel = new JPanel();
		// htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// htmlPanel.add(spielerListeAuswahlBox);
		// htmlPanel.add(new JLabel(Messages.getString("EigenschaftenView.24")));
		// //$NON-NLS-1$
		//
		// htmlAll.add(htmlPanel);
		// htmlPanel = new JPanel();
		// htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		// htmlPanel.add(turnierListeAuswahlBox);
		// htmlPanel.add(new JLabel(Messages.getString("EigenschaftenView.25")));
		// //$NON-NLS-1$
		//
		// htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
		titleView = new TitleLabelView(Messages.getString("EigenschaftenView.71"));
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);

		resetPropertiesButton = new JButton(Messages.getString("EigenschaftenView.71")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(resetPropertiesButton);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
	}

	private void makeHTMLEigenschaften() {
		final Color titleColor = new Color((SystemColor.menu).getRGB());
		final Border blackline = BorderFactory.createLineBorder(titleColor);

		// ohne Header und Footer
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader = new JLabel(Messages.getString("EigenschaftenView.1")); //$NON-NLS-1$
		final Dimension dimTextField = new Dimension(500, 40);

		checkBoxHeaderFooter = new JCheckBox();
		htmlPanel.add(checkBoxHeaderFooter);
		htmlPanel.add(labelHeader);
		TitleLabelView titleView = new TitleLabelView(Messages.getString("EigenschaftenView.73"));
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);

		htmlAll.add(htmlPanel);
		// Table style

		labelHeader = new JLabel(Messages.getString("EigenschaftenView.72")); //$NON-NLS-1$
		tableCSSTextField = new JTextField(40);
		tableCSSTextField.setPreferredSize(dimTextField);

		JPanel htmlPanelLabel = new JPanel();
		htmlPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel htmlPanelInput = new JPanel();
		htmlPanelInput.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanelLabel.add(labelHeader);

		htmlPanelInput.add(tableCSSTextField);
		JSeparator separator = new JSeparator();
		separator.setBorder(blackline);
		htmlAll.add(separator);

		htmlAll.add(htmlPanelLabel);
		htmlAll.add(htmlPanelInput);
		separator = new JSeparator();
		separator.setBorder(blackline);
		htmlAll.add(separator);

		// HTML Tabellen in die Zwischenablage kopieren
		labelHeader = new JLabel(Messages.getString("EigenschaftenView.60")); //$NON-NLS-1$
		checkBoxhtmlToClipboard = new JCheckBox();
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(checkBoxhtmlToClipboard);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		// PDF Links in HTML Tabellen einbinden
		labelHeader = new JLabel(Messages.getString("EigenschaftenView.42")); //$NON-NLS-1$
		checkBoxPDFLinks = new JCheckBox();
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(checkBoxPDFLinks);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		// Webserver Path

		labelHeader = new JLabel(Messages.getString("EigenschaftenView.41")); //$NON-NLS-1$
		webserverPathTextField = new JTextField(40);
		webserverPathTextField.setPreferredSize(dimTextField);
		htmlPanelLabel = new JPanel();
		htmlPanelLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanelInput = new JPanel();
		htmlPanelInput.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanelLabel.add(labelHeader);

		htmlPanelInput.add(webserverPathTextField);
		separator = new JSeparator();
		separator.setBorder(blackline);
		htmlAll.add(separator);

		htmlAll.add(htmlPanelLabel);
		htmlAll.add(htmlPanelInput);

		titleView = new TitleLabelView(Messages.getString("EigenschaftenView.74"));
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);
		// ohne DWZ
		labelHeader = new JLabel(Messages.getString("EigenschaftenView.3")); //$NON-NLS-1$
		checkBoxohneDWZ = new JCheckBox();
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(checkBoxohneDWZ);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		// ohne Folge DWZ
		labelHeader = new JLabel(Messages.getString("EigenschaftenView.4")); //$NON-NLS-1$
		checkBoxohneFolgeDWZ = new JCheckBox();
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(checkBoxohneFolgeDWZ);
		htmlPanel.add(labelHeader);
		htmlAll.add(htmlPanel);
		// ohne ELO
		labelHeader = new JLabel(Messages.getString("EigenschaftenView.61"));
		checkBoxohneELO = new JCheckBox();
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(checkBoxohneELO);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		// ohne Folge ELO
		labelHeader = new JLabel(Messages.getString("EigenschaftenView.62"));
		checkBoxohneFolgeELO = new JCheckBox();
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(checkBoxohneFolgeELO);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);
		titleView = new TitleLabelView(Messages.getString("EigenschaftenView.75"));
		titleView.setFlowLayoutLeft();

		htmlAll.add(titleView);
		// buchstaben anzahl
		final String[] listString = new String[20];
		for (int i = 0; i < 20; i++) {
			listString[i] = new Integer(i + 1).toString();

		}
		// forename length
		forenameLengthBox = new SpinnerView(listString, listString.length - 1,
				Messages.getString("EigenschaftenView.39"));
		htmlAll.add(forenameLengthBox);

		// surname length
		surnameLengthBox = new SpinnerView(listString, listString.length - 1,
				Messages.getString("EigenschaftenView.40"));
		htmlAll.add(surnameLengthBox);

	}

	private void tableLabel() {
		TitleLabelView titleView = new TitleLabelView(Messages.getString("EigenschaftenView.21"));
		titleView.setFlowLayoutLeft();
		// final Color windowBorder = new Color((SystemColor.windowBorder).getRGB());
		final Color windowBorder = new Color((SystemColor.menu).getRGB());
		htmlAll.add(titleView);
		final JPanel bothPanel = new JPanel();
		bothPanel.setLayout(new BorderLayout());
		final JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.setBorder(BorderFactory.createLineBorder(windowBorder));
		final JPanel rightPanel = new JPanel();
		rightPanel.setBorder(BorderFactory.createLineBorder(windowBorder));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		final Dimension dimTextField = new Dimension(175, 40);
		final int textFieldColumns = 9;
		oldDWZTextField = new JTextField(TournamentConstants.TABLE_COLUMN_OLD_DWZ, textFieldColumns);
		oldDWZTextField.setPreferredSize(dimTextField);

		newDWZTextField = new JTextField(TournamentConstants.TABLE_COLUMN_NEW_DWZ, textFieldColumns);
		newDWZTextField.setPreferredSize(dimTextField);
		oldELOTextField = new JTextField(TournamentConstants.TABLE_COLUMN_OLD_ELO, textFieldColumns);
		oldELOTextField.setPreferredSize(dimTextField);

		newELOTextField = new JTextField(TournamentConstants.TABLE_COLUMN_NEW_ELO, textFieldColumns);
		newELOTextField.setPreferredSize(dimTextField);

		pointsTextField = new JTextField(TournamentConstants.TABLE_COLUMN_POINTS, textFieldColumns);
		pointsTextField.setPreferredSize(dimTextField);

		sbbTextField = new JTextField(TournamentConstants.TABLE_COLUMN_SONNEBORNBERGER, textFieldColumns);
		sbbTextField.setPreferredSize(dimTextField);

		rankingTextField = new JTextField(TournamentConstants.TABLE_COLUMN_RANKING, textFieldColumns);
		rankingTextField.setPreferredSize(dimTextField);

		roundTextField = new JTextField(TournamentConstants.TABLE_COLUMN_ROUND, textFieldColumns);
		roundTextField.setPreferredSize(dimTextField);

		whiteTextField = new JTextField(TournamentConstants.TABLE_COLUMN_WHITE, textFieldColumns);
		whiteTextField.setPreferredSize(dimTextField);

		blackTextField = new JTextField(TournamentConstants.TABLE_COLUMN_BLACK, textFieldColumns);
		blackTextField.setPreferredSize(dimTextField);

		resultTextField = new JTextField(TournamentConstants.TABLE_COLUMN_RESULT, textFieldColumns);
		resultTextField.setPreferredSize(dimTextField);

		meetingTextField = new JTextField(TournamentConstants.TABLE_COLUMN_MEETING, textFieldColumns);
		meetingTextField.setPreferredSize(dimTextField);

		playerTextField = new JTextField(TournamentConstants.TABLE_COLUMN_PLAYER, textFieldColumns);
		playerTextField.setPreferredSize(dimTextField);

		roundTextField = new JTextField(TournamentConstants.TABLE_COLUMN_ROUND, textFieldColumns);
		roundTextField.setPreferredSize(dimTextField);

		spielfreiTextField = new JTextField(TournamentConstants.SPIELFREI, textFieldColumns);
		spielfreiTextField.setPreferredSize(dimTextField);
		
		final Dimension dim = new Dimension(175, 30);
		final JLabel oldDWZTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.22") + ":");
		oldDWZTextFieldLabel.setPreferredSize(dim);
		final JLabel newDWZTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.23") + ":");
		newDWZTextFieldLabel.setPreferredSize(dim);

		final JLabel oldELOTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.63") + ":");
		oldELOTextFieldLabel.setPreferredSize(dim);
		final JLabel newELOTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.64") + ":");
		newELOTextFieldLabel.setPreferredSize(dim);

		final JLabel pointDWZTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.32") + ":");
		pointDWZTextFieldLabel.setPreferredSize(dim);

		final JLabel sbbTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.33") + ":");
		sbbTextFieldLabel.setPreferredSize(dim);

		final JLabel rankingTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.34") + ":");
		rankingTextFieldLabel.setPreferredSize(dim);

		final JLabel whiteTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.27") + ":");
		whiteTextFieldLabel.setPreferredSize(dim);

		final JLabel blackTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.28") + ":");
		blackTextFieldLabel.setPreferredSize(dim);

		final JLabel resultTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.29") + ":");
		resultTextFieldLabel.setPreferredSize(dim);

		final JLabel meetingTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.30") + ":");
		meetingTextFieldLabel.setPreferredSize(dim);

		final JLabel playerTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.31") + ":");
		playerTextFieldLabel.setPreferredSize(dim);

		final JLabel roundTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.36") + ":");
		roundTextFieldLabel.setPreferredSize(dim);
		
		final JLabel spielfreiTextFieldLabel = new JLabel(Messages.getString("EigenschaftenView.76") + ":");
		spielfreiTextFieldLabel.setPreferredSize(dim);
		
		titleView = new TitleLabelView(Messages.getString("EigenschaftenView.37"));
		titleView.setFlowLayoutLeft();
		// JLabel leftTitleLabel = new
		// JLabel(Messages.getString("EigenschaftenView.37"));
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(titleView);
		leftPanel.add(htmlPanel);
		titleView = new TitleLabelView(Messages.getString("EigenschaftenView.38"));
		titleView.setFlowLayoutLeft();
		// JLabel rightTitleLabel = new
		// JLabel(Messages.getString("EigenschaftenView.38"));
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(titleView);
		rightPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(playerTextFieldLabel);
		htmlPanel.add(playerTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.add(oldDWZTextFieldLabel);
		htmlPanel.add(oldDWZTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(newDWZTextFieldLabel);
		htmlPanel.add(newDWZTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.add(oldELOTextFieldLabel);
		htmlPanel.add(oldELOTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(newELOTextFieldLabel);
		htmlPanel.add(newELOTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(pointDWZTextFieldLabel);
		htmlPanel.add(pointsTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(sbbTextFieldLabel);
		htmlPanel.add(sbbTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(rankingTextFieldLabel);
		htmlPanel.add(rankingTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(roundTextFieldLabel);
		htmlPanel.add(roundTextField);
		rightPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(whiteTextFieldLabel);
		htmlPanel.add(whiteTextField);
		rightPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(blackTextFieldLabel);
		htmlPanel.add(blackTextField);
		rightPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(resultTextFieldLabel);
		htmlPanel.add(resultTextField);
		rightPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(meetingTextFieldLabel);
		htmlPanel.add(meetingTextField);
		rightPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(spielfreiTextFieldLabel);
		htmlPanel.add(spielfreiTextField);
		rightPanel.add(htmlPanel);
		
		final JPanel leftP = new JPanel();
		leftP.setLayout(new BorderLayout());
		leftP.add(leftPanel, BorderLayout.NORTH);

		final JPanel rightP = new JPanel();
		rightP.setLayout(new BorderLayout());
		rightP.add(rightPanel, BorderLayout.NORTH);

		bothPanel.add(leftP, BorderLayout.WEST);
		bothPanel.add(rightP, BorderLayout.CENTER);
		htmlAll.add(bothPanel);
		htmlAll.add(new JSeparator());
	}

	public JTextField getBlackTextField() {
		return blackTextField;
	}

	public JCheckBox getCheckBoxHeaderFooter() {
		return checkBoxHeaderFooter;
	}

	public JCheckBox getCheckBoxhtmlToClipboard() {
		return checkBoxhtmlToClipboard;
	}

	public JCheckBox getCheckBoxohneDWZ() {
		return checkBoxohneDWZ;
	}

	public JCheckBox getCheckBoxohneELO() {
		return checkBoxohneELO;
	}

	public JCheckBox getCheckBoxohneFolgeDWZ() {
		return checkBoxohneFolgeDWZ;
	}

	public JCheckBox getCheckBoxohneFolgeELO() {
		return checkBoxohneFolgeELO;
	}

	public JCheckBox getCheckBoxPDFLinks() {
		return checkBoxPDFLinks;
	}

	public JButton getConvertDWZToSQLITEButton() {
		return convertDWZToSQLITEButton;
	}

	public JLabel getConvertDWZToSQLITELabel() {
		return convertDWZToSQLITELabel;
	}

	public JButton getConvertELOToSQLITEButton() {
		return convertELOToSQLITEButton;
	}

	public JLabel getConvertELOToSQLITELabel() {
		return convertELOToSQLITELabel;
	}

	public JRadioButton getEnglishLanguageCheckBox() {
		return englishLanguageCheckBox;
	}

	public SpinnerView getForenameLengthBox() {
		return forenameLengthBox;
	}

	public JRadioButton getGermanLanguageCheckBox() {
		return germanLanguageCheckBox;
	}

	public JTextField getMeetingTextField() {
		return meetingTextField;
	}

	public JTextField getNewDWZTextField() {
		return newDWZTextField;
	}

	public JTextField getNewELOTextField() {
		return newELOTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JTextField getOldDWZTextField() {
		return oldDWZTextField;
	}

	public JTextField getOldELOTextField() {
		return oldELOTextField;
	}

	public JButton getOpenDefaultPathButton() {
		return openDefaultPathButton;
	}

	public String getOpenDefaultPathLabel() {
		return openDefaultPathLabel.getText();
	}

	public JButton getOpenPlayersCSVButton() {
		return openPlayersCSVButton;
	}

	public JLabel getOpenPlayersCSVLabel() {
		return openPlayersCSVLabel;
	}

	public JButton getOpenPlayersELOButton() {
		return openPlayersELOButton;
	}

	public JLabel getOpenPlayersELOLabel() {
		return openPlayersELOLabel;
	}

	public JButton getOpenVereineCSVButton() {
		return openVereineCSVButton;
	}

	public String getOpenVereineCSVLabel() {
		return openVereineCSVLabel.getText();
	}

	public JTextField getPlayerTextField() {
		return playerTextField;
	}

	public JTextField getPointsTextField() {
		return pointsTextField;
	}

	public JTextField getRankingTextField() {
		return rankingTextField;
	}

	public JTextField getResultTextField() {
		return resultTextField;
	}

	public JTextField getRoundTextField() {
		return roundTextField;
	}

	public JTextField getSbbTextField() {
		return sbbTextField;
	}

	public JComboBox<String> getSpielerListeAuswahlBox() {
		return spielerListeAuswahlBox;
	}

	public SpinnerView getSurnameLengthBox() {
		return surnameLengthBox;
	}

	public JComboBox<String> getTurnierListeAuswahlBox() {
		return turnierListeAuswahlBox;
	}

	public JTextField getWebserverPathTextField() {
		return webserverPathTextField;
	}

	public JTextField getWhiteTextField() {
		return whiteTextField;
	}

	public JButton getFontChooserButton() {
		return fontChooserButton;
	}

	public void setFontChooserButton(final JButton fontChooserButton) {
		this.fontChooserButton = fontChooserButton;
	}

	public void setBlackTextField(final JTextField blackTextField) {
		this.blackTextField = blackTextField;
	}

	public void setCheckBoxHeaderFooter(final JCheckBox checkBoxHeaderFooter) {
		this.checkBoxHeaderFooter = checkBoxHeaderFooter;
	}

	public void setCheckBoxhtmlToClipboard(final JCheckBox checkBoxhtmlToClipboard) {
		this.checkBoxhtmlToClipboard = checkBoxhtmlToClipboard;
	}

	public void setCheckBoxohneDWZ(final JCheckBox checkBoxohneDWZ) {
		this.checkBoxohneDWZ = checkBoxohneDWZ;
	}

	public void setCheckBoxohneELO(final JCheckBox checkBoxohneELO) {
		this.checkBoxohneELO = checkBoxohneELO;
	}

	public void setCheckBoxohneFolgeDWZ(final JCheckBox checkBoxohneFolgeDWZ) {
		this.checkBoxohneFolgeDWZ = checkBoxohneFolgeDWZ;
	}

	public void setCheckBoxohneFolgeELO(final JCheckBox checkBoxohneFolgeELO) {
		this.checkBoxohneFolgeELO = checkBoxohneFolgeELO;
	}

	public void setCheckBoxPDFLinks(final JCheckBox checkBoxPDFLinks) {
		this.checkBoxPDFLinks = checkBoxPDFLinks;
	}

	public void setConvertDWZToSQLITEButton(final JButton convertDWZToSQLITEButton) {
		this.convertDWZToSQLITEButton = convertDWZToSQLITEButton;
	}

	public void setConvertDWZToSQLITELabel(final JLabel convertDWZToSQLITELabel) {
		this.convertDWZToSQLITELabel = convertDWZToSQLITELabel;
	}

	public void setConvertELOToSQLITEButton(final JButton convertELOToSQLITEButton) {
		this.convertELOToSQLITEButton = convertELOToSQLITEButton;
	}

	public void setConvertELOToSQLITELabel(final JLabel convertELOToSQLITELabel) {
		this.convertELOToSQLITELabel = convertELOToSQLITELabel;
	}

	public void setEnglishLanguageCheckBox(final JRadioButton englishLanguageCheckBox) {
		this.englishLanguageCheckBox = englishLanguageCheckBox;
	}

	public void setForenameLengthBox(final SpinnerView forenameLengthBox) {
		this.forenameLengthBox = forenameLengthBox;
	}

	public void setGermanLanguageCheckBox(final JRadioButton germanLanguageCheckBox) {
		this.germanLanguageCheckBox = germanLanguageCheckBox;
	}

	public void setMeetingTextField(final JTextField meetingTextField) {
		this.meetingTextField = meetingTextField;
	}

	public void setNewDWZTextField(final JTextField newDWZTextField) {
		this.newDWZTextField = newDWZTextField;
	}

	public void setNewELOTextField(final JTextField newELOTextField) {
		this.newELOTextField = newELOTextField;
	}

	public void setOkButton(final JButton okButton) {
		this.okButton = okButton;
	}

	public void setOldDWZTextField(final JTextField oldDWZTextField) {
		this.oldDWZTextField = oldDWZTextField;
	}

	public void setOldELOTextField(final JTextField oldELOTextField) {
		this.oldELOTextField = oldELOTextField;
	}

	public void setOpenDefaultPathButton(final JButton openDefaultPathButton) {
		this.openDefaultPathButton = openDefaultPathButton;
	}

	public void setOpenDefaultPathLabel(final String openDefaultPathLabel) {
		this.openDefaultPathLabel.setText(openDefaultPathLabel);
		this.openDefaultPathLabel.updateUI();
	}

	public void setOpenPlayersCSVButton(final JButton openPlayersCSVButton) {
		this.openPlayersCSVButton = openPlayersCSVButton;
	}

	public void setOpenPlayersCSVLabel(final String openVereineCSV) {
		openPlayersCSVLabel.setText(openVereineCSV);
		openPlayersCSVLabel.updateUI();
	}

	public void setOpenPlayersELOButton(final JButton openPlayersELOButton) {
		this.openPlayersELOButton = openPlayersELOButton;
	}

	public void setOpenPlayersELOLabel(final String openPlayersELOLabel) {
		this.openPlayersELOLabel.setText(openPlayersELOLabel);
	}

	public void setOpenVereineCSVButton(final JButton openVereineCSVButton) {
		this.openVereineCSVButton = openVereineCSVButton;
	}

	public void setOpenVereineCSVLabel(final String openVereineCSVLabel) {
		this.openVereineCSVLabel.setText(openVereineCSVLabel);
		this.openVereineCSVLabel.updateUI();
	}

	public void setPlayerTextField(final JTextField playerTextField) {
		this.playerTextField = playerTextField;
	}

	public void setPointsTextField(final JTextField pointsTextField) {
		this.pointsTextField = pointsTextField;
	}

	public void setRankingTextField(final JTextField rankingTextField) {
		this.rankingTextField = rankingTextField;
	}

	public void setResultTextField(final JTextField resultTextField) {
		this.resultTextField = resultTextField;
	}

	public void setRoundTextField(final JTextField roundTextField) {
		this.roundTextField = roundTextField;
	}

	public void setSbbTextField(final JTextField sbbTextField) {
		this.sbbTextField = sbbTextField;
	}

	public void setSpielerListeAuswahlBox(final JComboBox<String> spielerListeAuswahlBox) {
		this.spielerListeAuswahlBox = spielerListeAuswahlBox;
	}

	public void setSurnameLengthBox(final SpinnerView surnameLengthBox) {
		this.surnameLengthBox = surnameLengthBox;
	}

	public void setTurnierListeAuswahlBox(final JComboBox<String> turnierListeAuswahlBox) {
		this.turnierListeAuswahlBox = turnierListeAuswahlBox;
	}

	public void setWebserverPathTextField(final JTextField webserverPathTextField) {
		this.webserverPathTextField = webserverPathTextField;
	}

	public void setWhiteTextField(final JTextField whiteTextField) {
		this.whiteTextField = whiteTextField;
	}

	public JFontChooser getFontChooser() {
		return fontChooser;
	}

	public void setFontChooser(final JFontChooser fontChooser) {
		this.fontChooser = fontChooser;
	}

	public Font getSelectedFont() {
		return selectedFont;
	}

	public void setSelectedFont(final Font selectedFont) {
		this.selectedFont = selectedFont;
	}

	public JButton getResetPropertiesButton() {
		return resetPropertiesButton;
	}

	public void setResetPropertiesButton(final JButton resetPropertiesButton) {
		this.resetPropertiesButton = resetPropertiesButton;
	}

	public JTextField getTableCSSTextField() {
		return tableCSSTextField;
	}

	public void setTableCSSTextField(final JTextField tableCSSTextField) {
		this.tableCSSTextField = tableCSSTextField;
	}

	public JLabel getCreateELODateLabel() {
		return createELODateLabel;
	}

	public void setCreateELODateLabel(final JLabel createDateLabel) {
		createELODateLabel = createDateLabel;
	}

	public JLabel getCreateDWZDateLabel() {
		return createDWZDateLabel;
	}

	public void setCreateDWZDateLabel(final JLabel createDWZDateLabel) {
		this.createDWZDateLabel = createDWZDateLabel;
	}

	public JTextField getSpielfreiTextField() {
		return spielfreiTextField;
	}

	public void setSpielfreiTextField(JTextField spielfreiTextField) {
		this.spielfreiTextField = spielfreiTextField;
	}

}
