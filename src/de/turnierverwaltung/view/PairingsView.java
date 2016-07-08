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
//import java.awt.Color;
import java.awt.FlowLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class PairingsView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel flowPane;
	private JPanel downPane;
	private JTabbedPane tabbedPane;
	private JScrollPane scrollPane;
	private JComboBox<String>[] rundenNummer;
	private int spielerAnzahl;
	private JLabel[] weissSpieler;
	private JLabel[] schwarzSpieler;
	private JDatePickerImpl[] datum;
	private Properties property;
	private JButton[] changeColor;
	private int anzahlZeilen;
	private int anzahlElemente;
	private int ungerade;
	private int gerade;
	private int rundenanzahl;
	private int partienanzahl;
	private JLabel statusLabel;

	// private JButton reloadButton;

	@SuppressWarnings("unchecked")
	public PairingsView(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		anzahlZeilen = this.spielerAnzahl * (this.spielerAnzahl - 1) / 2;
		changeColor = new JButton[anzahlZeilen];
		rundenNummer = new JComboBox[anzahlZeilen];
		weissSpieler = new JLabel[anzahlZeilen];
		schwarzSpieler = new JLabel[anzahlZeilen];

		datum = new JDatePickerImpl[anzahlZeilen];

		property = new Properties();
		property.put("text.today", Messages.getString("RundenEingabeFormularView.1")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.month", Messages.getString("RundenEingabeFormularView.3")); //$NON-NLS-1$ //$NON-NLS-2$
		property.put("text.year", Messages.getString("RundenEingabeFormularView.5")); //$NON-NLS-1$ //$NON-NLS-2$
		ungerade = (this.spielerAnzahl + 1) % 2;
		gerade = 1 - ungerade;
		rundenanzahl = this.spielerAnzahl - ungerade;
		partienanzahl = (this.spielerAnzahl + gerade) / 2;
		makePanel();

	}

	public int getAnzahlElemente() {
		return anzahlElemente;
	}

	// public JButton getCancelButton() {
	// return cancelButton;
	// }

	public JButton[] getChangeColor() {
		return changeColor;
	}

	public JDatePickerImpl[] getDatum() {
		return datum;
	}

	// public JButton getOkButton() {
	// return okButton;
	// }

	@SuppressWarnings("rawtypes")
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
		// setBackground(new Color(249, 222, 112));
		setLayout(new BorderLayout());
		anzahlElemente = 0;
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		// contentPanel.setBackground(new Color(249, 222, 112));
		// setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// setLayout(new BorderLayout());
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);
		scrollPane.setAlignmentY(TOP_ALIGNMENT);
		add(scrollPane, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		JPanel status = new JPanel();
		status.setLayout(new FlowLayout(FlowLayout.LEFT));
		status.add(new JLabel(Messages.getString("SimpleTerminTabelleView.15"))); //$NON-NLS-1$
		statusLabel = new JLabel("0");
		JLabel changesLabel = new JLabel(Messages.getString("SimpleTerminTabelleView.16"));
		status.add(statusLabel);
		status.add(changesLabel);
		southPanel.add(status, BorderLayout.SOUTH);

		southPanel.add(status, BorderLayout.WEST);
		add(southPanel, BorderLayout.SOUTH);
	}

	private int[] getDatefromString(String zeile) {
		String[] splitDate = null;
		String[] dateItems = null;
		if (zeile.contains(".")) {
			splitDate = zeile.split("\\.");
		}
		if (zeile.contains("-")) {
			splitDate = zeile.split("-");
			dateItems = new String[splitDate.length];
			int increment = splitDate.length;
			for (int i = 0; i < splitDate.length; i++) {
				increment--;
				dateItems[increment] = splitDate[i];
			}
			splitDate = dateItems;
		}

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

	// public void setCancelButton(JButton cancelButton) {
	// this.cancelButton = cancelButton;
	// }

	public void setChangeColor(JButton[] changeColor) {
		this.changeColor = changeColor;
	}

	public void setDatum(JDatePickerImpl[] datum) {
		this.datum = datum;
	}

	public JLabel getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
		this.statusLabel.setText(this.statusLabel.getText() + " " + Messages.getString("SimpleTerminTabelleView.16"));

	}

	@SuppressWarnings("unchecked")
	public void setRundenNummer(@SuppressWarnings("rawtypes") JComboBox[] rundenNummer) {
		this.rundenNummer = rundenNummer;
	}

	public void setSchwarzSpieler(JLabel[] schwarzSpieler) {
		this.schwarzSpieler = schwarzSpieler;
	}

	@SuppressWarnings("unchecked")
	public void setSpielerAnzahl(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		anzahlZeilen = this.spielerAnzahl * (this.spielerAnzahl - 1) / 2;
		changeColor = new JButton[anzahlZeilen];
		rundenNummer = new JComboBox[anzahlZeilen];
		weissSpieler = new JLabel[anzahlZeilen];
		schwarzSpieler = new JLabel[anzahlZeilen];

		datum = new JDatePickerImpl[anzahlZeilen];
	}

	public void setWeissSpieler(JLabel[] weissSpieler) {
		this.weissSpieler = weissSpieler;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public void makeZeilen(String[][] terminMatrix) {
		String[] zeile = new String[5];

		tabbedPane = new JTabbedPane();
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setVgap(1);
		int index = 1;
		for (int r = 0; r < rundenanzahl + 0; r++) {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			flowPane = new JPanel();
			flowPane.setLayout(new BoxLayout(flowPane, BoxLayout.PAGE_AXIS));
			// flowPane.setLayout(new BorderLayout());
			flowPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			// flowPane.setAlignmentY(Component.TOP_ALIGNMENT);

			for (int i = 0; i < partienanzahl; i++) {
				zeile[0] = terminMatrix[0][index];
				zeile[1] = terminMatrix[1][index];
				zeile[2] = terminMatrix[2][index];
				zeile[3] = terminMatrix[3][index];
				zeile[4] = terminMatrix[4][index];
				downPane = new JPanel();

				downPane.setLayout(flowLayout);
				downPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				// downPane.setAlignmentY(Component.TOP_ALIGNMENT);
				if (zeile[0] != Messages.getString("RundenEingabeFormularView.7")) { //$NON-NLS-1$

					int[] dateInt;
					UtilDateModel model = new UtilDateModel();

					if (zeile[4].length() > 0) {
						dateInt = getDatefromString(zeile[4]);
						model.setDate(dateInt[2], dateInt[1] - 1, dateInt[0]);

						model.setSelected(true);
					}
					JDatePanelImpl datePanel = new JDatePanelImpl(model, property);

					// datePanel.setForeground(Color.WHITE);
					datum[anzahlElemente] = new JDatePickerImpl(datePanel, new DateLabelFormatter());
					downPane.add(new JLabel(Messages.getString("RundenEingabeFormularView.8"))); //$NON-NLS-1$
					downPane.add(datum[anzahlElemente]);
					downPane.add(new JLabel(" ")); //$NON-NLS-1$
					changeColor[anzahlElemente] = new JButton(Messages.getString("RundenEingabeFormularView.10")); //$NON-NLS-1$
					downPane.add(changeColor[anzahlElemente]);
					rundenNummer[anzahlElemente] = new JComboBox<String>();
					for (int x = 1; x <= this.spielerAnzahl - ungerade; x++) {
						rundenNummer[anzahlElemente].addItem(Integer.toString(x));
					}
					rundenNummer[anzahlElemente].setSelectedIndex(Integer.parseInt(zeile[0]) - 1);
					downPane.add(new JLabel(Messages.getString("RundenEingabeFormularView.11"))); //$NON-NLS-1$
					downPane.add(rundenNummer[anzahlElemente]);
					downPane.add(new JLabel(" = ")); //$NON-NLS-1$
					weissSpieler[anzahlElemente] = new JLabel();
					weissSpieler[anzahlElemente]
							.setText(Messages.getString("RundenEingabeFormularView.13") + zeile[1] + " - "); //$NON-NLS-1$ //$NON-NLS-2$
					schwarzSpieler[anzahlElemente] = new JLabel();
					schwarzSpieler[anzahlElemente]
							.setText(Messages.getString("RundenEingabeFormularView.15") + zeile[2] + " "); //$NON-NLS-1$ //$NON-NLS-2$
					downPane.add(weissSpieler[anzahlElemente]);
					downPane.add(schwarzSpieler[anzahlElemente]);

					anzahlElemente++;
				}

				index++;
				downPane.updateUI();

				flowPane.add(downPane, BorderLayout.CENTER);

				flowPane.updateUI();
			}

			panel.add(flowPane, BorderLayout.NORTH);
			tabbedPane.add(Messages.getString("RundenEingabeFormularView.17") + (r + 1), panel); //$NON-NLS-1$
		}
		contentPanel.add(tabbedPane, BorderLayout.CENTER);
		contentPanel.updateUI();
	}

	// public JButton getReloadButton() {
	// return reloadButton;
	// }
	//
	// public void setReloadButton(JButton reloadButton) {
	// this.reloadButton = reloadButton;
	// }

	public class DateLabelFormatter extends AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		// private String datePattern = "yyyy-MM-dd";
		private String datePattern = Messages.getString("TurnierView.15"); //$NON-NLS-1$

		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern, Locale.getDefault());

		public DateLabelFormatter() {
			super();
			// setBackground(Color.CYAN);
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
}
