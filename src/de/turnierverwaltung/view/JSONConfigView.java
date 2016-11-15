package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

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
	// private JTextField tournamentNameTextField;
	private JTextField menuNameTextField;
	private JButton sidePanelsButton;
	private JButton connectionTestButton;
	private JTextField uploadURLTextField;
	private JButton okButton;
	private JButton cancelButton;
	private ButtonPanelView buttonPane;
	private ImageIcon addImg = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/document-edit.png")));
	private ImageIcon connectionTestImg = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/homepage.png")));
	private ImageIcon buttonGroupImg = new ImageIcon(
			Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/edit-group.png")));
	private JPanel groupPanel;
	private Dimension buttonSize;
	private ArrayList<JButton> groupButtons;

	// main-Methode
	public JSONConfigView() {
		buttonSize = new Dimension(270, 60);
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
		// tournamentNameTextField = new JTextField("", textFieldColumns);
		// tournamentNameTextField.setPreferredSize(dimTextField);
		ContextMenuMouseListener cmmL = new ContextMenuMouseListener();

		menuNameTextField = new JTextField("", textFieldColumns);
		menuNameTextField.setPreferredSize(dimTextField);
		menuNameTextField.addMouseListener(cmmL);

		uploadURLTextField = new JTextField("", textFieldColumns);
		uploadURLTextField.setPreferredSize(dimTextField);
		uploadURLTextField.addMouseListener(cmmL);
		sidePanelsButton = new JButton(Messages.getString("JSONConfigView.0"), addImg);
		connectionTestButton = new JButton(Messages.getString("JSONConfigView.6"), connectionTestImg);
		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();
		okButton.setText(Messages.getString("JSONConfigView.8"));
		cancelButton = buttonPane.getCancelButton();
		groupPanel = new JPanel();
		groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.PAGE_AXIS));
		groupButtons = new ArrayList<JButton>();
	}

	public void makeGroupButtons(String groupName) {
		JButton groupButton = new JButton(groupName, buttonGroupImg);
		groupButton.setPreferredSize(buttonSize);
		groupButtons.add(groupButton);
		
		groupPanel.add(groupButton);
	}

	public void makePanel() {

		JPanel bothPanel = new JPanel();
		bothPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		// JPanel htmlPanel = new JPanel();
		// htmlPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));

		Dimension dim = new Dimension(175, 30);

		JLabel connectionTestButtonLabel = new JLabel(Messages.getString("JSONConfigView.7") + ":");
		connectionTestButtonLabel.setPreferredSize(dim);

		JLabel menuNameTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.2") + ":");
		menuNameTextFieldLabel.setPreferredSize(dim);

		JLabel uploadURLTextFieldLabel = new JLabel(Messages.getString("JSONConfigView.3") + ":");
		uploadURLTextFieldLabel.setPreferredSize(dim);

		JLabel sidePanelsLabel = new JLabel(Messages.getString("JSONConfigView.4") + ":");
		sidePanelsLabel.setPreferredSize(dim);

		JLabel groupPanelLabel = new JLabel(Messages.getString("JSONConfigView.9") + ":");
		groupPanelLabel.setPreferredSize(dim);

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(menuNameTextFieldLabel);
		htmlPanel.add(menuNameTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(uploadURLTextFieldLabel);
		htmlPanel.add(uploadURLTextField);
		leftPanel.add(htmlPanel);

		connectionTestButton.setPreferredSize(buttonSize);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(connectionTestButtonLabel);
		htmlPanel.add(connectionTestButton);
		leftPanel.add(htmlPanel);

		sidePanelsButton.setPreferredSize(buttonSize);
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(sidePanelsLabel);
		htmlPanel.add(sidePanelsButton);
		leftPanel.add(htmlPanel);
		
		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(groupPanelLabel);
		htmlPanel.add(groupPanel);
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

	public JButton getConnectionTestButton() {
		return connectionTestButton;
	}

	public void setConnectionTestButton(JButton connectionTestButton) {
		this.connectionTestButton = connectionTestButton;
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

	public ArrayList<JButton> getGroupButtons() {
		return groupButtons;
	}

	public void setGroupButtons(ArrayList<JButton> groupButtons) {
		this.groupButtons = groupButtons;
	}

}
