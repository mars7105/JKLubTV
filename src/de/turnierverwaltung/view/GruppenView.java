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
//import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
/*
 * Version 3.1.0
 */
//import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;

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
	/*
	 * Version 3.1.0
	 */
	// private JCheckBox[] doppelteRundenCheckBox;
	private JScrollPane scrollPane;

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
		/*
		 * Version 3.1.0
		 */
		// JLabel doppelteRundenLabel;
		// doppelteRundenCheckBox = new JCheckBox[this.gruppenAnzahl];
		setLayout(new BorderLayout());
		// setBackground(new Color(249, 222, 112));

		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		// contentPanel.setBackground(new Color(249, 222, 112));
		contentPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();

		JPanel centerPane = new JPanel();
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		for (int i = 0; i < this.gruppenAnzahl; i++) {

			JPanel groupBox = new JPanel();
			groupBox.setLayout(new FlowLayout(FlowLayout.LEFT));
			groupBox.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			gruppenNameTextField[i] = new JTextField();
			gruppenNameTextField[i].setColumns(10);
			JPanel gruppenLabelPanel = new JPanel();
			gruppenLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			gruppenLabelPanel.add(new JLabel(String.valueOf(i + 1) + Messages.getString("GruppenView.0"))); //$NON-NLS-1$
			groupBox.add(gruppenLabelPanel);
			groupBox.add(gruppenNameTextField[i]);
			/*
			 * Version 3.1.0
			 */
			// doppelteRundenCheckBox[i] = new JCheckBox();
			// groupBox.add(doppelteRundenCheckBox[i]);
			// doppelteRundenLabel = new
			// JLabel(Messages.getString("GruppenView.5"));
			// JPanel doppelRundigPanel = new JPanel();
			// doppelRundigPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			// doppelRundigPanel.add(doppelteRundenCheckBox[i]);
			// doppelRundigPanel.add(doppelteRundenLabel);

			// groupBox.add(doppelRundigPanel);
			centerPane.add(groupBox);
		}
		okButton = buttonPane.getOkButton();
		okButton.setText(Messages.getString("GruppenView.1")); //$NON-NLS-1$
		okButton.setActionCommand(Messages.getString("GruppenView.2")); //$NON-NLS-1$
		buttonPane.add(okButton);

		cancelButton = buttonPane.getCancelButton();
		cancelButton.setText(Messages.getString("GruppenView.3")); //$NON-NLS-1$
		cancelButton.setActionCommand(Messages.getString("GruppenView.4")); //$NON-NLS-1$
		buttonPane.add(cancelButton);
		centerPane.add(buttonPane);
		JPanel allGroupBoxes = new JPanel();
		allGroupBoxes.setLayout(new BorderLayout());
		allGroupBoxes.add(centerPane, BorderLayout.NORTH);

		contentPanel.add(allGroupBoxes);
		// contentPanel.add(buttonPane);

		scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setViewportView(contentPanel);
		add(scrollPane, BorderLayout.CENTER);
		// add(buttonPane, BorderLayout.SOUTH);

		setVisible(true);

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
