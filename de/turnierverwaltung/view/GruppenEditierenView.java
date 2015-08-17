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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.turnierverwaltung.model.Turnier;
import de.turnierverwaltung.model.TurnierKonstanten;

public class GruppenEditierenView extends JDialog {
	private JButton saveButton;
	private JButton cancelButton;
	private JTextField[] textFieldGruppenName;
	private JButton rundenEditierenButton;
	private JPanel buttonPane;

	public GruppenEditierenView(Turnier turnier) {

		this.saveButton = new JButton("Speichern");
		this.cancelButton = new JButton("Abbrechen");
		this.rundenEditierenButton = new JButton("Spielrunden bearbeiten");
		this.textFieldGruppenName = new JTextField[turnier.getAnzahlGruppen()];

		this.setAlwaysOnTop(true);
		setTitle("Gruppen editieren");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//		int windowWidth = TurnierKonstanten.WINDOW_WIDTH - 50;
//		int windowHeight = TurnierKonstanten.WINDOW_HEIGHT - 50;

		setLocationRelativeTo(null);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(new Color(249, 222, 112));
		for (int i = 0; i < turnier.getAnzahlGruppen(); i++) {
			JPanel centerPane = new JPanel();
			centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
			centerPane.setBackground(new Color(249, 222, 112));
			this.textFieldGruppenName[i] = new JTextField(15);
			textFieldGruppenName[i].setText(turnier.getGruppe()[i]
					.getGruppenName());
			centerPane.add(new JLabel("Gruppenname: "));
			centerPane.add(textFieldGruppenName[i]);

			contentPanel.add(centerPane);
		}
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		buttonPane.setBackground(new Color(249, 222, 112));
		buttonPane.add(this.saveButton);
		buttonPane.add(rundenEditierenButton);
		buttonPane.add(cancelButton);

		contentPanel.add(buttonPane);
		add(contentPanel);
		contentPanel.updateUI();
		setEnabled(true);
		setVisible(true);
		pack();
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getRundenEditierenButton() {
		return rundenEditierenButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JTextField[] getTextFieldGruppenName() {
		return textFieldGruppenName;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setRundenEditierenButton(JButton rundenEditierenButton) {
		this.rundenEditierenButton = rundenEditierenButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public void setTextFieldGruppenName(JTextField[] textFieldGruppenName) {
		this.textFieldGruppenName = textFieldGruppenName;
	}

}
