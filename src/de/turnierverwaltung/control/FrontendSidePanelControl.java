package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.view.FrontendSidePanelView;

public class FrontendSidePanelControl implements ActionListener {
	private FrontendSidePanelView sidePanel;
	private JDialog jsonDialog;
	private JTextField headerTextField;
	private JTextArea bodyTextArea;
	private JButton cancelButton;
	private JButton okButton;
	private ArrayList<String> headerText;
	private ArrayList<String> bodyText;
	private int index;

	public FrontendSidePanelControl() {
		sidePanel = new FrontendSidePanelView();
		jsonDialog = sidePanel.getJsonDialog();
		headerTextField = sidePanel.getHeaderTextField();
		bodyTextArea = sidePanel.getBodyTextArea();
		cancelButton = sidePanel.getCancelButton();
		okButton = sidePanel.getOkButton();

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		headerText = new ArrayList<String>();
		bodyText = new ArrayList<String>();
	}

	public void makeDialog() {
		sidePanel.makeDialog();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			jsonDialog.dispose();
		}
		if (e.getSource() == okButton) {
			headerText.add(headerTextField.getText());
			bodyText.add(bodyTextArea.getText());
			index += 2;
			jsonDialog.dispose();
		}
	}

	public ArrayList<String> getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {

		headerTextField.setText(headerText);
	}

	public ArrayList<String> getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {

		bodyTextArea.setText(bodyText);
	}

}
