package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.view.FrontendSidePanelView;

public class FrontendSidePanelControl implements ActionListener {
	private FrontendSidePanelView sidePanel;
	private JDialog jsonDialog;
	private JTextField headerTextField;
	private JTextArea bodyTextArea;
	private JButton cancelButton;
	private JButton okButton;
	private String headerText;
	private String bodyText;
	private ArrayList<Sidepanel> sideP;
	private int selectedIndex;
	private MainControl mainControl;
	private DynamicTreeDemo dynTree;

	public FrontendSidePanelControl(MainControl mainControl) {
		this.mainControl = mainControl;
		dynTree = new DynamicTreeDemo(mainControl);

		sidePanel = new FrontendSidePanelView(sideP, dynTree);
		this.mainControl.setFrontendSidePanelView(sidePanel);
		// dynTree.setSidePanelView(sidePanel);
		jsonDialog = sidePanel.getJsonDialog();
		headerTextField = sidePanel.getHeaderTextField();
		bodyTextArea = sidePanel.getBodyTextArea();
		cancelButton = sidePanel.getCancelButton();
		okButton = sidePanel.getOkButton();

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		headerText = "";
		bodyText = "";
	}

	public void makeDialog() {

		sidePanel.makeDialog();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == cancelButton) {
			jsonDialog.dispose();
			headerText = null;
			bodyText = null;
		}
		if (e.getSource() == okButton) {

			jsonDialog.dispose();

		}
	}

	public String getHeaderText() {
		return headerText;
	}

	public void setHeaderText(String headerText) {

		headerTextField.setText(headerText);
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {

		bodyTextArea.setText(bodyText);
	}

	public ArrayList<Sidepanel> getSideP() {
		return sideP;
	}

	public void setSideP(ArrayList<Sidepanel> sideP) {
		this.sideP = sideP;
	}

	public FrontendSidePanelView getSidePanel() {
		return sidePanel;
	}

	public void setSidePanel(FrontendSidePanelView sidePanel) {
		this.sidePanel = sidePanel;
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

}
