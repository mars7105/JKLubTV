package de.turnierverwaltung.view;
//JKlubTV - Ein Programm zum verwalten von Schach Turnieren

import java.awt.BorderLayout;

import javax.swing.BoxLayout;

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

import de.turnierverwaltung.control.MainControl;

public class TabbedPaneView extends JPanel {
	JTabbedPane tabbedPane;
	private TitleLabelView titleView;
	// private MainControl mainControl;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TabbedPaneView(MainControl mainCtrl, String title) {
		setLayout(new BorderLayout());
		titleView = new TitleLabelView(title);
		tabbedPane = new JTabbedPane();
		JPanel all = new JPanel();
		all.setLayout(new BoxLayout(all, BoxLayout.Y_AXIS));

		all.add(titleView);
		all.add(tabbedPane);
		add(all, BorderLayout.NORTH);

	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public TitleLabelView getTitleView() {
		return titleView;
	}

	public void setTitleView(TitleLabelView titleView) {
		this.titleView = titleView;
	}

}
