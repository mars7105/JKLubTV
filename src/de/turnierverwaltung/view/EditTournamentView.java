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
//import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.turnierverwaltung.model.Tournament;

public class EditTournamentView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public class DateLabelFormatter extends AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String datePattern = Messages.getString("TurnierView.15"); //$NON-NLS-1$
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern, Locale.getDefault());

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

	private JButton okButton;
	private JButton cancelButton;
	private JTextField textFieldTurnierName;
	private ButtonPanelView buttonPane;
	private DateChooserPanel startDatumTextField;
	private DateChooserPanel endDatumTextField;

	private Properties property;
	private JTextField[] textFieldGruppenName;

	public EditTournamentView(Tournament turnier) {
		// this.rundenEditierenButton = new JButton("Paarungen bearbeiten");
		this.textFieldGruppenName = new JTextField[turnier.getAnzahlGruppen()];
		property = new Properties();
		property.put("text.today", Messages.getString("TurnierEditierenView.3")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.month", Messages.getString("TurnierEditierenView.5")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.year", Messages.getString("TurnierEditierenView.7")); //$NON-NLS-1$ //$NON-NLS-2$

		this.textFieldTurnierName = new JTextField(20);
		this.setAlwaysOnTop(true);
		setTitle(Messages.getString("TurnierEditierenView.10")); //$NON-NLS-1$
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		// contentPanel.setBackground(new Color(249, 222, 112));

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		textFieldTurnierName.setText(turnier.getTurnierName());
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("TurnierEditierenView.11")); //$NON-NLS-1$
		centerPane.add(label);
		centerPane.add(textFieldTurnierName);
		contentPanel.add(centerPane);
		// int[] datumsIntStartdatum = dateStringToInt(turnier.getStartDatum());
		// UtilDateModel um1 = new UtilDateModel();
		// um1.setDate(datumsIntStartdatum[2], datumsIntStartdatum[1],
		// datumsIntStartdatum[0]);
		// um1.setSelected(true);
		// startDatumTextField = new JDatePickerImpl(new JDatePanelImpl(um1,
		// property), new DateLabelFormatter());
		startDatumTextField = new DateChooserPanel();
		startDatumTextField.setLocale(Locale.getDefault());
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

		try {
			startDatumTextField.setDate(formatter.parse(turnier.getStartDatum()));

		} catch (ParseException e) {

		}
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("TurnierEditierenView.12")); //$NON-NLS-1$
		centerPane.add(label);
		centerPane.add(startDatumTextField);
		contentPanel.add(centerPane);

		endDatumTextField = new DateChooserPanel();
		endDatumTextField.setLocale(Locale.getDefault());
		endDatumTextField = new DateChooserPanel();
		endDatumTextField.setLocale(Locale.getDefault());
		try {

			endDatumTextField.setDate(formatter.parse(turnier.getEndDatum()));

		} catch (ParseException e) {

		}
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("TurnierEditierenView.13")); //$NON-NLS-1$
		centerPane.add(label);
		centerPane.add(endDatumTextField);
		contentPanel.add(centerPane);
		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		this.okButton = buttonPane.getOkButton();
		this.cancelButton = buttonPane.getCancelButton();

		for (int i = 0; i < turnier.getAnzahlGruppen(); i++) {
			centerPane = new JPanel();
			centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
			this.textFieldGruppenName[i] = new JTextField(15);
			textFieldGruppenName[i].setText(turnier.getGruppe()[i].getGruppenName());
			label = new JLabel();
			label.setPreferredSize(new Dimension(120, 10));
			label.setText(Messages.getString("TurnierEditierenView.14") + (i + 1)); //$NON-NLS-1$
			centerPane.add(label);
			centerPane.add(textFieldGruppenName[i]);

			contentPanel.add(centerPane);
		}
		contentPanel.add(buttonPane);
		add(contentPanel);
		contentPanel.updateUI();
		pack();

		setLocationRelativeTo(null);
		setEnabled(true);
		setVisible(true);
	}

	// private int[] getDatefromString(String zeile) {
	// String[] splitDate = null;
	// String[] dateItems = null;
	// if (zeile.contains(".")) {
	// splitDate = zeile.split("\\.");
	// }
	// if (zeile.contains("-")) {
	// splitDate = zeile.split("-");
	// dateItems = new String[splitDate.length];
	// int increment = splitDate.length;
	// for (int i = 0; i < splitDate.length; i++) {
	// increment--;
	// dateItems[increment] = splitDate[i];
	// }
	// splitDate = dateItems;
	// }
	//
	// int[] dateInt = new int[splitDate.length];
	//
	// for (int i = 0; i < splitDate.length; i++) {
	// if (zeile.length() > 0) {
	// dateInt[i] = Integer.parseInt(splitDate[i]);
	// } else {
	// dateInt[i] = 0;
	// }
	// }
	//
	// return dateInt;
	// }

	// private int[] dateStringToInt(String datum) {
	// int[] dateInt = getDatefromString(datum);
	//
	// dateInt[1] = dateInt[1] - 1;
	// return dateInt;
	//
	// }

	public JButton getCancelButton() {
		return cancelButton;
	}

	public DateChooserPanel getEndDatumTextField() {
		return endDatumTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public DateChooserPanel getStartDatumTextField() {
		return startDatumTextField;
	}

	public JTextField getTextFieldTurnierName() {
		return textFieldTurnierName;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setEndDatumTextField(DateChooserPanel endDatumTextField) {
		this.endDatumTextField = endDatumTextField;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setStartDatumTextField(DateChooserPanel startDatumTextField) {
		this.startDatumTextField = startDatumTextField;
	}

	public void setTextFieldTurnierName(JTextField textFieldTurnierName) {
		this.textFieldTurnierName = textFieldTurnierName;
	}

	public JTextField[] getTextFieldGruppenName() {
		return textFieldGruppenName;
	}

	public void setTextFieldGruppenName(JTextField[] textFieldGruppenName) {
		this.textFieldGruppenName = textFieldGruppenName;
	}
}
