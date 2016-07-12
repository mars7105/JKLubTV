package de.turnierverwaltung.view;

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

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import de.turnierverwaltung.model.Tournament;

public class NewTournamentView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel hauptPanel;
	private JTextField turnierNameTextField;
	private JDatePickerImpl startDatumTextField;
	private JDatePickerImpl endDatumeTextField;
	private JTextField gruppenAnzahlTextField;
	private Tournament turnier;
	private JButton okButton;
	private String turnierName;
	private String startDatum;
	private String endDatum;
	private int gruppenAnzahl;
	private Properties property;

	public class DateLabelFormatter extends AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String datePattern = Messages.getString("TurnierView.15"); //$NON-NLS-1$
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern, Locale.getDefault());

		public DateLabelFormatter() {
		}

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return ""; //$NON-NLS-1$
		}

	}

	/**
	 * Create the dialog.
	 */
	public NewTournamentView() {
		property = new Properties();
		property.put("text.today", Messages.getString("TurnierView.3")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.month", Messages.getString("TurnierView.5")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.year", Messages.getString("TurnierView.7")); //$NON-NLS-1$ //$NON-NLS-2$
		gruppenAnzahl = 0;
		int windowWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int windowHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(0, 0, windowWidth, windowHeight);

		setLayout(new FlowLayout());
		// setBackground(new Color(249, 222, 112));
		hauptPanel = new JPanel();
		hauptPanel.setLayout(new BoxLayout(hauptPanel, BoxLayout.PAGE_AXIS));
		// hauptPanel.setBackground(new Color(249, 222, 112));
		hauptPanel.setVisible(true);
//		add(hauptPanel);

		JPanel panel1 = new JPanel();
		panel1.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		panel1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		// panel1.setBackground(new Color(249, 222, 112));
		hauptPanel.add(panel1);
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

		JLabel lblTurniername = new JLabel(Messages.getString("TurnierView.8")); //$NON-NLS-1$
		panel1.add(lblTurniername);

		turnierNameTextField = new JTextField();
		panel1.add(turnierNameTextField);
		turnierNameTextField.setColumns(10);

		JPanel panel2 = new JPanel();
		// panel2.setBackground(new Color(249, 222, 112));
		hauptPanel.add(panel2);
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		JLabel label = new JLabel(Messages.getString("TurnierView.9")); //$NON-NLS-1$
		panel2.add(label);

		startDatumTextField = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), property),
				new DateLabelFormatter());
		panel2.add(startDatumTextField);

		JPanel panel3 = new JPanel();
		// panel3.setBackground(new Color(249, 222, 112));
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		hauptPanel.add(panel3);

		JLabel lblEndDatum = new JLabel(Messages.getString("TurnierView.10")); //$NON-NLS-1$
		panel3.add(lblEndDatum);

		endDatumeTextField = new JDatePickerImpl(new JDatePanelImpl(new UtilDateModel(), property),
				new DateLabelFormatter());
		panel3.add(endDatumeTextField);

		JPanel panel4 = new JPanel();
		// panel4.setBackground(new Color(249, 222, 112));
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));
		hauptPanel.add(panel4);

		JLabel lblAnzahlGruppen = new JLabel(Messages.getString("TurnierView.11")); //$NON-NLS-1$
		panel4.add(lblAnzahlGruppen);

		gruppenAnzahlTextField = new JTextField();

		gruppenAnzahlTextField.setColumns(10);
		panel4.add(gruppenAnzahlTextField);

		ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeOKButton();
		hauptPanel.add(buttonPane);
		okButton = buttonPane.getOkButton();

		String help = Messages.getString("TurnierView.14"); //$NON-NLS-1$
		JPanel helpPanel = new JPanel();
		JTextArea helpText = new JTextArea();
		helpText.setText(help);
		helpText.setEditable(false);
		helpPanel.add(helpText);
		hauptPanel.add(helpPanel);
		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(hauptPanel);
		add(scrollPane, BorderLayout.CENTER);

	}

	public String getEndDatum() {
		return endDatum;
	}

	public String getEndDatumTextField() {
		return endDatumeTextField.getJFormattedTextField().getText();
	}

	public int getGruppenAnzahl() {
		gruppenAnzahl = new Integer(gruppenAnzahlTextField.getText());
		return gruppenAnzahl;
	}

	public JTextField getGruppenAnzahlTextField() {
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

	public String getStartDatumTextField() {
		return startDatumTextField.getJFormattedTextField().getText();
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

	public void setEndDatum(String endDatum) {
		this.endDatum = endDatum;
	}

	public void setEndDatumeTextField(JDatePickerImpl endDatumeTextField) {
		this.endDatumeTextField = endDatumeTextField;
	}

	public void setGruppenAnzahl(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

	public void setGruppenAnzahlTextField(JTextField gruppenAnzahlTextField) {
		this.gruppenAnzahlTextField = gruppenAnzahlTextField;
	}

	public void setHauptPanel(JPanel hauptPanel) {
		this.hauptPanel = hauptPanel;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setStartDatum(String startDatum) {
		this.startDatum = startDatum;
	}

	public void setStartDatumTextField(JDatePickerImpl startDatumTextField) {
		this.startDatumTextField = startDatumTextField;
	}

	public void setTurnier(Tournament turnier) {
		this.turnier = turnier;
	}

	public void setTurnierName(String turnierName) {
		this.turnierName = turnierName;
	}

	public void setTurnierNameTextField(JTextField turnierNameTextField) {
		this.turnierNameTextField = turnierNameTextField;
	}

}
