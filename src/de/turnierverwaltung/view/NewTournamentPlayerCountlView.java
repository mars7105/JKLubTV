package de.turnierverwaltung.view;

import java.awt.BorderLayout;
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
//import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class NewTournamentPlayerCountlView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JButton okButton;
	private JTextField anzahlSpielerTextField;

	public NewTournamentPlayerCountlView(String title) {

		setLayout(new FlowLayout());
		contentPanel = new JPanel();

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		

		ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		JPanel centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.X_AXIS));

		
		okButton = buttonPane.getOkButton();

		JLabel label = new JLabel(Messages.getString("SpielerAnzahlView.2")); //$NON-NLS-1$
		anzahlSpielerTextField = new JTextField(5);
		anzahlSpielerTextField.grabFocus();
		centerPane.add(label);
		centerPane.add(anzahlSpielerTextField);
		contentPanel.add(centerPane);
		contentPanel.add(buttonPane);

		String help = Messages.getString("SpielerAnzahlView.3"); //$NON-NLS-1$
		JPanel helpPanel = new JPanel();
		JTextArea helpText = new JTextArea();
		helpText.setText(help);
		helpText.setEditable(false);
		helpPanel.add(helpText);
		contentPanel.add(helpPanel);
		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(contentPanel);
		add(scrollPane);

		updateUI();

	}

	public JTextField getAnzahlSpielerTextField() {
		return anzahlSpielerTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setAnzahlSpielerTextField(JTextField anzahlSpielerTextField) {
		this.anzahlSpielerTextField = anzahlSpielerTextField;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}
}
