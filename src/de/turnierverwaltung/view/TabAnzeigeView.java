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
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.turnierverwaltung.controller.MainControl;

public class TabAnzeigeView extends JTabbedPane {
	private MainControl mainControl;
	private Boolean gruppenTab;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TabAnzeigeView(MainControl mainCtrl, Boolean gruppenTb) {
		this.mainControl = mainCtrl;
		this.gruppenTab = gruppenTb;
		ChangeListener changeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				String name = sourceTabbedPane.getTitleAt(index);
				int tabellennummer = -1;
				String tabellenname = "";
				if (mainControl.getTabAnzeigeView2() != null) {
					if (mainControl.getTabAnzeigeView2()[index].getTabCount() > 0) {
						tabellennummer = mainControl.getTabAnzeigeView2()[index].getSelectedIndex();

						tabellenname = mainControl.getTabAnzeigeView2()[index].getTitleAt(tabellennummer);
					}
				}
				if (gruppenTab == true) {
					mainControl.getNaviView().setGruppenname(name);
					if (tabellennummer >= 0) {
						mainControl.getNaviView().setTabellenname(tabellenname);
					}

				} else {
					mainControl.getNaviView().setTabellenname(name);

				}
			}
		};
		this.addChangeListener(changeListener);
	}
}
