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
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DSBDWZDialogView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JPanel dsbPanel;
	private JTextField vereinsSuche;
	private JButton vereinsSucheButton;
	private JButton okButton;
	private JButton cancelButton;

	private ButtonPanelView buttonPane;
	private URI dwzdbURI;
	private JButton dwzdbButton;

	private JTextField vereinsName;

	private JComboBox<String> vereinsAuswahl;

	private JButton vereinsAuswahlOkButton;

	// private JButton infoButton;
	private ImageIcon infoIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png")));
	private ImageIcon searchIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-find.png")));
	private ImageIcon tabIcon = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-new-2.png")));

	private PlayerSearchView playerSearchView;
	private JLabel statusLabel;

	/**
	 * Create the dialog.
	 * 
	 * @throws URISyntaxException
	 */
	public DSBDWZDialogView(Boolean cvsFiles) throws URISyntaxException {

		this.setAlwaysOnTop(true);
		setTitle(Messages.getString("DEWISDialogView.0")); //$NON-NLS-1$
		// setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		// setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());

		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		vereinsSuche = new JTextField(25);
		JPanel suchePanel = new JPanel();
		suchePanel.setLayout(new BoxLayout(suchePanel, BoxLayout.PAGE_AXIS));
		if (cvsFiles == false) {
			dwzdbURI = new URI("http://www.schachbund.de/verein.html"); //$NON-NLS-1$

			dwzdbButton = new JButton();
			dwzdbButton.setText("<HTML><FONT color=\"#000099\"><U>Vereinssuche (ZPS Nummer)</U></FONT></HTML>"); //$NON-NLS-1$
			// buttonDatePicker.setHorizontalAlignment(SwingConstants.LEFT);
			// buttonDatePicker.setBorderPainted(false);
			dwzdbButton.setOpaque(false);
			// buttonDatePicker.setBackground(Color.WHITE);
			dwzdbButton.setToolTipText(dwzdbURI.toString());
			dwzdbButton.addActionListener(new OpenUrlAction());

			JPanel zeilenPanel = new JPanel();
			zeilenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			zeilenPanel.add(dwzdbButton);
			suchePanel.add(zeilenPanel);
			zeilenPanel = new JPanel();
			zeilenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel vereinsSucheLabel = new JLabel(Messages.getString("DEWISDialogView.3")); //$NON-NLS-1$

			vereinsSucheButton = new JButton(Messages.getString("DEWISDialogView.4"), searchIcon);

			zeilenPanel.add(vereinsSucheLabel);

			suchePanel.add(zeilenPanel);
			zeilenPanel = new JPanel();
			zeilenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			zeilenPanel.add(vereinsSuche);
			zeilenPanel.add(vereinsSucheButton);
			suchePanel.add(zeilenPanel);
		}
		if (cvsFiles == true) {
			JPanel zeilenPanel = new JPanel();
			zeilenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			zeilenPanel.add(new JLabel(Messages.getString("DEWISDialogView.1"))); //$NON-NLS-1$
			suchePanel.add(zeilenPanel);

			zeilenPanel = new JPanel();
			zeilenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			suchePanel.add(zeilenPanel);

			vereinsAuswahl = new JComboBox<String>();
			vereinsAuswahlOkButton = new JButton("Ok");

			zeilenPanel = new JPanel();
			zeilenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			zeilenPanel.add(vereinsAuswahl);
			zeilenPanel.add(vereinsAuswahlOkButton);

			suchePanel.add(zeilenPanel);

		}
		contentPanel.add(suchePanel, BorderLayout.NORTH);
		dsbPanel = new JPanel();
		playerSearchView = new PlayerSearchView();
		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();
		cancelButton = buttonPane.getCancelButton();
		okButton.setText(Messages.getString("DEWISDialogView.6"));
		cancelButton.setText(Messages.getString("DEWISDialogView.7"));

		contentPanel.add(dsbPanel, BorderLayout.CENTER);

		statusLabel = new JLabel("Test");
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(statusLabel);
		southPanel.add(buttonPane);
		contentPanel.add(southPanel, BorderLayout.SOUTH);
		if (cvsFiles == false) {
			DSBDWZInfoView sv = new DSBDWZInfoView();

			JTabbedPane tp = new JTabbedPane();
			tp.addTab(Messages.getString("DEWISDialogView.0"), tabIcon, contentPanel);
			tp.addTab("Info", infoIcon, sv);
			getContentPane().add(tp, BorderLayout.CENTER);
		} else {
			JTabbedPane tp = new JTabbedPane();

			tp.addTab(Messages.getString("DEWISDialogView.0"), tabIcon, contentPanel);
			tp.addTab(Messages.getString("DEWISDialogView.8"), tabIcon, playerSearchView);
			getContentPane().add(tp, BorderLayout.CENTER);
		}
		pack();

		setEnabled(true);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	class OpenUrlAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(dwzdbButton)) {
				open(dwzdbURI);
			}

		}

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

	public void closeWindow() {
		this.dispose();
	}

	public JPanel getDsbPanel() {
		return dsbPanel;

	}

	public void setDsbPanel(JPanel dsbPanel) {
		contentPanel.remove(this.dsbPanel);
		this.dsbPanel = dsbPanel;
		contentPanel.add(dsbPanel, BorderLayout.CENTER);
	}

	public void refresh() {
		getButtonPanel().updateUI();
		getContentPanel().updateUI();
		pack();
		setLocationRelativeTo(null);
	}

	public PlayerSearchView getPlayerSearchView() {
		return playerSearchView;
	}

	public void setPlayerSearchView(PlayerSearchView playerSearchView) {
		this.playerSearchView = playerSearchView;
	}

	public ButtonPanelView getButtonPanel() {
		return buttonPane;
	}

	public void setButtonPanel(ButtonPanelView buttonPane) {
		this.buttonPane = buttonPane;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JButton getVereinsSucheButton() {
		return vereinsSucheButton;
	}

	public void setVereinsSucheButton(JButton vereinsSucheButton) {
		this.vereinsSucheButton = vereinsSucheButton;
	}

	public JTextField getVereinsSuche() {
		return vereinsSuche;
	}

	public void setVereinsSuche(JTextField vereinsSuche) {
		this.vereinsSuche = vereinsSuche;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public JTextField getVereinsName() {
		return vereinsName;
	}

	public void setVereinsName(JTextField vereinsName) {
		this.vereinsName = vereinsName;
	}

	public JComboBox<String> getVereinsAuswahl() {
		return vereinsAuswahl;
	}

	public void setVereinsAuswahl(JComboBox<String> vereinsAuswahl) {
		this.vereinsAuswahl = vereinsAuswahl;
	}

	public JButton getVereinsAuswahlOkButton() {
		return vereinsAuswahlOkButton;
	}

	public void setVereinsAuswahlOkButton(JButton vereinsAuswahlOkButton) {
		this.vereinsAuswahlOkButton = vereinsAuswahlOkButton;
	}

}
