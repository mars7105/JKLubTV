package de.turnierverwaltung.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import de.turnierverwaltung.view.JSONConfigView;

public class JSONConfigControl implements ActionListener {
	private JSONConfigView jsonView;
	private JButton okButton;
	private JButton cancelButton;
	private JButton sidePanelsButton;
	private String menuName;
	private JTextField menuNameTextField;
	private String tournamentName;
	private JTextField tournamentNameTextField;
	private String uploadURL;
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
		tournamentNameTextField = jsonView.getTournamentNameTextField();
		uploadURLTextField = jsonView.getUploadURLTextField();

		menuName = "Menu";
		tournamentName = this.mainControl.getTurnier().getTurnierName();
		uploadURL = "http://example.com/";

		menuNameTextField.setText(menuName);
		tournamentNameTextField.setText(tournamentName);
		uploadURLTextField.setText(uploadURL);

	}

	public void makeDialog() {
		jsonView.makePanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			jsonView.getJsonDialog().dispose();

		}

	}

}
