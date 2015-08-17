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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import de.turnierverwaltung.model.TurnierKonstanten;

public class TurnierTabelleView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	// private JPanel centerPane;
	private JPanel downPane;
	private JButton okButton;
	private JButton saveButton;

	private JButton cancelButton;
	private JScrollPane scrollPane;
	private JTextField[] rundenNummer;
	private int spielerAnzahl;
	private JLabel[] weissSpieler;
	private JLabel[] schwarzSpieler;
	private JLabel[] ergebniss;
	private JTextField[] datum;
	private JButton[] changeColor;
	private int anzahlElemente;

	public TurnierTabelleView() {
		makePanel();

	}

	public void makePanel() {
		anzahlElemente = 0;
//		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 200;
//		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 50;
//		setBounds(0, 0, windowWidth, windowHeight);
		setBackground(new Color(249, 222, 112));
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BorderLayout());
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
//		scrollPane.setPreferredSize(new Dimension(windowWidth - 25,
//				windowHeight - 25));
		add(scrollPane, BorderLayout.CENTER);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);
		okButton = new JButton("Übernehmen");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		saveButton = new JButton("Speichern");
		saveButton.setActionCommand("OK");
		buttonPane.add(saveButton);
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		contentPanel.add(buttonPane);

	}
}
