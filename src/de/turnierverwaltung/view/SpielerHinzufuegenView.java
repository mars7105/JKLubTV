package de.turnierverwaltung.view;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class SpielerHinzufuegenView extends JDialog {
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
	private JPanel centerPane;
	private JPanel contentPanel;
	private JComboBox<String> textComboBoxAge;

	public SpielerHinzufuegenView() {
		this.okButton = new JButton("Speichern");
		this.cancelButton = new JButton("Beenden");

		setTitle("Spieler hinzufügen");
		this.setAlwaysOnTop(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		

		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//		contentPanel.setBackground(new Color(249, 222, 112));

		buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
//		buttonPane.setBackground(new Color(249, 222, 112));
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);
		spielerPanel();

	}

	public void closeWindow() {
		this.dispose();
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

	public void spielerPanel() {
		centerPane = new JPanel();
		this.textFieldName = new JTextField(15);
		this.textFieldKuerzel = new JTextField(15);
		this.textFieldDwz = new JTextField(15);
		centerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
//		centerPane.setBackground(new Color(249, 222, 112));
		centerPane.add(new JLabel("Name: "));
		centerPane.add(textFieldName);

		centerPane.add(new JLabel("Kürzel: "));
		centerPane.add(textFieldKuerzel);

		centerPane.add(new JLabel("DWZ: "));
		centerPane.add(textFieldDwz);
		String[] ageStrings = { "unter 20", "20 bis 25", "über 25" };
		this.textComboBoxAge = new JComboBox<String>(ageStrings);
		centerPane.add(new JLabel("Alter: "));
		centerPane.add(textComboBoxAge);
		contentPanel.add(centerPane);
		contentPanel.add(buttonPane);
		add(contentPanel);
		contentPanel.updateUI();
		pack();
		setLocationRelativeTo(null);

		setEnabled(true);
		setVisible(true);
		

	}
}
