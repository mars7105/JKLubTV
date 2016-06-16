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
import java.awt.Dimension;
//import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SpielerEingabeView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JButton okButton;
	private JButton cancelButton;
	private JScrollPane scrollPane;
	private JTextField[] spielerTextfield;
	private JTextField[] dwzTextfield;
	private JTextField[] kuerzelTextfield;
	private JComboBox<String>[] spielerSuche;
	private int spielerAnzahl;
	private int[] spielerID;
	private JComboBox<String>[] textComboBoxAge;

	/**
	 * Create the panel.
	 * 
	 * @param spielerAnzahl
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SpielerEingabeView(int spielerAnzahl) {

		this.spielerAnzahl = spielerAnzahl;
		spielerID = new int[this.spielerAnzahl];
		spielerTextfield = new JTextField[this.spielerAnzahl];
		dwzTextfield = new JTextField[this.spielerAnzahl];
		kuerzelTextfield = new JTextField[this.spielerAnzahl];
		textComboBoxAge = new JComboBox[this.spielerAnzahl];

		contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
//		contentPanel.setBackground(new Color(249, 222, 112));
		setLayout(new BorderLayout());

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);

		add(scrollPane, BorderLayout.CENTER);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		okButton = new JButton(Messages.getString("SpielerEingabeView.0")); //$NON-NLS-1$
		okButton.setActionCommand(Messages.getString("SpielerEingabeView.1")); //$NON-NLS-1$
		buttonPane.add(okButton);

		cancelButton = new JButton(Messages.getString("SpielerEingabeView.2")); //$NON-NLS-1$
		cancelButton.setActionCommand(Messages.getString("SpielerEingabeView.3")); //$NON-NLS-1$
		buttonPane.add(cancelButton);

		add(buttonPane, BorderLayout.SOUTH);

		JPanel line;
		JPanel centerPane = new JPanel();
//		centerPane.setBackground(new Color(249, 222, 112));
		contentPanel.add(centerPane);
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		spielerSuche = new JComboBox[this.spielerAnzahl];
		for (int i = 0; i < this.spielerAnzahl; i++) {
			spielerID[i] = -1;
			line = new JPanel();
			line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
//			line.setBackground(new Color(249, 222, 112));
			JLabel label = new JLabel(" " + new Integer(i + 1).toString() //$NON-NLS-1$
					+ Messages.getString("SpielerEingabeView.5") + Messages.getString("SpielerEingabeView.6")); //$NON-NLS-1$ //$NON-NLS-2$
			spielerTextfield[i] = new JTextField(10);
			spielerSuche[i] = new JComboBox();
			Dimension dimTextField = new Dimension(170, 30);

			spielerSuche[i].setPreferredSize(dimTextField);
			spielerSuche[i].setMaximumRowCount(15);
			spielerSuche[i].addItem(Messages.getString("SpielerEingabeView.7")); //$NON-NLS-1$

			line.add(spielerSuche[i]);
			line.add(label);
			line.add(spielerTextfield[i]);

			JLabel label2 = new JLabel(Messages.getString("SpielerEingabeView.8")); //$NON-NLS-1$
			kuerzelTextfield[i] = new JTextField(10);
			line.add(label2);
			line.add(kuerzelTextfield[i]);

			JLabel label3 = new JLabel(Messages.getString("SpielerEingabeView.9")); //$NON-NLS-1$
			dwzTextfield[i] = new JTextField(10);
			line.add(label3);
			line.add(dwzTextfield[i]);

			String[] ageStrings = { Messages.getString("SpielerEingabeView.10"), //$NON-NLS-1$
					Messages.getString("SpielerEingabeView.11"), Messages.getString("SpielerEingabeView.12") }; //$NON-NLS-1$ //$NON-NLS-2$
			textComboBoxAge[i] = new JComboBox<String>(ageStrings);
			line.add(new JLabel(Messages.getString("SpielerEingabeView.13"))); //$NON-NLS-1$
			line.add(textComboBoxAge[i]);

			centerPane.add(line);

		}

	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JTextField[] getDwzTextfield() {
		return dwzTextfield;
	}

	public JTextField[] getKuerzelTextfield() {
		return kuerzelTextfield;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public int getSpielerAnzahl() {
		return spielerAnzahl;
	}

	public int[] getSpielerID() {
		return spielerID;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox[] getSpielerSuche() {
		return spielerSuche;
	}

	public JTextField[] getSpielerTextfield() {
		return spielerTextfield;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setDwzTextfield(JTextField[] dwzTextfield) {
		this.dwzTextfield = dwzTextfield;
	}

	public void setKuerzelTextfield(JTextField[] kuerzelTextfield) {
		this.kuerzelTextfield = kuerzelTextfield;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setSpielerAnzahl(int spielerAnzahl) {
		this.spielerAnzahl = spielerAnzahl;
	}

	public void setSpielerID(int[] spielerID) {
		this.spielerID = spielerID;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setSpielerSuche(JComboBox[] spielerSuche) {
		this.spielerSuche = spielerSuche;
	}

	public void setSpielerTextfield(JTextField[] spielerTextfield) {
		this.spielerTextfield = spielerTextfield;
	}

	public JComboBox<String>[] getTextComboBoxAge() {
		return textComboBoxAge;
	}

	public void setTextComboBoxAge(JComboBox<String>[] textComboBoxAge) {
		this.textComboBoxAge = textComboBoxAge;
	}

}
