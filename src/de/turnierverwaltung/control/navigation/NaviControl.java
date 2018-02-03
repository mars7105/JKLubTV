package de.turnierverwaltung.control.navigation;

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

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.turnierverwaltung.control.MainControl;
import de.turnierverwaltung.control.playerlist.ActionListenerPlayerListControl;
import de.turnierverwaltung.control.tournamentlist.ActionListenerTournamentEditControl;
import de.turnierverwaltung.control.tournamentlist.ActionListenerTournamentListControl;
import de.turnierverwaltung.control.tournamenttable.ActionListenerPairingsMenuControl;
import de.turnierverwaltung.view.navigation.NaviView;

/**
 *
 * @author mars
 *
 */
public class NaviControl {

	public static final int TURNIERTABELLE = 0;
	public static final int TERMINTABELLE = 1;
	public static final int PAARUNGSTABELLE = 2;
	public static final int STANDARD = 1;

	public static final int SORTIEREN = 2;

	private final MainControl mainControl;

	private final NaviView naviView;

	/**
	 *
	 * @param mainControl
	 */
	public NaviControl(final MainControl mainControl) {

		this.mainControl = mainControl;

		naviView = new NaviView();

		this.mainControl.setNaviView(naviView);
		this.mainControl.getNaviView().getTabellenPanel().setVisible(false);
		this.mainControl.getNaviView().getPairingsPanel().setVisible(false);
		this.mainControl.getNaviView().getTurnierListePanel().setVisible(false);
		this.mainControl.getNaviView().getSpielerListePanel().setVisible(false);
		this.mainControl.setActionListenerPairingsMenuControl(new ActionListenerPairingsMenuControl(this.mainControl));
		this.mainControl.setActionListenerFileMenuControl(new ActionListenerFileMenuControl(this.mainControl));
		this.mainControl.setActionListenerPlayerListControl(new ActionListenerPlayerListControl(this.mainControl));
		this.mainControl.setActionListenerTournamentListControl(new ActionListenerTournamentListControl(mainControl));
		this.mainControl
				.setActionListenerTournamentEditControl(new ActionListenerTournamentEditControl(this.mainControl));

		final JPanel hauptPanel = this.mainControl.getMainPanel();
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(naviView);
		hauptPanel.add(scrollPane, BorderLayout.WEST);

		hauptPanel.updateUI();

	}

}
