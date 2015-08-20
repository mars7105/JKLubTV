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
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	private ImageIcon turnierNew = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-new.png")));
	private ImageIcon turnierDelete = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-close-4.png")));
	private ImageIcon turnierProperties = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-edit.png")));
	private ImageIcon turnierLaden = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-preview.png")));
	private JButton turnierAddButton;

	public TurnierListeLadenView(int anzahlTurniere) {

		anzahlElemente = 0;

		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BorderLayout());
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		turnierAddButton = new JButton("Neues Turnier", turnierNew);
		JLabel titleLabel = new JLabel("Turnierliste");
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel newTurnierPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		titlepanel.add(titleLabel);
		newTurnierPanel.add(turnierAddButton);
		northPanel.add(titlepanel);
		northPanel.add(newTurnierPanel);

		add(northPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		contentPanel.add(centerPane);
		turnierLadeButton = new JButton[anzahlTurniere];
		turnierLoeschenButton = new JButton[anzahlTurniere];
		turnierBearbeitenButton = new JButton[anzahlTurniere];
		gruppenBearbeitenButton = new JButton[anzahlTurniere];
	}

	public JButton getTurnierAddButton() {
		return turnierAddButton;
	}

	public void setTurnierAddButton(JButton turnierAddButton) {
		this.turnierAddButton = turnierAddButton;
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

	public void makeTurnierZeile(String turnierName, String startDatum, String endDatum) {
		JPanel turnierLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel buttonLine = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		line = new JPanel();
		line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
		line.setBackground(new Color(249, 222, 112));
		turnierLadeButton[anzahlElemente] = new JButton("Laden", turnierLaden);
		buttonLine.add(turnierLadeButton[anzahlElemente]);
		turnierBearbeitenButton[anzahlElemente] = new JButton("Bearbeiten", turnierProperties);
		buttonLine.add(turnierBearbeitenButton[anzahlElemente]);

		gruppenBearbeitenButton[anzahlElemente] = new JButton("Gruppen", turnierProperties);
		buttonLine.add(gruppenBearbeitenButton[anzahlElemente]);
		turnierLoeschenButton[anzahlElemente] = new JButton("Löschen", turnierDelete);
		buttonLine.add(turnierLoeschenButton[anzahlElemente]);
		JLabel tName = new JLabel("  Turnier: " + turnierName);
		turnierLine.add(tName);
	
		line.add(turnierLine);
		line.add(buttonLine);
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
