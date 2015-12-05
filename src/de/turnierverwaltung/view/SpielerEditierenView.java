package de.turnierverwaltung.view;
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

import de.turnierverwaltung.model.Spieler;

public class SpielerEditierenView extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5642277833139693453L;
	private JButton okButton;
	private JButton cancelButton;
	private JTextField textFieldName;
	private JTextField textFieldKuerzel;
	private JTextField textFieldDwz;
	private JPanel buttonPane;
	private JButton addSpielerButton;
	private JComboBox<String> textComboBoxAge;

	public SpielerEditierenView(Spieler spieler) {
		this.setAlwaysOnTop(true);
		this.okButton = new JButton("Speichern");
		this.cancelButton = new JButton("Abbrechen");
		this.textFieldName = new JTextField(15);
		this.textFieldKuerzel = new JTextField(15);
		this.textFieldDwz = new JTextField(15);
		setTitle("Spieler editieren");
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//		contentPanel.setBackground(new Color(249, 222, 112));

		JPanel centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
//		centerPane.setBackground(new Color(249, 222, 112));
		textFieldName.setText(spieler.getName());
		JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(120,10));
		label.setText("Name: ");
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
//		centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldName);
		contentPanel.add(centerPane);
		textFieldKuerzel.setText(spieler.getKuerzel());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120,10));
		label.setText("Kürzel: ");
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
//		centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldKuerzel);
		contentPanel.add(centerPane);

		textFieldDwz.setText(spieler.getDwz());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120,10));
		label.setText("DWZ: ");
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
//		centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textFieldDwz);
		contentPanel.add(centerPane);

		String[] ageStrings = { "unter 20", "20 bis 25", "über 25" };
		this.textComboBoxAge = new JComboBox<String>(ageStrings);
		this.textComboBoxAge.setSelectedIndex(spieler.getAge());
		label = new JLabel();
		label.setPreferredSize(new Dimension(120,10));
		label.setText("Alter: ");
		centerPane = new JPanel();
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
//		centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(label);
		centerPane.add(textComboBoxAge);
		contentPanel.add(centerPane);
	
		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
//		buttonPane.setBackground(new Color(249, 222, 112));
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);

		
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
	
}
