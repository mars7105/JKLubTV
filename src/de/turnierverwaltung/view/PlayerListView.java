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
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import de.turnierverwaltung.model.Player;

public class PlayerListView extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel mainPane;
	private JPanel centerPane;
	private ArrayList<PlayerListItemView> playerListItems;
	// private JButton[] spielerLoeschenButton;
	private int anzahlElemente;
	private JTabbedPane spielerListe;

	// private final ImageIcon userDelete = new ImageIcon(
	// Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-delete-2.png")));
	// //$NON-NLS-1$
	// private final ImageIcon userProperties = new ImageIcon(
	// Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/user-properties.png")));
	// //$NON-NLS-1$

	private int spielerAnzahl;
	private final int spielerTabAnzahl;
	private TitleLabelView titleView;

	public PlayerListView(final int spielerAnzahl, final int spielerTabAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		this.spielerTabAnzahl = spielerTabAnzahl;
		init(this.spielerAnzahl);

	}

	public int getAnzahlElemente() {
		return anzahlElemente;
	}

	public ArrayList<PlayerListItemView> getPlayerListItems() {
		return playerListItems;
	}

	// public JButton[] getSpielerBearbeitenButton() {
	// return spielerBearbeitenButton;
	// }

	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}

	// public JButton[] getSpielerLoeschenButton() {
	// return spielerLoeschenButton;
	// }

	public JTabbedPane getSpielerListe() {
		return spielerListe;
	}

	public TitleLabelView getTitleView() {
		return titleView;
	}

	public void init(final int anzahl) {
		playerListItems = new ArrayList<PlayerListItemView>();
		spielerAnzahl = anzahl;
		anzahlElemente = 0;
		setLayout(new BorderLayout());
		spielerListe = new JTabbedPane();

		titleView = new TitleLabelView(Messages.getString("SpielerLadenView.2"));

		mainPane = new JPanel();
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.PAGE_AXIS));
		mainPane.add(titleView);

		add(mainPane, BorderLayout.NORTH);
		// spielerBearbeitenButton = new JButton[spielerAnzahl];
		// spielerLoeschenButton = new JButton[spielerAnzahl];

		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));

		add(spielerListe, BorderLayout.CENTER);

	}

	public void makeSpielerZeile(final Player spieler, final int index) {

		// final Border raisedetched =
		// BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		// line = new JPanel();
		//
		// line.setLayout(new FlowLayout(FlowLayout.LEFT));
		// final JPanel mainLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// mainLine.setBorder(raisedetched);
		// final JPanel playerLine = new JPanel();
		// playerLine.setLayout(new BoxLayout(playerLine, BoxLayout.PAGE_AXIS));
		// playerLine.setPreferredSize(new Dimension(350, 50));
		// playerLine.setBackground(Color.WHITE);
		//
		// final JPanel buttonLine = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// final String lineText0 = spieler.getName();
		// String lineText1 = "";
		//
		// if (spieler.getDWZ() > 0) {
		// lineText1 += "DWZ: " + spieler.getDwz();
		// }
		// if (spieler.getDwzData().getCsvFIDE_Elo() > 0) {
		// lineText1 += " ELO: " + spieler.getDwzData().getCsvFIDE_Elo();
		// }
		//
		// String lineText2 = "";
		// if (spieler.getDwzData().getCsvZPS().length() > 0) {
		// lineText2 += "ZPS: " + spieler.getDwzData().getCsvZPS() + " - MGL: " +
		// spieler.getDwzData().getCsvMgl_Nr();
		// }
		// final JLabel col0 = new JLabel(lineText0);
		// playerLine.add(col0);
		// final JLabel col1 = new JLabel(lineText1);
		// playerLine.add(col1);
		// final JLabel col2 = new JLabel(lineText2);
		// playerLine.add(col2);
		//
		// mainLine.add(playerLine);
		//
		// spielerBearbeitenButton[index] = new
		// JButton(Messages.getString("SpielerLadenView.5"), userProperties);
		// //$NON-NLS-1$
		//
		// buttonLine.add(spielerBearbeitenButton[index]);
		// spielerLoeschenButton[index] = new
		// JButton(Messages.getString("SpielerLadenView.6"), userDelete); //$NON-NLS-1$
		// buttonLine.add(spielerLoeschenButton[index]);
		// mainLine.add(buttonLine);
		// line.add(mainLine);
		final PlayerListItemView playerListItemView = new PlayerListItemView(spieler);
		playerListItems.add(playerListItemView);
		centerPane.add(playerListItemView);
		// centerPane.add(new JSeparator());

		anzahlElemente++;
		final int anzahlItems = 5 + spielerTabAnzahl * 5;
		// if (spielerTabAnzahl == 0) {
		// anzahlItems = 5;
		// }
		// if (spielerTabAnzahl == 1) {
		// anzahlItems = 10;
		// }
		// if (spielerTabAnzahl == 2) {
		// anzahlItems = 15;
		// }
		// if (spielerTabAnzahl == 3) {
		// anzahlItems = 20;
		// }
		if (anzahlElemente % anzahlItems == 0 || anzahlElemente == spielerAnzahl) {
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
			spielerListe.addTab(startIndex + " " + Messages.getString("SpielerLadenView.7") + " " + endIndex,
					playerScrollPane);
			centerPane = new JPanel();
			centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		}

	}

	public void setAnzahlElemente(final int anzahlElemente) {
		this.anzahlElemente = anzahlElemente;
	}

	// public void setSpielerBearbeitenButton(final JButton[]
	// spielerBearbeitenButton) {
	// this.spielerBearbeitenButton = spielerBearbeitenButton;
	// }

	public void setPlayerListItems(final ArrayList<PlayerListItemView> playerListItems) {
		this.playerListItems = playerListItems;
	}

	// public void setSpielerLoeschenButton(final JButton[] spielerLoeschenButton) {
	// this.spielerLoeschenButton = spielerLoeschenButton;
	// }

	public void setSpielerAnzahl(final int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}

	public void setSpielerListe(final JTabbedPane spielerListe) {
		this.spielerListe = spielerListe;
	}

	public void setTitleView(final TitleLabelView titleView) {
		this.titleView = titleView;
	}

}
