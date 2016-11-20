package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import de.turnierverwaltung.model.Sidepanel;
import de.turnierverwaltung.view.ColorSelectorView;
import de.turnierverwaltung.view.FrontendSidePanelView;

public class FrontendSidePanelControl implements ActionListener {
	private FrontendSidePanelView sidePanel;
	private JDialog jsonDialog;
	private JTextField headerTextField;
	private JTextArea bodyTextArea;
	private JButton okButton;
	private String headerText;
	private String bodyText;
	private ArrayList<Sidepanel> sideP;
	private int selectedIndex;
	private MainControl mainControl;
	private SidePanelControl dynTree;
	private ColorSelectorView colorSelector;

	public FrontendSidePanelControl(MainControl mainControl) {
		this.mainControl = mainControl;
		dynTree = new SidePanelControl(mainControl);

		sidePanel = new FrontendSidePanelView(sideP, dynTree);
		this.mainControl.setFrontendSidePanelView(sidePanel);
		jsonDialog = sidePanel.getJsonDialog();
		headerTextField = sidePanel.getHeaderTextField();
		bodyTextArea = sidePanel.getBodyTextArea();
		colorSelector = sidePanel.getColorSelectorPanel();
		okButton = sidePanel.getOkButton();

		okButton.addActionListener(this);

		headerText = "";
		bodyText = "";
	}

	public void makeDialog() {

		sidePanel.makeDialog();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

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

	public ColorSelectorView getColorSelector() {
		return colorSelector;
	}

	public void setColorSelector(ColorSelectorView colorSelector) {
		this.colorSelector = colorSelector;
	}

}
