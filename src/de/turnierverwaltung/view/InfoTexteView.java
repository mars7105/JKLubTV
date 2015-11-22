package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class InfoTexteView {

	private URI datepickerUri;
	private JButton buttonDatePicker;
	private URI sqLiteUri;
	private JButton buttonSQlite;
	private URI mySQLUri;
	private JButton buttonmySQL;
	private URI iTextUri;
	private JButton buttoniText;
	private URI openIconUri;
	private JButton buttonopenIcon;

	class OpenUrlAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonDatePicker) {
				open(datepickerUri);
			}
			if (e.getSource() == buttonSQlite) {
				open(sqLiteUri);
			}
			if (e.getSource() == buttonmySQL) {
				open(mySQLUri);
			}
			if (e.getSource() == buttoniText) {
				open(iTextUri);
			}
			if (e.getSource() == buttonopenIcon) {
				open(openIconUri);
			}

		}

	}

	public JPanel getLizenzText() throws URISyntaxException {
		datepickerUri = new URI("http://jdatepicker.org/");
		JPanel all = new JPanel(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		// panel.add(new JLabel("Dieses Programm nutzt verschiedene freie
		// Bibliotheken:\n"));
		JPanel temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(new JLabel("Dieses Programm nutzt verschiedene freie Bibliotheken:\n"));

		panel.add(temp);
		panel.add(new JSeparator());
		JLabel labelDatePicker = new JLabel();
		labelDatePicker.setText("1. JDatePicker -> jdatepicker-1.3.4.jar\n für die Auswahl des Datum.\n");
		buttonDatePicker = new JButton();
		buttonDatePicker.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>");
		// buttonDatePicker.setHorizontalAlignment(SwingConstants.LEFT);
		// buttonDatePicker.setBorderPainted(false);
		buttonDatePicker.setOpaque(false);
		// buttonDatePicker.setBackground(Color.WHITE);
		buttonDatePicker.setToolTipText(datepickerUri.toString());
		buttonDatePicker.addActionListener(new OpenUrlAction());
		// panel.add(labelDatePicker);
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));

		temp.add(buttonDatePicker);
		temp.add(labelDatePicker);
		panel.add(temp);
		panel.add(new JSeparator());
		sqLiteUri = new URI("https://bitbucket.org/xerial/sqlite-jdbc/downloads");

		JLabel labelSQlite = new JLabel(
				"2. SQLite JDBC -> sqlite-jdbc-3.8.11.1.jar\n für die Speicherung im SQLite Format.\n");
		buttonSQlite = new JButton();
		buttonSQlite.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>");
		// buttonSQlite.setHorizontalAlignment(SwingConstants.LEFT);
		// buttonSQlite.setBorderPainted(false);
		buttonSQlite.setOpaque(false);
		// buttonSQlite.setBackground(Color.WHITE);
		buttonSQlite.setToolTipText(sqLiteUri.toString());
		buttonSQlite.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonSQlite);

		temp.add(labelSQlite);
		panel.add(temp);
		panel.add(new JSeparator());
		// panel.add(buttonSQlite);

		mySQLUri = new URI("http://mvnrepository.com/artifact/mysql/mysql-connector-java");
		JLabel labelmySQL = new JLabel(
				"3. MySQL Connector -> mysql-connector-java-5.1.35-bin.jar\n für zukünftige Änderungen.\n");
		buttonmySQL = new JButton();
		buttonmySQL.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>");
		// buttonmySQL.setHorizontalAlignment(SwingConstants.LEFT);
		// buttonmySQL.setBorderPainted(false);
		buttonmySQL.setOpaque(false);
		// buttonmySQL.setBackground(Color.WHITE);
		buttonmySQL.setToolTipText(mySQLUri.toString());
		buttonmySQL.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonmySQL);

		temp.add(labelmySQL);
		panel.add(temp);
		panel.add(new JSeparator());
		// panel.add(labelmySQL);
		// panel.add(buttonmySQL);

		iTextUri = new URI("http://itextpdf.com/");
		JLabel labeliText = new JLabel("4. iText, Programmable PDF Software\n für die PDF Ausgabe.\n");
		buttoniText = new JButton();
		buttoniText.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>");
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
		// panel.add(labeliText);
		// panel.add(buttoniText);

		openIconUri = new URI("http://sourceforge.net/projects/openiconlibrary/");
		JLabel openIconPicker = new JLabel(
				"5. Bilder: Open Icon Library\nhttp://sourceforge.net/projects/openiconlibrary/\n");
		buttonopenIcon = new JButton();
		buttonopenIcon.setText("<HTML><FONT color=\"#000099\"><U>Link</U></FONT></HTML>");
		// buttonopenIcon.setHorizontalAlignment(SwingConstants.LEFT);
		// buttonopenIcon.setBorderPainted(false);
		buttonopenIcon.setOpaque(false);
		// buttonopenIcon.setBackground(Color.WHITE);
		buttonopenIcon.setToolTipText(openIconUri.toString());
		buttonopenIcon.addActionListener(new OpenUrlAction());
		temp = new JPanel();
		temp.setBackground(Color.WHITE);
		temp.setLayout(new FlowLayout(FlowLayout.LEFT));
		temp.add(buttonopenIcon);

		temp.add(openIconPicker);
		panel.add(temp);
		panel.add(new JSeparator());
		// panel.add(openIconPicker);
		// panel.add(buttonopenIcon);
		all.add(panel, BorderLayout.NORTH);
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
