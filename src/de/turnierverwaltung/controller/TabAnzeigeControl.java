package de.turnierverwaltung.controller;
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
import javax.swing.JPanel;

import de.turnierverwaltung.view.TabAnzeigeView;

public class TabAnzeigeControl {
	private TabAnzeigeView tabAnzeigeView;
	private MainControl mainControl;

	public TabAnzeigeControl(MainControl mainControl) {
		this.mainControl = mainControl;
		this.mainControl.setTabAnzeigeView(new TabAnzeigeView(this.mainControl));
		tabAnzeigeView = this.mainControl.getTabAnzeigeView();
	}

	public void makeTab(String title, JPanel panel) {
		tabAnzeigeView.add(title, panel);
	}
}