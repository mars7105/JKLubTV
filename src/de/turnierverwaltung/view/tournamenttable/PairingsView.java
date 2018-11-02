package de.turnierverwaltung.view.tournamenttable;

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
//import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Locale;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import de.turnierverwaltung.model.EventDate;
import de.turnierverwaltung.view.DateChooserPanel;
import de.turnierverwaltung.view.Messages;
import de.turnierverwaltung.view.TitleLabelView;

public class PairingsView extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel flowPane;
	private JPanel downPane;
	private JPanel tabbedPane;
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

	public JLabel getStatusLabel() {
		return statusLabel.getTitleLabel();
	}

	public JPanel getTabbedPane() {
		return tabbedPane;
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

		final JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());

		final JPanel status = new JPanel();
		status.setLayout(new FlowLayout(FlowLayout.LEFT));
		status.add(new JLabel(Messages.getString("SimpleTerminTabelleView.15"))); //$NON-NLS-1$
		statusLabel = new TitleLabelView("0");
		statusLabel.setFlowLayoutLeft();
		statusLabel.setOpaque(true);

		final JLabel changesLabel = new JLabel(Messages.getString("SimpleTerminTabelleView.16"));
		status.add(statusLabel);
		status.add(changesLabel);
		southPanel.add(status, BorderLayout.SOUTH);

		southPanel.add(status, BorderLayout.WEST);
		add(southPanel, BorderLayout.SOUTH);
	}

	public void makeZeilen(String[][] terminMatrix) {
		String[] zeile = new String[5];

		tabbedPane = new JPanel();
		tabbedPane.setLayout(new BoxLayout(tabbedPane, BoxLayout.PAGE_AXIS));
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT);
		flowLayout.setVgap(1);
		Dimension dim = new Dimension(175, 35);
		for (int r = 0; r < rundenanzahl; r++) {

			flowPane = new JPanel();
			flowPane.setLayout(new BoxLayout(flowPane, BoxLayout.PAGE_AXIS));
			// flowPane.setLayout(new BorderLayout());
			flowPane.setBorder(BorderFactory.createLineBorder(Color.BLUE));
			// flowPane.setAlignmentY(Component.TOP_ALIGNMENT);

			for (int i = 0; i < partienanzahl; i++) {
				zeile[0] = terminMatrix[0][i + 1 + (r * partienanzahl)];
				zeile[1] = terminMatrix[1][i + 1 + (r * partienanzahl)];
				zeile[2] = terminMatrix[2][i + 1 + (r * partienanzahl)];
				zeile[3] = terminMatrix[3][i + 1 + (r * partienanzahl)];
				zeile[4] = terminMatrix[4][i + 1 + (r * partienanzahl)];
				downPane = new JPanel();

				downPane.setLayout(flowLayout);
				downPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				// downPane.setAlignmentY(Component.TOP_ALIGNMENT);
				// if
				// (!zeile[0].equals(Messages.getString("RundenEingabeFormularView.7")))
				// {
				// //$NON-NLS-1$

				DateChooserPanel datePanel = new DateChooserPanel();
				datePanel.setLocale(Locale.getDefault());
				// datePanel.setDateFormatString(zeile[4]);
				// DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy",
				// Locale.getDefault());
				EventDate eventDate = new EventDate(zeile[4]);
				// Date d = eventDate.getDate();
				if (Locale.getDefault().equals(Locale.US)) {
					datePanel.setDateFormatString(eventDate.getEnglishFormat());
				}
				if (Locale.getDefault().equals(Locale.GERMANY)) {
					datePanel.setDateFormatString(eventDate.getGermanFormat());
				}
				// datePanel.setDefaultLocale(Locale.getDefault());
				datePanel.setDate(eventDate.getDate());

				// datePanel.setForeground(Color.WHITE);
				datum[anzahlElemente] = datePanel;
				// JFormattedTextField textField =
				// datum[anzahlElemente].getComponent(n)
				// Color preserveBackgroundColor = Color.gray;
				// textField.setFont(new Font("Some-Font-Name", Font.BOLD, 12));
				// textField.setBackground(preserveBackgroundColor );
				downPane.add(new JLabel(Messages.getString("RundenEingabeFormularView.8"))); //$NON-NLS-1$
				downPane.add(datum[anzahlElemente]);
				downPane.add(new JLabel(" ")); //$NON-NLS-1$
				changeColor[anzahlElemente] = new JButton(Messages.getString("RundenEingabeFormularView.10")); //$NON-NLS-1$
				downPane.add(changeColor[anzahlElemente]);
				rundenNummer[anzahlElemente] = new JComboBox<String>();
				for (int x = 1; x <= spielerAnzahl - ungerade; x++) {
					rundenNummer[anzahlElemente].addItem(Integer.toString(x));
				}
				rundenNummer[anzahlElemente].setSelectedIndex(Integer.parseInt(zeile[0]) - 1);
				downPane.add(new JLabel(Messages.getString("RundenEingabeFormularView.11"))); //$NON-NLS-1$
				downPane.add(rundenNummer[anzahlElemente]);
				downPane.add(new JLabel(" = ")); //$NON-NLS-1$
				weissSpieler[anzahlElemente] = new JLabel();
				weissSpieler[anzahlElemente].setPreferredSize(dim);
				weissSpieler[anzahlElemente].setBorder(new EmptyBorder(5, 5, 5, 5));
				weissSpieler[anzahlElemente].setOpaque(true);
				weissSpieler[anzahlElemente].setBackground(Color.WHITE);
				weissSpieler[anzahlElemente].setForeground(Color.BLACK);
				weissSpieler[anzahlElemente].setText(zeile[1]);
				schwarzSpieler[anzahlElemente] = new JLabel();
				schwarzSpieler[anzahlElemente].setPreferredSize(dim);
				schwarzSpieler[anzahlElemente].setBorder(new EmptyBorder(5, 5, 5, 5));
				schwarzSpieler[anzahlElemente].setOpaque(true);
				schwarzSpieler[anzahlElemente].setBackground(Color.BLACK);
				schwarzSpieler[anzahlElemente].setForeground(Color.WHITE);
				schwarzSpieler[anzahlElemente].setText(zeile[2]);
				downPane.add(weissSpieler[anzahlElemente]);
				downPane.add(new JLabel(" - "));
				downPane.add(schwarzSpieler[anzahlElemente]);

				anzahlElemente++;
				// }

				downPane.updateUI();

				flowPane.add(downPane, BorderLayout.CENTER);

				flowPane.updateUI();
			}

			JLabel roundLabel = new JLabel(Messages.getString("RundenEingabeFormularView.17") + (r + 1));
			roundLabel.setOpaque(true);
			Font font = roundLabel.getFont();
			float size = font.getSize() + 2;
			roundLabel.setFont(font.deriveFont(size));
			roundLabel.getFont().deriveFont(size);

			tabbedPane.add(roundLabel);
			tabbedPane.add(flowPane);

		}
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(tabbedPane, BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		scrollPane.setAlignmentY(TOP_ALIGNMENT);
		contentPanel.add(scrollPane, BorderLayout.CENTER);
		contentPanel.updateUI();
	}

	public void setAnzahlElemente(final int anzahlElemente) {
		this.anzahlElemente = anzahlElemente;
	}

	public void setChangeColor(final JButton[] changeColor) {
		this.changeColor = changeColor;
	}

	public void setDatum(final DateChooserPanel[] datum) {
		this.datum = datum;
	}

	@SuppressWarnings("unchecked")
	public void setRundenNummer(@SuppressWarnings("rawtypes") final JComboBox[] rundenNummer) {
		this.rundenNummer = rundenNummer;
	}

	public void setSchwarzSpieler(final JLabel[] schwarzSpieler) {
		this.schwarzSpieler = schwarzSpieler;
	}

	@SuppressWarnings("unchecked")
	public void setSpielerAnzahl(final int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
		anzahlZeilen = this.spielerAnzahl * (this.spielerAnzahl - 1) / 2;
		changeColor = new JButton[anzahlZeilen];
		rundenNummer = new JComboBox[anzahlZeilen];
		weissSpieler = new JLabel[anzahlZeilen];
		schwarzSpieler = new JLabel[anzahlZeilen];

		datum = new DateChooserPanel[anzahlZeilen];
	}

	public void setStatusLabel(final JLabel statusLabel) {
		this.statusLabel.setTitleLabel(statusLabel);

	}

	public void setTabbedPane(final JPanel tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public void setWeissSpieler(final JLabel[] weissSpieler) {
		this.weissSpieler = weissSpieler;
	}

}
