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

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.turnierverwaltung.model.Player;

public class EditPlayerView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5642277833139693453L;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField textFieldForename;
	private JTextField textFieldSurname;
	private JTextField textFieldName;
	private JTextField textFieldKuerzel;
	private JTextField textFieldDwz;
	private ButtonPanelView buttonPane;
	private JButton addSpielerButton;
	private JComboBox<String> textComboBoxAge;
	private JTextField textFieldZPS;
	private JTextField textFieldMGL;
	private JTextField textFieldDwzIndex;

	public EditPlayerView(Player spieler) {
		this.setAlwaysOnTop(true);
		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		this.okButton = buttonPane.getOkButton();
		this.cancelButton = buttonPane.getCancelButton();
		this.textFieldName = new JTextField(15);
		this.textFieldForename = new JTextField(15);
		this.textFieldSurname = new JTextField(15);
		this.textFieldKuerzel = new JTextField(15);
		this.textFieldDwz = new JTextField(15);
		this.textFieldDwzIndex = new JTextField(15);
		this.textFieldZPS = new JTextField(15);
		this.textFieldMGL = new JTextField(15);
		setTitle(Messages.getString("SpielerEditierenView.2")); //$NON-NLS-1$
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		// contentPanel.setBackground(new Color(249, 222, 112));

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		// textFieldName.setText(spieler.getName());
		// JLabel label = new JLabel();
		// label.setPreferredSize(new Dimension(120, 10));
		// label.setText(Messages.getString("SpielerEditierenView.3")); //$NON-NLS-1$
		// centerPane = new JPanel();
		// centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// // centerPane.setBackground(new Color(249, 222, 112));
		// centerPane.add(label);
		// centerPane.add(textFieldName);
		// contentPanel.add(centerPane);

		textFieldForename.setText(spieler.getForename());
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("SpielerEditierenView.10")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldForename);
		contentPanel.add(centerPane);

		textFieldSurname.setText(spieler.getSurname());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("SpielerEditierenView.11")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldSurname);
		contentPanel.add(centerPane);

		textFieldKuerzel.setText(spieler.getKuerzel());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("SpielerEditierenView.4")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldKuerzel);
		contentPanel.add(centerPane);

		textFieldDwz.setText(spieler.getDwz());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("SpielerEditierenView.5")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldDwz);
		contentPanel.add(centerPane);

		textFieldDwzIndex.setText(Integer.toString(spieler.getDwzindex()));
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("SpielerEditierenView.14")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldDwzIndex);
		contentPanel.add(centerPane);

		String[] ageStrings = { Messages.getString("SpielerEditierenView.6"), //$NON-NLS-1$
				Messages.getString("SpielerEditierenView.7"), Messages.getString("SpielerEditierenView.8") }; //$NON-NLS-1$ //$NON-NLS-2$
		this.textComboBoxAge = new JComboBox<String>(ageStrings);
		this.textComboBoxAge.setSelectedIndex(spieler.getAge());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("SpielerEditierenView.9")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textComboBoxAge);
		contentPanel.add(centerPane);

		textFieldZPS.setText(spieler.getDsbZPSNumber());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("SpielerEditierenView.12")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldZPS);
		contentPanel.add(centerPane);

		textFieldMGL.setText(spieler.getDsbMGLNumber());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120, 10));
		label.setText(Messages.getString("SpielerEditierenView.13")); //$NON-NLS-1$
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
		// centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldMGL);

		contentPanel.add(centerPane);

		contentPanel.add(buttonPane);
		add(contentPanel);
		contentPanel.updateUI();
		pack();

		setLocationRelativeTo(null);

		setEnabled(true);
		setVisible(true);
	}

	public void closeWindow() {
		this.dispose();
	}

	public JButton getAddSpielerButton() {
		return addSpielerButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JTextField getTextFieldDwz() {
		return textFieldDwz;
	}

	public JTextField getTextFieldKuerzel() {
		return textFieldKuerzel;
	}

	public JTextField getTextFieldName() {
		return textFieldName;
	}

	public void setAddSpielerButton(JButton addSpielerButton) {
		this.addSpielerButton = addSpielerButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public void setTextFieldDwz(JTextField textFieldDwz) {
		this.textFieldDwz = textFieldDwz;
	}

	public void setTextFieldKuerzel(JTextField textFieldKuerzel) {
		this.textFieldKuerzel = textFieldKuerzel;
	}

	public void setTextFieldName(JTextField name) {
		this.textFieldName = name;
	}

	public JComboBox<String> getTextComboBoxAge() {
		return textComboBoxAge;
	}

	public void setTextComboBoxAge(JComboBox<String> textComboBoxAge) {
		this.textComboBoxAge = textComboBoxAge;
	}

	public JTextField getTextFieldForename() {
		return textFieldForename;
	}

	public void setTextFieldForename(JTextField textFieldForename) {
		this.textFieldForename = textFieldForename;
	}

	public JTextField getTextFieldSurname() {
		return textFieldSurname;
	}

	public void setTextFieldSurname(JTextField textFieldSurname) {
		this.textFieldSurname = textFieldSurname;
	}

	public JTextField getTextFieldZPS() {
		return textFieldZPS;
	}

	public void setTextFieldZPS(JTextField textFieldZPS) {
		this.textFieldZPS = textFieldZPS;
	}

	public JTextField getTextFieldMGL() {
		return textFieldMGL;
	}

	public void setTextFieldMGL(JTextField textFieldMGL) {
		this.textFieldMGL = textFieldMGL;
	}

	public JTextField getTextFieldDwzIndex() {
		return textFieldDwzIndex;
	}

	public void setTextFieldDwzIndex(JTextField textFieldDwzIndex) {
		this.textFieldDwzIndex = textFieldDwzIndex;
	}

}
