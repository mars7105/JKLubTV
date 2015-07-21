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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import de.turnierverwaltung.model.TurnierKonstanten;

public class TurnierListeLadenView extends JPanel {
	private JPanel contentPanel;
	private JPanel centerPane;
	private JPanel line;
	private JScrollPane scrollPane;
	private JButton[] turnierLadeButton;
	private int anzahlElemente;
	private JButton[] turnierLoeschenButton;
	private JButton[] turnierBearbeitenButton;
	private JButton[] gruppenBearbeitenButton;

	public TurnierListeLadenView(int anzahlTurniere) {

		anzahlElemente = 0;

		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 100;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 100;

		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
		add(scrollPane, BorderLayout.CENTER);
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		contentPanel.add(centerPane);
		turnierLadeButton = new JButton[anzahlTurniere];
		turnierLoeschenButton = new JButton[anzahlTurniere];
		turnierBearbeitenButton = new JButton[anzahlTurniere];
		gruppenBearbeitenButton = new JButton[anzahlTurniere];
	}

	public JButton[] getGruppenBearbeitenButton() {
		return gruppenBearbeitenButton;
	}

	public JButton[] getTurnierBearbeitenButton() {
		return turnierBearbeitenButton;
	}

	public JButton[] getTurnierLadeButton() {
		return turnierLadeButton;
	}

	public JButton[] getTurnierLoeschenButton() {
		return turnierLoeschenButton;
	}

	public void makeTurnierZeile(String turnierName, String startDatum,
			String endDatum) {
		line = new JPanel();
		line.setLayout(new FlowLayout(FlowLayout.LEFT));
		line.setBackground(new Color(249, 222, 112));
		turnierLadeButton[anzahlElemente] = new JButton("Turnier laden");
		line.add(turnierLadeButton[anzahlElemente]);
		turnierBearbeitenButton[anzahlElemente] = new JButton(
				"Turnier bearbeiten");
		line.add(turnierBearbeitenButton[anzahlElemente]);
		turnierLoeschenButton[anzahlElemente] = new JButton("Turnier löschen");
		line.add(turnierLoeschenButton[anzahlElemente]);
		gruppenBearbeitenButton[anzahlElemente] = new JButton(
				"Gruppen bearbeiten");
		line.add(gruppenBearbeitenButton[anzahlElemente]);
		JLabel tName = new JLabel("  Turnier: " + turnierName);
		line.add(tName);
		JLabel sDatum = new JLabel(" Datum: " + startDatum);
		line.add(sDatum);
		JLabel eDatum = new JLabel(" - " + endDatum);
		line.add(eDatum);

		centerPane.add(line);
		centerPane.add(new JSeparator());
		anzahlElemente++;
	}

	public void setGruppenBearbeitenButton(JButton[] gruppenBearbeitenButton) {
		this.gruppenBearbeitenButton = gruppenBearbeitenButton;
	}

	public void setTurnierBearbeitenButton(JButton[] turnierBearbeitenButton) {
		this.turnierBearbeitenButton = turnierBearbeitenButton;
	}

	public void setTurnierLadeButton(JButton[] turnierLadeButton) {
		this.turnierLadeButton = turnierLadeButton;
	}

	public void setTurnierLoeschenButton(JButton[] turnierLoeschenButton) {
		this.turnierLoeschenButton = turnierLoeschenButton;
	}

}
