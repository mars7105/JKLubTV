package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.turnierverwaltung.view.Messages;

public class JSONConfigView {

	private JDialog jsonDialog;

	private JPanel htmlAll;
	private JTextField tournamentNameTextField;
	private JTextField menuNameTextField;
	private JButton sidePanelsButton;
	private JTextField uploadURLTextField;
	private JButton okButton;
	private JButton cancelButton;
	private ButtonPanelView buttonPane;
	private ImageIcon addImg = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-add-2.png")));

	// main-Methode
	public JSONConfigView() {
		// Erzeugung eines neuen Frames mit
		// dem Titel Beispiel JDialog
		jsonDialog = new JDialog();
		jsonDialog.setAlwaysOnTop(true);
		// Titel wird gesetzt
		jsonDialog.setTitle(Messages.getString("JSONConfigView.5"));
		// Breite und Höhe des Fensters werden
		// auf 200 Pixel gesetzt
		jsonDialog.setSize(600, 500);
		// Dialog wird auf modal gesetzt
		jsonDialog.setModal(true);
		// Wir lassen unseren Dialog anzeigen
		htmlAll = new JPanel();
		htmlAll.setLayout(new BorderLayout());
		Dimension dimTextField = new Dimension(350, 30);
		int textFieldColumns = 18;
		tournamentNameTextField = new JTextField("", textFieldColumns);
		tournamentNameTextField.setPreferredSize(dimTextField);

		menuNameTextField = new JTextField("", textFieldColumns);
		menuNameTextField.setPreferredSize(dimTextField);

		uploadURLTextField = new JTextField("", textFieldColumns);
		uploadURLTextField.setPreferredSize(dimTextField);
		sidePanelsButton = new JButton(Messages.getString("JSONConfigView.0"), addImg);

		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();
		cancelButton = buttonPane.getCancelButton();

	}

	public void makePanel() {

		JPanel bothPanel = new JPanel();
		bothPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		Dimension dim = new Dimension(175, 30);

		JLabel tournamentTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.1") + ":");
		tournamentTextFieldLabel.setPreferredSize(dim);

		JLabel menuNameTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.2") + ":");
		menuNameTextFieldLabel.setPreferredSize(dim);

		JLabel uploadURLTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.3") + ":");
		uploadURLTextFieldLabel.setPreferredSize(dim);

		JLabel sidePanelsLabel = new JLabel(Messages.getString("JSONConfigView.4") + ":");
		sidePanelsLabel.setPreferredSize(dim);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(tournamentTextFieldLabel);
		htmlPanel.add(tournamentNameTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(menuNameTextFieldLabel);
		htmlPanel.add(menuNameTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(uploadURLTextFieldLabel);
		htmlPanel.add(uploadURLTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(sidePanelsLabel);
		htmlPanel.add(sidePanelsButton);
		leftPanel.add(htmlPanel);

		bothPanel.add(leftPanel, BorderLayout.NORTH);
		htmlAll.add(bothPanel, BorderLayout.CENTER);
		htmlAll.add(buttonPane, BorderLayout.SOUTH);
		jsonDialog.add(htmlAll);
		jsonDialog.pack();
		jsonDialog.setLocationRelativeTo(null);
		jsonDialog.setEnabled(true);
		jsonDialog.setVisible(true);
	}

	public JDialog getJsonDialog() {
		return jsonDialog;
	}

	public void setJsonDialog(JDialog jsonDialog) {
		this.jsonDialog = jsonDialog;
	}

	public JTextField getUploadURLTextField() {
		return uploadURLTextField;
	}

	public void setUploadURLTextField(JTextField uploadURLTextField) {
		this.uploadURLTextField = uploadURLTextField;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JTextField getTournamentNameTextField() {
		return tournamentNameTextField;
	}

	public void setTournamentNameTextField(JTextField tournamentNameTextField) {
		this.tournamentNameTextField = tournamentNameTextField;
	}

	public JTextField getMenuNameTextField() {
		return menuNameTextField;
	}

	public void setMenuNameTextField(JTextField menuNameTextField) {
		this.menuNameTextField = menuNameTextField;
	}

	public JButton getSidePanelsButton() {
		return sidePanelsButton;
	}

	public void setSidePanelsButton(JButton sidePanelsButton) {
		this.sidePanelsButton = sidePanelsButton;
	}

}
