package de.turnierverwaltung.view.playerlist;
//JKlubTV - Ein Programm zum verwalten von Schach Turnieren

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;

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

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.Messages;

public class NewPlayerView extends JDialog {
	/**
		 *
		 */
	private static final long serialVersionUID = -5642277833139693453L;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField textFieldKuerzel;
	private JTextField textFieldDwz;
	private final ButtonPanelView buttonPane;
	private JPanel centerPane;
	private final JPanel contentPanel;
	private JComboBox<String> textComboBoxAge;
	private JTextField textFieldForeName;
	private JTextField textFieldSurName;

	public NewPlayerView() {
		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		buttonPane.getOkButton().setText(Messages.getString("SpielerHinzufuegenView.10"));
		buttonPane.getCancelButton().setText(Messages.getString("SpielerHinzufuegenView.11"));
		okButton = buttonPane.getOkButton();
		cancelButton = buttonPane.getCancelButton();

		setTitle(Messages.getString("SpielerHinzufuegenView.2")); //$NON-NLS-1$
		// this.setAlwaysOnTop(true);
		// setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(TournamentConstants.WINDOW_WIDTH, TournamentConstants.WINDOW_HEIGHT));

		// contentPanel.setBackground(new Color(249, 222, 112));

		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(contentPanel, BorderLayout.NORTH);
		final JScrollPane jsP = new JScrollPane(mainPanel);
		jsP.setViewportView(mainPanel);
		buttonPane.setLayout(new FlowLayout());
		add(jsP, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
		spielerPanel();

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

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JComboBox<String> getTextComboBoxAge() {
		return textComboBoxAge;
	}

	public JTextField getTextFieldDwz() {
		return textFieldDwz;
	}

	public JTextField getTextFieldForeName() {
		return textFieldForeName;
	}

	public JTextField getTextFieldKuerzel() {
		return textFieldKuerzel;
	}

	public JTextField getTextFieldSurName() {
		return textFieldSurName;
	}

	public void setCancelButton(final JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setOkButton(final JButton okButton) {
		this.okButton = okButton;
	}

	public void setTextComboBoxAge(final JComboBox<String> textComboBoxAge) {
		this.textComboBoxAge = textComboBoxAge;
	}

	public void setTextFieldDwz(final JTextField textFieldDwz) {
		this.textFieldDwz = textFieldDwz;
	}

	public void setTextFieldForeName(final JTextField textFieldForeName) {
		this.textFieldForeName = textFieldForeName;
	}

	public void setTextFieldKuerzel(final JTextField textFieldKuerzel) {
		this.textFieldKuerzel = textFieldKuerzel;
	}

	public void setTextFieldSurName(final JTextField textFieldSurName) {
		this.textFieldSurName = textFieldSurName;
	}

	public void spielerPanel() {
		final JPanel all = new JPanel();
		all.setLayout(new BoxLayout(all, BoxLayout.Y_AXIS));
		centerPane = new JPanel();
		textFieldForeName = new JTextField(15);
		textFieldSurName = new JTextField(15);
		textFieldKuerzel = new JTextField(15);
		textFieldDwz = new JTextField(15);
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		final Dimension dim = new Dimension(90, 30);
		JLabel label = new JLabel();
		label.setPreferredSize(dim);
		label.setText(Messages.getString("SpielerHinzufuegenView.12"));
		centerPane.add(label);
		centerPane.add(textFieldForeName);
		label = new JLabel();
		label.setPreferredSize(dim);
		label.setText(Messages.getString("SpielerHinzufuegenView.13"));
		centerPane.add(label);
		centerPane.add(textFieldSurName);
		label = new JLabel();
		label.setPreferredSize(dim);
		label.setText(Messages.getString("SpielerHinzufuegenView.4"));
		centerPane.add(label);
		centerPane.add(textFieldKuerzel);
		all.add(centerPane);
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		label = new JLabel();
		label.setPreferredSize(dim);
		label.setText(Messages.getString("SpielerHinzufuegenView.5"));
		centerPane.add(label); // $NON-NLS-1$
		centerPane.add(textFieldDwz);
		final String[] ageStrings = { Messages.getString("SpielerHinzufuegenView.6"), //$NON-NLS-1$
				Messages.getString("SpielerHinzufuegenView.7"), Messages.getString("SpielerHinzufuegenView.8") }; //$NON-NLS-1$ //$NON-NLS-2$
		textComboBoxAge = new JComboBox<String>(ageStrings);
		label = new JLabel();
		label.setPreferredSize(dim);
		label.setText(Messages.getString("SpielerHinzufuegenView.9"));
		centerPane.add(label);
		centerPane.add(textComboBoxAge);
		all.add(centerPane);
		all.add(new JSeparator());
		final JPanel allall = new JPanel();
		allall.setLayout(new BorderLayout());
		allall.add(all, BorderLayout.NORTH);
		contentPanel.add(allall);
		// contentPanel.add(buttonPane);

		contentPanel.updateUI();

	}
}
