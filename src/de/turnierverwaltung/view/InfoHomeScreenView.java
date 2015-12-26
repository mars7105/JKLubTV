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
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class InfoHomeScreenView {

	public JPanel getLizenzText() throws URISyntaxException {
		JPanel all = new JPanel(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JPanel temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new BoxLayout(temp, BoxLayout.X_AXIS));
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		// Anzuzeigende URL wird gesetzt.
		// Da setPage eine IOException wirft, muss
		// der Aufruf im try-catch-Block erfolgen.
		try {
			editorPane.setPage("http://www.mamuck.de/fileadmin/user_upload/projekte/Info/JKlubTV.html"); //$NON-NLS-1$
		} catch (IOException ioe) {
			// HTML wird als Texttyp vorgegeben.
			editorPane.setContentType("text/html"); //$NON-NLS-1$

			// Text für Fehlermeldung wird
			// im HTML-Format übergeben.
			editorPane.setText("<html> <center>" + Messages.getString("InfoHomeScreenView.0") + "</center> </html>."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		panel.add(editorPane);
		panel.add(new JSeparator());

		all.add(panel, BorderLayout.NORTH);
		editorPane.setPreferredSize(new Dimension(500,900));

		return all;

	}

	public String getHelpText() {
		return null;

	}


}
