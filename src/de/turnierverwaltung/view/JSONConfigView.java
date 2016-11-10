package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.turnierverwaltung.view.Messages;

public class JSONConfigView {

	private JDialog jsonDialog;

	private JPanel htmlAll;
	private JTextField categoryTextField;
	private JTextField tournamentTextField;
	private JTextField groupTextField;
	private JTextField crossTableTextField;
	private JTextField meetingTableTextField;
	private JTextField startDateTextField;
	private JTextField endDateTextField;
	private JTextField regularitiesTextField;
	private JTextField uploadURLTextField;

	private JButton okButton;

	private JButton cancelButton;

	// main-Methode
	public JSONConfigView() {
		// Erzeugung eines neuen Frames mit
		// dem Titel Beispiel JDialog
		jsonDialog = new JDialog();
		jsonDialog.setAlwaysOnTop(true);
		// Titel wird gesetzt
		jsonDialog.setTitle(Messages.getString("JSONConfigView.9"));
		// Breite und Höhe des Fensters werden
		// auf 200 Pixel gesetzt
		jsonDialog.setSize(600, 500);
		// Dialog wird auf modal gesetzt
		jsonDialog.setModal(true);
		// Wir lassen unseren Dialog anzeigen
		htmlAll = new JPanel();
		htmlAll.setLayout(new BorderLayout());
		makePanel();
		ButtonPanelView buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();
		cancelButton = buttonPane.getCancelButton();
		htmlAll.add(buttonPane, BorderLayout.SOUTH);
		jsonDialog.add(htmlAll);
		jsonDialog.pack();
		jsonDialog.setLocationRelativeTo(null);
		jsonDialog.setEnabled(true);
		jsonDialog.setVisible(true);
	}

	private void makePanel() {

		JPanel bothPanel = new JPanel();
		bothPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		Dimension dimTextField = new Dimension(350, 30);
		int textFieldColumns = 18;
		categoryTextField = new JTextField("", textFieldColumns);
		categoryTextField.setPreferredSize(dimTextField);

		tournamentTextField = new JTextField("", textFieldColumns);
		tournamentTextField.setPreferredSize(dimTextField);

		groupTextField = new JTextField("", textFieldColumns);
		groupTextField.setPreferredSize(dimTextField);

		crossTableTextField = new JTextField("", textFieldColumns);
		crossTableTextField.setPreferredSize(dimTextField);

		meetingTableTextField = new JTextField("", textFieldColumns);
		meetingTableTextField.setPreferredSize(dimTextField);

		startDateTextField = new JTextField("", textFieldColumns);
		startDateTextField.setPreferredSize(dimTextField);

		endDateTextField = new JTextField("", textFieldColumns);
		endDateTextField.setPreferredSize(dimTextField);

		regularitiesTextField = new JTextField("", textFieldColumns);
		regularitiesTextField.setPreferredSize(dimTextField);

		uploadURLTextField = new JTextField("", textFieldColumns);
		uploadURLTextField.setPreferredSize(dimTextField);

		Dimension dim = new Dimension(175, 30);
		JLabel categoryTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.0") + ":");
		categoryTextFieldLabel.setPreferredSize(dim);
		JLabel tournamentTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.1") + ":");
		tournamentTextFieldLabel.setPreferredSize(dim);

		JLabel groupTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.2") + ":");
		groupTextFieldLabel.setPreferredSize(dim);

		JLabel crossTableTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.3") + ":");
		crossTableTextFieldLabel.setPreferredSize(dim);

		JLabel meetingTableTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.4") + ":");
		meetingTableTextFieldLabel.setPreferredSize(dim);
		
		JLabel uploadURLTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.5") + ":");
		uploadURLTextFieldLabel.setPreferredSize(dim);

		JLabel startDateTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.6") + ":");
		startDateTextFieldLabel.setPreferredSize(dim);

		JLabel endDateTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.7") + ":");
		endDateTextFieldLabel.setPreferredSize(dim);

		JLabel regularitiesTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.8") + ":");
		regularitiesTextFieldLabel.setPreferredSize(dim);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(categoryTextFieldLabel);
		htmlPanel.add(categoryTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(tournamentTextFieldLabel);
		htmlPanel.add(tournamentTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(groupTextFieldLabel);
		htmlPanel.add(groupTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(crossTableTextFieldLabel);
		htmlPanel.add(crossTableTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(uploadURLTextFieldLabel);
		htmlPanel.add(uploadURLTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(meetingTableTextFieldLabel);
		htmlPanel.add(meetingTableTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(startDateTextFieldLabel);
		htmlPanel.add(startDateTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(endDateTextFieldLabel);
		htmlPanel.add(endDateTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(regularitiesTextFieldLabel);
		htmlPanel.add(regularitiesTextField);
		leftPanel.add(htmlPanel);

		bothPanel.add(leftPanel, BorderLayout.NORTH);
		htmlAll.add(bothPanel, BorderLayout.CENTER);

	}

	public JDialog getJsonDialog() {
		return jsonDialog;
	}

	public void setJsonDialog(JDialog jsonDialog) {
		this.jsonDialog = jsonDialog;
	}

	public JTextField getCategoryTextField() {
		return categoryTextField;
	}

	public void setCategoryTextField(JTextField categoryTextField) {
		this.categoryTextField = categoryTextField;
	}

	public JTextField getTournamentTextField() {
		return tournamentTextField;
	}

	public void setTournamentTextField(JTextField tournamentTextField) {
		this.tournamentTextField = tournamentTextField;
	}

	public JTextField getGroupTextField() {
		return groupTextField;
	}

	public void setGroupTextField(JTextField groupTextField) {
		this.groupTextField = groupTextField;
	}

	public JTextField getCrossTableTextField() {
		return crossTableTextField;
	}

	public void setCrossTableTextField(JTextField crossTableTextField) {
		this.crossTableTextField = crossTableTextField;
	}

	public JTextField getMeetingTableTextField() {
		return meetingTableTextField;
	}

	public void setMeetingTableTextField(JTextField meetingTableTextField) {
		this.meetingTableTextField = meetingTableTextField;
	}

	public JTextField getStartDateTextField() {
		return startDateTextField;
	}

	public void setStartDateTextField(JTextField startDateTextField) {
		this.startDateTextField = startDateTextField;
	}

	public JTextField getEndDateTextField() {
		return endDateTextField;
	}

	public void setEndDateTextField(JTextField endDateTextField) {
		this.endDateTextField = endDateTextField;
	}

	public JTextField getRegularitiesTextField() {
		return regularitiesTextField;
	}

	public void setRegularitiesTextField(JTextField regularitiesTextField) {
		this.regularitiesTextField = regularitiesTextField;
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

}
