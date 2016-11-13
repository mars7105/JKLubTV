package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.turnierverwaltung.model.JSON;
import de.turnierverwaltung.view.FrontendSidePanelView;
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
		menuName = "Menu";
		uploadURL = "http://example.com";

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

			try {
				json.uploadJSONFile(uploadURL, menuName);
				JOptionPane.showMessageDialog(mainControl, "Daten hochgeladen!");
			} catch (IOException exc) {
				JOptionPane.showMessageDialog(mainControl, Messages.getString("NaviController.32"));
			}

		}
		if (e.getSource() == sidePanelsButton) {
			FrontendSidePanelView sidePanel = new FrontendSidePanelView();
			sidePanel.makeDialog();
		}
		if (e.getSource() == connectionTestButton) {
			String url = uploadURLTextField.getText();
			JSON jsonCross = new JSON(url + "/receiveJSON.php");
			try {
				Boolean testConnection = jsonCross.testConnection();
				if (testConnection == true) {
					JOptionPane.showMessageDialog(mainControl, "Test ok!");
				} else {
					JOptionPane.showMessageDialog(mainControl, "Verbindungsfehler! Falsche URL?");
				}
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(mainControl, "Verbindungsfehler! Falsche URL?");
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

}
