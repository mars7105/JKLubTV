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
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class InfoLicenseView {

	private URI datepickerUri;
	private JButton buttonDatePicker;
	private URI sqLiteUri;
	private JButton buttonSQlite;
	private URI iTextUri;
	private JButton buttoniText;
	private URI openIconUri;
	private JButton buttonopenIcon;
	private URI emailUri;
	private JButton buttonemailUri;
	private URI dewisUri;
	private JButton buttondewisUri;
	private ImageIcon logoImg = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/Logo.png"))); //$NON-NLS-1$
	private URI jgoodiesUri;
	private JButton buttonjgoodies;
	private URI opencsvUri;
	private JButton buttonopencsv;
	private URI poiUri;
	private JButton buttonpoi;
	private URI iCal4jUri;
	private JButton buttoniCal4j;
	private URI gsonUri;
	private JButton buttonGson;

	class OpenUrlAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonDatePicker) {
				open(datepickerUri);
			}
			if (e.getSource() == buttonSQlite) {
				open(sqLiteUri);
			}
			if (e.getSource() == buttoniText) {
				open(iTextUri);
			}
			if (e.getSource() == buttonopenIcon) {
				open(openIconUri);
			}
			if (e.getSource() == buttonemailUri) {
				open(emailUri);
			}
			if (e.getSource() == buttondewisUri) {
				open(dewisUri);
			}
			if (e.getSource() == buttonjgoodies) {
				open(jgoodiesUri);
			}
			if (e.getSource() == buttonopencsv) {
				open(opencsvUri);
			}
			if (e.getSource() == buttonpoi) {
				open(poiUri);
			}
			if (e.getSource() == buttoniCal4j) {
				open(iCal4jUri);
			}
			if (e.getSource() == buttonGson) {
				open(gsonUri);
			}
		}

	}

	public JPanel getLizenzText() throws URISyntaxException {
		datepickerUri = new URI("http://jdatepicker.org/"); //$NON-NLS-1$
		JPanel all = new JPanel(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JPanel temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new BoxLayout(temp, BoxLayout.X_AXIS));
		JTextArea lizenzLabel = new JTextArea(Messages.getString("InfoLizenzenView.2") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.3") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.4") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.5") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.6") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.7") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.8") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.9") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.10") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.12") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.13") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.14") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.15") //$NON-NLS-1$
				+ Messages.getString("InfoLizenzenView.16")); //$NON-NLS-1$
		emailUri = new URI(Messages.getString("InfoLizenzenView.17")); //$NON-NLS-1$
		buttonemailUri = new JButton();
		buttonemailUri.add(new JLabel(logoImg));
		buttonemailUri.setOpaque(false);
		buttonemailUri.setToolTipText(emailUri.toString());
		buttonemailUri.addActionListener(new OpenUrlAction());
		temp.add(buttonemailUri);
		JPanel temp2 = new JPanel();
		temp2.setBackground(Color.WHITE);
		temp2.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp2.add(lizenzLabel);
		temp.add(temp2);
		panel.add(temp);
		panel.add(new JSeparator());

		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(new JLabel(Messages.getString("InfoLizenzenView.18"))); //$NON-NLS-1$

		panel.add(temp);
		panel.add(new JSeparator());
		JLabel labelDatePicker = new JLabel();
		labelDatePicker.setText("1. JDatePicker -> jdatepicker-1.3.4.jar\n für die Auswahl des Datum.\n"); //$NON-NLS-1$
		buttonDatePicker = new JButton();
		buttonDatePicker.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$
		buttonDatePicker.setOpaque(false);
		buttonDatePicker.setToolTipText(datepickerUri.toString());
		buttonDatePicker.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));

		temp.add(buttonDatePicker);
		temp.add(labelDatePicker);
		panel.add(temp);
		panel.add(new JSeparator());

		sqLiteUri = new URI("https://bitbucket.org/xerial/sqlite-jdbc/downloads"); //$NON-NLS-1$

		JLabel labelSQlite = new JLabel(
				"2. SQLite JDBC -> sqlite-jdbc-3.8.11.1.jar\n für die Speicherung im SQLite Format.\n"); //$NON-NLS-1$
		buttonSQlite = new JButton();
		buttonSQlite.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$

		buttonSQlite.setOpaque(false);
		buttonSQlite.setToolTipText(sqLiteUri.toString());
		buttonSQlite.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonSQlite);

		temp.add(labelSQlite);
		panel.add(temp);
		panel.add(new JSeparator());

		iTextUri = new URI("http://itextpdf.com/"); //$NON-NLS-1$
		JLabel labeliText = new JLabel("3. iText, Programmable PDF Software\n für die PDF Ausgabe.\n"); //$NON-NLS-1$
		buttoniText = new JButton();
		buttoniText.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$
		// buttoniText.setHorizontalAlignment(SwingConstants.LEFT);
		// buttoniText.setBorderPainted(false);
		buttoniText.setOpaque(false);
		// buttoniText.setBackground(Color.WHITE);
		buttoniText.setToolTipText(iTextUri.toString());
		buttoniText.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttoniText);

		temp.add(labeliText);
		panel.add(temp);
		panel.add(new JSeparator());
		dewisUri = new URI("http://www.schachbund.de/api.html"); //$NON-NLS-1$
		buttondewisUri = new JButton();
		JLabel labeldewis = new JLabel();
		labeldewis.setText("4. Java API von Peter Fahsel -> dewis.jar\n Zugriff auf DWZ-Listen mit Java.\n"); //$NON-NLS-1$
		buttondewisUri.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$

		buttondewisUri.setOpaque(false);
		buttondewisUri.setToolTipText(datepickerUri.toString());
		buttondewisUri.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));

		temp.add(buttondewisUri);
		temp.add(labeldewis);
		panel.add(temp);
		panel.add(new JSeparator());

		openIconUri = new URI("http://sourceforge.net/projects/openiconlibrary/"); //$NON-NLS-1$
		JLabel openIconPicker = new JLabel(
				"5. Bilder: Open Icon Library -> \nhttp://sourceforge.net/projects/openiconlibrary/\n"); //$NON-NLS-1$
		buttonopenIcon = new JButton();
		buttonopenIcon.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$

		buttonopenIcon.setOpaque(false);
		buttonopenIcon.setToolTipText(openIconUri.toString());
		buttonopenIcon.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonopenIcon);

		temp.add(openIconPicker);
		panel.add(temp);
		panel.add(new JSeparator());

		jgoodiesUri = new URI("http://www.jgoodies.com/"); //$NON-NLS-1$
		JLabel jgoodiesUriPicker = new JLabel("6. Look and Feel: JGoodies -> \nhttp://www.jgoodies.com/\n"); //$NON-NLS-1$
		buttonjgoodies = new JButton();
		buttonjgoodies.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$

		buttonjgoodies.setOpaque(false);
		buttonjgoodies.setToolTipText(jgoodiesUri.toString());
		buttonjgoodies.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonjgoodies);

		temp.add(jgoodiesUriPicker);
		panel.add(temp);
		panel.add(new JSeparator());

		opencsvUri = new URI("http://opencsv.sourceforge.net/"); //$NON-NLS-1$
		JLabel opencsvUriPicker = new JLabel("7. Opencsv -> \nhttp://opencsv.sourceforge.net/\n"); //$NON-NLS-1$
		buttonopencsv = new JButton();
		buttonopencsv.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$

		buttonopencsv.setOpaque(false);
		buttonopencsv.setToolTipText(opencsvUri.toString());
		buttonopencsv.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonopencsv);

		temp.add(opencsvUriPicker);
		panel.add(temp);
		panel.add(new JSeparator());

		poiUri = new URI("https://poi.apache.org/"); //$NON-NLS-1$
		JLabel poiUriPicker = new JLabel(
				"8. Apache POI - the Java API for Microsoft Documents -> \nhttps://poi.apache.org/\n"); //$NON-NLS-1$
		buttonpoi = new JButton();
		buttonpoi.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$

		buttonpoi.setOpaque(false);
		buttonpoi.setToolTipText(poiUri.toString());
		buttonpoi.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonpoi);

		temp.add(poiUriPicker);
		panel.add(temp);
		panel.add(new JSeparator());
		iCal4jUri = new URI("http://ical4j.sourceforge.net/index.html"); //$NON-NLS-1$
		JLabel iCal4jUriPicker = new JLabel(
				"9. iCal4j is a Java library used to read and write iCalendar data streams as defined in RFC2445. -> \nhttp://ical4j.sourceforge.net/index.html\n"); //$NON-NLS-1$
		buttoniCal4j = new JButton();
		buttoniCal4j.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$

		buttoniCal4j.setOpaque(false);
		buttoniCal4j.setToolTipText(iCal4jUri.toString());
		buttoniCal4j.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttoniCal4j);

		temp.add(iCal4jUriPicker);
		panel.add(temp);
		panel.add(new JSeparator());

		gsonUri = new URI("https://github.com/google/gson"); //$NON-NLS-1$
		JLabel gsonUriPicker = new JLabel(
				"10. A Java serialization/deserialization library that can convert Java Objects into JSON and back. -> \nhttps://github.com/google/gson\n"); //$NON-NLS-1$
		buttonGson = new JButton();
		buttonGson.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>"); //$NON-NLS-1$

		buttonGson.setOpaque(false);
		buttonGson.setToolTipText(gsonUri.toString());
		buttonGson.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonGson);

		temp.add(gsonUriPicker);
		panel.add(temp);
		panel.add(new JSeparator());

		JPanel panelBorder = new JPanel();
		panelBorder.setLayout(new BorderLayout());
		panelBorder.add(panel, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panelBorder);
		all.add(scrollPane, BorderLayout.CENTER);

		return all;

	}

	public String getHelpText() {
		return null;

	}

	private static void open(URI uri) {
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(uri);
			} catch (IOException e) {
				/* TODO: error handling */ }
		} else {
			/* TODO: error handling */ }
	}
}
