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
import java.awt.Color;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.turnierverwaltung.model.Turnier;

public class TurnierEditierenView extends JDialog {
	public class DateLabelFormatter extends AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String datePattern = "dd.MM.yyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(
				datePattern);

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

			return "";
		}

	}

	private static final long serialVersionUID = -5642277833139693453L;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField textFieldTurnierName;
	private JPanel buttonPane;
	private JDatePickerImpl startDatumTextField;
	private JDatePickerImpl endDatumTextField;

	private Properties property;

	public TurnierEditierenView(Turnier turnier) {
		property = new Properties();
		property.put("text.today", "Heute");
		property.put("text.month", "Monat");
		property.put("text.year", "Jahr");
		this.okButton = new JButton("Speichern");
		this.cancelButton = new JButton("Abbrechen");
		this.textFieldTurnierName = new JTextField(15);
		this.setAlwaysOnTop(true);
		setTitle("Turnier editieren");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		// int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 50;
		// int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 50;
		// setBounds(TurnierKonstanten.WINDOW_WIDTH / 3,
		// TurnierKonstanten.WINDOW_HEIGHT / 3, windowWidth, windowHeight);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(new Color(249, 222, 112));

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		centerPane.setBackground(new Color(249, 222, 112));
		textFieldTurnierName.setText(turnier.getTurnierName());
		centerPane.add(new JLabel("Turniername: "));
		centerPane.add(textFieldTurnierName);

		int[] datumsIntStartdatum = dateStringToInt(turnier.getStartDatum());
		UtilDateModel um1 = new UtilDateModel();
		um1.setDate(datumsIntStartdatum[2], datumsIntStartdatum[1],
				datumsIntStartdatum[0]);
		um1.setSelected(true);
		startDatumTextField = new JDatePickerImpl(new JDatePanelImpl(um1,
				property), new DateLabelFormatter());
		centerPane.add(new JLabel("Startdatum: "));
		centerPane.add(startDatumTextField);

		int[] datumsIntEnddatum = dateStringToInt(turnier.getEndDatum());
		UtilDateModel um2 = new UtilDateModel();
		um2.setDate(datumsIntEnddatum[2], datumsIntEnddatum[1],
				datumsIntEnddatum[0]);
		um2.setSelected(true);
		endDatumTextField = new JDatePickerImpl(new JDatePanelImpl(um2,
				property), new DateLabelFormatter());
		centerPane.add(new JLabel("Enddatum: "));
		centerPane.add(endDatumTextField);

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		buttonPane.setBackground(new Color(249, 222, 112));
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);

		contentPanel.add(centerPane);
		contentPanel.add(buttonPane);
		add(contentPanel);
		contentPanel.updateUI();
		setLocationRelativeTo(null);
		setEnabled(true);
		setVisible(true);
		pack();
	}

	private int[] dateStringToInt(String datum) {
		int[] dateInt = new int[3];

		int index = 0;
		for (String zahlenWerte : datum.split("\\.")) {

			dateInt[index] = Integer.parseInt(zahlenWerte);

			index++;

		}
		dateInt[1] = dateInt[1] - 1;
		return dateInt;

	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JDatePickerImpl getEndDatumTextField() {
		return endDatumTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JDatePickerImpl getStartDatumTextField() {
		return startDatumTextField;
	}

	public JTextField getTextFieldTurnierName() {
		return textFieldTurnierName;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setEndDatumTextField(JDatePickerImpl endDatumTextField) {
		this.endDatumTextField = endDatumTextField;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setStartDatumTextField(JDatePickerImpl startDatumTextField) {
		this.startDatumTextField = startDatumTextField;
	}

	public void setTextFieldTurnierName(JTextField textFieldTurnierName) {
		this.textFieldTurnierName = textFieldTurnierName;
	}
}
