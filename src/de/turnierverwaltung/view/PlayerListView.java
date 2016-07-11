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
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.Player;

public class PlayerListView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JPanel centerPane;
	private JButton[] spielerBearbeitenButton;
	private JButton[] spielerLoeschenButton;
	private int anzahlElemente;
	private JPanel line;

	private JTabbedPane spielerListe;

	private ImageIcon userDelete = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-delete-2.png"))); //$NON-NLS-1$
	private ImageIcon userProperties = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-properties.png"))); //$NON-NLS-1$

	private int spielerAnzahl;
	private int spielerTabAnzahl;
	private TitleLabelView titleView;

	public PlayerListView(int spielerAnzahl, int spielerTabAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		this.spielerTabAnzahl = spielerTabAnzahl;
		init(this.spielerAnzahl);

	}

	public void init(int anzahl) {
		spielerAnzahl = anzahl;
		anzahlElemente = 0;
		setLayout(new BorderLayout());
		spielerListe = new JTabbedPane();

		titleView = new TitleLabelView(Messages.getString("SpielerLadenView.2"));

		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
		mainPane.add(titleView);

		add(mainPane, BorderLayout.NORTH);
		spielerBearbeitenButton = new JButton[this.spielerAnzahl];
		spielerLoeschenButton = new JButton[this.spielerAnzahl];

		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));

		add(spielerListe, BorderLayout.CENTER);

	}

	public JButton[] getSpielerBearbeitenButton() {
		return spielerBearbeitenButton;
	}

	public JButton[] getSpielerLoeschenButton() {
		return spielerLoeschenButton;
	}

	public void makeSpielerZeile(Player spieler, int index) {
		line = new JPanel();

		line.setLayout(new FlowLayout(FlowLayout.LEFT));

		JPanel playerLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		playerLine.setPreferredSize(new Dimension(350, 50));
		JPanel buttonLine = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JLabel sname = new JLabel(Messages.getString("SpielerLadenView.3") + spieler.getName()); //$NON-NLS-1$
		playerLine.add(sname);

		JLabel dwz = new JLabel(Messages.getString("SpielerLadenView.4") + spieler.getDwz()); //$NON-NLS-1$
		playerLine.add(dwz);
		line.add(playerLine);
		spielerBearbeitenButton[index] = new JButton(Messages.getString("SpielerLadenView.5"), userProperties); //$NON-NLS-1$

		buttonLine.add(spielerBearbeitenButton[index]);
		spielerLoeschenButton[index] = new JButton(Messages.getString("SpielerLadenView.6"), userDelete); //$NON-NLS-1$
		buttonLine.add(spielerLoeschenButton[index]);
		line.add(buttonLine);
		centerPane.add(line);
		centerPane.add(new JSeparator());

		anzahlElemente++;
		int anzahlItems = 0;
		if (spielerTabAnzahl == 0) {
			anzahlItems = 5;
		}
		if (spielerTabAnzahl == 1) {
			anzahlItems = 10;
		}
		if (spielerTabAnzahl == 2) {
			anzahlItems = 15;
		}
		if (spielerTabAnzahl == 3) {
			anzahlItems = 20;
		}
		if (anzahlElemente % anzahlItems == 0 || anzahlElemente == spielerAnzahl) {
			int endIndex = anzahlElemente;
			int startIndex = endIndex + 1 - centerPane.getComponentCount() / 2;
			if (startIndex < 1) {
				startIndex = 1;
			}
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(centerPane, BorderLayout.NORTH);
			JScrollPane playerScrollPane = new JScrollPane();
			playerScrollPane.setViewportView(panel);
			playerScrollPane.setAlignmentY(TOP_ALIGNMENT);
			spielerListe.addTab(startIndex + " " + Messages.getString("SpielerLadenView.7") + " " + endIndex,
					playerScrollPane);
			centerPane = new JPanel();
			centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		}

	}

	public void setSpielerBearbeitenButton(JButton[] spielerBearbeitenButton) {
		this.spielerBearbeitenButton = spielerBearbeitenButton;
	}

	public void setSpielerLoeschenButton(JButton[] spielerLoeschenButton) {
		this.spielerLoeschenButton = spielerLoeschenButton;
	}

	public int getAnzahlElemente() {
		return anzahlElemente;
	}

	public void setAnzahlElemente(int anzahlElemente) {
		this.anzahlElemente = anzahlElemente;
	}

	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}

	public void setSpielerAnzahl(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}

	public JTabbedPane getSpielerListe() {
		return spielerListe;
	}

	public void setSpielerListe(JTabbedPane spielerListe) {
		this.spielerListe = spielerListe;
	}

	public TitleLabelView getTitleView() {
		return titleView;
	}

	public void setTitleView(TitleLabelView titleView) {
		this.titleView = titleView;
	}

}
