package de.turnierverwaltung.view.ratingdialog;

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
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.turnierverwaltung.view.Messages;

public class ELODialogView extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JPanel dsbPanel;
	private JTextField vereinsSuche;
	private JButton vereinsSucheButton;
//	private JButton okButton;
//	private JButton cancelButton;
//
//	private ButtonPanelView buttonPane;
	private JTextField vereinsName;

	private JComboBox<String> vereinsAuswahl;

	private JButton vereinsAuswahlOkButton;

	// private JButton infoButton;
	// private ImageIcon infoIcon = new ImageIcon(
	// Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/emblem-notice.png")));
	// private ImageIcon searchIcon = new ImageIcon(
	// Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-find.png")));
	// private ImageIcon tabIcon = new ImageIcon(
	// Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-new-2.png")));
	private PlayerSearchView playerSearchView;

	private JLabel statusLabel;

	/**
	 * Create the dialog.
	 * 
	 * @throws URISyntaxException
	 */
	public ELODialogView() {

		this.setAlwaysOnTop(true);
		setTitle(Messages.getString("DEWISDialogView.0")); //$NON-NLS-1$

		getContentPane().setLayout(new BorderLayout());

		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		vereinsSuche = new JTextField(25);

		dsbPanel = new JPanel();
		playerSearchView = new PlayerSearchView();
//		buttonPane = new ButtonPanelView();
//		buttonPane.makeAllButtons();
//		okButton = buttonPane.getOkButton();
//		cancelButton = buttonPane.getCancelButton();
//		okButton.setText(Messages.getString("DEWISDialogView.6"));
//		cancelButton.setText(Messages.getString("DEWISDialogView.7"));

		contentPanel.add(playerSearchView, BorderLayout.CENTER);

		statusLabel = new JLabel("Test");
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(statusLabel);
//		southPanel.add(buttonPane);
		contentPanel.add(southPanel, BorderLayout.SOUTH);

		getContentPane().add(contentPanel, BorderLayout.CENTER);
		pack();

		setEnabled(true);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	public void closeWindow() {
		this.dispose();
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public JPanel getDsbPanel() {
		return dsbPanel;

	}

	public PlayerSearchView getPlayerSearchView() {
		return playerSearchView;
	}

//	public ButtonPanelView getButtonPanel() {
//		return buttonPane;
//	}
//
//	public void setButtonPanel(ButtonPanelView buttonPane) {
//		this.buttonPane = buttonPane;
//	}
//
//	public JButton getOkButton() {
//		return okButton;
//	}
//
//	public void setOkButton(JButton okButton) {
//		this.okButton = okButton;
//	}
//
//	public JButton getCancelButton() {
//		return cancelButton;
//	}

//	public void setCancelButton(JButton cancelButton) {
//		this.cancelButton = cancelButton;
//	}

	public JComboBox<String> getVereinsAuswahl() {
		return vereinsAuswahl;
	}

	public JButton getVereinsAuswahlOkButton() {
		return vereinsAuswahlOkButton;
	}

	public JTextField getVereinsName() {
		return vereinsName;
	}

	public JTextField getVereinsSuche() {
		return vereinsSuche;
	}

	public JButton getVereinsSucheButton() {
		return vereinsSucheButton;
	}

	public void refresh() {
//		getButtonPanel().updateUI();
		getContentPanel().updateUI();
		pack();
		setLocationRelativeTo(null);
	}

	public void setDsbPanel(JPanel dsbPanel) {
		contentPanel.remove(this.dsbPanel);
		this.dsbPanel = dsbPanel;
		contentPanel.add(dsbPanel, BorderLayout.CENTER);
	}

	public void setPlayerSearchView(PlayerSearchView playerSearchView) {
		this.playerSearchView = playerSearchView;
	}

	public void setVereinsAuswahl(JComboBox<String> vereinsAuswahl) {
		this.vereinsAuswahl = vereinsAuswahl;
	}

	public void setVereinsAuswahlOkButton(JButton vereinsAuswahlOkButton) {
		this.vereinsAuswahlOkButton = vereinsAuswahlOkButton;
	}

	public void setVereinsName(JTextField vereinsName) {
		this.vereinsName = vereinsName;
	}

	public void setVereinsSuche(JTextField vereinsSuche) {
		this.vereinsSuche = vereinsSuche;
	}

	public void setVereinsSucheButton(JButton vereinsSucheButton) {
		this.vereinsSucheButton = vereinsSucheButton;
	}

}
