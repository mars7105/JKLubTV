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

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton cancelButton;
	public MainView() {

		setLayout(new FlowLayout());
//		setBackground(new Color(249, 222, 112));
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane);

		cancelButton = new JButton("Alles Verwerfen");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

	}
}
