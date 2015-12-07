package de.turnierverwaltung.view;

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
			editorPane.setPage("http://www.mamuck.de/fileadmin/user_upload/projekte/Info/JKlubTV.html");
		} catch (IOException ioe) {
			// HTML wird als Texttyp vorgegeben.
			editorPane.setContentType("text/html");

			// Text für Fehlermeldung wird
			// im HTML-Format übergeben.
			editorPane.setText("<html> <center>" + "<h1>Page not found</h1>" + "</center> </html>.");
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
