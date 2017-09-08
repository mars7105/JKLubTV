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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class TournamentListView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private JPanel contentPanel;
	private JPanel centerPane;
	private JPanel line;
	// private JScrollPane scrollPane;
	private JButton[] turnierLadeButton;
	private int anzahlElemente;
	private JButton[] turnierLoeschenButton;
	private JButton[] turnierBearbeitenButton;

	private ImageIcon turnierDelete = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-close-4.png"))); //$NON-NLS-1$
	private ImageIcon turnierProperties = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-edit.png"))); //$NON-NLS-1$
	private ImageIcon turnierLaden = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-preview.png"))); //$NON-NLS-1$
	private JTabbedPane turnierListe;
	private int turnierTabAnzahl;
	private int anzahlTurniere;
	private TitleLabelView titleView;

	public TournamentListView(int anzahlTurniere, int turnierTabAnzahl) {
		this.anzahlTurniere = anzahlTurniere;
		this.turnierTabAnzahl = turnierTabAnzahl;
		makePanel(anzahlTurniere);
	}

	public void makePanel(int anzahlTurniere) {
		this.anzahlTurniere = anzahlTurniere;
		anzahlElemente = 0;
		setLayout(new BorderLayout());
		turnierListe = new JTabbedPane();
		// contentPanel = new JPanel();
		// contentPanel.setLayout(new BorderLayout());
		// contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BorderLayout());
		// scrollPane = new JScrollPane();
		// scrollPane.setViewportView(contentPanel);
		titleView = new TitleLabelView(Messages.getString("TurnierListeLadenView.3"));

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		northPanel.add(titleView);

		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		// contentPanel.add(turnierListe, BorderLayout.NORTH);
		turnierLadeButton = new JButton[anzahlTurniere];
		turnierLoeschenButton = new JButton[anzahlTurniere];
		turnierBearbeitenButton = new JButton[anzahlTurniere];
		add(northPanel, BorderLayout.NORTH);
		add(turnierListe, BorderLayout.CENTER);
	}

	public TitleLabelView getTitleView() {
		return titleView;
	}

	public void setTitleView(TitleLabelView titleView) {
		this.titleView = titleView;
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
		Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);

		line = new JPanel();
		line.setLayout(new FlowLayout(FlowLayout.LEFT));
		JPanel mainLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		mainLine.setBorder(raisedetched);
		JPanel turnierLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		turnierLine.setPreferredSize(new Dimension(350, 50));
		turnierLine.setBackground(Color.WHITE);
		JPanel buttonLine = new JPanel(new FlowLayout(FlowLayout.LEFT));

		turnierLadeButton[anzahlElemente] = new JButton(Messages.getString("TurnierListeLadenView.4"), turnierLaden); //$NON-NLS-1$
		buttonLine.add(turnierLadeButton[anzahlElemente]);
		turnierBearbeitenButton[anzahlElemente] = new JButton(Messages.getString("TurnierListeLadenView.5"), //$NON-NLS-1$
				turnierProperties);
		buttonLine.add(turnierBearbeitenButton[anzahlElemente]);

		turnierLoeschenButton[anzahlElemente] = new JButton(Messages.getString("TurnierListeLadenView.6"), //$NON-NLS-1$
				turnierDelete);
		buttonLine.add(turnierLoeschenButton[anzahlElemente]);
		JLabel tName = new JLabel(Messages.getString("TurnierListeLadenView.7") + turnierName); //$NON-NLS-1$
		turnierLine.add(tName);
		mainLine.add(turnierLine);
		mainLine.add(buttonLine);
		line.add(mainLine);
		centerPane.add(line);
		// centerPane.add(new JSeparator());
		anzahlElemente++;
		int anzahlItems = 0;
		if (turnierTabAnzahl == 0) {
			anzahlItems = 5;
		}
		if (turnierTabAnzahl == 1) {
			anzahlItems = 10;
		}
		if (turnierTabAnzahl == 2) {
			anzahlItems = 15;
		}
		if (turnierTabAnzahl == 3) {
			anzahlItems = 20;
		}
		if (anzahlElemente % anzahlItems == 0 || anzahlElemente == anzahlTurniere) {
			int endIndex = anzahlElemente;
			int startIndex = endIndex + 1 - centerPane.getComponentCount();
			if (startIndex < 1) {
				startIndex = 1;
			}
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(centerPane, BorderLayout.NORTH);
			JScrollPane playerScrollPane = new JScrollPane();
			playerScrollPane.setViewportView(panel);
			playerScrollPane.setAlignmentY(TOP_ALIGNMENT);
			turnierListe.addTab(startIndex + " " + Messages.getString("SpielerLadenView.7") + " " + endIndex,
					playerScrollPane);
			centerPane = new JPanel();
			centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		}
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
