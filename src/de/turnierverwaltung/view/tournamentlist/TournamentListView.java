package de.turnierverwaltung.view.tournamentlist;

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
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.TitleLabelView;

public class TournamentListView extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel centerPane;
	private int anzahlElemente;

	private ArrayList<TournamentListItemView> tournamentListItems;

	private JTabbedPane turnierListe;
	private final int turnierTabAnzahl;
	private int anzahlTurniere;
	private TitleLabelView titleView;

	public TournamentListView(final int anzahlTurniere, final int turnierTabAnzahl) {
		this.anzahlTurniere = anzahlTurniere;
		this.turnierTabAnzahl = turnierTabAnzahl;
		makePanel(anzahlTurniere);
	}

	public TitleLabelView getTitleView() {
		return titleView;
	}

	public ArrayList<TournamentListItemView> getTournamentListItems() {
		return tournamentListItems;
	}

	public void makePanel(final int anzahlTurniere) {
		this.anzahlTurniere = anzahlTurniere;
		anzahlElemente = 0;
		setLayout(new BorderLayout());
		turnierListe = new JTabbedPane();

		titleView = new TitleLabelView(Messages.getString("TurnierListeLadenView.3"));

		final JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.PAGE_AXIS));
		northPanel.add(titleView);

		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));

		tournamentListItems = new ArrayList<TournamentListItemView>();
		add(northPanel, BorderLayout.NORTH);
		add(turnierListe, BorderLayout.CENTER);
	}

	public void makeTurnierZeile(final String turnierName, final String startDatum, final String endDatum) {

		// line = new JPanel();
		// line.setLayout(new FlowLayout(FlowLayout.LEFT));

		final TournamentListItemView tournamentListItemView = new TournamentListItemView(turnierName);
		tournamentListItems.add(tournamentListItemView);
		// line.add(tournamentListItemView);
		centerPane.add(tournamentListItemView);
		anzahlElemente++;
		// int anzahlItems = 0;
		// if (turnierTabAnzahl == 0) {
		// anzahlItems = 5;
		// }
		// if (turnierTabAnzahl == 1) {
		// anzahlItems = 10;
		// }
		// if (turnierTabAnzahl == 2) {
		// anzahlItems = 15;
		// }
		// if (turnierTabAnzahl == 3) {
		// anzahlItems = 20;
		// }
		final int anzahlItems = 5 + turnierTabAnzahl * 5;
		if (anzahlElemente % anzahlItems == 0 || anzahlElemente == anzahlTurniere) {
			final int endIndex = anzahlElemente;
			int startIndex = endIndex + 1 - centerPane.getComponentCount();
			if (startIndex < 1) {
				startIndex = 1;
			}
			final JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(centerPane, BorderLayout.NORTH);
			final JScrollPane playerScrollPane = new JScrollPane();
			playerScrollPane.setViewportView(panel);
			playerScrollPane.setAlignmentY(TOP_ALIGNMENT);
			turnierListe.addTab(startIndex + " " + Messages.getString("SpielerLadenView.7") + " " + endIndex,
					playerScrollPane);
			centerPane = new JPanel();
			centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		}
	}

	public void setTitleView(final TitleLabelView titleView) {
		this.titleView = titleView;
	}

	public void setTournamentListItems(final ArrayList<TournamentListItemView> tournamentListItems) {
		this.tournamentListItems = tournamentListItems;
	}

}
