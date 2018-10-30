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
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class InfoView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private JButton lizenzenButton;
	private JTabbedPane lizenzenPane;

	private JTextArea textArea;
	private JButton okButton;

	public InfoView(JTabbedPane lizenzenPane) {
		this.lizenzenPane = lizenzenPane;

		TitleLabelView titleView = new TitleLabelView(Messages.getString("InfoView.0"));

		setLayout(new BorderLayout());

		// JLabel titleLabel = new JLabel(Messages.getString("InfoView.0"));
		// //$NON-NLS-1$
		// JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		// titlepanel.add(titleLabel);

		// scrollPane = new JScrollPane();
		// scrollPane.setViewportView(lizenzenPane);
		northPanel.add(titleView);
		northPanel.add(topPanel);
		add(northPanel, BorderLayout.NORTH);
		textArea = new JTextArea();
		add(lizenzenPane, BorderLayout.CENTER);
		okButton = new JButton("Ok");
		ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		this.okButton = buttonPane.getOkButton();
		add(buttonPane, BorderLayout.SOUTH);
		this.updateUI();

	}

	public JTabbedPane getLizenzenPane() {
		return lizenzenPane;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

}
