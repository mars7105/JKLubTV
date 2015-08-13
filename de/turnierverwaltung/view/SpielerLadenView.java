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

import de.turnierverwaltung.model.Spieler;
import de.turnierverwaltung.model.TurnierKonstanten;

public class SpielerLadenView extends JPanel {
	private JPanel contentPanel;
	private JPanel mainPane;
	private JScrollPane scrollPane;
	private JPanel centerPane;
	private JButton[] spielerBearbeitenButton;
	private JButton[] spielerLoeschenButton;
	private JButton spielerAddButton;
	private int anzahlElemente;
	private JPanel line;
	int spielerAnzahl;

	public SpielerLadenView(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		anzahlElemente = 0;

		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 100;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 100;
		setPreferredSize(new Dimension(windowWidth, windowHeight));
		spielerAddButton = new JButton("neue Spieler");
		mainPane = new JPanel();
		mainPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		mainPane.add(spielerAddButton);
		add(mainPane);
		spielerBearbeitenButton = new JButton[this.spielerAnzahl];
		spielerLoeschenButton = new JButton[this.spielerAnzahl];
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

	}

	public JButton getSpielerAddButton() {
		return spielerAddButton;
	}

	public JButton[] getSpielerBearbeitenButton() {
		return spielerBearbeitenButton;
	}

	public JButton[] getSpielerLoeschenButton() {
		return spielerLoeschenButton;
	}

	public void makeSpielerZeile(Spieler spieler) {
		line = new JPanel();
		line.setLayout(new FlowLayout(FlowLayout.LEFT));
		line.setBackground(new Color(249, 222, 112));
		spielerBearbeitenButton[anzahlElemente] = new JButton(
				"Spieler bearbeiten");

		line.add(spielerBearbeitenButton[anzahlElemente]);
		spielerLoeschenButton[anzahlElemente] = new JButton("Spieler löschen");
		line.add(spielerLoeschenButton[anzahlElemente]);
		JLabel sname = new JLabel("  Spielername: " + spieler.getName());
		line.add(sname);
		JLabel nkuerzel = new JLabel(" Namenskürzel " + spieler.getKuerzel());
		line.add(nkuerzel);
		JLabel dwz = new JLabel("  DWZ: " + spieler.getDwz());
		line.add(dwz);
//		JLabel age = new JLabel("  Alter: " + spieler.getAge());
//		line.add(age);

		centerPane.add(line);
		centerPane.add(new JSeparator());

		anzahlElemente++;
	}

	public void setSpielerAddButton(JButton spielerAddButton) {
		this.spielerAddButton = spielerAddButton;
	}

	public void setSpielerBearbeitenButton(JButton[] spielerBearbeitenButton) {
		this.spielerBearbeitenButton = spielerBearbeitenButton;
	}

	public void setSpielerLoeschenButton(JButton[] spielerLoeschenButton) {
		this.spielerLoeschenButton = spielerLoeschenButton;
	}

}
