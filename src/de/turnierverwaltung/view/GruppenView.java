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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GruppenView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private int gruppenAnzahl;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField[] gruppenNameTextField;
	private String[] gruppenName;

	public GruppenView() {

	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public int getGruppenAnzahl() {
		return gruppenAnzahl;
	}

	public String[] getGruppenName() {
		for (int i = 0; i < gruppenAnzahl; i++) {
			gruppenName[i] = this.gruppenNameTextField[i].getText();
		}
		return gruppenName;
	}

	public JTextField[] getGruppenNameTextField() {
		return gruppenNameTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void runView(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
		gruppenName = new String[this.gruppenAnzahl];

		gruppenNameTextField = new JTextField[this.gruppenAnzahl];
		setLayout(new FlowLayout());
		setBackground(new Color(249, 222, 112));

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(new Color(249, 222, 112));

		add(contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			buttonPane.setBackground(new Color(249, 222, 112));
			JPanel centerPane = new JPanel();
			centerPane.setBackground(new Color(249, 222, 112));
			centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
			for (int i = 0; i < this.gruppenAnzahl; i++) {
				gruppenNameTextField[i] = new JTextField();
				gruppenNameTextField[i].setColumns(10);
				centerPane.add(new JLabel(String.valueOf(i + 1) + ". Gruppenname"));
				centerPane.add(gruppenNameTextField[i]);

			}

			contentPanel.add(centerPane);
			contentPanel.add(buttonPane);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
			}
			{
				cancelButton = new JButton("Abbruch");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);

			}
			setVisible(true);
		}
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setGruppenAnzahl(int gruppenAnzahl) {
		this.gruppenAnzahl = gruppenAnzahl;
	}

	public void setGruppenName(String[] gruppenName) {
		this.gruppenName = gruppenName;
	}

	public void setGruppenNameTextField(JTextField[] gruppenNameTextField) {
		this.gruppenNameTextField = gruppenNameTextField;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

}
