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
import java.awt.Dialog;
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
	// private JButton okButton;
	// private JButton cancelButton;
	//
	// private ButtonPanelView buttonPane;
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

	private final JLabel statusLabel;

	/**
	 * Create the dialog.
	 *
	 * @throws URISyntaxException
	 */
	public ELODialogView() {

		// setAlwaysOnTop(true);
		setTitle(Messages.getString("ELOPlayerView.1")); //$NON-NLS-1$

//		getContentPane().setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		contentPanel.setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		vereinsSuche = new JTextField(25);

		dsbPanel = new JPanel();
		playerSearchView = new PlayerSearchView(Messages.getString("ELOPlayerView.1"));

		contentPanel.add(playerSearchView, BorderLayout.CENTER);

		statusLabel = new JLabel("");
		final JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
		southPanel.add(statusLabel);
		// southPanel.add(buttonPane);
		contentPanel.add(southPanel, BorderLayout.SOUTH);

//		getContentPane().add(contentPanel, BorderLayout.CENTER);
		add(contentPanel, BorderLayout.CENTER);

//		setEnabled(true);
//		setVisible(true);
		// setLocationRelativeTo(null);
	}
	public void showDialog() {
		pack();

		setLocationRelativeTo(null);
		setEnabled(true);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setVisible(true);

	}
	public void closeWindow() {
		dispose();
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

	// public ButtonPanelView getButtonPanel() {
	// return buttonPane;
	// }
	//
	// public void setButtonPanel(ButtonPanelView buttonPane) {
	// this.buttonPane = buttonPane;
	// }
	//
	// public JButton getOkButton() {
	// return okButton;
	// }
	//
	// public void setOkButton(JButton okButton) {
	// this.okButton = okButton;
	// }
	//
	// public JButton getCancelButton() {
	// return cancelButton;
	// }

	// public void setCancelButton(JButton cancelButton) {
	// this.cancelButton = cancelButton;
	// }

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
		// getButtonPanel().updateUI();
//		updateUI();
		// pack();
		// setLocationRelativeTo(null);
	}

	public void setDsbPanel(final JPanel dsbPanel) {
		contentPanel.remove(this.dsbPanel);
		this.dsbPanel = dsbPanel;
		contentPanel.add(dsbPanel, BorderLayout.CENTER);
	}

	public void setPlayerSearchView(final PlayerSearchView playerSearchView) {
		this.playerSearchView = playerSearchView;
	}

	public void setVereinsAuswahl(final JComboBox<String> vereinsAuswahl) {
		this.vereinsAuswahl = vereinsAuswahl;
	}

	public void setVereinsAuswahlOkButton(final JButton vereinsAuswahlOkButton) {
		this.vereinsAuswahlOkButton = vereinsAuswahlOkButton;
	}

	public void setVereinsName(final JTextField vereinsName) {
		this.vereinsName = vereinsName;
	}

	public void setVereinsSuche(final JTextField vereinsSuche) {
		this.vereinsSuche = vereinsSuche;
	}

	public void setVereinsSucheButton(final JButton vereinsSucheButton) {
		this.vereinsSucheButton = vereinsSucheButton;
	}

}
