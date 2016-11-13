package de.turnierverwaltung.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.model.Sidepanel;

public class FrontendSidePanelView {

	private JDialog jsonDialog;
	private JTextField headerTextField;
	private JTextArea bodyTextArea;
	private JPanel htmlAll;
	private ButtonPanelView buttonPane;
	private JButton cancelButton;
	private JButton okButton;

	public FrontendSidePanelView() {
		// Erzeugung eines neuen Frames mit
		// dem Titel Beispiel JDialog
		jsonDialog = new JDialog();
		jsonDialog.setAlwaysOnTop(true);
		// Titel wird gesetzt
		jsonDialog.setTitle(Messages.getString("FrontendSidePanelView.0"));
		// Breite und Höhe des Fensters werden
		// auf 200 Pixel gesetzt
		jsonDialog.setSize(600, 500);
		// Dialog wird auf modal gesetzt
		jsonDialog.setModal(true);
		jsonDialog.setLayout(new BorderLayout());
		htmlAll = new JPanel();
		htmlAll.setLayout(new BorderLayout());
		Dimension dimTextField = new Dimension(350, 30);
		int textFieldColumns = 18;
		int textFieldRows = 18;

		ContextMenuMouseListener cmmL = new ContextMenuMouseListener();

		headerTextField = new JTextField("", textFieldColumns);
		headerTextField.setPreferredSize(dimTextField);
		headerTextField.addMouseListener(cmmL);

		bodyTextArea = new JTextArea("", textFieldRows, textFieldColumns);
		bodyTextArea.setPreferredSize(dimTextField);
		bodyTextArea.addMouseListener(cmmL);

		buttonPane = new ButtonPanelView();
		buttonPane.makeAllButtons();
		okButton = buttonPane.getOkButton();
		cancelButton = buttonPane.getCancelButton();

	}

	public void makeDialog(ArrayList<Sidepanel> sidepanel) {
		JPanel bothPanel = new JPanel();
		bothPanel.setLayout(new BorderLayout());
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		JPanel htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		Dimension dim = new Dimension(175, 30);

		JLabel headerTextFieldLabel = new JLabel(Messages.getString("FrontendSidePanelView.1") + ":");
		headerTextFieldLabel.setPreferredSize(dim);

		JLabel bodyTextAreaLabel = new JLabel(Messages.getString("FrontendSidePanelView.2") + ":");
		bodyTextAreaLabel.setPreferredSize(dim);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(headerTextFieldLabel);
		htmlPanel.add(headerTextField);
		leftPanel.add(htmlPanel);

		htmlPanel = new JPanel();
		htmlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		htmlPanel.add(bodyTextAreaLabel);
		htmlPanel.add(bodyTextArea);
		leftPanel.add(htmlPanel);

		bothPanel.add(leftPanel, BorderLayout.NORTH);
		htmlAll.add(bothPanel, BorderLayout.CENTER);
		htmlAll.add(buttonPane, BorderLayout.SOUTH);
		jsonDialog.add(htmlAll, BorderLayout.EAST);

		TreeDemo treedemo = new TreeDemo(sidepanel);
		jsonDialog.add(treedemo, BorderLayout.WEST);
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

	public JTextField getHeaderTextField() {
		return headerTextField;
	}

	public void setHeaderTextField(JTextField headerTextField) {
		this.headerTextField = headerTextField;
	}

	public JTextArea getBodyTextArea() {
		return bodyTextArea;
	}

	public void setBodyTextArea(JTextArea bodyTextArea) {
		this.bodyTextArea = bodyTextArea;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public void setOkButton(JButton okButton) {
		this.okButton = okButton;
	}

}
