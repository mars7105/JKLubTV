package de.turnierverwaltung.view;

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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
import javax.swing.JTextField;

import de.turnierverwaltung.model.TurnierKonstanten;

public class EigenschaftenView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JScrollPane scrollPane;
	private JPanel centerPane;

	private JCheckBox checkBoxHeaderFooter;
	private JCheckBox checkBoxohneDWZ;
	private JCheckBox checkBoxohneFolgeDWZ;
	private JRadioButton germanLanguageCheckBox;
	private JRadioButton englishLanguageCheckBox;
	private JPanel htmlAll;
	private ImageIcon germanFlag = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/images/de.png"))); //$NON-NLS-1$
	private ImageIcon englishFlag = new ImageIcon(Toolkit.getDefaultToolkit()
			.getImage(getClass().getResource("/images/flag-gb.png"))); //$NON-NLS-1$
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
	private JTextField pointDWZTextField;
	private JTextField sbbTextField;
	private JTextField rankingTextField;
	private JTextField roundTextField;
	private JTextField whiteTextField;
	private JTextField blackTextField;
	private JTextField resultTextField;
	private JTextField meetingTextField;

	/**
	 * Create the panel.
	 */
	public EigenschaftenView() {
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
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

		new JButton("OK");

		JLabel titleLabel = new JLabel(
				Messages.getString("EigenschaftenView.0")); //$NON-NLS-1$
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		titlepanel.add(titleLabel);

		northPanel.add(titlepanel);
		northPanel.add(topPanel);
		add(northPanel, BorderLayout.NORTH);
		centerPane = new JPanel();
		centerPane.setLayout(new BorderLayout());
		// centerPane.setPreferredSize(new Dimension(900,1000));
		htmlAll = new JPanel();
		htmlAll.setLayout(new BoxLayout(htmlAll, BoxLayout.PAGE_AXIS));
		languageSupport();
		makeHTMLEigenschaften();
//		tableLabel();
		anzahlElemente();
		defaultPath();

		downloadLinks();
		centerPane.add(htmlAll, BorderLayout.NORTH);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(centerPane);
		// contentPanel.add(scrollPane);
		add(scrollPane, BorderLayout.CENTER);
	}

	@SuppressWarnings("unused")
	private void tableLabel() {

		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.LEFT));
		title.add(new JLabel(Messages.getString("EigenschaftenView.21"))); //$NON-NLS-1$
		htmlAll.add(title);

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Dimension dimTextField = new Dimension(175, 30);
		oldDWZTextField = new JTextField(TurnierKonstanten.TABLE_COLUMN_OLD_DWZ);
		oldDWZTextField.setPreferredSize(dimTextField);

		newDWZTextField = new JTextField(TurnierKonstanten.TABLE_COLUMN_NEW_DWZ);
		newDWZTextField.setPreferredSize(dimTextField);

		pointDWZTextField = new JTextField(
				TurnierKonstanten.TABLE_COLUMN_POINTS);
		pointDWZTextField.setPreferredSize(dimTextField);

		sbbTextField = new JTextField(
				TurnierKonstanten.TABLE_COLUMN_SONNEBORNBERGER);
		sbbTextField.setPreferredSize(dimTextField);

		rankingTextField = new JTextField(
				TurnierKonstanten.TABLE_COLUMN_RANKING);
		rankingTextField.setPreferredSize(dimTextField);

		roundTextField = new JTextField(TurnierKonstanten.TABLE_COLUMN_ROUND);
		roundTextField.setPreferredSize(dimTextField);

		whiteTextField = new JTextField(TurnierKonstanten.TABLE_COLUMN_WHITE);
		whiteTextField.setPreferredSize(dimTextField);

		blackTextField = new JTextField(TurnierKonstanten.TABLE_COLUMN_BLACK);
		blackTextField.setPreferredSize(dimTextField);

		resultTextField = new JTextField(TurnierKonstanten.TABLE_COLUMN_RESULT);
		resultTextField.setPreferredSize(dimTextField);

		meetingTextField = new JTextField(
				TurnierKonstanten.TABLE_COLUMN_MEETING);
		meetingTextField.setPreferredSize(dimTextField);

		playerTextField = new JTextField(
				TurnierKonstanten.TABLE_COLUMN_PLAYER);
		playerTextField.setPreferredSize(dimTextField);

		Dimension dim = new Dimension(175, 30);
		JLabel oldDWZTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.22") + ":");
		oldDWZTextFieldLabel.setPreferredSize(dim);
		JLabel newDWZTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.23") + ":");
		newDWZTextFieldLabel.setPreferredSize(dim);

		JLabel pointDWZTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.32") + ":");
		pointDWZTextFieldLabel.setPreferredSize(dim);

		JLabel sbbTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.33") + ":");
		sbbTextFieldLabel.setPreferredSize(dim);

		JLabel rankingTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.34") + ":");
		rankingTextFieldLabel.setPreferredSize(dim);

		JLabel whiteTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.27") + ":");
		whiteTextFieldLabel.setPreferredSize(dim);

		JLabel blackTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.28") + ":");
		blackTextFieldLabel.setPreferredSize(dim);

		JLabel resultTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.29") + ":");
		resultTextFieldLabel.setPreferredSize(dim);

		JLabel meetingTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.30") + ":");
		meetingTextFieldLabel.setPreferredSize(dim);

		JLabel playerTextFieldLabel = new JLabel(
				Messages.getString("EigenschaftenView.31") + ":");
		playerTextFieldLabel.setPreferredSize(dim);

		htmlPanel.add(oldDWZTextFieldLabel);
		htmlPanel.add(oldDWZTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(newDWZTextFieldLabel);
		htmlPanel.add(newDWZTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(pointDWZTextFieldLabel);
		htmlPanel.add(pointDWZTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(sbbTextFieldLabel);
		htmlPanel.add(sbbTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(rankingTextFieldLabel);
		htmlPanel.add(rankingTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(whiteTextFieldLabel);
		htmlPanel.add(whiteTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(blackTextFieldLabel);
		htmlPanel.add(blackTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(resultTextFieldLabel);
		htmlPanel.add(resultTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(meetingTextFieldLabel);
		htmlPanel.add(meetingTextField);
		htmlAll.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(playerTextFieldLabel);
		htmlPanel.add(playerTextField);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
	}

	private void defaultPath() {

		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.LEFT));
		title.add(new JLabel(Messages.getString("EigenschaftenView.20"))); //$NON-NLS-1$
		htmlAll.add(title);

		openDefaultPathButton = new JButton(
				Messages.getString("EigenschaftenView.19")); //$NON-NLS-1$
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openDefaultPathButton);
		openDefaultPathLabel = new JLabel();
		htmlPanel.add(openDefaultPathLabel);
		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
	}

	private void languageSupport() {
		ButtonGroup group = new ButtonGroup();
		// deutsch
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader = new JLabel("Deutsch (erst nach Neustart sichtbar)"); //$NON-NLS-1$
		JLabel germanFlagLabel = new JLabel(germanFlag);
		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.LEFT));
		title.add(new JLabel("Sprache / Language")); //$NON-NLS-1$
		htmlAll.add(title);
		germanLanguageCheckBox = new JRadioButton();
		germanLanguageCheckBox.setSelected(true);
		group.add(germanLanguageCheckBox);
		htmlPanel.add(germanLanguageCheckBox);
		htmlPanel.add(germanFlagLabel);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		// englisch
		labelHeader = new JLabel("English (visible after restart)"); //$NON-NLS-1$
		JLabel englishFlagLabel = new JLabel(englishFlag);

		englishLanguageCheckBox = new JRadioButton();
		group.add(englishLanguageCheckBox);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(englishLanguageCheckBox);
		htmlPanel.add(englishFlagLabel);

		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());

	}

	private void anzahlElemente() {
		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.LEFT));
		title.add(new JLabel(Messages.getString("EigenschaftenView.26"))); //$NON-NLS-1$
		htmlAll.add(title);
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(spielerListeAuswahlBox);
		htmlPanel.add(new JLabel(Messages.getString("EigenschaftenView.24"))); //$NON-NLS-1$

		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(turnierListeAuswahlBox);
		htmlPanel.add(new JLabel(Messages.getString("EigenschaftenView.25"))); //$NON-NLS-1$

		htmlAll.add(htmlPanel);

		htmlAll.add(new JSeparator());
	}

	private void downloadLinks() {
		// ohne Header und Footer
		try {
			dsbHomepage = new URI("http://www.schachbund.de/download.html"); //$NON-NLS-1$
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.LEFT));
		title.add(new JLabel(Messages.getString("EigenschaftenView.5"))); //$NON-NLS-1$
		htmlAll.add(title);
		dsbHomepageButton = new JButton();
		dsbHomepageButton
				.setText("<HTML><FONT color=\"#000099\"><U>http://www.schachbund.de/download.html</U></FONT></HTML>"); //$NON-NLS-1$

		dsbHomepageButton.setOpaque(false);
		dsbHomepageButton.setToolTipText(dsbHomepage.toString());
		dsbHomepageButton.addActionListener(new OpenUrlAction());
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(dsbHomepageButton);
		htmlAll.add(htmlPanel);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader1 = new JLabel(
				Messages.getString("EigenschaftenView.10") //$NON-NLS-1$
						+ Messages.getString("EigenschaftenView.11")); //$NON-NLS-1$
		JLabel labelHeader1b = new JLabel(
				Messages.getString("EigenschaftenView.12") //$NON-NLS-1$
						+ Messages.getString("EigenschaftenView.13")); //$NON-NLS-1$
		JLabel labelHeader2 = new JLabel(
				Messages.getString("EigenschaftenView.14") //$NON-NLS-1$
						+ Messages.getString("EigenschaftenView.15")); //$NON-NLS-1$
		JLabel labelHeader2b = new JLabel(
				Messages.getString("EigenschaftenView.16")); //$NON-NLS-1$
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

		openVereineCSVButton = new JButton(
				Messages.getString("EigenschaftenView.18")); //$NON-NLS-1$
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(openVereineCSVButton);
		openVereineCSVLabel = new JLabel();
		htmlPanel.add(openVereineCSVLabel);
		htmlAll.add(htmlPanel);

		htmlAll.add(htmlPanel);
		htmlAll.add(new JSeparator());

	}

	private void makeHTMLEigenschaften() {
		// ohne Header und Footer
		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel labelHeader = new JLabel(
				Messages.getString("EigenschaftenView.1")); //$NON-NLS-1$

		checkBoxHeaderFooter = new JCheckBox();
		htmlPanel.add(checkBoxHeaderFooter);
		htmlPanel.add(labelHeader);
		JPanel title = new JPanel();
		title.setLayout(new FlowLayout(FlowLayout.LEFT));
		title.add(new JLabel(Messages.getString("EigenschaftenView.2"))); //$NON-NLS-1$
		htmlAll.add(title);
		htmlAll.add(htmlPanel);

		// ohne DWZ
		labelHeader = new JLabel(Messages.getString("EigenschaftenView.3")); //$NON-NLS-1$
		checkBoxohneDWZ = new JCheckBox();
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(checkBoxohneDWZ);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);
		JPanel all = new JPanel();
		all.setLayout(new BorderLayout());
		all.add(htmlAll, BorderLayout.NORTH);
		// ohne Folge DWZ
		labelHeader = new JLabel(Messages.getString("EigenschaftenView.4")); //$NON-NLS-1$
		checkBoxohneFolgeDWZ = new JCheckBox();
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(checkBoxohneFolgeDWZ);
		htmlPanel.add(labelHeader);

		htmlAll.add(htmlPanel);
		htmlAll.add(new JSeparator());

	}

	public JCheckBox getCheckBoxHeaderFooter() {
		return checkBoxHeaderFooter;
	}

	public void setCheckBoxHeaderFooter(JCheckBox checkBoxHeaderFooter) {
		this.checkBoxHeaderFooter = checkBoxHeaderFooter;
	}

	public JCheckBox getCheckBoxohneDWZ() {
		return checkBoxohneDWZ;
	}

	public void setCheckBoxohneDWZ(JCheckBox checkBoxohneDWZ) {
		this.checkBoxohneDWZ = checkBoxohneDWZ;
	}

	public JCheckBox getCheckBoxohneFolgeDWZ() {
		return checkBoxohneFolgeDWZ;
	}

	public void setCheckBoxohneFolgeDWZ(JCheckBox checkBoxohneFolgeDWZ) {
		this.checkBoxohneFolgeDWZ = checkBoxohneFolgeDWZ;
	}

	public JRadioButton getGermanLanguageCheckBox() {
		return germanLanguageCheckBox;
	}

	public void setGermanLanguageCheckBox(JRadioButton germanLanguageCheckBox) {
		this.germanLanguageCheckBox = germanLanguageCheckBox;
	}

	public JRadioButton getEnglishLanguageCheckBox() {
		return englishLanguageCheckBox;
	}

	public void setEnglishLanguageCheckBox(JRadioButton englishLanguageCheckBox) {
		this.englishLanguageCheckBox = englishLanguageCheckBox;
	}

	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				/* TODO: error handling */}
		} else {
			/* TODO: error handling */}
	}

	public JButton getOpenVereineCSVButton() {
		return openVereineCSVButton;
	}

	public void setOpenVereineCSVButton(JButton openVereineCSVButton) {
		this.openVereineCSVButton = openVereineCSVButton;
	}

	public String getOpenVereineCSVLabel() {
		return openVereineCSVLabel.getText();
	}

	public void setOpenVereineCSVLabel(String openVereineCSVLabel) {
		this.openVereineCSVLabel.setText(openVereineCSVLabel);
		this.openVereineCSVLabel.updateUI();
	}

	public JComboBox<String> getSpielerListeAuswahlBox() {
		return spielerListeAuswahlBox;
	}

	public void setSpielerListeAuswahlBox(
			JComboBox<String> spielerListeAuswahlBox) {
		this.spielerListeAuswahlBox = spielerListeAuswahlBox;
	}

	public JComboBox<String> getTurnierListeAuswahlBox() {
		return turnierListeAuswahlBox;
	}

	public void setTurnierListeAuswahlBox(
			JComboBox<String> turnierListeAuswahlBox) {
		this.turnierListeAuswahlBox = turnierListeAuswahlBox;
	}

	public JButton getOpenDefaultPathButton() {
		return openDefaultPathButton;
	}

	public void setOpenDefaultPathButton(JButton openDefaultPathButton) {
		this.openDefaultPathButton = openDefaultPathButton;
	}

	public String getOpenDefaultPathLabel() {
		return openDefaultPathLabel.getText();
	}

	public void setOpenDefaultPathLabel(String openDefaultPathLabel) {
		this.openDefaultPathLabel.setText(openDefaultPathLabel);
		this.openDefaultPathLabel.updateUI();
	}

	class OpenUrlAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == dsbHomepageButton) {
				open(dsbHomepage);
			}

		}

	}
}
