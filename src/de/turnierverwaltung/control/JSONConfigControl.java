package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.turnierverwaltung.model.JSON;
import de.turnierverwaltung.view.JSONConfigView;

public class JSONConfigControl implements ActionListener {
	private JSONConfigView jsonView;
	private JButton okButton;
	private JButton cancelButton;
	private JButton sidePanelsButton;
	private String menuName;
	private JTextField menuNameTextField;

	private String uploadURL;
	private JButton connectionTestButton;
	private JTextField uploadURLTextField;
	private MainControl mainControl;
	private FrontendSidePanelControl sidePanel;

	public JSONConfigControl(MainControl mainControl) {
		this.mainControl = mainControl;

		jsonView = new JSONConfigView();

		okButton = jsonView.getOkButton();
		okButton.addActionListener(this);
		cancelButton = jsonView.getCancelButton();
		cancelButton.addActionListener(this);
		sidePanelsButton = jsonView.getSidePanelsButton();
		sidePanelsButton.addActionListener(this);
		menuNameTextField = jsonView.getMenuNameTextField();
		uploadURLTextField = jsonView.getUploadURLTextField();
		connectionTestButton = jsonView.getConnectionTestButton();
		connectionTestButton.addActionListener(this);
		menuName = mainControl.getPropertiesControl().getFrontendMenuname();

		uploadURL = mainControl.getPropertiesControl().getFrontendURL();

		menuNameTextField.setText(menuName);
		uploadURLTextField.setText(uploadURL);

	}

	public void makeDialog() {
		jsonView.makePanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			menuName = jsonView.getMenuNameTextField().getText();
			uploadURL = jsonView.getUploadURLTextField().getText();
			jsonView.getJsonDialog().dispose();
			JSONSaveControl json = new JSONSaveControl(this.mainControl);
			ArrayList<String> header = null;
			ArrayList<String> body = null;
			if (sidePanel != null) {
				header = sidePanel.getHeaderText();
				body = sidePanel.getBodyText();
			}
			try {
				json.uploadJSONFile(uploadURL, menuName,header,body);
				mainControl.getPropertiesControl().setFrontendMenuname(menuName);
				mainControl.getPropertiesControl().setFrontendURL(uploadURL);
				mainControl.getPropertiesControl().writeProperties();
				JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.0"));
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.2"));
			}

		}
		if (e.getSource() == sidePanelsButton) {
			sidePanel = new FrontendSidePanelControl();
			sidePanel.makeDialog();
		}
		if (e.getSource() == connectionTestButton) {
			String url = uploadURLTextField.getText();
			JSON jsonCross = new JSON(url + "/receiveJSON.php");
			try {
				Boolean testConnection = jsonCross.testConnection();
				if (testConnection == true) {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.1"));
				} else {
					JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.2"));
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(mainControl, Messages.getString("JSONConfigControl.2"));
			}
		}
		if (e.getSource() == cancelButton) {
			jsonView.getJsonDialog().dispose();

		}

	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUploadURL() {
		return uploadURL;
	}

	public void setUploadURL(String uploadURL) {
		this.uploadURL = uploadURL;
	}

	public FrontendSidePanelControl getSidePanel() {
		return sidePanel;
	}

	public void setSidePanel(FrontendSidePanelControl sidePanel) {
		this.sidePanel = sidePanel;
	}

}
