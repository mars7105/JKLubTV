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
import java.awt.SystemColor;
import java.io.IOException;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class StartpagePlayerHTMLView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String language;

	public StartpagePlayerHTMLView() {
		language = Locale.getDefault().getLanguage();
		// setBackground(titleColor);
		// setForeground(titleTextColor);
		ContextMenuMouseListener cmmL = new ContextMenuMouseListener();
		java.net.URL helpURL = getClass().getResource("/files/startpageplayer.html");
		if (language.equals(new Locale("de").getLanguage())) {
			helpURL = getClass().getResource("/files/startpageplayer.html");
		} else if (language.equals(new Locale("en").getLanguage())) {
			helpURL = getClass().getResource("/files/startpageplayer_en.html");
		}
		JPanel all = new JPanel(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JPanel temp = new JPanel();
		Color titleColor = new Color((SystemColor.text).getRGB());
		Color titleTextColor = new Color((SystemColor.textText).getRGB());
		temp.setBackground(titleColor);
		temp.setForeground(titleTextColor);
		temp.setLayout(new BoxLayout(temp, BoxLayout.X_AXIS));
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		editorPane.addMouseListener(cmmL);
		// Anzuzeigende URL wird gesetzt.
		// Da setPage eine IOException wirft, muss
		// der Aufruf im try-catch-Block erfolgen.
		try {
			editorPane.setPage(helpURL); // $NON-NLS-1$
		} catch (IOException ioe) {
			// HTML wird als Texttyp vorgegeben.
			editorPane.setContentType("text/html"); //$NON-NLS-1$

			// Text für Fehlermeldung wird
			// im HTML-Format übergeben.
			editorPane.setText("<html> <center>" + Messages.getString("InfoHomeScreenView.0") + "</center> </html>."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
		panel.add(editorPane);
		panel.add(new JSeparator());
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		all.add(scrollPane, BorderLayout.CENTER);
		// all.add(panel, BorderLayout.NORTH);
		editorPane.setPreferredSize(new Dimension(500, 900));

		add(all);
	}

}
