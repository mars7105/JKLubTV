package de.turnierverwaltung.view.tournamentlist;

import java.awt.BorderLayout;

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

import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import de.turnierverwaltung.model.Tournament;
import de.turnierverwaltung.model.TournamentConstants;
import de.turnierverwaltung.view.ButtonPanelView;
import de.turnierverwaltung.view.DateChooserPanel;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.SpinnerView;

public class NewTournamentView extends JPanel {
	public class DateLabelFormatter extends AbstractFormatter {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private final String datePattern = Messages.getString("TurnierView.15"); //$NON-NLS-1$
		private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern, Locale.getDefault());

		public DateLabelFormatter() {
		}

		@Override
		public Object stringToValue(final String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(final Object value) throws ParseException {
			if (value != null) {
				final Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return ""; //$NON-NLS-1$
		}

	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel hauptPanel;
	private JTextField turnierNameTextField;
	private DateChooserPanel startDatumTextField;
	private DateChooserPanel endDatumeTextField;
	private SpinnerView gruppenAnzahlTextField;
	private Tournament turnier;
	private JButton okButton;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private int gruppenAnzahl;
	private final int maxGroups = TournamentConstants.MAX_GROUPS;

	private final Properties property;

	/**
	 * Create the dialog.
	 */
	public NewTournamentView() {
		property = new Properties();
		property.put("text.today", Messages.getString("TurnierView.3")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.month", Messages.getString("TurnierView.5")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.year", Messages.getString("TurnierView.7")); //$NON-NLS-1$ //$NON-NLS-2$
		gruppenAnzahl = 0;
		final int windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		final int windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(0, 0, windowWidth, windowHeight);

		setLayout(new FlowLayout());
		// setBackground(new Color(249, 222, 112));
		hauptPanel = new JPanel();
		hauptPanel.setLayout(new BoxLayout(hauptPanel, BoxLayout.PAGE_AXIS));
		// hauptPanel.setBackground(new Color(249, 222, 112));
		hauptPanel.setVisible(true);
		// add(hauptPanel);

		final JPanel panel1 = new JPanel();
		panel1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		// panel1.setBackground(new Color(249, 222, 112));

		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

		final JLabel lblTurniername = new JLabel(Messages.getString("TurnierView.8")); //$NON-NLS-1$

		turnierNameTextField = new JTextField();
		turnierNameTextField.setColumns(20);
		panel1.add(turnierNameTextField);
		panel1.add(lblTurniername);
		hauptPanel.add(panel1);
		final JPanel panel2 = new JPanel();
		// panel2.setBackground(new Color(249, 222, 112));

		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));

		final JLabel label = new JLabel(Messages.getString("TurnierView.9")); //$NON-NLS-1$

		startDatumTextField = new DateChooserPanel();
		startDatumTextField.setLocale(Locale.getDefault());
		if (Locale.getDefault().equals(Locale.US)) {
			startDatumTextField.setDateFormatString("yyyy/MM/dd");
		}
		if (Locale.getDefault().equals(Locale.GERMANY)) {
			startDatumTextField.setDateFormatString("dd.MM.yyyy");
		}
		panel2.add(startDatumTextField);
		panel2.add(label);
		hauptPanel.add(panel2);
		final JPanel panel3 = new JPanel();
		// panel3.setBackground(new Color(249, 222, 112));
		panel3.setLayout(new FlowLayout(FlowLayout.LEFT));

		final JLabel lblEndDatum = new JLabel(Messages.getString("TurnierView.10")); //$NON-NLS-1$

		hauptPanel.add(panel3);
		endDatumeTextField = new DateChooserPanel();
		endDatumeTextField.setLocale(Locale.getDefault());
		if (Locale.getDefault().equals(Locale.US)) {
			endDatumeTextField.setDateFormatString("yyyy/MM/dd");
		}
		if (Locale.getDefault().equals(Locale.GERMANY)) {
			endDatumeTextField.setDateFormatString("dd.MM.yyyy");
		}
		panel3.add(endDatumeTextField);
		panel3.add(lblEndDatum);
		final JPanel panel4 = new JPanel();
		// panel4.setBackground(new Color(249, 222, 112));
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
		hauptPanel.add(panel4);

		// JLabel lblAnzahlGruppen = new JLabel(Messages.getString("TurnierView.11"));
		// //$NON-NLS-1$
		// panel4.add(lblAnzahlGruppen);
		// buchstaben anzahl
		final String[] listString = new String[15];
		for (int i = 0; i < maxGroups; i++) {
			listString[i] = new Integer(i + 1).toString();

		}
		gruppenAnzahlTextField = new SpinnerView(listString, 0, Messages.getString("TurnierView.11"));

		// gruppenAnzahlTextField.setColumns(10);
		panel4.add(gruppenAnzahlTextField);

		final ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		hauptPanel.add(buttonPane);
		okButton = buttonPane.getOkButton();

		final String help = Messages.getString("TurnierView.14"); //$NON-NLS-1$
		final JPanel helpPanel = new JPanel();
		final JTextArea helpText = new JTextArea();
		helpText.setText(help);
		helpText.setEditable(false);
		helpPanel.add(helpText);
		hauptPanel.add(helpPanel);
		final JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(hauptPanel);
		add(scrollPane, BorderLayout.CENTER);

	}

	public String getEndDatum() {
		return endDatum;
	}

	public Date getEndDatumTextField() {
		return endDatumeTextField.getDate();
	}

	public int getGruppenAnzahl() {
		gruppenAnzahl = Integer.parseInt(gruppenAnzahlTextField.getValue());
		return gruppenAnzahl;
	}

	public SpinnerView getGruppenAnzahlTextField() {
		return gruppenAnzahlTextField;
	}

	public JPanel getHauptPanel() {
		return hauptPanel;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public String getStartDatum() {
		return startDatum;
	}

	public Date getStartDatumTextField() {
		return startDatumTextField.getDate();
	}

	public Tournament getTurnier() {
		return turnier;
	}

	public String getTurnierName() {
		return turnierName;
	}

	public JTextField getTurnierNameTextField() {
		return turnierNameTextField;
	}

	public void setEndDatum(final String endDatum) {
		this.endDatum = endDatum;
	}

	public void setEndDatumeTextField(final DateChooserPanel endDatumeTextField) {
		this.endDatumeTextField = endDatumeTextField;
	}

	public void setGruppenAnzahl(final int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

	public void setGruppenAnzahlTextField(final SpinnerView gruppenAnzahlTextField) {
		this.gruppenAnzahlTextField = gruppenAnzahlTextField;
	}

	public void setHauptPanel(final JPanel hauptPanel) {
		this.hauptPanel = hauptPanel;
	}

	public void setOkButton(final JButton okButton) {
		this.okButton = okButton;
	}

	public void setStartDatum(final String startDatum) {
		this.startDatum = startDatum;
	}

	public void setStartDatumTextField(final DateChooserPanel startDatumTextField) {
		this.startDatumTextField = startDatumTextField;
	}

	public void setTurnier(final Tournament turnier) {
		this.turnier = turnier;
	}

	public void setTurnierName(final String turnierName) {
		this.turnierName = turnierName;
	}

	public void setTurnierNameTextField(final JTextField turnierNameTextField) {
		this.turnierNameTextField = turnierNameTextField;
	}

}
