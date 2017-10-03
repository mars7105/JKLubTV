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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class PairingsView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel flowPane;
	private JPanel downPane;
	private JTabbedPane tabbedPane;
	private JComboBox<String>[] rundenNummer;
	private int spielerAnzahl;
	private JLabel[] weissSpieler;
	private JLabel[] schwarzSpieler;
	private DateChooserPanel[] datum;
	private Properties property;
	private JButton[] changeColor;
	private int anzahlZeilen;
	private int anzahlElemente;
	private int ungerade;
	private int gerade;
	private int rundenanzahl;
	private int partienanzahl;
	private TitleLabelView statusLabel;

	// private JButton reloadButton;

	@SuppressWarnings("unchecked")
	public PairingsView(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		anzahlZeilen = this.spielerAnzahl * (this.spielerAnzahl - 1) / 2;
		changeColor = new JButton[anzahlZeilen];
		rundenNummer = new JComboBox[anzahlZeilen];
		weissSpieler = new JLabel[anzahlZeilen];
		schwarzSpieler = new JLabel[anzahlZeilen];

		datum = new DateChooserPanel[anzahlZeilen];

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

	public DateChooserPanel[] getDatum() {
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

		add(contentPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		JPanel status = new JPanel();
		status.setLayout(new FlowLayout(FlowLayout.LEFT));
		status.add(new JLabel(Messages.getString("SimpleTerminTabelleView.15"))); //$NON-NLS-1$
		statusLabel = new TitleLabelView("0");
		statusLabel.setFlowLayoutLeft();
		statusLabel.setOpaque(true);

		JLabel changesLabel = new JLabel(Messages.getString("SimpleTerminTabelleView.16"));
		status.add(statusLabel);
		status.add(changesLabel);
		southPanel.add(status, BorderLayout.SOUTH);

		southPanel.add(status, BorderLayout.WEST);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void setAnzahlElemente(int anzahlElemente) {
		this.anzahlElemente = anzahlElemente;
	}

	public void setChangeColor(JButton[] changeColor) {
		this.changeColor = changeColor;
	}

	public void setDatum(DateChooserPanel[] datum) {
		this.datum = datum;
	}

	public JLabel getStatusLabel() {
		return statusLabel.getTitleLabel();
	}

	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel.setTitleLabel(statusLabel);

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

		datum = new DateChooserPanel[anzahlZeilen];
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

					DateChooserPanel datePanel = new DateChooserPanel();
					datePanel.setLocale(Locale.getDefault());
					// datePanel.setDateFormatString(zeile[4]);
					DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
					try {
						Date d = formatter.parse(zeile[4]);
						datePanel.setDate(d);

					} catch (ParseException e) {

					}

					// datePanel.setForeground(Color.WHITE);
					datum[anzahlElemente] = datePanel;
					// JFormattedTextField textField = datum[anzahlElemente].getComponent(n)
					// Color preserveBackgroundColor = Color.gray;
					// textField.setFont(new Font("Some-Font-Name", Font.BOLD, 12));
					// textField.setBackground(preserveBackgroundColor );
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
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setViewportView(panel);
			scrollPane.setAlignmentY(TOP_ALIGNMENT);
			tabbedPane.add(Messages.getString("RundenEingabeFormularView.17") + (r + 1), scrollPane); //$NON-NLS-1$
		}
		contentPanel.add(tabbedPane, BorderLayout.CENTER);
		contentPanel.updateUI();
	}

}
