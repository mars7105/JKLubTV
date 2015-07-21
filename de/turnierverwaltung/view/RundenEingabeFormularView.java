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
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import de.turnierverwaltung.model.TurnierKonstanten;

public class RundenEingabeFormularView extends JPanel {
	public class DateLabelFormatter extends AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// private String datePattern = "yyyy-MM-dd";
		private String datePattern = "dd.MM.yyy";

		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel centerPane;
	private JPanel flowPane;
	private JPanel downPane;
	private JButton okButton;

	private JButton cancelButton;
	private JScrollPane scrollPane;
	private JComboBox<String>[] rundenNummer;
	private int spielerAnzahl;
	private JLabel[] weissSpieler;
	private JLabel[] schwarzSpieler;
	private JDatePickerImpl[] datum;
	private Properties property;
	private JButton[] changeColor;

	private int anzahlElemente;

	public RundenEingabeFormularView(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		changeColor = new JButton[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];
		rundenNummer = new JComboBox[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];
		weissSpieler = new JLabel[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];
		schwarzSpieler = new JLabel[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];

		datum = new JDatePickerImpl[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];

		property = new Properties();
		property.put("text.today", "Heute");
		property.put("text.month", "Monat");
		property.put("text.year", "Jahr");

		makePanel();

	}

	public int getAnzahlElemente() {
		return anzahlElemente;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton[] getChangeColor() {
		return changeColor;
	}

	public JDatePickerImpl[] getDatum() {
		return datum;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JComboBox[] getRundenNummer() {
		return rundenNummer;
	}

	public JLabel[] getSchwarzSpieler() {
		return schwarzSpieler;
	}

	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}

	public JLabel[] getWeissSpieler() {
		return weissSpieler;
	}

	public void makePanel() {
		setBackground(new Color(249, 222, 112));
		anzahlElemente = 0;
		int windowWidth = TurnierKonstanten.WINDOW_WIDTH;
		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 50;
		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		scrollPane.setPreferredSize(new Dimension(windowWidth, windowHeight));
		add(scrollPane, BorderLayout.CENTER);
		centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		centerPane.setBackground(new Color(249, 222, 112));
		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.Y_AXIS));
		bottomPane.setBackground(new Color(249, 222, 112));
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(249, 222, 112));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottomPane.add(centerPane);
		bottomPane.add(buttonPane);
		contentPanel.add(bottomPane, BorderLayout.SOUTH);
		okButton = new JButton("Übernehmen");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		cancelButton = new JButton("Abbruch");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}

	public void makeZeile(String[] zeile) {

		flowPane = new JPanel();
		flowPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		flowPane.setBackground(new Color(249, 222, 112));
		downPane = new JPanel();
		downPane.setLayout(new BoxLayout(downPane, BoxLayout.X_AXIS));
		downPane.setBackground(new Color(249, 222, 112));

		if (zeile[0] == "Runde") {

		} else {
			int[] dateInt;
			UtilDateModel model = new UtilDateModel();
			if (zeile[4].length() > 0) {

				dateInt = getDatefromString(zeile[4]);
				model.setDate(dateInt[2], dateInt[1] - 1, dateInt[0]);
				model.setSelected(true);

			}

			datum[anzahlElemente] = new JDatePickerImpl(new JDatePanelImpl(model, property), new DateLabelFormatter());

			downPane.add(new JLabel("Datum: "));
			downPane.add(datum[anzahlElemente]);
			downPane.add(new JLabel(" "));
			changeColor[anzahlElemente] = new JButton("Farben vertauschen");
			downPane.add(changeColor[anzahlElemente]);
			rundenNummer[anzahlElemente] = new JComboBox<String>();
			int ungerade = (this.spielerAnzahl + 1) % 2;
			for (int i = 1; i <= this.spielerAnzahl - ungerade; i++) {
				rundenNummer[anzahlElemente].addItem(Integer.toString(i));
			}
			rundenNummer[anzahlElemente].setSelectedIndex(Integer.parseInt(zeile[0]) - 1);
			downPane.add(new JLabel("  Runde: "));
			downPane.add(rundenNummer[anzahlElemente]);
			downPane.add(new JLabel(" = "));
			weissSpieler[anzahlElemente] = new JLabel();
			weissSpieler[anzahlElemente].setText("Weiss:  " + zeile[1] + " - ");
			schwarzSpieler[anzahlElemente] = new JLabel();
			schwarzSpieler[anzahlElemente].setText("Schwarz:  " + zeile[2] + " ");
			downPane.add(weissSpieler[anzahlElemente]);
			downPane.add(schwarzSpieler[anzahlElemente]);

			anzahlElemente++;
		}
		downPane.updateUI();
		flowPane.updateUI();

		flowPane.add(downPane);
		centerPane.add(flowPane);

	}

	private int[] getDatefromString(String zeile) {
		String[] splitDate = zeile.split("\\.");
		int[] dateInt = new int[splitDate.length];

		for (int i = 0; i < splitDate.length; i++) {
			if (zeile.length() > 0) {
				dateInt[i] = Integer.parseInt(splitDate[i]);
			} else {
				dateInt[i] = 0;
			}
		}
		return dateInt;
	}

	public void setAnzahlElemente(int anzahlElemente) {
		this.anzahlElemente = anzahlElemente;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setChangeColor(JButton[] changeColor) {
		this.changeColor = changeColor;
	}

	public void setDatum(JDatePickerImpl[] datum) {
		this.datum = datum;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setRundenNummer(JComboBox[] rundenNummer) {
		this.rundenNummer = rundenNummer;
	}

	public void setSchwarzSpieler(JLabel[] schwarzSpieler) {
		this.schwarzSpieler = schwarzSpieler;
	}

	public void setSpielerAnzahl(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		changeColor = new JButton[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];
		rundenNummer = new JComboBox[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];
		weissSpieler = new JLabel[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];
		schwarzSpieler = new JLabel[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];

		datum = new JDatePickerImpl[this.spielerAnzahl * (this.spielerAnzahl - 1) / 2];
	}

	public void setWeissSpieler(JLabel[] weissSpieler) {
		this.weissSpieler = weissSpieler;
	}
}
