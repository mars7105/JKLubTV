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


import de.turnierverwaltung.model.Spieler;

public class SpielerLadenView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel mainPane;
	private JScrollPane scrollPane;
	private JPanel centerPane;
	private JButton[] spielerBearbeitenButton;
	private JButton[] spielerLoeschenButton;
	private JButton spielerAddButton;
	private int anzahlElemente;
	private JPanel line;
	private ImageIcon userNew = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-new-3.png")));
	private ImageIcon userDelete = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-delete-2.png")));
	private ImageIcon userProperties = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-properties.png")));
	private ImageIcon userImport = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-open-4.png")));
	private ImageIcon userExport = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-export.png")));

	int spielerAnzahl;
	private JButton spielerImport;
	private JButton spielerExport;

	public SpielerLadenView(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		anzahlElemente = 0;
		setLayout(new BorderLayout());
//		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 100;
//		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 100;
//		setPreferredSize(new Dimension(windowWidth, windowHeight));
		spielerAddButton = new JButton("Neuer Spieler", userNew);
		spielerImport = new JButton("Import Spielerliste", userImport);
//		spielerImport.setEnabled(false);
		spielerExport = new JButton("Export Spielerliste", userExport);
		//spielerExport.setEnabled(false);
		JLabel titleLabel = new JLabel("Spielerliste");
		JPanel titlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel newPlayerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		titlepanel.add(titleLabel);
		newPlayerPanel.add(spielerAddButton);
		newPlayerPanel.add(spielerImport);
		newPlayerPanel.add(spielerExport);
		
		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane,BoxLayout.PAGE_AXIS));
		mainPane.add(titlepanel);
		mainPane.add(newPlayerPanel);

		add(mainPane,BorderLayout.NORTH);
		spielerBearbeitenButton = new JButton[this.spielerAnzahl];
		spielerLoeschenButton = new JButton[this.spielerAnzahl];
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
//		contentPanel.setBackground(new Color(249, 222, 112));
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		contentPanel.add(centerPane,BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
//		scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
		add(scrollPane, BorderLayout.CENTER);


	}

	public JButton getSpielerImport() {
		return spielerImport;
	}

	public void setSpielerImport(JButton spielerImport) {
		this.spielerImport = spielerImport;
	}

	public JButton getSpielerExport() {
		return spielerExport;
	}

	public void setSpielerExport(JButton spielerExport) {
		this.spielerExport = spielerExport;
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

//		line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
		line.setLayout(new FlowLayout(FlowLayout.CENTER));
		line.setPreferredSize(new Dimension(600,50));
//		line.setBackground(new Color(249, 222, 112));
		JPanel playerLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		playerLine.setPreferredSize(new Dimension(300,50));
		JPanel buttonLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonLine.setPreferredSize(new Dimension(300,50));

		JLabel sname = new JLabel("  Spielername: " + spieler.getName());
		playerLine.add(sname);
		// JLabel nkuerzel = new JLabel(" Namenskürzel " +
		// spieler.getKuerzel());
		// playerLine.add(nkuerzel);
		JLabel dwz = new JLabel("  DWZ: " + spieler.getDwz());
		playerLine.add(dwz);
		line.add(playerLine);
		spielerBearbeitenButton[anzahlElemente] = new JButton("Bearbeiten", userProperties);

		buttonLine.add(spielerBearbeitenButton[anzahlElemente]);
		spielerLoeschenButton[anzahlElemente] = new JButton("Löschen", userDelete);
		buttonLine.add(spielerLoeschenButton[anzahlElemente]);
		line.add(buttonLine);
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
